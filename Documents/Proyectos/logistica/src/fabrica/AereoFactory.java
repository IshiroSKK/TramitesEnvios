package fabrica;

import modelo.Aereo;
import modelo.Envio;

public class AereoFactory extends EnvioFactory {

    @Override
    public Envio crearEnvio(String codigo, String cliente, double peso, double distancia) {
        return new Aereo(codigo, cliente, peso, distancia);
    }

    @Override
    public String getTipo() {
        return "Aéreo";
    }
}
