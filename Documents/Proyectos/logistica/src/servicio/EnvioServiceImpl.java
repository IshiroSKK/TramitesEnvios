package servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Envio;

public class EnvioServiceImpl implements EnvioService {

    private final List<Envio> envios = new ArrayList<>();

    @Override
    public void agregar(Envio envio) {
        if (existe(envio.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un envío con el código " + envio.getCodigo());
        }
        envios.add(envio);
    }

    @Override
    public boolean retirar(String codigo) {
        return envios.removeIf(envio -> envio.getCodigo().equals(codigo));
    }

    @Override
    public boolean existe(String codigo) {
        return envios.stream().anyMatch(envio -> envio.getCodigo().equals(codigo));
    }

    @Override
    public List<Envio> listar() {
        return Collections.unmodifiableList(envios);
    }
}
