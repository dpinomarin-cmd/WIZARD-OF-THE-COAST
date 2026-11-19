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
public class FabricaAventureroTest {

    @Test
    public void ofreceLasOchoClases() {
        assertEquals(8, FabricaAventurero.clases().length);
    }

    @Test
    public void creaCadaClaseConSuTipoCorrecto() {
        assertTrue(FabricaAventurero.crearClase("Guerrero", 1, "sí") instanceof Guerrero);
        assertTrue(FabricaAventurero.crearClase("Mago", 1, "3") instanceof Mago);
        assertTrue(FabricaAventurero.crearClase("Clérigo", 1, "Pelor") instanceof Clerigo);
        assertTrue(FabricaAventurero.crearClase("Monje", 1, "4") instanceof Monje);
        assertTrue(FabricaAventurero.crearClase("Paladín", 1, "15") instanceof Paladin);
        assertTrue(FabricaAventurero.crearClase("Explorador", 1, "Bosque") instanceof Explorador);
        assertTrue(FabricaAventurero.crearClase("Pícaro", 1, "no") instanceof Picaro);
        assertTrue(FabricaAventurero.crearClase("Bardo", 1, "Laúd") instanceof Bardo);
    }

    @Test
    public void respetaElNivelIndicado() {
        assertEquals(7, FabricaAventurero.crearClase("Mago", 7, "2").getNivel());
    }

    @Test
    public void aceptaNombresDeClaseSinTilde() {
        assertTrue(FabricaAventurero.crearClase("Picaro", 1, "no") instanceof Picaro);
        assertTrue(FabricaAventurero.crearClase("paladin", 1, "15") instanceof Paladin);
    }

    @Test
    public void convierteSiNoEnBooleano() {
        Guerrero conArmadura = (Guerrero) FabricaAventurero.crearClase("Guerrero", 1, "Sí");
        Guerrero sinArmadura = (Guerrero) FabricaAventurero.crearClase("Guerrero", 1, "no");
        assertTrue(conArmadura.isArmaduraPesada());
        assertFalse(sinArmadura.isArmaduraPesada());
    }

    @Test
    public void numeroInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> FabricaAventurero.crearClase("Mago", 1, "tres"));
        assertThrows(IllegalArgumentException.class, () -> FabricaAventurero.crearClase("Monje", 1, ""));
    }

    @Test
    public void valorBooleanoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> FabricaAventurero.crearClase("Guerrero", 1, "quizás"));
    }

    @Test
    public void claseDesconocidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> FabricaAventurero.crearClase("Dragón", 1, "x"));
        assertThrows(IllegalArgumentException.class, () -> FabricaAventurero.crearClase(null, 1, "x"));
    }

    @Test
    public void claveDeClaseSinTildesNiMayusculas() {
        assertEquals("picaro", FabricaAventurero.claveClase("Pícaro"));
        assertEquals("clerigo", FabricaAventurero.claveClase(" Clérigo "));
    }

    @Test
    public void identificaClasesConAtributoBooleano() {
        assertTrue(FabricaAventurero.esAtributoBooleano("Guerrero"));
        assertTrue(FabricaAventurero.esAtributoBooleano("Pícaro"));
        assertFalse(FabricaAventurero.esAtributoBooleano("Mago"));
    }

    @Test
    public void cadaClaseTieneEtiqueta() {
        for (String clase : FabricaAventurero.clases()) {
            assertFalse(FabricaAventurero.etiquetaAtributo(clase).isEmpty());
        }
    }
}
