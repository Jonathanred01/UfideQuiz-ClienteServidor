/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CrownClown
 */

public class Partida {

    private List<Jugador> jugadores;
    private List<Pregunta> preguntas;
    private int turnoActual;
    private boolean enCurso;

    public Partida(List<Jugador> jugadores, List<Pregunta> preguntas) {
        this.jugadores = new ArrayList<>(jugadores); // copia de la lista
        this.preguntas = preguntas;
        this.turnoActual = 0;
        this.enCurso = true;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public boolean estaEnCurso() {
        return enCurso;
    }

    public void finalizarPartida() {
        enCurso = false;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}

// Clase que representa una partida de quiz, maneja los jugadores, las preguntas,
// el turno actual y el estado de la partida (en curso o finalizada)