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
public class Paladin extends ClaseAventurero {

    private int puntosFuerza;

    public Paladin(int nivel, int puntosFuerza) {
        super(nivel);
        setPuntosFuerza(puntosFuerza);
    }

    public int getPuntosFuerza() {
        return puntosFuerza;
    }

    public final void setPuntosFuerza(int puntosFuerza) {
        if (puntosFuerza < 0) {
            throw new IllegalArgumentException("Los puntos de fuerza no pueden ser negativos.");
        }
        this.puntosFuerza = puntosFuerza;
    }

    @Override
    public String getNombreClase() {
        return "Paladín";
    }

    @Override
    public String getAtributoEspecial() {
        return "puntosFuerza: " + puntosFuerza;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Paladín " + nombreAventurero
                + " consume magia para imbuir su arma con un Castigo Divino de daño radiante.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 10) + Dado.tirar(azar, 2, 6) + puntosFuerza / 5;
    }
}
