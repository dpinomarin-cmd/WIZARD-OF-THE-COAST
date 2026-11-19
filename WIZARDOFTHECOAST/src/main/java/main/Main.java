/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import controlador.ControladorGremio;
import historiaCombates.RegistroGremio;
import vista.VistaGremio;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VistaGremio vista = new VistaGremio();
            ControladorGremio controlador = new ControladorGremio(vista, new RegistroGremio());
            controlador.iniciar();
        });
    }
}
