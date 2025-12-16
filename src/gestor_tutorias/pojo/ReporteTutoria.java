package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstadoReporte;

public class ReporteTutoria {
    private int idReporte;
    private int idTutor;
    private int idEstudiante;
    private int idFechaTutoria;
    private String reporte;
    private String respuestaCoordinador;
    private boolean asistencia;
    private EstadoReporte estado;

    public ReporteTutoria() {
    }

    // Constructor para inserción (sin idReporte)
    public ReporteTutoria(int idTutor, int idEstudiante, int idFechaTutoria, String reporte,
                          String respuestaCoordinador, boolean asistencia) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idFechaTutoria = idFechaTutoria;
        this.reporte = reporte;
        this.respuestaCoordinador = respuestaCoordinador;
        this.asistencia = asistencia;
        // 'estado' se deja para el valor por defecto de la DB ('Pendiente')
    }

    // Constructor completo (útil para recuperar de la DB)
    public ReporteTutoria(int idReporte, int idTutor, int idEstudiante, int idFechaTutoria,
                          String reporte, String respuestaCoordinador, boolean asistencia, EstadoReporte estado) {
        this.idReporte = idReporte;
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idFechaTutoria = idFechaTutoria;
        this.reporte = reporte;
        this.respuestaCoordinador = respuestaCoordinador;
        this.asistencia = asistencia;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdReporte() { return idReporte; }
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }

    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    public String getReporte() { return reporte; }
    public void setReporte(String reporte) { this.reporte = reporte; }

    public String getRespuestaCoordinador() { return respuestaCoordinador; }
    public void setRespuestaCoordinador(String respuestaCoordinador) { this.respuestaCoordinador = respuestaCoordinador; }

    public boolean isAsistencia() { return asistencia; }
    public void setAsistencia(boolean asistencia) { this.asistencia = asistencia; }

    public EstadoReporte getEstado() { return estado; }
    public void setEstado(EstadoReporte estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "ReporteTutoria{" +
                "idReporte=" + idReporte +
                ", idTutor=" + idTutor +
                ", idEstudiante=" + idEstudiante +
                ", idFechaTutoria=" + idFechaTutoria +
                ", reporte='" + reporte + '\'' +
                ", respuestaCoordinador='" + respuestaCoordinador + '\'' +
                ", asistencia=" + asistencia +
                ", estado=" + estado +
                '}';
    }
}

