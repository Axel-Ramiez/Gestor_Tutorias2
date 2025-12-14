package gestor_tutorias.excepcion;

public class LongitudInsuficiente extends RuntimeException {
    public LongitudInsuficiente(String message) {
        super(message);
    }
}
