/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.dao;

import cr.ac.ufide.quiz.cliente.servidor.conexion.ConexionBD;
import cr.ac.ufide.quiz.cliente.servidor.modelo.Jugador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
public class JugadorDAO {
    
    public void insertar(Jugador jugador) {
        String sql = "INSERT INTO jugador (nombre, puntaje) VALUES (?, ?)";

        try (Connection conn = ConexionBD.Conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jugador.getNombre());
            stmt.setInt(2, jugador.getPuntaje());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    public List<Jugador> listar() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT * FROM jugador";

        try (Connection conn = ConexionBD.Conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                Jugador j = new Jugador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("puntaje")
                );
                lista.add(j);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}
