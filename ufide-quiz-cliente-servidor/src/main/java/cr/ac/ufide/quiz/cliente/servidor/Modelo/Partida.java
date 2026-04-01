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

    private List<Pregunta> preguntas;
    private int indicePreguntaActual;
    private boolean iniciada;
    private boolean finalizada;
    private List<Respuesta> respuestas;

    public Partida(List<Pregunta> preguntas) {
        this.preguntas = new ArrayList<>(preguntas);
        this.indicePreguntaActual = 0;
        this.iniciada = false;
        this.finalizada = false;
        this.respuestas = new ArrayList<>();
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public int getIndicePreguntaActual() {
        return indicePreguntaActual;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void iniciar() {
        this.iniciada = true;
        this.finalizada = false;
    }

    public void finalizar() {
        this.finalizada = true;
    }

    public Pregunta getPreguntaActual() {
        if (indicePreguntaActual >= 0 && indicePreguntaActual < preguntas.size()) {
            return preguntas.get(indicePreguntaActual);
        }
        return null;
    }

    public void agregarRespuesta(Respuesta respuesta) {
        respuestas.add(respuesta);
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void limpiarRespuestasRonda() {
        respuestas.clear();
    }

    public boolean avanzarPregunta() {
        indicePreguntaActual++;
        return indicePreguntaActual < preguntas.size();
    }
}

// Clase que representa una partida de quiz, maneja los jugadores, las preguntas,
// el turno actual y el estado de la partida (en curso o finalizada)