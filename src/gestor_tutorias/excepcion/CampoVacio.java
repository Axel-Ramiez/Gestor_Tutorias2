package gestor_tutorias.excepcion;

public class CampoVacio extends RuntimeException {
    public CampoVacio(String message) {
        super(message);
    }
}
