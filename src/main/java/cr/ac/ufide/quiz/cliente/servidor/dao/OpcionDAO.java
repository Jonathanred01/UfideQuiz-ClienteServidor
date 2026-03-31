/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.dao;

import cr.ac.ufide.quiz.cliente.servidor.conexion.ConexionBD;
import cr.ac.ufide.quiz.cliente.servidor.modelo.Opcion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
public class OpcionDAO {
    
    public void insertar(Opcion o) {
        String sql = "INSERT INTO opcion (texto, es_correcta) VALUES (?, ?)";

        try (Connection conn = ConexionBD.Conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getTexto());
            stmt.setBoolean(2, o.isEsCorrecta());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al insertar opcion: " + e.getMessage());
        }
    }

    public List<Opcion> listarPorPregunta(int idPregunta) {
        List<Opcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM opcion WHERE id_pregunta = ?";

        try (Connection conn = ConexionBD.Conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPregunta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Opcion o = new Opcion(
                        rs.getInt("id"),
                        rs.getString("texto"),
                        rs.getBoolean("Es_correcta")
                );
                
                lista.add(o);
            }
        } catch (Exception e) {
            System.out.println("Error al listar opciones: " + e.getMessage());
        }
        return lista;
    }
}
