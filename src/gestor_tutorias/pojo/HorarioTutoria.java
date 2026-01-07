package gestor_tutorias.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioTutoria {

    // ===== CAMPOS BD =====
    private int idHorarioTutoria;
    private LocalDate fechaHorarioTutoria;
    private LocalTime horaInicioHorarioTutoria;
    private LocalTime horaFinHorarioTutoria;

    private int idUsuario;               // tutor (NOT NULL)
    private Integer idEstudiante;        // puede ser NULL
    private Integer idPeriodoEscolar;    // puede ser NULL

    // ===== CAMPOS AUXILIARES (NO BD) =====
    private String nombreTutor;
    private String nombreEstudiante;
    private String nombrePeriodoEscolar;

    // ===== CONSTRUCTORES =====
    public HorarioTutoria() {
    }

    public HorarioTutoria(LocalDate fechaHorarioTutoria,
                          LocalTime horaInicioHorarioTutoria,
                          LocalTime horaFinHorarioTutoria,
                          int idUsuario,
                          Integer idEstudiante,
                          Integer idPeriodoEscolar) {

        this.fechaHorarioTutoria = fechaHorarioTutoria;
        this.horaInicioHorarioTutoria = horaInicioHorarioTutoria;
        this.horaFinHorarioTutoria = horaFinHorarioTutoria;
        this.idUsuario = idUsuario;
        this.idEstudiante = idEstudiante;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    // ===== GETTERS & SETTERS =====

    public int getIdHorarioTutoria() {
        return idHorarioTutoria;
    }

    public void setIdHorarioTutoria(int idHorarioTutoria) {
        this.idHorarioTutoria = idHorarioTutoria;
    }

    public LocalDate getFechaHorarioTutoria() {
        return fechaHorarioTutoria;
    }

    public void setFechaHorarioTutoria(LocalDate fechaHorarioTutoria) {
        this.fechaHorarioTutoria = fechaHorarioTutoria;
    }

    public LocalTime getHoraInicioHorarioTutoria() {
        return horaInicioHorarioTutoria;
    }

    public void setHoraInicioHorarioTutoria(LocalTime horaInicioHorarioTutoria) {
        this.horaInicioHorarioTutoria = horaInicioHorarioTutoria;
    }

    public LocalTime getHoraFinHorarioTutoria() {
        return horaFinHorarioTutoria;
    }

    public void setHoraFinHorarioTutoria(LocalTime horaFinHorarioTutoria) {
        this.horaFinHorarioTutoria = horaFinHorarioTutoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(Integer idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    // ===== AUXILIARES =====

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

    public String getNombrePeriodoEscolar() {
        return nombrePeriodoEscolar;
    }

    public void setNombrePeriodoEscolar(String nombrePeriodoEscolar) {
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
    }

    // ===== LÓGICA =====
    public boolean isOcupado() {
        return idEstudiante != null;
    }
}
