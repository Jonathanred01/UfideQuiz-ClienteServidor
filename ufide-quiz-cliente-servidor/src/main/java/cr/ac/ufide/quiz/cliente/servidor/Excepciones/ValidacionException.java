package cr.ac.ufide.quiz.cliente.servidor.Excepciones;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author John
 */
// Esta excepcion se usa para mostrar errores de validacion en el sistema
public class ValidacionException extends Exception {

    // Recibe el mensaje de error y lo envia a la clase padre
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}