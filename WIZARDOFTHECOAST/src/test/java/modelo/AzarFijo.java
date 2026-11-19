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
public class AzarFijo extends Random {

    private final int valor;

    public AzarFijo(boolean maximo) {
        this(maximo ? Integer.MAX_VALUE : 0);
    }

    public AzarFijo(int valor) {
        this.valor = valor;
    }

    @Override
    public int nextInt(int bound) {
        return Math.min(valor, bound - 1);
    }
}
