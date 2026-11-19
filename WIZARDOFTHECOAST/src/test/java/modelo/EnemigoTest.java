/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 *
 * @author Daniel
 */
public class EnemigoTest {

    @Test
    public void constructorAsignaTodosLosDatos() {
        Enemigo e = new Enemigo("Dragón Rojo", 300, 20, 35);
        assertEquals("Dragón Rojo", e.getNombre());
        assertEquals(300, e.getPuntosVida());
        assertEquals(300, e.getPuntosVidaActuales());
        assertEquals(20, e.getNivel());
        assertEquals(35, e.getDanio());
    }

    @Test
    public void datosInvalidosLanzanExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Enemigo(" ", 10, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Enemigo("Goblin", 0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Enemigo("Goblin", 10, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Enemigo("Goblin", 10, 31, 1));
        assertThrows(IllegalArgumentException.class, () -> new Enemigo("Goblin", 10, 1, 0));
    }

    @Test
    public void recibirDanioNuncaDejaVidaNegativa() {
        Enemigo e = new Enemigo("Goblin", 10, 1, 3);
        e.recibirDanio(4);
        assertEquals(6, e.getPuntosVidaActuales());
        assertTrue(e.estaVivo());
        e.recibirDanio(100);
        assertEquals(0, e.getPuntosVidaActuales());
        assertFalse(e.estaVivo());
    }

    @Test
    public void danioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Enemigo("Goblin", 10, 1, 3).recibirDanio(-1));
    }

    @Test
    public void restaurarVidaVuelveALaMaxima() {
        Enemigo e = new Enemigo("Goblin", 10, 1, 3);
        e.recibirDanio(10);
        e.restaurarVida();
        assertEquals(10, e.getPuntosVidaActuales());
    }

    @Test
    public void ataquesPorRondaDependenDelNivel() {
        assertEquals(1, new Enemigo("A", 10, 1, 1).getAtaquesPorRonda());
        assertEquals(1, new Enemigo("A", 10, 9, 1).getAtaquesPorRonda());
        assertEquals(2, new Enemigo("A", 10, 10, 1).getAtaquesPorRonda());
        assertEquals(3, new Enemigo("A", 10, 20, 1).getAtaquesPorRonda());
        assertEquals(4, new Enemigo("A", 10, 30, 1).getAtaquesPorRonda());
    }

    @Test
    public void bonoDeAtaqueSubeCadaCuatroNiveles() {
        assertEquals(2, new Enemigo("A", 10, 1, 1).getBonoAtaque());
        assertEquals(2, new Enemigo("A", 10, 4, 1).getBonoAtaque());
        assertEquals(3, new Enemigo("A", 10, 5, 1).getBonoAtaque());
        assertEquals(6, new Enemigo("A", 10, 17, 1).getBonoAtaque());
        assertEquals(9, new Enemigo("A", 10, 30, 1).getBonoAtaque());
    }

    @Test
    public void claseDeArmaduraDependeDelNivel() {
        assertEquals(10, new Enemigo("A", 10, 1, 1).getClaseArmadura());
        assertEquals(15, new Enemigo("A", 10, 10, 1).getClaseArmadura());
        assertEquals(20, new Enemigo("A", 10, 20, 1).getClaseArmadura());
        assertEquals(25, new Enemigo("A", 10, 30, 1).getClaseArmadura());
    }

    @Test
    public void toStringMuestraLosDatos() {
        String texto = new Enemigo("Goblin", 12, 1, 4).toString();
        assertTrue(texto.contains("Goblin"));
        assertTrue(texto.contains("PV 12"));
        assertTrue(texto.contains("Daño 4"));
    }
}
