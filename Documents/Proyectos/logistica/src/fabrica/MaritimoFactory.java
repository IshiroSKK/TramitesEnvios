package fabrica;

import modelo.Envio;
import modelo.Maritimo;

public class MaritimoFactory extends EnvioFactory {

    @Override
    public Envio crearEnvio(String codigo, String cliente, double peso, double distancia) {
        return new Maritimo(codigo, cliente, peso, distancia);
    }

    @Override
    public String getTipo() {
        return "Marítimo";
    }
}
