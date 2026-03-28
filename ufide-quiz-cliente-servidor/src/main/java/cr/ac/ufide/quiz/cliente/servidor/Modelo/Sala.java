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
    private List<Jugador> jugadores;
    private EstadoSala estado;
    private int capacidadMaxima;

    public Sala(int id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.estado = EstadoSala.ESPERANDO;
        this.capacidadMaxima = 2;
    }

    public void agregarJugador(Jugador jugador) {
        if (jugadores.size() >= capacidadMaxima) {
            throw new IllegalStateException("Sala llena");
        }
        jugadores.add(jugador);
    }

    public boolean puedeIniciar() {
        return jugadores.size() >= 2;
    }

    public int getId() {
        return id;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public enum EstadoSala {
        ESPERANDO,
        INICIADA,
        TERMINADA
    }
}


//Lobby donde estan los jugadores