/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.servidor;

import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author ferna
 */
public class SalaServidor {
    
    // Los Jugadores conectados
    public static Set<String> jugadores = new HashSet<>();

    // Salidas para enviar mensajes a todos
    public static Set<PrintWriter> clientes = new HashSet<>();

    // Agregar jugadores
    public static void agregarJugador(String nombre) {
        jugadores.add(nombre);
    }

    // Eliminar jugadores
    public static void eliminarJugador(String nombre) {
        jugadores.remove(nombre);
    }

    // Broadcast a todos
    public static void enviarATodos(String mensaje) {
        for (PrintWriter out : clientes) {
            out.println(mensaje);
        }
    }

    // Obtener la lista
    public static String obtenerLista() {
        return String.join(",", jugadores);
    }
    
    public static Map<String, Boolean> estados = new HashMap<>();

    public static void setListo(String nombre) {
        estados.put(nombre, true);
    }

    public static void setNoListo(String nombre) {
        estados.put(nombre, false);
    }

    public static boolean todosListos() {
        if (estados.isEmpty()) return false;
        for (boolean listo : estados.values()) {
            if (!listo) return false;
        }
        return true;
    }

    public static String obtenerEstados() {
        
    // Usen este Formato:nombre:true,nombre:false
        StringBuilder sb = new StringBuilder();
        for (String j : estados.keySet()) {
            if (sb.length() > 0) sb.append(",");
            sb.append(j).append(":").append(estados.get(j)).append(",");
        }
        return sb.toString();
    }
}
