/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package historiaCombates;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
/**
 *
 * @author Daniel
 */
public class RegistroGremioTest {

    @TempDir
    Path carpeta;

    @Test
    public void anexarCreaElArchivoYAgregaLineas() throws IOException {
        RegistroGremio registro = new RegistroGremio(carpeta.resolve("registro_gremio.txt").toString());
        registro.anexar("REGISTRO | Guerrero Thorin");
        registro.anexar("COMBATE | El Guerrero Thorin usa Oleada de Acción");

        List<String> lineas = registro.leerTodo();
        assertEquals(2, lineas.size());
        assertTrue(lineas.get(0).contains("REGISTRO | Guerrero Thorin"));
        assertTrue(lineas.get(1).contains("COMBATE"));
    }

    @Test
    public void anexarNoSobrescribeLoAnterior() throws IOException {
        String ruta = carpeta.resolve("registro_gremio.txt").toString();
        new RegistroGremio(ruta).anexar("primera");
        new RegistroGremio(ruta).anexar("segunda");
        assertEquals(2, new RegistroGremio(ruta).leerTodo().size());
    }

    @Test
    public void leerTodoSinArchivoDevuelveListaVacia() throws IOException {
        RegistroGremio registro = new RegistroGremio(carpeta.resolve("no_existe.txt").toString());
        assertTrue(registro.leerTodo().isEmpty());
    }

    @Test
    public void constructorPorDefectoUsaRegistroGremioTxt() {
        assertEquals("registro_gremio.txt", new RegistroGremio().getRuta().toString());
    }
}
