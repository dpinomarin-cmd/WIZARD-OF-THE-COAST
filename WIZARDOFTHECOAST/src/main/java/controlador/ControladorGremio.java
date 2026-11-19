/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import historiaCombates.RegistroGremio;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import modelo.Aventurero;
import modelo.ClaseAventurero;
import modelo.Combate;
import modelo.Enemigo;
import modelo.FabricaAventurero;
import vista.VistaGremio;

/**
 *
 * @author Daniel
 */
public class ControladorGremio implements ActionListener {

    private static final int TAM_ICONO = 96;
    private static final String SALTO = System.lineSeparator();

    private final VistaGremio vista;
    private final RegistroGremio registro;
    private final List<Aventurero> gremio = new ArrayList<>();
    private final List<ClaseAventurero> clasesPendientes = new ArrayList<>();
    private Enemigo enemigo;

    public ControladorGremio(VistaGremio vista, RegistroGremio registro) {
        this.vista = vista;
        this.registro = registro;
    }

    public void iniciar() {
        vista.getCmbClase().setModel(new DefaultComboBoxModel<>(FabricaAventurero.clases()));
        vista.getCmbClase().addActionListener(this);
        vista.getBtnAgregarClase().addActionListener(this);
        vista.getBtnLimpiarClases().addActionListener(this);
        vista.getBtnRegistrar().addActionListener(this);
        vista.getBtnCrearEnemigo().addActionListener(this);
        vista.getBtnSimular().addActionListener(this);

        actualizarCampoEspecial();
        refrescarPendientes();
        refrescarGremio();
        mostrarEnemigo();
        vista.getLblResultado().setText("Sin combates todavía");

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen == vista.getCmbClase()) {
            actualizarCampoEspecial();
        } else if (origen == vista.getBtnAgregarClase()) {
            agregarClase();
        } else if (origen == vista.getBtnLimpiarClases()) {
            clasesPendientes.clear();
            refrescarPendientes();
        } else if (origen == vista.getBtnRegistrar()) {
            registrarAventurero();
        } else if (origen == vista.getBtnCrearEnemigo()) {
            crearEnemigo();
        } else if (origen == vista.getBtnSimular()) {
            simularCombate();
        }
    }

    private void actualizarCampoEspecial() {
        String clase = (String) vista.getCmbClase().getSelectedItem();
        boolean esBooleano = FabricaAventurero.esAtributoBooleano(clase);
        vista.getLblAtributo().setText(FabricaAventurero.etiquetaAtributo(clase));
        vista.getChkAtributo().setVisible(esBooleano);
        vista.getTxtAtributo().setVisible(!esBooleano);

        ImageIcon icono = cargarIcono(FabricaAventurero.claveClase(clase) + ".png");
        vista.getLblImagenClase().setIcon(icono);
        vista.getLblImagenClase().setText(icono == null ? clase : "");
    }

    private void agregarClase() {
        try {
            String clase = (String) vista.getCmbClase().getSelectedItem();
            int nivel = valorSpinner(vista.getSpnNivel());
            ClaseAventurero nueva = FabricaAventurero.crearClase(clase, nivel, valorAtributo(clase));
            Aventurero.validarNuevaClase(clasesPendientes, nueva);
            clasesPendientes.add(nueva);
            vista.getTxtAtributo().setText("");
            vista.getChkAtributo().setSelected(false);
            refrescarPendientes();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void registrarAventurero() {
        if (gremio.size() >= Combate.PARTY_MAXIMA) {
            vista.mostrarError("La party ya está completa: máximo " + Combate.PARTY_MAXIMA + " aventureros.");
            return;
        }
        if (clasesPendientes.isEmpty()) {
            vista.mostrarError("Agrega al menos una clase al aventurero antes de registrarlo.");
            return;
        }
        try {
            Aventurero nuevo = new Aventurero(vista.getTxtNombre().getText(),
                    valorSpinner(vista.getSpnVida()));
            for (ClaseAventurero clase : clasesPendientes) {
                nuevo.agregarClase(clase);
            }
            gremio.add(nuevo);
            clasesPendientes.clear();
            refrescarGremio();
            refrescarPendientes();
            vista.getTxtNombre().setText("");
            vista.getTxtNombre().requestFocus();
            registro.anexar("REGISTRO | " + nuevo);
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (IOException ex) {
            vista.mostrarError("El aventurero se registró, pero no se pudo escribir "
                    + RegistroGremio.ARCHIVO_POR_DEFECTO + ": " + ex.getMessage());
        }
    }

    private String valorAtributo(String clase) {
        if (FabricaAventurero.esAtributoBooleano(clase)) {
            return vista.getChkAtributo().isSelected() ? "sí" : "no";
        }
        return vista.getTxtAtributo().getText();
    }

    private void crearEnemigo() {
        try {
            Enemigo nuevo = new Enemigo(vista.getTxtEnemigoNombre().getText(),
                    valorSpinner(vista.getSpnEnemigoVida()),
                    valorSpinner(vista.getSpnEnemigoNivel()),
                    valorSpinner(vista.getSpnEnemigoDanio()));
            enemigo = nuevo;
            mostrarEnemigo();
            registro.anexar("ENEMIGO | " + nuevo);
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (IOException ex) {
            vista.mostrarError("El enemigo se creó, pero no se pudo escribir el historial: " + ex.getMessage());
        }
    }

    private void mostrarEnemigo() {
        vista.getLblEnemigo().setText(enemigo == null ? "Aún no hay enemigo" : enemigo.toString());
        ImageIcon icono = cargarIcono("enemigo.png");
        vista.getLblImagenEnemigo().setIcon(icono);
        vista.getLblImagenEnemigo().setText(icono == null ? "Enemigo" : "");
    }

  
    private void simularCombate() {
        if (gremio.size() < Combate.PARTY_MINIMA) {
            vista.mostrarError("Necesitas al menos " + Combate.PARTY_MINIMA
                    + " aventureros para iniciar la batalla (tienes " + gremio.size() + ").");
            return;
        }
        if (enemigo == null) {
            vista.mostrarError("Primero crea un enemigo en la pestaña Enemigo.");
            return;
        }
        try {
            Combate combate = new Combate(gremio, enemigo);
            Combate.Resultado resultado = combate.simular();

            StringBuilder salida = new StringBuilder();
            registro.anexar("COMBATE | Party de " + gremio.size() + " aventureros contra " + enemigo);
            for (String linea : combate.getRegistro()) {
                salida.append(linea).append(SALTO);
                registro.anexar("COMBATE | " + linea);
            }
            vista.getTxtAreaCombate().setText(salida.toString());
            vista.getTxtAreaCombate().setCaretPosition(0);
            vista.getLblResultado().setText(resultado.name() + " en " + combate.getRondas() + " ronda(s)");
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (IOException ex) {
            vista.mostrarError("No se pudo escribir el historial: " + ex.getMessage());
        }
    }

    private void refrescarGremio() {
        StringBuilder texto = new StringBuilder();
        for (Aventurero a : gremio) {
            texto.append(a).append(SALTO);
        }
        vista.getTxtAreaGremio().setText(texto.toString());
        vista.getLblParty().setText("Party: " + gremio.size() + "/" + Combate.PARTY_MAXIMA
                + " (mínimo " + Combate.PARTY_MINIMA + " para combatir)");
    }

    private void refrescarPendientes() {
        StringBuilder texto = new StringBuilder();
        int total = 0;
        for (ClaseAventurero c : clasesPendientes) {
            texto.append(c).append(SALTO);
            total += c.getNivel();
        }
        texto.append("Nivel total: ").append(total).append(" / ").append(Aventurero.NIVEL_MAXIMO_TOTAL);
        vista.getTxtAreaClases().setText(texto.toString());
    }

    private int valorSpinner(JSpinner spinner) {
        return ((Number) spinner.getValue()).intValue();
    }

    private ImageIcon cargarIcono(String nombreArchivo) {
        URL url = getClass().getResource("/imagenes/" + nombreArchivo);
        if (url == null) {
            return null;
        }
        Image imagen = new ImageIcon(url).getImage()
                .getScaledInstance(TAM_ICONO, TAM_ICONO, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }

    public List<Aventurero> getGremio() {
        return new ArrayList<>(gremio);
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }
}
