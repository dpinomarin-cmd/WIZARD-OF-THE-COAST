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
public final class Dado {

    public static final int CARAS_D20 = 20;

    private Dado() {
    }

    public static int tirar(Random azar, int caras) {
        if (caras < 1) {
            throw new IllegalArgumentException("El dado debe tener al menos 1 cara.");
        }
        return azar.nextInt(caras) + 1;
    }

    public static int tirar(Random azar, int cantidad, int caras) {
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += tirar(azar, caras);
        }
        return total;
    }

    public static int d20(Random azar) {
        return tirar(azar, CARAS_D20);
    }

    public static boolean impacta(int tirada, int bono, int claseArmadura) {
        if (tirada == CARAS_D20) {
            return true;
        }
        if (tirada == 1) {
            return false;
        }
        return tirada + bono >= claseArmadura;
    }
}
