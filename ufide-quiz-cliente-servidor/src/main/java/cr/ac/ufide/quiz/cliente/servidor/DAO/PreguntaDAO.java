/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.DAO;

import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author John
 */
public class PreguntaDAO {

    public List<Pregunta> obtenerPreguntasPrueba() {
        List<Pregunta> preguntas = new ArrayList<>();

        List<Opcion> opciones1 = new ArrayList<>();
        opciones1.add(new Opcion(1, "San Jose", true));
        opciones1.add(new Opcion(2, "Cartago", false));
        opciones1.add(new Opcion(3, "Heredia", false));
        opciones1.add(new Opcion(4, "Alajuela", false));
        preguntas.add(new Pregunta(1, "Cual es la capital de Costa Rica", opciones1));

        List<Opcion> opciones2 = new ArrayList<>();
        opciones2.add(new Opcion(1, "4", false));
        opciones2.add(new Opcion(2, "5", true));
        opciones2.add(new Opcion(3, "6", false));
        opciones2.add(new Opcion(4, "3", false));
        preguntas.add(new Pregunta(2, "Cuanto es 2 + 3", opciones2));

        List<Opcion> opciones3 = new ArrayList<>();
        opciones3.add(new Opcion(1, "Java", true));
        opciones3.add(new Opcion(2, "HTML", false));
        opciones3.add(new Opcion(3, "CSS", false));
        opciones3.add(new Opcion(4, "SQL", false));
        preguntas.add(new Pregunta(3, "Cual de estos es un lenguaje de programacion", opciones3));

        return preguntas;
    }
}
