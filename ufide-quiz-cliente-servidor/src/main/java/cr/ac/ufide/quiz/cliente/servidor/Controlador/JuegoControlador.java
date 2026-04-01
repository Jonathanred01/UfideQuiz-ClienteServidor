/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package cr.ac.ufide.quiz.cliente.servidor.Controlador;

import cr.ac.ufide.quiz.cliente.servidor.DAO.PreguntaDAO;
import cr.ac.ufide.quiz.cliente.servidor.Excepciones.ValidacionException;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Jugador;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Partida;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Respuesta;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Sala;
import cr.ac.ufide.quiz.cliente.servidor.Servidor.ClienteHandler;
import cr.ac.ufide.quiz.cliente.servidor.Util.Protocolo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author John
 */
public class JuegoControlador {

    private Sala sala;
    private Partida partida;
    private PreguntaDAO preguntaDAO;
    private List<ClienteHandler> clientes;
    private int consecutivoJugador;

    public JuegoControlador() {
        this.sala = new Sala(1, "Sala principal", 10);
        this.preguntaDAO = new PreguntaDAO();
        this.clientes = new ArrayList<>();
        this.consecutivoJugador = 1;
    }

    public synchronized Jugador conectarJugador(String nombre, ClienteHandler cliente) throws ValidacionException {
        validarNickname(nombre);

        Jugador existente = sala.buscarJugadorPorNombre(nombre);
        if (existente != null) {
            throw new ValidacionException("El nickname ya existe en la sala");
        }

        if (sala.estaLlena()) {
            throw new ValidacionException("La sala esta llena");
        }

        Jugador jugador = new Jugador(consecutivoJugador, nombre);
        consecutivoJugador++;
        sala.agregarJugador(jugador);
        clientes.add(cliente);
        ordenarClientes();
        enviarMensajeATodos(Protocolo.MENSAJE + "|Se conecto " + jugador.getNombre());
        enviarJugadoresATodos();
        return jugador;
    }

    public synchronized void desconectarJugador(ClienteHandler cliente) {
        Jugador jugador = cliente.getJugador();

        if (jugador != null) {
            sala.quitarJugador(jugador);
            enviarMensajeATodos(Protocolo.MENSAJE + "|Se desconecto " + jugador.getNombre());
        }

        clientes.remove(cliente);
        enviarJugadoresATodos();
    }

    public synchronized void marcarListo(Jugador jugador) {
        if (jugador == null) {
            return;
        }

        jugador.setListo(true);
        enviarJugadoresATodos();
        enviarMensajeATodos(Protocolo.MENSAJE + "|" + jugador.getNombre() + " esta listo");

        if (sala.puedeIniciar() && sala.todosListos() && (partida == null || !partida.isIniciada())) {
            iniciarPartida();
        }
    }

    public synchronized void iniciarPartida() {
        List<Pregunta> preguntas = preguntaDAO.obtenerPreguntas();
        partida = new Partida(preguntas);
        partida.iniciar();
        reiniciarEstadoRespuestasJugadores();
        enviarMensajeATodos(Protocolo.MENSAJE + "|La partida ha iniciado");
        enviarPreguntaActualATodos();
    }

    public synchronized void procesarRespuesta(Jugador jugador, int idOpcion) {
        if (jugador == null) {
            return;
        }

        if (partida == null || !partida.isIniciada() || partida.isFinalizada()) {
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.ERROR + "|No hay una partida activa");
            return;
        }

