package modelo;

public class Maritimo extends Envio {

    private static final double TARIFA_BASE_POR_KM = 800;
    private static final double RECARGO_POR_KG = 1000;

    public Maritimo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public String getTipo() {
        return "Marítimo";
    }

    @Override
    public double calcularTarifa() {
        return getDistancia() * TARIFA_BASE_POR_KM + getPeso() * RECARGO_POR_KG;
    }
}
