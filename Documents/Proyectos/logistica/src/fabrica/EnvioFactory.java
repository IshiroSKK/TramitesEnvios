package fabrica;

import modelo.Envio;

/**
 * Creador abstracto (Factory Method). Cada fábrica concreta decide
 * qué subtipo de Envio instanciar.
 */
public abstract class EnvioFactory {

    /** Factory Method. */
    public abstract Envio crearEnvio(String codigo, String cliente, double peso, double distancia);

    /** Nombre del tipo de envío que produce esta fábrica (se muestra en la vista). */
    public abstract String getTipo();
}
