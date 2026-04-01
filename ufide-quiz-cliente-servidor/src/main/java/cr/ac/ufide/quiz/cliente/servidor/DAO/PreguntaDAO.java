/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.DAO;

import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author John
 */
public class PreguntaDAO {

    public List<Pregunta> obtenerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();

        String sql = "SELECT p.id_pregunta, p.enunciado, o.id_opcion, o.texto, o.es_correcta " +
                     "FROM pregunta p " +
                     "INNER JOIN opcion o ON p.id_pregunta = o.id_pregunta " +
                     "ORDER BY p.id_pregunta, o.id_opcion";

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            int idPreguntaActual = -1;
            String enunciadoActual = "";
            List<Opcion> opcionesActuales = new ArrayList<>();

            while (rs.next()) {
                int idPregunta = rs.getInt("id_pregunta");

                if (idPreguntaActual != -1 && idPreguntaActual != idPregunta) {
                    preguntas.add(new Pregunta(idPreguntaActual, enunciadoActual, opcionesActuales));
                    opcionesActuales = new ArrayList<>();
                }

                if (idPreguntaActual != idPregunta) {
                    idPreguntaActual = idPregunta;
                    enunciadoActual = rs.getString("enunciado");
                }

                Opcion opcion = new Opcion(
                        rs.getInt("id_opcion"),
                        rs.getString("texto"),
                        rs.getBoolean("es_correcta")
                );

                opcionesActuales.add(opcion);
            }

            if (idPreguntaActual != -1) {
                preguntas.add(new Pregunta(idPreguntaActual, enunciadoActual, opcionesActuales));
            }

        } catch (Exception e) {
            System.out.println("No se pudieron cargar preguntas desde BD: " + e.getMessage());
            return obtenerPreguntasPrueba();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (preguntas.isEmpty()) {
            return obtenerPreguntasPrueba();
        }

        return preguntas;
    }

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