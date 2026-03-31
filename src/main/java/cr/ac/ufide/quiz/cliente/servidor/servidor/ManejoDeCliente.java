/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.servidor;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author ferna
 */
public class ManejoDeCliente extends Thread {
    
    private Socket socket;
    private PrintWriter salida;
    private String nombre;

    public ManejoDeCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ){
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Agregar cliente a la lista
            SalaServidor.clientes.add(salida);

            // Primer mensaje es = nombre
            nombre = entrada.readLine();
            SalaServidor.agregarJugador(nombre);
            SalaServidor.setNoListo(nombre);
            System.out.println(nombre + " se ha unido.");

            // Enviar la lista actualizada
            SalaServidor.enviarATodos("LISTA DE JUGADORES:" + SalaServidor.obtenerLista());
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                
                if (mensaje.equalsIgnoreCase("LISTO")) {
                    SalaServidor.setListo(nombre);

                    System.out.println(nombre + " está listo");

            // Enviar los estados a todos
                    SalaServidor.enviarATodos("ESTADOS:" + SalaServidor.obtenerEstados());

            // Verificar si todos están listos
                    if (SalaServidor.todosListos()) {
                        SalaServidor.enviarATodos("INICIAR");
                        System.out.println("Todos listos. Iniciando la partida...");
                    }

                } else {
                    System.out.println(nombre + ": " + mensaje);
                }
            }

        } catch (IOException e) {
            System.out.println("El Cliente:" + nombre + "se ha desconectado");
        } finally {
            if (nombre != null) {
                SalaServidor.eliminarJugador(nombre);
               
                SalaServidor.enviarATodos("LISTA:" + SalaServidor.obtenerLista());
                SalaServidor.enviarATodos("ESTADOS:" + SalaServidor.obtenerEstados());
            }
        }
    }
}
