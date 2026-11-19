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
public class Picaro extends ClaseAventurero {

    private boolean herramientasLadron;

    public Picaro(int nivel, boolean herramientasLadron) {
        super(nivel);
        this.herramientasLadron = herramientasLadron;
    }

    public boolean isHerramientasLadron() {
        return herramientasLadron;
    }

    public void setHerramientasLadron(boolean herramientasLadron) {
        this.herramientasLadron = herramientasLadron;
    }

    @Override
    public String getNombreClase() {
        return "Pícaro";
    }

    @Override
    public String getAtributoEspecial() {
        return "herramientasLadron: " + (herramientasLadron ? "Sí" : "No");
    }

    @Override
    public String simularCombate(String nombreAventurero) {
        return "El Pícaro " + nombreAventurero
                + " aprovecha la ventaja táctica para asestar un Ataque Furtivo masivo y se reposiciona.";
    }

    @Override
    public int calcularDanio(Random azar) {
        return Dado.tirar(azar, 6)
                + Dado.tirar(azar, (getNivel() + 1) / 2, 6)
                + (herramientasLadron ? 1 : 0);
    }
}
