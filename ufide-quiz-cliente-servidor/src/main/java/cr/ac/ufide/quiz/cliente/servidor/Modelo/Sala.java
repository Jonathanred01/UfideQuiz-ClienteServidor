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
public class Sala {

    private int id;
    private String nombre;
    private int capacidadMaxima;
    private List<Jugador> jugadores;

    public Sala(int id, String nombre, int capacidadMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.jugadores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public boolean estaLlena() {
        return jugadores.size() >= capacidadMaxima;
    }

    public boolean puedeIniciar() {
        return jugadores.size() >= 2;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void quitarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    public boolean todosListos() {
        if (jugadores.isEmpty()) {
            return false;
        }

        for (Jugador jugador : jugadores) {
            if (!jugador.isListo()) {
                return false;
            }
        }
        return true;
    }
}


//Lobby donde estan los jugadores