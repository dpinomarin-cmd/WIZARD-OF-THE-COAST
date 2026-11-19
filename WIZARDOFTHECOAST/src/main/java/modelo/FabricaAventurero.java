/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.text.Normalizer;

/**
 *
 * @author Daniel
 */
public final class FabricaAventurero {

    private static final String[] CLASES = {
        "Guerrero", "Mago", "Clérigo", "Monje", "Paladín", "Explorador", "Pícaro", "Bardo"
    };

    private FabricaAventurero() {
    }

    public static String[] clases() {
        return CLASES.clone();
    }

    public static String claveClase(String clase) {
        if (clase == null) {
            throw new IllegalArgumentException("Debe seleccionar una clase.");
        }
        return Normalizer.normalize(clase.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public static boolean esAtributoBooleano(String clase) {
        String c = claveClase(clase);
        return c.equals("guerrero") || c.equals("picaro");
    }

    public static String etiquetaAtributo(String clase) {
        switch (claveClase(clase)) {
            case "guerrero":
                return "¿Usa armadura pesada?";
            case "mago":
                return "Espacios de conjuro";
            case "clerigo":
                return "Dios venerado";
            case "monje":
                return "Puntos de ki";
            case "paladin":
                return "Puntos de fuerza";
            case "explorador":
                return "Terreno favorecido";
            case "picaro":
                return "¿Tiene herramientas de ladrón?";
            case "bardo":
                return "Instrumento musical";
            default:
                throw new IllegalArgumentException("Clase desconocida: " + clase);
        }
    }

    public static ClaseAventurero crearClase(String clase, int nivel, String valor) {
        switch (claveClase(clase)) {
            case "guerrero":
                return new Guerrero(nivel, aBooleano(valor));
            case "mago":
                return new Mago(nivel, aEntero(valor, "espacios de conjuro"));
            case "clerigo":
                return new Clerigo(nivel, valor);
            case "monje":
                return new Monje(nivel, aEntero(valor, "puntos de ki"));
            case "paladin":
                return new Paladin(nivel, aEntero(valor, "puntos de fuerza"));
            case "explorador":
                return new Explorador(nivel, valor);
            case "picaro":
                return new Picaro(nivel, aBooleano(valor));
            case "bardo":
                return new Bardo(nivel, valor);
            default:
                throw new IllegalArgumentException("Clase desconocida: " + clase);
        }
    }

    private static int aEntero(String valor, String campo) {
        try {
            return Integer.parseInt(valor == null ? "" : valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" debe ser un número entero.");
        }
    }

    private static boolean aBooleano(String valor) {
        String v = claveClase(valor == null ? "" : valor);
        if (v.equals("si") || v.equals("s") || v.equals("true") || v.equals("yes")) {
            return true;
        }
        if (v.equals("no") || v.equals("n") || v.equals("false")) {
            return false;
        }
        throw new IllegalArgumentException("El valor debe ser sí o no.");
    }
}
