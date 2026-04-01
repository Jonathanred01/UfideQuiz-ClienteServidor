/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Modelo;

import java.util.List;

/**
 *
 * @author CrownClown
 */
public class Pregunta {

    private int id;
    private String enunciado;
    private List<Opcion> opciones;

    public Pregunta(int id, String enunciado, List<Opcion> opciones) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = opciones;
    }

    public int getId() {
        return id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public Opcion buscarOpcionPorId(int idOpcion) {
        for (Opcion opcion : opciones) {
            if (opcion.getId() == idOpcion) {
                return opcion;
            }
        }
        return null;
    }

    public Opcion getOpcionCorrecta() {
        for (Opcion opcion : opciones) {
            if (opcion.isCorrecta()) {
                return opcion;
            }
        }
        return null;
    }
}

// Donde se maneja las preguntas
