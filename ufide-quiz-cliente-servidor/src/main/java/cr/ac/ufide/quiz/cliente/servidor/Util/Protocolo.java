/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Util;

import cr.ac.ufide.quiz.cliente.servidor.Modelo.Jugador;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.util.List;

/**
 *
 * @author John
 */
public class Protocolo {

    public static final String SEPARADOR_PRINCIPAL = "\\|";
    public static final String CONECTAR = "CONECTAR";
    public static final String LISTO = "LISTO";
    public static final String RESPUESTA = "RESPUESTA";
    public static final String SALIR = "SALIR";
    public static final String ERROR = "ERROR";
    public static final String OK = "OK";
    public static final String JUGADORES = "JUGADORES";
    public static final String PREGUNTA = "PREGUNTA";
    public static final String PUNTAJES = "PUNTAJES";
    public static final String GANADOR = "GANADOR";
    public static final String MENSAJE = "MENSAJE";
    public static final String RESPUESTA_RESULTADO = "RESPUESTA_RESULTADO";

    public static String construirMensajeJugadores(List<Jugador> jugadores) {
        StringBuilder sb = new StringBuilder(JUGADORES).append("|");

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            sb.append(jugador.getNombre())
              .append(":")
              .append(jugador.getPuntaje())
              .append(":")
              .append(jugador.isListo() ? "LISTO" : "ESPERANDO");

            if (i < jugadores.size() - 1) {
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static String construirMensajePregunta(Pregunta pregunta) {
        StringBuilder sb = new StringBuilder(PREGUNTA)
                .append("|")
                .append(pregunta.getId())
                .append("|")
                .append(pregunta.getEnunciado())
                .append("|");

        List<Opcion> opciones = pregunta.getOpciones();
        for (int i = 0; i < opciones.size(); i++) {
            Opcion opcion = opciones.get(i);
            sb.append(opcion.getId()).append("~").append(opcion.getTexto());
            if (i < opciones.size() - 1) {
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static String construirMensajePuntajes(List<Jugador> jugadores) {
        StringBuilder sb = new StringBuilder(PUNTAJES).append("|");

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            sb.append(jugador.getNombre()).append(":").append(jugador.getPuntaje());
            if (i < jugadores.size() - 1) {
                sb.append(";");
            }
        }

        return sb.toString();
    }
}
