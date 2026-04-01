/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Servidor;

import cr.ac.ufide.quiz.cliente.servidor.Controlador.JuegoControlador;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author John
 */
public class ServidorUfideQuiz {

    private int puerto;
    private JuegoControlador controlador;

    public ServidorUfideQuiz(int puerto) {
        this.puerto = puerto;
        this.controlador = new JuegoControlador();
    }

    public void iniciar() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor UfideQuiz iniciado en el puerto " + puerto);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Nuevo cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());
                ClienteHandler cliente = new ClienteHandler(socketCliente, controlador);
                cliente.start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el servidor: " + e.getMessage());
            }
        }
    }
}
