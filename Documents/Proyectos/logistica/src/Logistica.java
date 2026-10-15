import controlador.LogisticaController;
import fabrica.AereoFactory;
import fabrica.EnvioFactoryRegistry;
import fabrica.MaritimoFactory;
import fabrica.TerrestreFactory;
import javax.swing.SwingUtilities;
import servicio.EnvioService;
import servicio.EnvioServiceImpl;
import vista.VentanaPrincipal;

/**
 * Punto de entrada y raíz de composición: aquí se registran las fábricas.
 * Para agregar un medio nuevo (p. ej. Express) basta con crear la clase Express,
 * su ExpressFactory y añadir una línea de registro aquí.
 */
public class Logistica {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnvioFactoryRegistry fabricas = new EnvioFactoryRegistry();
            fabricas.registrar(new TerrestreFactory());
            fabricas.registrar(new AereoFactory());
            fabricas.registrar(new MaritimoFactory());
            // fabricas.registrar(new ExpressFactory());   // futura extensión

            EnvioService servicio = new EnvioServiceImpl();
            VentanaPrincipal vista = new VentanaPrincipal(fabricas.getTipos());
            new LogisticaController(vista, servicio, fabricas);
            vista.setVisible(true);
        });
    }
}