        if (jugador.isRespondioPreguntaActual()) {
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.ERROR + "|Ya respondiste esta pregunta");
            return;
        }

        Pregunta preguntaActual = partida.getPreguntaActual();
        if (preguntaActual == null) {
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.ERROR + "|No existe pregunta activa");
            return;
        }

        Opcion opcion = preguntaActual.buscarOpcionPorId(idOpcion);
        if (opcion == null) {
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.ERROR + "|La opcion enviada no existe");
            return;
        }

        Respuesta respuesta = new Respuesta(jugador, preguntaActual, opcion);
        partida.agregarRespuesta(respuesta);
        jugador.setRespondioPreguntaActual(true);

        if (respuesta.isCorrecta()) {
            jugador.agregarPuntos(10);
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.RESPUESTA_RESULTADO + "|CORRECTA|10");
        } else {
            buscarClientePorJugador(jugador).enviarMensaje(Protocolo.RESPUESTA_RESULTADO + "|INCORRECTA|0");
        }

        if (todosRespondieron()) {
            enviarPuntajesATodos();
            avanzarFlujoJuego();
        }
    }

    private synchronized void avanzarFlujoJuego() {
        partida.limpiarRespuestasRonda();
        boolean haySiguiente = partida.avanzarPregunta();

        if (haySiguiente) {
            reiniciarEstadoRespuestasJugadores();
            enviarPreguntaActualATodos();
        } else {
            finalizarPartida();
        }
    }

    private synchronized void finalizarPartida() {
        if (partida != null) {
            partida.finalizar();
        }

        enviarPuntajesATodos();
        Jugador ganador = obtenerGanador();

        if (ganador != null) {
            enviarMensajeATodos(Protocolo.GANADOR + "|" + ganador.getNombre() + "|" + ganador.getPuntaje());
        } else {
            enviarMensajeATodos(Protocolo.GANADOR + "|SIN_GANADOR|0");
        }
        System.out.println("Partida finalizada. Cerrando servidor...");
        System.exit(0);
    }
    
    private synchronized Jugador obtenerGanador() {
        if (sala.getJugadores().isEmpty()) {
            return null;
        }

        List<Jugador> jugadoresOrdenados = new ArrayList<>(sala.getJugadores());
        jugadoresOrdenados.sort(new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return Integer.compare(o2.getPuntaje(), o1.getPuntaje());
            }
        });

        return jugadoresOrdenados.get(0);
    }

    private synchronized boolean todosRespondieron() {
        for (Jugador jugador : sala.getJugadores()) {
            if (!jugador.isRespondioPreguntaActual()) {
                return false;
            }
        }
        return !sala.getJugadores().isEmpty();
    }

    private synchronized void reiniciarEstadoRespuestasJugadores() {
        for (Jugador jugador : sala.getJugadores()) {
            jugador.setRespondioPreguntaActual(false);
        }
    }

    private void validarNickname(String nombre) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Debe ingresar un nickname");
        }

        if (nombre.trim().length() < 3 || nombre.trim().length() > 15) {
            throw new ValidacionException("El nickname debe tener entre 3 y 15 caracteres");
        }
    }

    public synchronized void enviarJugadoresATodos() {
        enviarMensajeATodos(Protocolo.construirMensajeJugadores(sala.getJugadores()));
    }

    public synchronized void enviarPreguntaActualATodos() {
        if (partida == null) {
            return;
        }

        Pregunta preguntaActual = partida.getPreguntaActual();
        if (preguntaActual != null) {
            enviarMensajeATodos(Protocolo.construirMensajePregunta(preguntaActual));
        }
    }

    public synchronized void enviarPuntajesATodos() {
        ordenarJugadoresPorPuntaje();
        enviarMensajeATodos(Protocolo.construirMensajePuntajes(sala.getJugadores()));
    }

    private synchronized void ordenarJugadoresPorPuntaje() {
        Collections.sort(sala.getJugadores(), new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return Integer.compare(o2.getPuntaje(), o1.getPuntaje());
            }
        });
        ordenarClientes();
    }

    private synchronized void ordenarClientes() {
        Collections.sort(clientes, new Comparator<ClienteHandler>() {
            @Override
            public int compare(ClienteHandler o1, ClienteHandler o2) {
                String nombre1 = o1.getJugador() == null ? "" : o1.getJugador().getNombre();
                String nombre2 = o2.getJugador() == null ? "" : o2.getJugador().getNombre();
                return nombre1.compareToIgnoreCase(nombre2);
            }
        });
    }

    public synchronized void enviarMensajeATodos(String mensaje) {
        List<ClienteHandler> copia = new ArrayList<>(clientes);
        for (ClienteHandler cliente : copia) {
            cliente.enviarMensaje(mensaje);
        }
    }

    public synchronized ClienteHandler buscarClientePorJugador(Jugador jugador) {
        for (ClienteHandler cliente : clientes) {
            if (cliente.getJugador() != null && cliente.getJugador().getId() == jugador.getId()) {
                return cliente;
            }
        }
        return null;
    }
}