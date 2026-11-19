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
public class Monje extends ClaseAventurero {

    private int puntosKi;

    public Monje(int nivel, int puntosKi) {
        super(nivel);
        setPuntosKi(puntosKi);
    }

    public int getPuntosKi() {
        return puntosKi;
    }

    public final void setPuntosKi(int puntosKi) {
        if (puntosKi < 0) {
            throw new IllegalArgumentException("Los puntos de ki no pueden ser negativos.");
        }
        this.puntosKi = puntosKi;
    }

    @Override
    public String getNombreClase() {
        return "Monje";
    }

    @Override
    public String getAtributoEspecial() {
        return "puntosKi: " + puntosKi;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Monje " + nombreAventurero
                + " gasta puntos de Ki para ejecutar una Ráfaga de Golpes y un Ataque Aturdidor.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 2, 6) + Math.min(puntosKi, getNivel());
    }
}
