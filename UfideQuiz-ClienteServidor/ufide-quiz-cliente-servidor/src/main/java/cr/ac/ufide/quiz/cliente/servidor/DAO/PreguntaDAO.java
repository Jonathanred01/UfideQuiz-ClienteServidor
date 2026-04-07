/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.DAO;

import cr.ac.ufide.quiz.cliente.servidor.Modelo.Opcion;
import cr.ac.ufide.quiz.cliente.servidor.Modelo.Pregunta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CrownClown
 */
public class PreguntaDAO {

    public List<Pregunta> obtenerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();

        String sqlPreguntas = "SELECT id_pregunta, texto FROM preguntas ORDER BY id_pregunta";
        String sqlOpciones = "SELECT id_opcion, texto, es_correcta FROM opciones WHERE id_pregunta = ? ORDER BY id_opcion";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement psPreguntas = conn.prepareStatement(sqlPreguntas); ResultSet rsPreguntas = psPreguntas.executeQuery()) {

            while (rsPreguntas.next()) {
                int idPregunta = rsPreguntas.getInt("id_pregunta");
                String textoPregunta = rsPreguntas.getString("texto");

                List<Opcion> opciones = new ArrayList<>();

                try (PreparedStatement psOpciones = conn.prepareStatement(sqlOpciones)) {
                    psOpciones.setInt(1, idPregunta);

                    try (ResultSet rsOpciones = psOpciones.executeQuery()) {
                        while (rsOpciones.next()) {
                            Opcion opcion = new Opcion(
                                    rsOpciones.getInt("id_opcion"),
                                    rsOpciones.getString("texto"),
                                    rsOpciones.getBoolean("es_correcta")
                            );
                            opciones.add(opcion);
                        }
                    }
                }

                preguntas.add(new Pregunta(idPregunta, textoPregunta, opciones));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preguntas;
    }
}
