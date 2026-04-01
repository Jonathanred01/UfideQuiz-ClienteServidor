/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cr.ac.ufide.quiz.cliente.servidor;

import cr.ac.ufide.quiz.cliente.servidor.Servidor.ServidorUfideQuiz;

/**
 *
 * @author CrownClown
 */
public class UfideQuizClienteServidor {

    public static void main(String[] args) {
        ServidorUfideQuiz servidor = new ServidorUfideQuiz(6500);
        servidor.iniciar();
    }
}