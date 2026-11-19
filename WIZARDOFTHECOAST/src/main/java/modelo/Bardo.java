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
public class Bardo extends ClaseAventurero {

    private String instrumentoMusical;

    public Bardo(int nivel, String instrumentoMusical) {
        super(nivel);
        setInstrumentoMusical(instrumentoMusical);
    }

    public String getInstrumentoMusical() {
        return instrumentoMusical;
    }

    public final void setInstrumentoMusical(String instrumentoMusical) {
        if (instrumentoMusical == null || instrumentoMusical.trim().isEmpty()) {
            throw new IllegalArgumentException("El instrumento musical no puede estar vacío.");
        }
        this.instrumentoMusical = instrumentoMusical.trim();
    }

    @Override
    public String getNombreClase() {
        return "Bardo";
    }

    @Override
    public String getAtributoEspecial() {
        return "instrumentoMusical: " + instrumentoMusical;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Bardo " + nombreAventurero
                + " toca su instrumento para otorgar Inspiración Bárdica a un aliado.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 2, 6) + getNivel() / 2;
    }
}
