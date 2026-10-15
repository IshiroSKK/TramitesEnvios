package controlador;

import fabrica.EnvioFactoryRegistry;
import modelo.Envio;
import servicio.EnvioService;
import vista.VentanaPrincipal;

/**
 * Controlador: atiende los eventos de la vista y coordina servicio y fábricas.
 * Depende de abstracciones (EnvioService, EnvioFactory), no de tipos concretos de envío.
 */
public class LogisticaController {

    private final VentanaPrincipal vista;
    private final EnvioService servicio;
    private final EnvioFactoryRegistry fabricas;

    public LogisticaController(VentanaPrincipal vista, EnvioService servicio, EnvioFactoryRegistry fabricas) {
        this.vista = vista;
        this.servicio = servicio;
        this.fabricas = fabricas;
        conectarEventos();
        refrescarLista();
    }

    private void conectarEventos() {
        vista.onAgregar(e -> vista.habilitarFormulario(true));
        vista.onCancelar(e -> cancelar());
        vista.onGuardar(e -> guardarEnvio());
        vista.onRetirar(e -> retirarEnvio());
    }

    private void cancelar() {
        vista.limpiarFormulario();
        vista.habilitarFormulario(false);
    }

    private void guardarEnvio() {
        try {
            String codigo = vista.getCodigo();
            String cliente = vista.getCliente();
            if (codigo.isEmpty() || cliente.isEmpty()) {
                vista.mostrarError("El código y el cliente son obligatorios.");
                return;
            }
            double peso = leerPositivo(vista.getPesoTexto(), "peso");
            double distancia = leerPositivo(vista.getDistanciaTexto(), "distancia");

            Envio envio = fabricas.obtener(vista.getTipoSeleccionado())
                    .crearEnvio(codigo, cliente, peso, distancia);
            servicio.agregar(envio);

            refrescarLista();
            cancelar();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void retirarEnvio() {
        String codigo = vista.pedirCodigoParaRetirar();
        if (codigo == null) {
            return;
        }
        if (servicio.retirar(codigo)) {
            refrescarLista();
            vista.mostrarInformacion("Envío " + codigo + " retirado.");
        } else {
            vista.mostrarInformacion("El código " + codigo + " no existe.");
        }
    }

    private double leerPositivo(String texto, String campo) {
        try {
            double valor = Double.parseDouble(texto.replace(',', '.'));
            if (valor <= 0) {
                throw new IllegalArgumentException("El " + campo + " debe ser mayor que cero.");
            }
            return valor;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El " + campo + " debe ser un número válido.");
        }
    }

    private void refrescarLista() {
        vista.mostrarEnvios(servicio.listar());
    }
}
