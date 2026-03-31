/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cr.ac.ufide.quiz.cliente.servidor.main;

import cr.ac.ufide.quiz.cliente.servidor.dao.JugadorDAO;
import cr.ac.ufide.quiz.cliente.servidor.modelo.Jugador;

/**
 *
 * @author CrownClown
 */
public class UfideQuizClienteServidor {

    public static void main(String[] args) {
        
    JugadorDAO dao = new JugadorDAO();

    dao.insertar(new Jugador(0, "Fernando", 0));

    dao.listar().forEach(j -> 
        System.out.println(j.getNombre())
    );
    }
}
