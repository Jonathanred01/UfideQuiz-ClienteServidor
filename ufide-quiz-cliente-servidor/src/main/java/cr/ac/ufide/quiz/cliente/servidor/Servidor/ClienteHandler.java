/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Servidor;

import cr.ac.ufide.quiz.cliente.servidor.Controlador.JuegoControlador;
import cr.ac.ufide.quiz.cliente.servidor.Excepciones.ValidacionException;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Jugador;
import cr.ac.ufide.quiz.cliente.servidor.Util.Protocolo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 *
 * @author John
 */
public class ClienteHandler extends Thread {

    private Socket socket;
    private JuegoControlador controlador;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Jugador jugador;
    private boolean activo;

    public ClienteHandler(Socket socket, JuegoControlador controlador) {
        this.socket = socket;
        this.controlador = controlador;
        this.activo = true;
    }

    @Override
    public void run() {
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            enviarMensaje(Protocolo.MENSAJE + "|Conexion establecida con el servidor");

            while (activo) {
                String mensaje = entrada.readUTF();
                procesarMensaje(mensaje);
            }
        } catch (IOException e) {
            enviarMensajeSilencioso(Protocolo.ERROR + "|Se perdio la conexion con el cliente");
        } finally {
            cerrarConexion();
        }
    }

    private void procesarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            enviarMensaje(Protocolo.ERROR + "|Mensaje vacio");
            return;
        }

        String[] partes = mensaje.split(Protocolo.SEPARADOR_PRINCIPAL);
        String comando = partes[0];

        try {
            if (Protocolo.CONECTAR.equalsIgnoreCase(comando)) {
                procesarConexion(partes);
            } else if (Protocolo.LISTO.equalsIgnoreCase(comando)) {
                controlador.marcarListo(jugador);
            } else if (Protocolo.RESPUESTA.equalsIgnoreCase(comando)) {
                procesarRespuesta(partes);
            } else if (Protocolo.SALIR.equalsIgnoreCase(comando)) {
                activo = false;
            } else {
                enviarMensaje(Protocolo.ERROR + "|Comando no reconocido");
            }
        } catch (ValidacionException e) {
            enviarMensaje(Protocolo.ERROR + "|" + e.getMessage());
        }
    }

    private void procesarConexion(String[] partes) throws ValidacionException {
        if (partes.length < 2) {
            throw new ValidacionException("Debe enviar el nickname");
        }

        if (jugador != null) {
            throw new ValidacionException("El cliente ya se encuentra conectado");
        }

        String nickname = partes[1];
        jugador = controlador.conectarJugador(nickname, this);
        enviarMensaje(Protocolo.OK + "|Bienvenido " + jugador.getNombre());
    }

    private void procesarRespuesta(String[] partes) throws ValidacionException {
        if (jugador == null) {
            throw new ValidacionException("Primero debe conectarse a la sala");
        }

        if (partes.length < 2) {
            throw new ValidacionException("Debe enviar el id de la opcion");
        }

        try {
            int idOpcion = Integer.parseInt(partes[1]);
            controlador.procesarRespuesta(jugador, idOpcion);
        } catch (NumberFormatException e) {
            throw new ValidacionException("El id de la opcion debe ser numerico");
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            if (salida != null) {
                salida.writeUTF(mensaje);
                salida.flush();
            }
        } catch (IOException e) {
            activo = false;
        }
    }

    private void enviarMensajeSilencioso(String mensaje) {
        try {
            if (salida != null) {
                salida.writeUTF(mensaje);
                salida.flush();
            }
        } catch (IOException e) {
            activo = false;
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    private void cerrarConexion() {
        activo = false;
        controlador.desconectarJugador(this);

        try {
            if (entrada != null) {
                entrada.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (salida != null) {
                salida.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
