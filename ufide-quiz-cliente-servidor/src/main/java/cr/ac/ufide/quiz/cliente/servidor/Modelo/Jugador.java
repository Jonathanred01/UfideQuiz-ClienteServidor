/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Modelo;

/**
 *
 * @author CrownClown
 */

public class Jugador {

    private int id;
    private String nombre;
    private int puntaje;
    private boolean listo;
    private boolean respondioPreguntaActual;

    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = 0;
        this.listo = false;
        this.respondioPreguntaActual = false;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean isListo() {
        return listo;
    }

    public boolean isRespondioPreguntaActual() {
        return respondioPreguntaActual;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public void setRespondioPreguntaActual(boolean respondioPreguntaActual) {
        this.respondioPreguntaActual = respondioPreguntaActual;
    }

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntaje += puntos;
        }
    }
}


//id: identifica de forma única al jugador. No debería cambiar una vez creado.
//nombre: representa el nombre del jugador. Es un dato descriptivo y puede modificarse si es necesario.
//puntaje: almacena los puntos acumulados durante la partida. No se modifica directamente, solo a través del método agregarPuntos() para mantener la lógica del juego.
//constructor: se encarga de crear el jugador con id y nombre, e inicializa el puntaje en 0.
//agregarPuntos(): suma puntos al jugador. Controla que no se agreguen valores negativos.
//getters: permiten consultar los datos del jugador sin modificarlos.