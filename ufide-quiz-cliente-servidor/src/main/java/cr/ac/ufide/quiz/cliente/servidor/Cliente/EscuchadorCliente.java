/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ufide.quiz.cliente.servidor.Cliente;

/**
 *
 * @author John
 */
// Esta interfaz se usa para avisar a la parte visual del cliente
// lo que va pasando en el juego, por ejemplo conexiones,
// errores, mensajes, preguntas, puntajes y ganador final.
public interface EscuchadorCliente {

    void alConectado(String mensaje);

    void alError(String mensaje);

    void alMensaje(String mensaje);

    void alJugadores(String datos);

    void alPregunta(String idPregunta, String enunciado, String opciones);

    void alResultadoRespuesta(String resultado, int puntos);

    void alPuntajes(String datos);

    void alGanador(String nombreGanador, int puntaje);

    void alDesconectado(String mensaje);
}
