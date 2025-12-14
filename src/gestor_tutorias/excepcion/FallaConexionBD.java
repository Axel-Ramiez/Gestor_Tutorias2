package gestor_tutorias.excepcion;

public class FallaConexionBD extends RuntimeException {
    public FallaConexionBD(String message) {
        super(message);
    }
}
