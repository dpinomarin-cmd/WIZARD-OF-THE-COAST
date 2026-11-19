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
public class Guerrero extends ClaseAventurero {

    private boolean armaduraPesada;

    public Guerrero(int nivel, boolean armaduraPesada) {
        super(nivel);
        this.armaduraPesada = armaduraPesada;
    }

    public Guerrero(int nivel) {
        this(nivel, false);
    }

    public boolean isArmaduraPesada() {
        return armaduraPesada;
    }

    public void setArmaduraPesada(boolean armaduraPesada) {
        this.armaduraPesada = armaduraPesada;
    }

    @Override
    public String getNombreClase() {
        return "Guerrero";
    }

    @Override
    public String getAtributoEspecial() {
        return "armaduraPesada: " + (armaduraPesada ? "Sí" : "No");
    }

    @Override
    public int getBonoClaseArmadura() {
        return armaduraPesada ? 4 : 0;
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Guerrero " + nombreAventurero
                + " usa Oleada de Acción para realizar múltiples ataques físicos continuos.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 2, 10) + getNivel();
    }
}
