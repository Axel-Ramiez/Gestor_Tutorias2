package gestor_tutorias.excepcion;

public class InyeccionSQL extends RuntimeException {
    public InyeccionSQL(String message) {
        super(message);
    }
}
