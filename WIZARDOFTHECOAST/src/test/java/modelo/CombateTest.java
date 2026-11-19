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
public class CombateTest {

    private static Aventurero heroe(String nombre, int vida, ClaseAventurero clase) {
        Aventurero a = new Aventurero(nombre, vida);
        a.agregarClase(clase);
        return a;
    }

   
    private static List<Aventurero> party(int cantidad, int vida, boolean armaduraPesada) {
        List<Aventurero> lista = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            lista.add(heroe("Heroe" + i, vida, new Guerrero(1, armaduraPesada)));
        }
        return lista;
    }

    private static List<Aventurero> party(int cantidad, int vida) {
        return party(cantidad, vida, false);
    }


    @Test
    public void partyConMenosDeCuatroLanzaExcepcion() {
        Enemigo e = new Enemigo("Goblin", 10, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Combate(party(3, 10), e));
    }

    @Test
    public void partyConMasDeSeisLanzaExcepcion() {
        Enemigo e = new Enemigo("Goblin", 10, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Combate(party(7, 10), e));
    }

    @Test
    public void partyDeCuatroYDeSeisSonValidas() {
        Enemigo e = new Enemigo("Goblin", 10, 1, 1);
        assertDoesNotThrow(() -> new Combate(party(4, 10), e));
        assertDoesNotThrow(() -> new Combate(party(6, 10), e));
    }

    @Test
    public void sinEnemigoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Combate(party(4, 10), null));
    }

    @Test
    public void aventureroSinClasesLanzaExcepcion() {
        List<Aventurero> lista = party(3, 10);
        lista.add(new Aventurero("SinClase", 10));
        assertThrows(IllegalArgumentException.class,
                () -> new Combate(lista, new Enemigo("Goblin", 10, 1, 1)));
    }


    @Test
    public void victoriaCuandoLaPartyDerrotaAlEnemigo() {
        List<Aventurero> lista = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            lista.add(heroe("Heroe" + i, 30, new Guerrero(5)));
        }
        Enemigo goblin = new Enemigo("Goblin", 10, 1, 3);
        Combate combate = new Combate(lista, goblin, new AzarFijo(true));  

        assertEquals(Combate.Resultado.VICTORIA, combate.simular());
        assertEquals(Combate.Resultado.VICTORIA, combate.getResultado());
        assertEquals(1, combate.getRondas());
        assertFalse(goblin.estaVivo());
        assertEquals(30, lista.get(0).getPuntosVidaActuales()); 
    }

    @Test
    public void derrotaCuandoCaeTodaLaParty() {
        List<Aventurero> lista = party(4, 5);
        Enemigo troll = new Enemigo("Troll", 100000, 1, 50);
        Combate combate = new Combate(lista, troll, new AzarFijo(true));

        assertEquals(Combate.Resultado.DERROTA, combate.simular());
        assertEquals(4, combate.getRondas());   
        assertTrue(troll.estaVivo());
        for (Aventurero a : lista) {
            assertFalse(a.estaVivo());
        }
    }

    @Test
    public void empateCuandoTodosPifian() {
        List<Aventurero> lista = party(4, 100);
        Enemigo muro = new Enemigo("Muro", 1000, 1, 10);
        Combate combate = new Combate(lista, muro, new AzarFijo(false));

        assertEquals(Combate.Resultado.EMPATE, combate.simular());
        assertEquals(Combate.RONDAS_MAXIMAS, combate.getRondas());
        assertEquals(1000, muro.getPuntosVidaActuales());
        for (Aventurero a : lista) {
            assertEquals(100, a.getPuntosVidaActuales());
        }
        assertTrue(combate.getRegistro().stream().anyMatch(l -> l.contains("¡Pifia!")));
    }


    @Test
    public void armaduraPesadaHaceQueElEnemigoFalle() {
        List<Aventurero> lista = party(4, 5, true);         
        Enemigo goblin = new Enemigo("Goblin", 100000, 1, 5); 
        Combate combate = new Combate(lista, goblin, new AzarFijo(9));

        assertEquals(Combate.Resultado.EMPATE, combate.simular());
        for (Aventurero a : lista) {
            assertEquals(5, a.getPuntosVidaActuales());
        }
        int ataques = 0;
        for (String linea : combate.getRegistro()) {
            if (linea.startsWith("Goblin ataca")) {
                ataques++;
                assertTrue(linea.endsWith("Falla."));
            }
        }
        assertEquals(Combate.RONDAS_MAXIMAS, ataques);
    }

    @Test
    public void sinArmaduraElMismoEnemigoSiImpacta() {
        List<Aventurero> lista = party(4, 5, false);        
        Enemigo goblin = new Enemigo("Goblin", 100000, 1, 5); 
        Combate combate = new Combate(lista, goblin, new AzarFijo(9));

        assertEquals(Combate.Resultado.DERROTA, combate.simular());
        assertEquals(4, combate.getRondas());
    }

    @Test
    public void unAventureroSoloImpactaSiAlcanzaLaCADelEnemigo() {
      
        Enemigo debil = new Enemigo("Rata", 100000, 1, 1);
        Combate c1 = new Combate(party(4, 100), debil, new AzarFijo(9));
        c1.simular();
        assertTrue(c1.getRegistro().stream().anyMatch(l -> l.contains("¡Impacta!")));

     
        Enemigo duro = new Enemigo("Tarrasque", 100000, 30, 1);
        Combate c2 = new Combate(party(4, 100000), duro, new AzarFijo(9));
        c2.simular();
        assertFalse(c2.getRegistro().stream().anyMatch(l -> l.contains("Impacta") && !l.contains("Tarrasque ataca")));
    }



    @Test
    public void simularVariasVecesRestauraLaVida() {
        Combate combate = new Combate(party(4, 5), new Enemigo("Troll", 100000, 1, 50), new AzarFijo(true));
        combate.simular();
        combate.simular();
        assertEquals(4, combate.getRondas());
        assertEquals(Combate.Resultado.DERROTA, combate.getResultado());
    }

    @Test
    public void elRegistroNarraElCombate() {
        Combate combate = new Combate(party(4, 5), new Enemigo("Troll", 100000, 1, 50), new AzarFijo(true));
        combate.simular();
        List<String> registro = combate.getRegistro();
        assertTrue(registro.get(0).contains("Troll"));
        assertTrue(registro.get(0).contains("CA 10"));
        assertTrue(registro.stream().anyMatch(l -> l.contains("Ronda 1")));
        assertTrue(registro.stream().anyMatch(l -> l.contains("usa Oleada de Acción")));
        assertTrue(registro.stream().anyMatch(l -> l.contains("Tirada d20: 20+2=22 vs CA 10")));
        assertTrue(registro.stream().anyMatch(l -> l.contains("¡CRÍTICO!")));
        assertTrue(registro.stream().anyMatch(l -> l.contains("cae inconsciente")));
        assertTrue(registro.get(registro.size() - 1).startsWith("Derrota"));
    }

    @Test
    public void unEnemigoDeNivelAltoAtacaVariasVecesPorRonda() {
        List<Aventurero> lista = party(4, 1000);
        Enemigo dragon = new Enemigo("Dragón", 1000000, 20, 5);  
        Combate combate = new Combate(lista, dragon, new AzarFijo(false));
        combate.simular();
        int ataquesRonda1 = 0;
        for (String linea : combate.getRegistro()) {
            if (linea.contains("Ronda 2")) {
                break;
            }
            if (linea.startsWith("Dragón ataca a")) {
                ataquesRonda1++;
            }
        }
        assertEquals(3, ataquesRonda1);
    }
}
