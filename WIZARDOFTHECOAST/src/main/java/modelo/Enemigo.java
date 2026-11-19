/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel
 */
public class Enemigo {

    public static final int NIVEL_MINIMO = 1;
    public static final int NIVEL_MAXIMO = 30;

    private String nombre;
    private int puntosVida;
    private int puntosVidaActuales;
    private int nivel;
    private int danio;

    public Enemigo(String nombre, int puntosVida, int nivel, int danio) {
        setNombre(nombre);
        setPuntosVida(puntosVida);
        setNivel(nivel);
        setDanio(danio);
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enemigo no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public final void setPuntosVida(int puntosVida) {
        if (puntosVida <= 0) {
            throw new IllegalArgumentException("La vida del enemigo debe ser mayor que 0.");
        }
        this.puntosVida = puntosVida;
        this.puntosVidaActuales = puntosVida;
    }

    public int getPuntosVidaActuales() {
        return puntosVidaActuales;
    }

    public int getNivel() {
        return nivel;
    }

    public final void setNivel(int nivel) {
        if (nivel < NIVEL_MINIMO || nivel > NIVEL_MAXIMO) {
            throw new IllegalArgumentException("El nivel del enemigo debe estar entre "
                    + NIVEL_MINIMO + " y " + NIVEL_MAXIMO + ".");
        }
        this.nivel = nivel;
    }

    public int getDanio() {
        return danio;
    }

    public final void setDanio(int danio) {
        if (danio <= 0) {
            throw new IllegalArgumentException("El daño del enemigo debe ser mayor que 0.");
        }
        this.danio = danio;
    }

  
    public int getAtaquesPorRonda() {
        return 1 + nivel / 10;
    }

 
    public int getBonoAtaque() {
        return 2 + (nivel - 1) / 4;
    }

    public int getClaseArmadura() {
        return 10 + nivel / 2;
    }

    public void recibirDanio(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        puntosVidaActuales = Math.max(0, puntosVidaActuales - cantidad);
    }

    public boolean estaVivo() {
        return puntosVidaActuales > 0;
    }

    public void restaurarVida() {
        puntosVidaActuales = puntosVida;
    }

    @Override
    public String toString() {
        return nombre + " (Nivel " + nivel + ", PV " + puntosVida + ", Daño " + danio + ")";
    }
}
