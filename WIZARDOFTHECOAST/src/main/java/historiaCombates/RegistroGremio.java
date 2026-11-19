/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package historiaCombates;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RegistroGremio {

    public static final String ARCHIVO_POR_DEFECTO = "registro_gremio.txt";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path ruta;

    public RegistroGremio() {
        this(ARCHIVO_POR_DEFECTO);
    }

   
    public RegistroGremio(String ruta) {
        this.ruta = Paths.get(ruta);
    }

    public void anexar(String linea) throws IOException {
        String texto = "[" + LocalDateTime.now().format(FORMATO_FECHA) + "] "
                + linea + System.lineSeparator();
        Files.write(ruta, texto.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public List<String> leerTodo() throws IOException {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(ruta, StandardCharsets.UTF_8);
    }

    public Path getRuta() {
        return ruta;
    }
}
