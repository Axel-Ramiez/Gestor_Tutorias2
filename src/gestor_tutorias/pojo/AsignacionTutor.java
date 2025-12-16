package gestor_tutorias.pojo;

public class AsignacionTutor {
    private int idAsignacion;
    private int idTutor;
    private int idEstudiante;
    private int idPeriodo; // Mapea directamente a la clave foránea id_periodo

    public AsignacionTutor() {
    }

    public AsignacionTutor(int idTutor, int idEstudiante, int idPeriodo) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
    }


    public AsignacionTutor(int idAsignacion, int idTutor, int idEstudiante, int idPeriodo) {
        this.idAsignacion = idAsignacion;
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
    }


    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }


    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }


    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }


    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }
}