/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.modelo;

import java.util.List;

/**
 *
 * @author CrownClown
 */
public class Pregunta {

    private int id;
    private String texto;
    private List<Opcion> opciones;
    private boolean respondida;

    public Pregunta(int id, String texto, List<Opcion> opciones) {
        this.id = id;
        this.texto = texto;
        this.opciones = opciones;
        this.respondida = false;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void marcarComoRespondida() {
        this.respondida = true;
    }
}

// Donde se maneja las preguntas
