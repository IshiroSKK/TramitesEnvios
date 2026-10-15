package fabrica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registro de fábricas por tipo. Permite agregar nuevos medios de transporte
 * (p. ej. Express) registrando una fábrica, sin modificar las existentes
 * ni el controlador (Open/Closed).
 */
public class EnvioFactoryRegistry {

    private final Map<String, EnvioFactory> fabricas = new LinkedHashMap<>();

    public void registrar(EnvioFactory fabrica) {
        fabricas.put(fabrica.getTipo(), fabrica);
    }

    public EnvioFactory obtener(String tipo) {
        EnvioFactory fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalArgumentException("Tipo de envío no soportado: " + tipo);
        }
        return fabrica;
    }

    public List<String> getTipos() {
        return new ArrayList<>(fabricas.keySet());
    }
}
