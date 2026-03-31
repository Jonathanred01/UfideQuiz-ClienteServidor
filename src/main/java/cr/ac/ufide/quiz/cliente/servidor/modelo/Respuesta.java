/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.modelo;

/**
 *
 * @author CrownClown
 */

public class Respuesta {

    private Jugador jugador;
    private Pregunta pregunta;
    private Opcion opcionSeleccionada;
    private boolean esCorrecta;

    public Respuesta(Jugador jugador, Pregunta pregunta, Opcion opcionSeleccionada) {
        this.jugador = jugador;
        this.pregunta = pregunta;
        this.opcionSeleccionada = opcionSeleccionada;
        this.esCorrecta = opcionSeleccionada.isEsCorrecta();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public Opcion getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }
}
