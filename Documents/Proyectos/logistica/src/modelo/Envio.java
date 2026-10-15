package modelo;

/**
 * Abstracción de un envío. Cada medio de transporte define su propia tarifa.
 */
public abstract class Envio {

    private final String codigo;
    private final String cliente;
    private final double peso;
    private final double distancia;

    protected Envio(String codigo, String cliente, double peso, double distancia) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.peso = peso;
        this.distancia = distancia;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public double getPeso() {
        return peso;
    }

    public double getDistancia() {
        return distancia;
    }

    /** Nombre del medio de transporte, para mostrar en la interfaz. */
    public abstract String getTipo();

    /** Tarifa = distancia * tarifa base por km + peso * recargo por kg. */
    public abstract double calcularTarifa();
}
