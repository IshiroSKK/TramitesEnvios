package fabrica;

import modelo.Envio;
import modelo.Terrestre;

public class TerrestreFactory extends EnvioFactory {

    @Override
    public Envio crearEnvio(String codigo, String cliente, double peso, double distancia) {
        return new Terrestre(codigo, cliente, peso, distancia);
    }

    @Override
    public String getTipo() {
        return "Terrestre";
    }
}
