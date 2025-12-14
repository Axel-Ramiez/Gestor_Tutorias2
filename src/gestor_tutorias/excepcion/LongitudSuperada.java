package gestor_tutorias.excepcion;

public class LongitudSuperada extends RuntimeException {
    public LongitudSuperada(String message) {
        super(message);
    }
}
