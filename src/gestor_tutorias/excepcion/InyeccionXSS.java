package gestor_tutorias.excepcion;

public class InyeccionXSS extends RuntimeException {
    public InyeccionXSS(String message) {
        super(message);
    }
}
