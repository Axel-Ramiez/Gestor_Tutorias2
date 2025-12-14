package gestor_tutorias.excepcion;

public class AccionIncapazPorElementoEliminado extends RuntimeException {
    public AccionIncapazPorElementoEliminado(String message) {
        super(message);
    }
}
