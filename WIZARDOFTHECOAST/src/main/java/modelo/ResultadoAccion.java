/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel
 */
public final class ResultadoAccion {

    private final String mensaje;
    private final int tirada;
    private final int bono;
    private final boolean impacta;
    private final boolean critico;
    private final int danio;

    public ResultadoAccion(String mensaje, int tirada, int bono, boolean impacta, boolean critico, int danio) {
        this.mensaje = mensaje;
        this.tirada = tirada;
        this.bono = bono;
        this.impacta = impacta;
        this.critico = critico;
        this.danio = danio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getTirada() {
        return tirada;
    }

    public int getBono() {
        return bono;
    }

    public int getTotalAtaque() {
        return tirada + bono;
    }

    public boolean isImpacta() {
        return impacta;
    }

    public boolean isCritico() {
        return critico;
    }
    
    public int getDanio() {
        return danio;
    }
}
