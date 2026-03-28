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

    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = 0;
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

    public void agregarPuntos(int puntos) {
        if (puntos < 0) {
            return; // ignora valores negativos
        }
        this.puntaje += puntos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


//id: identifica de forma única al jugador. No debería cambiar una vez creado.
//nombre: representa el nombre del jugador. Es un dato descriptivo y puede modificarse si es necesario.
//puntaje: almacena los puntos acumulados durante la partida. No se modifica directamente, solo a través del método agregarPuntos() para mantener la lógica del juego.
//constructor: se encarga de crear el jugador con id y nombre, e inicializa el puntaje en 0.
//agregarPuntos(): suma puntos al jugador. Controla que no se agreguen valores negativos.
//getters: permiten consultar los datos del jugador sin modificarlos.