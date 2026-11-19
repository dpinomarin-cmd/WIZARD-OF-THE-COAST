/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class Combate {

    public enum Resultado {
        VICTORIA, DERROTA, EMPATE
    }

    public static final int PARTY_MINIMA = 4;
    public static final int PARTY_MAXIMA = 6;
    public static final int RONDAS_MAXIMAS = 50;

    private final List<Aventurero> party;
    private final Enemigo enemigo;
    private final Random azar;
    private final List<String> registro = new ArrayList<>();
    private Resultado resultado;
    private int rondas;

    public Combate(List<Aventurero> party, Enemigo enemigo, Random azar) {
        if (party == null || party.size() < PARTY_MINIMA || party.size() > PARTY_MAXIMA) {
            throw new IllegalArgumentException("La party debe tener entre " + PARTY_MINIMA + " y "
                    + PARTY_MAXIMA + " aventureros (tiene " + (party == null ? 0 : party.size()) + ").");
        }
        if (enemigo == null) {
            throw new IllegalArgumentException("Debe existir un enemigo.");
        }
        for (Aventurero a : party) {
            if (!a.tieneClases()) {
                throw new IllegalArgumentException("El aventurero " + a.getNombre() + " no tiene clases.");
            }
        }
        this.party = new ArrayList<>(party);
        this.enemigo = enemigo;
        this.azar = azar;
    }

    public Combate(List<Aventurero> party, Enemigo enemigo) {
        this(party, enemigo, new Random());
    }

    public Resultado simular() {
        registro.clear();
        rondas = 0;
        enemigo.restaurarVida();
        for (Aventurero a : party) {
            a.restaurarVida();
        }
        registro.add("¡" + enemigo + " (CA " + enemigo.getClaseArmadura() + ") aparece ante la party de "
                + party.size() + " aventureros!");

        while (enemigo.estaVivo() && hayVivos() && rondas < RONDAS_MAXIMAS) {
            rondas++;
            registro.add("--- Ronda " + rondas + " ---");
            turnoDeLaParty();
            if (enemigo.estaVivo()) {
                turnoDelEnemigo();
            }
        }

        if (!enemigo.estaVivo()) {
            resultado = Resultado.VICTORIA;
            registro.add("¡Victoria! La party derrota a " + enemigo.getNombre()
                    + " en " + rondas + " ronda(s).");
        } else if (!hayVivos()) {
            resultado = Resultado.DERROTA;
            registro.add("Derrota: la party cae ante " + enemigo.getNombre()
                    + " en " + rondas + " ronda(s).");
        } else {
            resultado = Resultado.EMPATE;
            registro.add("Tras " + rondas + " rondas nadie logra imponerse: el combate termina en empate.");
        }
        return resultado;
    }

    private void turnoDeLaParty() {
        for (Aventurero a : party) {
            if (!enemigo.estaVivo()) {
                break;
            }
            if (!a.estaVivo()) {
                continue;
            }
            int ca = enemigo.getClaseArmadura();
            ResultadoAccion accion = a.actuar(azar, ca);   // polimorfismo: cada clase actúa a su manera
            String tirada = detalleTirada(accion.getTirada(), accion.getBono(), ca);
            if (accion.isImpacta()) {
                enemigo.recibirDanio(accion.getDanio());
                registro.add(accion.getMensaje() + " " + tirada
                        + (accion.isCritico() ? " ¡CRÍTICO!" : " ¡Impacta!")
                        + " Inflige " + accion.getDanio() + " de daño. ("
                        + enemigo.getNombre() + ": " + enemigo.getPuntosVidaActuales() + " PV)");
            } else {
                registro.add(accion.getMensaje() + " " + tirada
                        + (accion.getTirada() == 1 ? " ¡Pifia!" : "") + " Falla.");
            }
        }
    }

    private void turnoDelEnemigo() {
        for (int i = 0; i < enemigo.getAtaquesPorRonda() && hayVivos(); i++) {
            List<Aventurero> vivos = new ArrayList<>();
            for (Aventurero a : party) {
                if (a.estaVivo()) {
                    vivos.add(a);
                }
            }
            Aventurero objetivo = vivos.get(azar.nextInt(vivos.size()));
            int tirada = Dado.d20(azar);
            int bono = enemigo.getBonoAtaque();
            int ca = objetivo.getClaseArmadura();
            String ataque = enemigo.getNombre() + " ataca a " + objetivo.getNombre() + ". "
                    + detalleTirada(tirada, bono, ca);

            if (Dado.impacta(tirada, bono, ca)) {
                boolean critico = tirada == Dado.CARAS_D20;
                int danio = enemigo.getDanio() * (critico ? 2 : 1);
                objetivo.recibirDanio(danio);
                registro.add(ataque + (critico ? " ¡CRÍTICO!" : " ¡Impacta!")
                        + " Inflige " + danio + " de daño. (" + objetivo.getNombre() + ": "
                        + objetivo.getPuntosVidaActuales() + " PV)"
                        + (objetivo.estaVivo() ? "" : " ¡" + objetivo.getNombre() + " cae inconsciente!"));
            } else {
                registro.add(ataque + (tirada == 1 ? " ¡Pifia!" : "") + " Falla.");
            }
        }
    }

    private static String detalleTirada(int tirada, int bono, int claseArmadura) {
        return "Tirada d20: " + tirada + "+" + bono + "=" + (tirada + bono) + " vs CA " + claseArmadura + ".";
    }

    private boolean hayVivos() {
        for (Aventurero a : party) {
            if (a.estaVivo()) {
                return true;
            }
        }
        return false;
    }

    public List<String> getRegistro() {
        return new ArrayList<>(registro);
    }

    public Resultado getResultado() {
        return resultado;
    }

    public int getRondas() {
        return rondas;
    }
}
