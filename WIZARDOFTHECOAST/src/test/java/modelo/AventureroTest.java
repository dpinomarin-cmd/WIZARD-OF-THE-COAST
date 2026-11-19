/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Daniel
 */
public class AventureroTest {

    @Test
    public void constructorCompletoAsignaLosDatos() {
        Aventurero a = new Aventurero("Juan", 40);
        assertEquals("Juan", a.getNombre());
        assertEquals(40, a.getPuntosVida());
        assertEquals(40, a.getPuntosVidaActuales());
        assertFalse(a.tieneClases());
        assertEquals(0, a.getNivelTotal());
    }

    @Test
    public void constructorSobrecargadoUsaVidaPorDefecto() {
        assertEquals(Aventurero.VIDA_POR_DEFECTO, new Aventurero("Juan").getPuntosVida());
    }

    @Test
    public void nombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Aventurero("  ", 10));
        assertThrows(IllegalArgumentException.class, () -> new Aventurero(null, 10));
    }

    @Test
    public void vidaNoPositivaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Aventurero("Juan", 0));
    }


    @Test
    public void cuatroClasesDeNivelCincoSumanVeinte() {
        Aventurero juan = new Aventurero("Juan", 50);
        juan.agregarClase(new Guerrero(5, true));
        juan.agregarClase(new Mago(5, 3));
        juan.agregarClase(new Paladin(5, 15));
        juan.agregarClase(new Bardo(5, "Laúd"));
        assertEquals(4, juan.getClases().size());
        assertEquals(20, juan.getNivelTotal());
    }

    @Test
    public void sumaDeNivelesMayorAVeinteLanzaExcepcion() {
        Aventurero a = new Aventurero("Juan", 50);
        a.agregarClase(new Guerrero(15));
        assertThrows(IllegalArgumentException.class, () -> a.agregarClase(new Mago(6, 1)));
        a.agregarClase(new Mago(5, 1));   
        assertEquals(20, a.getNivelTotal());
    }

    @Test
    public void claseRepetidaLanzaExcepcion() {
        Aventurero a = new Aventurero("Juan", 50);
        a.agregarClase(new Guerrero(3));
        assertThrows(IllegalArgumentException.class, () -> a.agregarClase(new Guerrero(2, true)));
    }

    @Test
    public void maximoCuatroClases() {
        Aventurero a = new Aventurero("Juan", 50);
        a.agregarClase(new Guerrero(1));
        a.agregarClase(new Mago(1, 1));
        a.agregarClase(new Monje(1, 1));
        a.agregarClase(new Bardo(1, "Flauta"));
        assertThrows(IllegalArgumentException.class, () -> a.agregarClase(new Clerigo(1, "Pelor")));
    }

    @Test
    public void validarNuevaClaseNoModificaLaLista() {
        List<ClaseAventurero> lista = new ArrayList<>();
        Aventurero.validarNuevaClase(lista, new Guerrero(5));
        assertTrue(lista.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> Aventurero.validarNuevaClase(lista, null));
    }

    @Test
    public void getClasesDevuelveUnaCopia() {
        Aventurero a = new Aventurero("Juan", 20);
        a.agregarClase(new Guerrero(3));
        a.getClases().clear();
        assertEquals(1, a.getClases().size());
    }


    @Test
    public void recibirDanioReduceLaVidaActualPeroNoLaMaxima() {
        Aventurero a = new Aventurero("Juan", 30);
        a.recibirDanio(12);
        assertEquals(18, a.getPuntosVidaActuales());
        assertEquals(30, a.getPuntosVida());
        assertTrue(a.estaVivo());
    }

    @Test
    public void recibirDanioNuncaDejaVidaNegativa() {
        Aventurero a = new Aventurero("Juan", 10);
        a.recibirDanio(500);
        assertEquals(0, a.getPuntosVidaActuales());
        assertFalse(a.estaVivo());
    }

    @Test
    public void golpeCriticoDuplicaElDanio() {
        Aventurero a = new Aventurero("Juan", 30);
        a.recibirDanio(5, true);
        assertEquals(20, a.getPuntosVidaActuales());
        a.recibirDanio(5, false);
        assertEquals(15, a.getPuntosVidaActuales());
    }

    @Test
    public void danioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Aventurero("Juan").recibirDanio(-1));
    }

    @Test
    public void restaurarVidaVuelveALaMaxima() {
        Aventurero a = new Aventurero("Juan", 30);
        a.recibirDanio(30);
        a.restaurarVida();
        assertEquals(30, a.getPuntosVidaActuales());
        assertTrue(a.estaVivo());
    }


    @Test
    public void claseDeArmaduraBaseYConArmaduraPesada() {
        Aventurero a = new Aventurero("Juan", 30);
        assertEquals(12, a.getClaseArmadura());
        a.agregarClase(new Mago(3, 2));
        assertEquals(12, a.getClaseArmadura());
        a.agregarClase(new Guerrero(3, true));
        assertEquals(16, a.getClaseArmadura());
    }

    @Test
    public void bonoDeCompetenciaSegunElNivelTotal() {
        int[] niveles = {1, 4, 5, 9, 13, 17, 20};
        int[] bonos = {2, 2, 3, 4, 5, 6, 6};
        for (int i = 0; i < niveles.length; i++) {
            Aventurero a = new Aventurero("Juan", 30);
            a.agregarClase(new Guerrero(niveles[i]));
            assertEquals(bonos[i], a.getBonoCompetencia());
        }
    }


    @Test
    public void actuarSinClasesLanzaExcepcion() {
        assertThrows(IllegalStateException.class,
                () -> new Aventurero("Juan").actuar(new AzarFijo(false), 10));
    }

    @Test
    public void unoNaturalSiempreFalla() {
        Aventurero a = new Aventurero("Juan", 30);
        a.agregarClase(new Guerrero(5));
        ResultadoAccion r = a.actuar(new AzarFijo(false), 1);   
        assertEquals(1, r.getTirada());
        assertFalse(r.isImpacta());
        assertFalse(r.isCritico());
        assertEquals(0, r.getDanio());
        assertTrue(r.getMensaje().startsWith("El Guerrero Juan"));
    }

    @Test
    public void veinteNaturalEsCriticoYDuplicaElDanio() {
        Aventurero a = new Aventurero("Juan", 30);
        a.agregarClase(new Guerrero(5));
        a.agregarClase(new Mago(4, 3));
        ResultadoAccion r = a.actuar(new AzarFijo(true), 99);  
        assertEquals(20, r.getTirada());
        assertTrue(r.isImpacta());
        assertTrue(r.isCritico());
        assertEquals(52, r.getDanio());                          
        assertTrue(r.getMensaje().startsWith("El Mago Juan"));
    }

    @Test
    public void impactaSoloSiTiradaMasBonoAlcanzanLaCA() {
        Aventurero a = new Aventurero("Juan", 30);
        a.agregarClase(new Guerrero(5));                         
        ResultadoAccion acierta = a.actuar(new AzarFijo(9), 13); 
        assertEquals(10, acierta.getTirada());
        assertEquals(3, acierta.getBono());
        assertEquals(13, acierta.getTotalAtaque());
        assertTrue(acierta.isImpacta());
        assertFalse(acierta.isCritico());
        assertEquals(25, acierta.getDanio());                   

        ResultadoAccion falla = a.actuar(new AzarFijo(9), 14);   
        assertFalse(falla.isImpacta());
        assertEquals(0, falla.getDanio());
    }

    @Test
    public void toStringIncluyeNombreClasesYNivelTotal() {
        Aventurero a = new Aventurero("Juan", 30);
        a.agregarClase(new Guerrero(5, true));
        a.agregarClase(new Mago(5, 2));
        String texto = a.toString();
        assertTrue(texto.contains("Juan"));
        assertTrue(texto.contains("Guerrero"));
        assertTrue(texto.contains("Mago"));
        assertTrue(texto.contains("Nivel total 10"));
    }
}
