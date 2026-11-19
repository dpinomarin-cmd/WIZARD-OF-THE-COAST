/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel
 */
public abstract class ClaseAventurero implements IAccionCombate {

    private int nivel;

    public ClaseAventurero(int nivel) {
        setNivel(nivel);
    }

    public int getNivel() {
        return nivel;
    }

    public final void setNivel(int nivel) {
        if (nivel < Aventurero.NIVEL_MINIMO || nivel > Aventurero.NIVEL_MAXIMO_TOTAL) {
            throw new IllegalArgumentException("El nivel debe estar entre "
                    + Aventurero.NIVEL_MINIMO + " y " + Aventurero.NIVEL_MAXIMO_TOTAL + ".");
        }
        this.nivel = nivel;
    }

    public abstract String getNombreClase();

    public abstract String getAtributoEspecial();

    public int getBonoClaseArmadura() {
        return 0;
    }

    @Override
    public String toString() {
        return getNombreClase() + " Nv." + nivel + " (" + getAtributoEspecial() + ")";
    }
}
