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
public class Mago extends ClaseAventurero {

    private int espaciosConjuro;

    public Mago(int nivel, int espaciosConjuro) {
        super(nivel);
        setEspaciosConjuro(espaciosConjuro);
    }

    public int getEspaciosConjuro() {
        return espaciosConjuro;
    }

    public final void setEspaciosConjuro(int espaciosConjuro) {
        if (espaciosConjuro < 0) {
            throw new IllegalArgumentException("Los espacios de conjuro no pueden ser negativos.");
        }
        this.espaciosConjuro = espaciosConjuro;
    }

    @Override
    public String getNombreClase() {
        return "Mago";
    }

    @Override
    public String getAtributoEspecial() {
        return "espaciosConjuro: " + espaciosConjuro;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Mago " + nombreAventurero
                + " gasta un espacio de conjuro para lanzar una poderosa Bola de Fuego.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 1 + Math.min(espaciosConjuro, 4), 6) + getNivel() / 2;
    }
}
