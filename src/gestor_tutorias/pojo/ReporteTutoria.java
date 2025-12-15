package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstadoReporte;

import java.time.LocalDateTime;

public class ReporteTutoria {
    private int idReporte;
    private int idAsignacion;
    private int idFechaTutoria;
    private boolean asistio;
    private String comentariosAsistencia;
    private String observacionesTutor;
    private LocalDateTime fechaRegistro;
    private EstadoReporte estado;
    private String retroalimentacionCoord;
    private LocalDateTime fechaRevision;

    public ReporteTutoria() {
    }

    
    
    public ReporteTutoria(int idReporte, int idAsignacion, int idFechaTutoria, boolean asistio, String comentariosAsistencia, String observacionesTutor, LocalDateTime fechaRegistro, EstadoReporte estado, String retroalimentacionCoord, LocalDateTime fechaRevision) {
        this.idReporte = idReporte;
        this.idAsignacion = idAsignacion;
        this.idFechaTutoria = idFechaTutoria;
        this.asistio = asistio;
        this.comentariosAsistencia = comentariosAsistencia;
        this.observacionesTutor = observacionesTutor;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.retroalimentacionCoord = retroalimentacionCoord;
        this.fechaRevision = fechaRevision;
    }
   
    


// Getters y Setters

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }

    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public String getComentariosAsistencia() {
        return comentariosAsistencia;
    }

    public void setComentariosAsistencia(String comentariosAsistencia) {
        this.comentariosAsistencia = comentariosAsistencia;
    }

    public String getObservacionesTutor() {
        return observacionesTutor;
    }

    public void setObservacionesTutor(String observacionesTutor) {
        this.observacionesTutor = observacionesTutor;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }

    public String getRetroalimentacionCoord() {
        return retroalimentacionCoord;
    }

    public void setRetroalimentacionCoord(String retroalimentacionCoord) {this.retroalimentacionCoord = retroalimentacionCoord;}

    public LocalDateTime getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
    
}
