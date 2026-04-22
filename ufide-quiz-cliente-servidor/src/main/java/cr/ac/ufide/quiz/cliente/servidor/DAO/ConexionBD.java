/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author John
 */
public class ConexionBD {

    // URL de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/ufidequiz?serverTimezone=UTC";
    
    // Usuario de MySQL
    private static final String USUARIO = "root";
    
    // Contrasena de MySQL
    private static final String CLAVE = "";

    static {
        try {
            // Carga el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontro el driver de MySQL", e);
        }
    }

    private ConexionBD() {
        
    }

    public static Connection obtenerConexion() throws SQLException {
        // Retorna la conexion activa
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}