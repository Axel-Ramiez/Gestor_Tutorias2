package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstadoReporte;

import java.time.LocalDate;

public class ReporteTutoria {

    private int idReporte;               // id_reporte_tutoria
    private LocalDate fechaReporte;      // fecha_reporte_tutoria
    private String textoReporte;         // texto_reporte_tutoria
    private String respuestaCoordinador; // respuesta_coordinador
    private boolean asistencia;          // asistencia_reporte_tutoria
    private EstadoReporte estado;         // estado_reporte_tutoria

    private int idUsuario;               // id_usuario
    private int idEstudiante;             // id_estudiante
    private int idPeriodoEscolar;         // id_periodo_escolar


    private String nombreTutor;
    private String nombreEstudiante;
    private String periodoEscolar;

    public ReporteTutoria() {
        this.estado = EstadoReporte.PENDIENTE;
        this.asistencia = false;
    }

    public ReporteTutoria(int idUsuario, int idEstudiante, int idPeriodoEscolar,
                          LocalDate fechaReporte, String textoReporte,
                          String respuestaCoordinador, boolean asistencia,
                          EstadoReporte estado) {

        this.idUsuario = idUsuario;
        this.idEstudiante = idEstudiante;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.fechaReporte = fechaReporte;
        this.textoReporte = textoReporte;
        this.respuestaCoordinador = respuestaCoordinador;
        this.asistencia = asistencia;
        this.estado = (estado != null) ? estado : EstadoReporte.PENDIENTE;
    }

    // Getters y setters ↓↓↓

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getTextoReporte() {
        return textoReporte;
    }

    public void setTextoReporte(String textoReporte) {
        this.textoReporte = textoReporte;
    }

    public String getRespuestaCoordinador() {
        return respuestaCoordinador;
    }

    public void setRespuestaCoordinador(String respuestaCoordinador) {
        this.respuestaCoordinador = respuestaCoordinador;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }
}
