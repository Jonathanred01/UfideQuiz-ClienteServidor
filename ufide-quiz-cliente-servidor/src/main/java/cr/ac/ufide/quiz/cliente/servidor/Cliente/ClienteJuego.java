/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Cliente;
import cr.ac.ufide.quiz.cliente.servidor.Util.Protocolo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;
/**
 *
 * @author John
 */
// Esta clase maneja toda la conexion del cliente con el servidor.
// Se encarga de conectarse, enviar mensajes, recibir respuestas
// y avisarle a la interfaz lo que va pasando en el juego.
public class ClienteJuego extends Thread {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean activo;
    private EscuchadorCliente escuchador;

    // Guarda el escuchador para mandar avisos a la interfaz
    public void setEscuchador(EscuchadorCliente escuchador) {
        this.escuchador = escuchador;
    }

    // Conecta el cliente al servidor e inicia el hilo de escucha
    public void conectar(String ip, int puerto, String nickname) throws Exception {
        socket = new Socket(ip, puerto);
        entrada = new DataInputStream(socket.getInputStream());
        salida = new DataOutputStream(socket.getOutputStream());
        activo = true;
        start();
        enviarMensaje(Protocolo.CONECTAR + "|" + nickname);
    }

    @Override
    public void run() {
        try {
            // Se queda escuchando mensajes del servidor mientras este activo
            while (activo) {
                String mensaje = entrada.readUTF();
                procesarMensaje(mensaje);
            }
        } catch (Exception e) {
            notificarDesconexion("Conexion cerrada");
        } finally {
            cerrarRecursos();
        }
    }

    // Aqui se revisa que tipo de mensaje llego y que accion hacer
    private void procesarMensaje(String mensaje) {
        String[] partes = mensaje.split("\\|", 4);
        String comando = partes[0];

        if (Protocolo.OK.equals(comando)) {
            notificarConectado(partes.length > 1 ? partes[1] : "Conectado");
        } else if (Protocolo.ERROR.equals(comando)) {
            notificarError(partes.length > 1 ? partes[1] : "Error");
        } else if (Protocolo.MENSAJE.equals(comando)) {
            notificarMensaje(partes.length > 1 ? partes[1] : "");
        } else if (Protocolo.JUGADORES.equals(comando)) {
            notificarJugadores(partes.length > 1 ? partes[1] : "");
        } else if (Protocolo.PREGUNTA.equals(comando)) {
            String idPregunta = partes.length > 1 ? partes[1] : "";
            String enunciado = partes.length > 2 ? partes[2] : "";
            String opciones = partes.length > 3 ? partes[3] : "";
            notificarPregunta(idPregunta, enunciado, opciones);
        } else if (Protocolo.RESPUESTA_RESULTADO.equals(comando)) {
            String resultado = partes.length > 1 ? partes[1] : "";
            int puntos = 0;

            if (partes.length > 2) {
                try {
                    puntos = Integer.parseInt(partes[2]);
                } catch (Exception e) {
                    puntos = 0;
                }
            }

            notificarResultadoRespuesta(resultado, puntos);
        } else if (Protocolo.PUNTAJES.equals(comando)) {
            notificarPuntajes(partes.length > 1 ? partes[1] : "");
        } else if (Protocolo.GANADOR.equals(comando)) {
            String nombre = partes.length > 1 ? partes[1] : "SIN_GANADOR";
            int puntaje = 0;

            if (partes.length > 2) {
                try {
                    puntaje = Integer.parseInt(partes[2]);
                } catch (Exception e) {
                    puntaje = 0;
                }
            }

            notificarGanador(nombre, puntaje);
        }
    }

    // Envia al servidor que el jugador ya esta listo
    public void enviarListo() {
        enviarMensaje(Protocolo.LISTO);
    }

    // Envia la respuesta elegida por el jugador
    public void enviarRespuesta(int idOpcion) {
        enviarMensaje(Protocolo.RESPUESTA + "|" + idOpcion);
    }

    // Cierra la conexion del cliente
    public void salir() {
        activo = false;
        enviarMensaje(Protocolo.SALIR);
        cerrarRecursos();
    }

    // Metodo general para enviar mensajes al servidor
    private void enviarMensaje(String mensaje) {
        try {
            if (salida != null) {
                salida.writeUTF(mensaje);
                salida.flush();
            }
        } catch (Exception e) {
            notificarError("No se pudo enviar el mensaje");
        }
    }

    // Cierra los flujos y el socket para liberar recursos
    private void cerrarRecursos() {
        try {
            if (entrada != null) {
                entrada.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (salida != null) {
                salida.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Estos metodos mandan avisos a la interfaz para actualizar
    // mensajes, preguntas, puntajes o estado de la conexion
    private void notificarConectado(final String mensaje) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alConectado(mensaje);
                }
            });
        }
    }

    private void notificarError(final String mensaje) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alError(mensaje);
                }
            });
        }
    }

    private void notificarMensaje(final String mensaje) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alMensaje(mensaje);
                }
            });
        }
    }

    private void notificarJugadores(final String datos) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alJugadores(datos);
                }
            });
        }
    }

    private void notificarPregunta(final String idPregunta, final String enunciado, final String opciones) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alPregunta(idPregunta, enunciado, opciones);
                }
            });
        }
    }

    private void notificarResultadoRespuesta(final String resultado, final int puntos) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alResultadoRespuesta(resultado, puntos);
                }
            });
        }
    }

    private void notificarPuntajes(final String datos) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alPuntajes(datos);
                }
            });
        }
    }

    private void notificarGanador(final String nombreGanador, final int puntaje) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alGanador(nombreGanador, puntaje);
                }
            });
        }
    }

    private void notificarDesconexion(final String mensaje) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    escuchador.alDesconectado(mensaje);
                }
            });
        }
    }
}
