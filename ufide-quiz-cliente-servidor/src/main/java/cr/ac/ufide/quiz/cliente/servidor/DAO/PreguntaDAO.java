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

    public List<Pregunta> obtenerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();

        List<Opcion> opciones1 = new ArrayList<>();
        opciones1.add(new Opcion(1, "San Jose", true));
        opciones1.add(new Opcion(2, "Cartago", false));
        opciones1.add(new Opcion(3, "Heredia", false));
        opciones1.add(new Opcion(4, "Alajuela", false));
        preguntas.add(new Pregunta(1, "Cual es la capital de Costa Rica", opciones1));

        List<Opcion> opciones2 = new ArrayList<>();
        opciones2.add(new Opcion(5, "4", false));
        opciones2.add(new Opcion(6, "5", true));
        opciones2.add(new Opcion(7, "6", false));
        opciones2.add(new Opcion(8, "3", false));
        preguntas.add(new Pregunta(2, "Cuanto es 2 + 3", opciones2));

        List<Opcion> opciones3 = new ArrayList<>();
        opciones3.add(new Opcion(9, "Java", true));
        opciones3.add(new Opcion(10, "HTML", false));
        opciones3.add(new Opcion(11, "CSS", false));
        opciones3.add(new Opcion(12, "SQL", false));
        preguntas.add(new Pregunta(3, "Cual de estos es un lenguaje de programacion", opciones3));

        List<Opcion> opciones4 = new ArrayList<>();
        opciones4.add(new Opcion(13, "CPU", false));
        opciones4.add(new Opcion(14, "RAM", true));
        opciones4.add(new Opcion(15, "Monitor", false));
        opciones4.add(new Opcion(16, "Teclado", false));
        preguntas.add(new Pregunta(4, "Cual componente guarda datos de forma temporal", opciones4));

        List<Opcion> opciones5 = new ArrayList<>();
        opciones5.add(new Opcion(17, "199", false));
        opciones5.add(new Opcion(18, "200", false));
        opciones5.add(new Opcion(19, "201", true));
        opciones5.add(new Opcion(20, "202", false));
        preguntas.add(new Pregunta(5, "Cuanto es 100 + 101", opciones5));

        return preguntas;
    }
}