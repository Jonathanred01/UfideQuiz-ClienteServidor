/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ferna
 */
public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/ufidequiz";
    private static final String USER = "root";
    private static final String PASSWORD = "1996";

    public static Connection Conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexion con la Base de Datos:" + e.getMessage());
            return null;
        }
    }
}
