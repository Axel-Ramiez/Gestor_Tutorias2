package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstadoReporte;

public class ReporteTutoria {
    private int idReporte;
    private int idTutor;
    private int idEstudiante;
    private int idFechaTutoria;
    private String descripcionGeneral;
    private boolean asistencia;
    private EstadoReporte estado;

    public ReporteTutoria() {
    }

    // Constructor para la inserción (sin idReporte)
    public ReporteTutoria(int idTutor, int idEstudiante, int idFechaTutoria, String descripcionGeneral, boolean asistencia) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idFechaTutoria = idFechaTutoria;
        this.descripcionGeneral = descripcionGeneral;
        this.asistencia = asistencia;
        // El campo 'estado' se deja para el valor por defecto de la DB ('Pendiente')
    }

    // Constructor completo (útil para recuperar de la DB)
    public ReporteTutoria(int idReporte, int idTutor, int idEstudiante, int idFechaTutoria, String descripcionGeneral, boolean asistencia, EstadoReporte estado) {
        this.idReporte = idReporte;
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idFechaTutoria = idFechaTutoria;
        this.descripcionGeneral = descripcionGeneral;
        this.asistencia = asistencia;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---

    // id_reporte (int NOT NULL AUTO_INCREMENT) -> idReporte
    public int getIdReporte() { return idReporte; }
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }

    // id_tutor (int NOT NULL) -> idTutor
    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }

    // id_estudiante (int NOT NULL) -> idEstudiante
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    // id_fecha_tutoria (int NOT NULL) -> idFechaTutoria
    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    // descripcion_general (text) -> descripcionGeneral
    public String getDescripcionGeneral() { return descripcionGeneral; }
    public void setDescripcionGeneral(String descripcionGeneral) { this.descripcionGeneral = descripcionGeneral; }

    // asistencia (tinyint(1) DEFAULT '0') -> asistencia
    public boolean isAsistencia() { return asistencia; }
    public void setAsistencia(boolean asistencia) { this.asistencia = asistencia; }

    // estado (ENUM('Pendiente', 'Revisado') NOT NULL DEFAULT 'Pendiente') -> estado
    public EstadoReporte getEstado() { return estado; }
    public void setEstado(EstadoReporte estado) { this.estado = estado; }

    // Puedes añadir equals, hashCode y toString si es necesario.
}
