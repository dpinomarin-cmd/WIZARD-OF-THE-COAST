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
public class Aventurero {

    public static final int NIVEL_MINIMO = 1;
    public static final int NIVEL_MAXIMO_TOTAL = 20;
    public static final int MAX_CLASES = 4;
    public static final int VIDA_POR_DEFECTO = 10;
    public static final int CLASE_ARMADURA_BASE = 12;

    private String nombre;
    private int puntosVida;
    private int puntosVidaActuales;
    private final List<ClaseAventurero> clases = new ArrayList<>();

   
    public Aventurero(String nombre, int puntosVida) {
        setNombre(nombre);
        setPuntosVida(puntosVida);
    }

    public Aventurero(String nombre) {
        this(nombre, VIDA_POR_DEFECTO);
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public final void setPuntosVida(int puntosVida) {
        if (puntosVida <= 0) {
            throw new IllegalArgumentException("Los puntos de vida deben ser mayores que 0.");
        }
        this.puntosVida = puntosVida;
        this.puntosVidaActuales = puntosVida;
    }

    public int getPuntosVidaActuales() {
        return puntosVidaActuales;
    }

    public static void validarNuevaClase(List<ClaseAventurero> actuales, ClaseAventurero nueva) {
        if (nueva == null) {
            throw new IllegalArgumentException("La clase no puede ser nula.");
        }
        if (actuales.size() >= MAX_CLASES) {
            throw new IllegalArgumentException(
                    "Un aventurero puede tener máximo " + MAX_CLASES + " clases.");
        }
        int suma = nueva.getNivel();
        for (ClaseAventurero c : actuales) {
            if (c.getNombreClase().equals(nueva.getNombreClase())) {
                throw new IllegalArgumentException("Ya tiene la clase " + c.getNombreClase() + ".");
            }
            suma += c.getNivel();
        }
        if (suma > NIVEL_MAXIMO_TOTAL) {
            throw new IllegalArgumentException("La suma de niveles de todas las clases no puede superar "
                    + NIVEL_MAXIMO_TOTAL + " (con esta clase sería " + suma + ").");
        }
    }

    public void agregarClase(ClaseAventurero nueva) {
        validarNuevaClase(clases, nueva);
        clases.add(nueva);
    }

    public List<ClaseAventurero> getClases() {
        return new ArrayList<>(clases);
    }

    public boolean tieneClases() {
        return !clases.isEmpty();
    }

    public int getNivelTotal() {
        int total = 0;
        for (ClaseAventurero c : clases) {
            total += c.getNivel();
        }
        return total;
    }

    public int getClaseArmadura() {
        int mayor = 0;
        for (ClaseAventurero c : clases) {
            mayor = Math.max(mayor, c.getBonoClaseArmadura());
        }
        return CLASE_ARMADURA_BASE + mayor;
    }

    public int getBonoCompetencia() {
        return 2 + Math.max(0, getNivelTotal() - 1) / 4;
    }

    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        puntosVidaActuales = Math.max(0, puntosVidaActuales - danio);
    }

    public void recibirDanio(int danio, boolean golpeCritico) {
        recibirDanio(golpeCritico ? danio * 2 : danio);
    }

    public boolean estaVivo() {
        return puntosVidaActuales > 0;
    }

    public void restaurarVida() {
        puntosVidaActuales = puntosVida;
    }

    public ResultadoAccion actuar(Random azar, int claseArmaduraObjetivo) {
        if (clases.isEmpty()) {
            throw new IllegalStateException("El aventurero " + nombre + " no tiene clases.");
        }
        ClaseAventurero elegida = clases.get(azar.nextInt(clases.size()));
        int tirada = Dado.d20(azar);
        int bono = getBonoCompetencia();
        boolean impacta = Dado.impacta(tirada, bono, claseArmaduraObjetivo);
        boolean critico = tirada == Dado.CARAS_D20;
        int danio = 0;
        if (impacta) {
            danio = elegida.calcularDanio(azar) * (critico ? 2 : 1);
        }
        return new ResultadoAccion(elegida.simularCombate(nombre), tirada, bono, impacta, critico, danio);
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder(nombre).append(" [");
        for (int i = 0; i < clases.size(); i++) {
            if (i > 0) {
                texto.append(", ");
            }
            texto.append(clases.get(i));
        }
        return texto.append("] Nivel total ").append(getNivelTotal())
                .append(", PV ").append(puntosVida).toString();
    }
}
