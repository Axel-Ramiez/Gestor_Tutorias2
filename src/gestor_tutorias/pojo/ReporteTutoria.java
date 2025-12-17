package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstadoReporte;

public class ReporteTutoria {

    private int idReporte;
    private int idTutor;
    private int idEstudiante;
    private int idFechaTutoria;
    private String reporte;
    private String respuestaCoordinador;
    private boolean asistencia; // Mapea a tinyint(1)
    private EstadoReporte estado;

    // Variables auxiliares para visualización en UI (Joins en SQL)
    private String nombreTutor;
    private String nombreEstudiante;
    private String fecha;
    private String periodoEscolar;

    public ReporteTutoria() {
    }

    // Constructor para insertar nuevo reporte (Sin ID)
    public ReporteTutoria(int idTutor, int idEstudiante, int idFechaTutoria, String reporte,
                          String respuestaCoordinador, boolean asistencia, EstadoReporte estado) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
        this.idFechaTutoria = idFechaTutoria;
        this.reporte = reporte;
        this.respuestaCoordinador = respuestaCoordinador;
        this.asistencia = asistencia;
        this.estado = (estado != null) ? estado : EstadoReporte.PENDIENTE;
    }

    // Constructor completo para recuperar de la BD
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

    // --- GETTERS Y SETTERS AUXILIARES ---
    public String getNombreTutor() { return nombreTutor; }
    public void setNombreTutor(String nombreTutor) { this.nombreTutor = nombreTutor; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getPeriodoEscolar() { return periodoEscolar; }
    public void setPeriodoEscolar(String periodoEscolar) { this.periodoEscolar = periodoEscolar; }
}

