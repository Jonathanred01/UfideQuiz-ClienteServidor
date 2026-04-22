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
// Esta clase controla toda la logica principal del juego.
// Aqui se maneja la sala, los jugadores, el inicio de la partida,
// las respuestas, los puntajes y el ganador final.
public class JuegoControlador {

    private Sala sala;
    private Partida partida;
    private PreguntaDAO preguntaDAO;
    private List<ClienteHandler> clientes;
    private int consecutivoJugador;

    // Inicializa la sala y las listas necesarias del juego
    public JuegoControlador() {
        this.sala = new Sala(1, "Sala principal", 10);
        this.preguntaDAO = new PreguntaDAO();
        this.clientes = new ArrayList<>();
        this.consecutivoJugador = 1;
    }

    // Conecta un jugador nuevo validando nombre y espacio en la sala
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

    // Quita al jugador de la sala cuando se desconecta
    public synchronized void desconectarJugador(ClienteHandler cliente) {
        Jugador jugador = cliente.getJugador();

        if (jugador != null) {
            sala.quitarJugador(jugador);
            enviarMensajeATodos(Protocolo.MENSAJE + "|Se desconecto " + jugador.getNombre());
        }

        clientes.remove(cliente);
        enviarJugadoresATodos();
    }

    // Marca a un jugador como listo y revisa si ya puede iniciar la partida
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

    // Inicia la partida cargando preguntas desde la base de datos
    public synchronized void iniciarPartida() {
        try {
            // Carga preguntas aleatorias desde MySQL
            List<Pregunta> preguntas = preguntaDAO.obtenerPreguntasAleatorias(5);

            // Valida que existan preguntas
            if (preguntas.isEmpty()) {
                enviarMensajeATodos(Protocolo.ERROR + "|No hay preguntas registradas en la base de datos");
                return;
            }

            // Crea la partida con las preguntas obtenidas
            partida = new Partida(preguntas);
            partida.iniciar();

            // Reinicia el estado de respuestas
            reiniciarEstadoRespuestasJugadores();

            // Notifica a todos los clientes
            enviarMensajeATodos(Protocolo.MENSAJE + "|La partida ha iniciado");
            enviarPreguntaActualATodos();

        } catch (RuntimeException e) {
            System.out.println("Error al iniciar partida: " + e.getMessage());
            enviarMensajeATodos(Protocolo.ERROR + "|No se pudieron cargar las preguntas desde MySQL");
        }
    }

    // Procesa la respuesta del jugador y suma puntos si es correcta
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

        // Si todos respondieron, se actualiza el juego
        if (todosRespondieron()) {
            enviarPuntajesATodos();
            avanzarFlujoJuego();
        }
    }

    // Pasa a la siguiente pregunta o finaliza la partida
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

    // Finaliza la partida y manda el ganador
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
    
    // Busca al jugador con mayor puntaje
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

    // Revisa si todos los jugadores ya respondieron
    private synchronized boolean todosRespondieron() {
        for (Jugador jugador : sala.getJugadores()) {
            if (!jugador.isRespondioPreguntaActual()) {
                return false;
            }
        }
        return !sala.getJugadores().isEmpty();
    }

    // Reinicia el estado de respuesta para una nueva ronda
    private synchronized void reiniciarEstadoRespuestasJugadores() {
        for (Jugador jugador : sala.getJugadores()) {
            jugador.setRespondioPreguntaActual(false);
        }
    }

    // Valida que el nickname tenga formato correcto
    private void validarNickname(String nombre) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Debe ingresar un nickname");
        }

        if (nombre.trim().length() < 3 || nombre.trim().length() > 15) {
            throw new ValidacionException("El nickname debe tener entre 3 y 15 caracteres");
        }
    }

    // Envia a todos la lista de jugadores actualizada
    public synchronized void enviarJugadoresATodos() {
        enviarMensajeATodos(Protocolo.construirMensajeJugadores(sala.getJugadores()));
    }

    // Envia la pregunta actual a todos los jugadores
    public synchronized void enviarPreguntaActualATodos() {
        if (partida == null) {
            return;
        }

        Pregunta preguntaActual = partida.getPreguntaActual();
        if (preguntaActual != null) {
            enviarMensajeATodos(Protocolo.construirMensajePregunta(preguntaActual));
        }
    }

    // Envia a todos los puntajes ordenados
    public synchronized void enviarPuntajesATodos() {
        ordenarJugadoresPorPuntaje();
        enviarMensajeATodos(Protocolo.construirMensajePuntajes(sala.getJugadores()));
    }

    // Ordena los jugadores de mayor a menor puntaje
    private synchronized void ordenarJugadoresPorPuntaje() {
        Collections.sort(sala.getJugadores(), new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return Integer.compare(o2.getPuntaje(), o1.getPuntaje());
            }
        });
        ordenarClientes();
    }

    // Ordena la lista de clientes por nombre
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

    // Envia un mensaje general a todos los clientes conectados
    public synchronized void enviarMensajeATodos(String mensaje) {
        List<ClienteHandler> copia = new ArrayList<>(clientes);
        for (ClienteHandler cliente : copia) {
            cliente.enviarMensaje(mensaje);
        }
    }

    // Busca el cliente asociado a un jugador
    public synchronized ClienteHandler buscarClientePorJugador(Jugador jugador) {
        for (ClienteHandler cliente : clientes) {
            if (cliente.getJugador() != null && cliente.getJugador().getId() == jugador.getId()) {
                return cliente;
            }
        }
        return null;
    }
}