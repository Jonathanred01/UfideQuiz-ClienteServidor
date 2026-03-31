/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.dao;

import cr.ac.ufide.quiz.cliente.servidor.conexion.ConexionBD;
import cr.ac.ufide.quiz.cliente.servidor.modelo.Pregunta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
public class PreguntaDAO {
    
    public void insertar(Pregunta p) {
        String sql = "INSERT INTO pregunta (enunciado) VALUES (?)";

        try (Connection conn = ConexionBD.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
   
            stmt.setString(1, p.getTexto());
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error al insertar pregunta: " + e.getMessage());
        }
    }

    public List<Pregunta> listar() {
        List<Pregunta> lista = new ArrayList<>();
        String sql = "SELECT * FROM pregunta";

        try (Connection conn = ConexionBD.Conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("enunciado");
                Pregunta p = new Pregunta(id, texto, new ArrayList<>());
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar preguntas: " + e.getMessage());
        }

        return lista;
    }
    
}
