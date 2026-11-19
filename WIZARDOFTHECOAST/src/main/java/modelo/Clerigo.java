/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class Clerigo extends ClaseAventurero {

    private String diosVenerado;

    public Clerigo(int nivel, String diosVenerado) {
        super(nivel);
        setDiosVenerado(diosVenerado);
    }

    public String getDiosVenerado() {
        return diosVenerado;
    }

    public final void setDiosVenerado(String diosVenerado) {
        if (diosVenerado == null || diosVenerado.trim().isEmpty()) {
            throw new IllegalArgumentException("El dios venerado no puede estar vacío.");
        }
        this.diosVenerado = diosVenerado.trim();
    }

    @Override
    public String getNombreClase() {
        return "Clérigo";
    }

    @Override
    public String getAtributoEspecial() {
        return "diosVenerado: " + diosVenerado;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Clérigo " + nombreAventurero
                + " usa Canalizar Divinidad para curar a sus aliados y expulsar muertos vivientes.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 8) + getNivel();
    }
}
