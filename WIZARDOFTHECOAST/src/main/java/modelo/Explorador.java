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
public class Explorador extends ClaseAventurero {

    private String terrenoFavorecido;

    public Explorador(int nivel, String terrenoFavorecido) {
        super(nivel);
        setTerrenoFavorecido(terrenoFavorecido);
    }

    public String getTerrenoFavorecido() {
        return terrenoFavorecido;
    }

    public final void setTerrenoFavorecido(String terrenoFavorecido) {
        if (terrenoFavorecido == null || terrenoFavorecido.trim().isEmpty()) {
            throw new IllegalArgumentException("El terreno favorecido no puede estar vacío.");
        }
        this.terrenoFavorecido = terrenoFavorecido.trim();
    }

    @Override
    public String getNombreClase() {
        return "Explorador";
    }

    @Override
    public String getAtributoEspecial() {
        return "terrenoFavorecido: " + terrenoFavorecido;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Explorador " + nombreAventurero
                + " aplica Marca del Cazador sobre el objetivo para maximizar su daño sostenido.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 8) + Dado.tirar(azar, 6) + getNivel() / 2;
    }
}
