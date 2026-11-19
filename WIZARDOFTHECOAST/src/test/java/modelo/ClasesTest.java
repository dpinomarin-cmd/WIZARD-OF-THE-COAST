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
public class ClasesTest {

    private static final AzarFijo MAXIMO = new AzarFijo(true);
    private static final AzarFijo MINIMO = new AzarFijo(false);


    @Test
    public void guerreroUsaOleadaDeAccion() {
        assertEquals("El Guerrero Thorin usa Oleada de Acción para realizar múltiples ataques físicos continuos.",
                new Guerrero(5, true).simularCombate("Thorin"));
    }

    @Test
    public void magoLanzaBolaDeFuego() {
        assertEquals("El Mago Merlin gasta un espacio de conjuro para lanzar una poderosa Bola de Fuego.",
                new Mago(5, 3).simularCombate("Merlin"));
    }

    @Test
    public void clerigoCanalizaDivinidad() {
        assertEquals("El Clérigo Elara usa Canalizar Divinidad para curar a sus aliados y expulsar muertos vivientes.",
                new Clerigo(4, "Pelor").simularCombate("Elara"));
    }

    @Test
    public void monjeEjecutaRafagaDeGolpes() {
        assertEquals("El Monje Kai gasta puntos de Ki para ejecutar una Ráfaga de Golpes y un Ataque Aturdidor.",
                new Monje(4, 4).simularCombate("Kai"));
    }

    @Test
    public void paladinUsaCastigoDivino() {
        assertEquals("El Paladín Arthas consume magia para imbuir su arma con un Castigo Divino de daño radiante.",
                new Paladin(6, 18).simularCombate("Arthas"));
    }

    @Test
    public void exploradorAplicaMarcaDelCazador() {
        assertEquals("El Explorador Legolas aplica Marca del Cazador sobre el objetivo para maximizar su daño sostenido.",
                new Explorador(5, "Bosque").simularCombate("Legolas"));
    }

    @Test
    public void picaroAsestaAtaqueFurtivo() {
        assertEquals("El Pícaro Vex aprovecha la ventaja táctica para asestar un Ataque Furtivo masivo y se reposiciona.",
                new Picaro(3, true).simularCombate("Vex"));
    }

    @Test
    public void bardoOtorgaInspiracionBardica() {
        assertEquals("El Bardo Lira toca su instrumento para otorgar Inspiración Bárdica a un aliado.",
                new Bardo(2, "Laúd").simularCombate("Lira"));
    }

    @Test
    public void danioDelGuerrero() {
        assertEquals(25, new Guerrero(5).calcularDanio(MAXIMO));   
        assertEquals(7, new Guerrero(5).calcularDanio(MINIMO));
    }

    @Test
    public void danioDelMago() {
        assertEquals(26, new Mago(4, 3).calcularDanio(MAXIMO));   
        assertEquals(6, new Mago(4, 3).calcularDanio(MINIMO));
        assertEquals(32, new Mago(4, 10).calcularDanio(MAXIMO));   
    }

    @Test
    public void danioDelClerigo() {
        assertEquals(12, new Clerigo(4, "Pelor").calcularDanio(MAXIMO));  
        assertEquals(5, new Clerigo(4, "Pelor").calcularDanio(MINIMO));
    }

    @Test
    public void danioDelMonje() {
        assertEquals(14, new Monje(4, 2).calcularDanio(MAXIMO));   
        assertEquals(4, new Monje(4, 2).calcularDanio(MINIMO));
    }

    @Test
    public void danioDelPaladin() {
        assertEquals(25, new Paladin(6, 18).calcularDanio(MAXIMO)); 
        assertEquals(6, new Paladin(6, 18).calcularDanio(MINIMO));
    }

    @Test
    public void danioDelExplorador() {
        assertEquals(16, new Explorador(5, "Bosque").calcularDanio(MAXIMO));
        assertEquals(4, new Explorador(5, "Bosque").calcularDanio(MINIMO));
    }

    @Test
    public void danioDelPicaro() {
        assertEquals(19, new Picaro(3, true).calcularDanio(MAXIMO)); 
        assertEquals(4, new Picaro(3, true).calcularDanio(MINIMO));
        assertEquals(18, new Picaro(3, false).calcularDanio(MAXIMO));
    }

    @Test
    public void danioDelBardo() {
        assertEquals(13, new Bardo(2, "Laúd").calcularDanio(MAXIMO)); 
        assertEquals(3, new Bardo(2, "Laúd").calcularDanio(MINIMO));
    }


    @Test
    public void armaduraPesadaSubeLaClaseDeArmadura() {
        assertEquals(4, new Guerrero(5, true).getBonoClaseArmadura());
        assertEquals(0, new Guerrero(5, false).getBonoClaseArmadura());
        assertEquals(0, new Mago(5, 1).getBonoClaseArmadura());
    }

    @Test
    public void constructorSobrecargadoDelGuerreroNoUsaArmadura() {
        assertFalse(new Guerrero(3).isArmaduraPesada());
    }

    @Test
    public void nivelDeClaseFueraDeRangoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Guerrero(0));
        assertThrows(IllegalArgumentException.class, () -> new Guerrero(21));
    }

    @Test
    public void atributosEspecialesInvalidosLanzanExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Mago(1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Monje(1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Paladin(1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Clerigo(1, ""));
        assertThrows(IllegalArgumentException.class, () -> new Explorador(1, " "));
        assertThrows(IllegalArgumentException.class, () -> new Bardo(1, null));
    }

    @Test
    public void nombresDeClaseConTilde() {
        assertEquals("Clérigo", new Clerigo(1, "Pelor").getNombreClase());
        assertEquals("Paladín", new Paladin(1, 10).getNombreClase());
        assertEquals("Pícaro", new Picaro(1, false).getNombreClase());
    }

    @Test
    public void toStringMuestraClaseNivelYAtributo() {
        String texto = new Monje(4, 4).toString();
        assertTrue(texto.contains("Monje"));
        assertTrue(texto.contains("4"));
        assertTrue(texto.contains("puntosKi: 4"));
    }


    @Test
    public void pruebaDeAtaqueConD20() {
        assertEquals(20, Dado.d20(MAXIMO));
        assertEquals(1, Dado.d20(MINIMO));
        assertTrue(Dado.impacta(20, -10, 30));    
        assertFalse(Dado.impacta(1, 20, 5));      
        assertTrue(Dado.impacta(10, 2, 12));      
        assertFalse(Dado.impacta(10, 2, 13));
    }

    @Test
    public void dadoDevuelveElValorEsperado() {
        assertEquals(6, Dado.tirar(MAXIMO, 6));
        assertEquals(1, Dado.tirar(MINIMO, 6));
        assertEquals(18, Dado.tirar(MAXIMO, 3, 6));
        assertEquals(3, Dado.tirar(MINIMO, 3, 6));
        assertThrows(IllegalArgumentException.class, () -> Dado.tirar(MAXIMO, 0));
    }
}
