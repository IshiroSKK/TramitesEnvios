package servicio;

import java.util.List;
import modelo.Envio;

/** Contrato de las operaciones sobre la colección de envíos. */
public interface EnvioService {

    /** @throws IllegalArgumentException si ya existe un envío con el mismo código. */
    void agregar(Envio envio);

    /** @return true si existía y fue eliminado; false si el código no existe. */
    boolean retirar(String codigo);

    boolean existe(String codigo);

    List<Envio> listar();
}
