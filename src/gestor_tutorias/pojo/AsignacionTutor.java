package gestor_tutorias.pojo;

public class AsignacionTutor {
    private int idAsignacion;
    private int idTutor;
    private int idEstudiante;
    private int idPeriodo; // Mapea directamente a la clave foránea id_periodo

    public AsignacionTutor() {
    }

    // Constructor para la inserción (sin idAsignacion)
    public AsignacionTutor(int idTutor, int idEstudiante, int idPeriodo) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
    }

    // Constructor completo (útil para recuperar de la DB)
    public AsignacionTutor(int idAsignacion, int idTutor, int idEstudiante, int idPeriodo) {
        this.idAsignacion = idAsignacion;
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
    }

    // --- GETTERS Y SETTERS ---

    // id_asignacion (int NOT NULL AUTO_INCREMENT) -> idAsignacion
    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }

    // id_tutor (int NOT NULL) -> idTutor
    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }

    // id_estudiante (int NOT NULL) -> idEstudiante
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    // id_periodo (int NOT NULL) -> idPeriodo
    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }
}