/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Modelo;

/**
 *
 * @author CrownClown
 */

public class Opcion {

    private int id;
    private String texto;
    private boolean correcta;

    public Opcion(int id, String texto, boolean correcta) {
        this.id = id;
        this.texto = texto;
        this.correcta = correcta;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isCorrecta() {
        return correcta;
    }
}

// Clase que representa una opcion de respuesta en el juego
// Cada opcion tiene un identificador el texto es la respuesta
// y un indicador de si es la respuesta correcta o no