/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.cliente;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author ferna
 */
public class Cliente {
    
    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PUERTO);
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(
                new InputStreamReader(System.in))
        ) {

            System.out.print("Ingrese su nombre:");
            String nombre = teclado.readLine();
            salida.println(nombre);

            // Hilo para escuchar el servidor
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = entrada.readLine()) != null) {
                        System.out.println("Servidor: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Conexión cerrada.");
                }
            }).start();

            // Enviar los mensajes
            String mensaje;
            while ((mensaje = teclado.readLine()) != null) {
                salida.println(mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
