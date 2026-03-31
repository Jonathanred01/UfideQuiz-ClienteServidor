/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ferna
 */
public class Servidor {
    
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado correctamente...");
            System.out.println("Escuchando en puerto " + PUERTO);

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                // Crear hilo para cada cliente
                new ManejoDeCliente(cliente).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
