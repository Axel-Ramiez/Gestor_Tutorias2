package gestor_tutorias.pojo;

public class AsignacionEstudiante
{
    private int idAsignacion;
    private int idTutor;
    private int idEstudiante;
    private String periodoEscolar;
    private boolean activo;

    public AsignacionEstudiante() {
    }

    public AsignacionEstudiante(int idAsignacion, int idTutor, int idEstudiante, String periodoEscolar, boolean activo) {
        this.idAsignacion = idAsignacion;
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.periodoEscolar = periodoEscolar;
        this.activo = activo;
    }
    
    
    // Getters y Setters

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
