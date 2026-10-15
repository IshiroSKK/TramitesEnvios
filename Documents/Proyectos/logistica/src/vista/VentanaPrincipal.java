package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import modelo.Envio;

/**
 * Vista: gestiona exclusivamente la interfaz gráfica. No contiene lógica de negocio.
 */
public class VentanaPrincipal extends JFrame {

    private static final String[] COLUMNAS = {"Tipo", "Código", "Cliente", "Peso (kg)", "Distancia (km)", "Tarifa"};

    private final DecimalFormat formatoMoneda = new DecimalFormat("$ #,##0.##");

    private final JButton btnAgregar = new JButton("Agregar envío");
    private final JButton btnRetirar = new JButton("Retirar envío");
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JTextField txtCodigo = new JTextField(10);
    private final JTextField txtCliente = new JTextField(15);
    private final JTextField txtPeso = new JTextField(10);
    private final JTextField txtDistancia = new JTextField(10);
    private final JComboBox<String> cmbTipo = new JComboBox<>();

    private final DefaultTableModel modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    public VentanaPrincipal(List<String> tipos) {
        super("Operador Logístico");
        tipos.forEach(cmbTipo::addItem);
        construirInterfaz();
        habilitarFormulario(false);
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JToolBar barra = new JToolBar();
        barra.setFloatable(false);
        barra.add(btnAgregar);
        barra.add(btnRetirar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(barra, BorderLayout.NORTH);
        norte.add(construirFormulario(), BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);

        setSize(760, 460);
        setLocationRelativeTo(null);
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;

        agregarFila(panel, gbc, 0, "Código", txtCodigo, "Tipo", cmbTipo);
        agregarFila(panel, gbc, 1, "Cliente", txtCliente, "Distancia en Km", txtDistancia);
        agregarFila(panel, gbc, 2, "Peso (kg)", txtPeso, null, null);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        botones.add(btnGuardar);
        botones.add(btnCancelar);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(botones, gbc);
        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila,
                             String etiqueta1, java.awt.Component campo1,
                             String etiqueta2, java.awt.Component campo2) {
        gbc.gridwidth = 1;
        gbc.gridy = fila;
        gbc.gridx = 0;
        panel.add(new JLabel(etiqueta1), gbc);
        gbc.gridx = 1;
        panel.add(campo1, gbc);
        if (etiqueta2 != null) {
            gbc.gridx = 2;
            panel.add(new JLabel(etiqueta2), gbc);
            gbc.gridx = 3;
            panel.add(campo2, gbc);
        }
    }

    // ---------- Registro de listeners (el controlador los conecta) ----------

    public void onAgregar(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void onRetirar(ActionListener listener) {
        btnRetirar.addActionListener(listener);
    }

    public void onGuardar(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void onCancelar(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    // ---------- Lectura del formulario ----------

    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public String getCliente() {
        return txtCliente.getText().trim();
    }

    public String getPesoTexto() {
        return txtPeso.getText().trim();
    }

    public String getDistanciaTexto() {
        return txtDistancia.getText().trim();
    }

    public String getTipoSeleccionado() {
        return (String) cmbTipo.getSelectedItem();
    }

    // ---------- Acciones de presentación ----------

    public void habilitarFormulario(boolean habilitado) {
        txtCodigo.setEnabled(habilitado);
        txtCliente.setEnabled(habilitado);
        txtPeso.setEnabled(habilitado);
        txtDistancia.setEnabled(habilitado);
        cmbTipo.setEnabled(habilitado);
        btnGuardar.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        if (cmbTipo.getItemCount() > 0) {
            cmbTipo.setSelectedIndex(0);
        }
    }

    /** Pinta la tabla; la tarifa se obtiene polimórficamente desde la referencia Envio. */
    public void mostrarEnvios(List<Envio> envios) {
        modeloTabla.setRowCount(0);
        for (Envio envio : envios) {
            modeloTabla.addRow(new Object[]{
                envio.getTipo(),
                envio.getCodigo(),
                envio.getCliente(),
                envio.getPeso(),
                envio.getDistancia(),
                formatoMoneda.format(envio.calcularTarifa())
            });
        }
    }

    /** @return el código digitado, o null si el usuario canceló. */
    public String pedirCodigoParaRetirar() {
        String codigo = JOptionPane.showInputDialog(this, "Código del envío a retirar:",
                "Retirar envío", JOptionPane.QUESTION_MESSAGE);
        return codigo == null ? null : codigo.trim();
    }

    public void mostrarInformacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
