/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.DAO;

import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.util.ArrayList;
import java.util.List;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author John
 */

public class PreguntaDAO {

    public List<Pregunta> obtenerPreguntas() {
        // Retorna 5 preguntas aleatorias por partida
        return obtenerPreguntasAleatorias(5);
    }

    public List<Pregunta> obtenerPreguntasAleatorias(int cantidad) {
        // Carga todas las preguntas desde MySQL
        List<Pregunta> preguntas = cargarPreguntasDesdeBD();

        // Mezcla el orden de las preguntas
        mezclarPreguntas(preguntas);

        // Mezcla el orden de las opciones de cada pregunta
        for (Pregunta pregunta : preguntas) {
            mezclarOpciones(pregunta.getOpciones());
        }

        // Si hay mas preguntas que la cantidad pedida, corta la lista
        if (cantidad > 0 && cantidad < preguntas.size()) {
            return new ArrayList<>(preguntas.subList(0, cantidad));
        }

        return preguntas;
    }

    private List<Pregunta> cargarPreguntasDesdeBD() {
        List<Pregunta> preguntas = new ArrayList<>();
        Map<Integer, Pregunta> mapaPreguntas = new LinkedHashMap<>();

        String sql = "SELECT p.id_pregunta, p.enunciado, o.id_opcion, o.texto, o.es_correcta "
                + "FROM pregunta p "
                + "INNER JOIN opcion o ON p.id_pregunta = o.id_pregunta "
                + "ORDER BY p.id_pregunta, o.id_opcion";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPregunta = rs.getInt("id_pregunta");
                Pregunta pregunta = mapaPreguntas.get(idPregunta);

                // Si la pregunta aun no existe en el mapa, se crea
                if (pregunta == null) {
                    pregunta = new Pregunta(idPregunta, rs.getString("enunciado"), new ArrayList<>());
                    mapaPreguntas.put(idPregunta, pregunta);
                }

                // Se agrega cada opcion a su pregunta correspondiente
                Opcion opcion = new Opcion(
                        rs.getInt("id_opcion"),
                        rs.getString("texto"),
                        rs.getBoolean("es_correcta")
                );

                pregunta.getOpciones().add(opcion);
            }

            preguntas.addAll(mapaPreguntas.values());
            return preguntas;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar preguntas en MySQL: " + e.getMessage(), e);
        }
    }

    private void mezclarPreguntas(List<Pregunta> preguntas) {
        // Mezcla manual usando Math.random()
        for (int i = preguntas.size() - 1; i > 0; i--) {
            int indiceAleatorio = (int) (Math.random() * (i + 1));

            Pregunta temporal = preguntas.get(i);
            preguntas.set(i, preguntas.get(indiceAleatorio));
            preguntas.set(indiceAleatorio, temporal);
        }
    }

    private void mezclarOpciones(List<Opcion> opciones) {
        // Mezcla manual usando Math.random()
        for (int i = opciones.size() - 1; i > 0; i--) {
            int indiceAleatorio = (int) (Math.random() * (i + 1));

            Opcion temporal = opciones.get(i);
            opciones.set(i, opciones.get(indiceAleatorio));
            opciones.set(indiceAleatorio, temporal);
        }
    }
}