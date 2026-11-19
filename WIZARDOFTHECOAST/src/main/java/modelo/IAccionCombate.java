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
public interface IAccionCombate {

    String simularCombate(String nombreAventurero);
    int calcularDanio(Random azar);
}
