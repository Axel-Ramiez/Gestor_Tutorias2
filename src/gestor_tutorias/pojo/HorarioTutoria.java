package gestor_tutorias.pojo;

import java.time.LocalTime;

public class HorarioTutoria {

    private int idHorario;
    private int idTutor;
    private int idFechaTutoria;
    private Integer idEstudiante;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private String nombreTutor;
    private String nombreEstudiante;
    private String fechaTutoria;
    private boolean ocupado;


    public HorarioTutoria() {
    }

    public HorarioTutoria(int idTutor, int idFechaTutoria,
                          Integer idEstudiante,
                          LocalTime horaInicio, LocalTime horaFin) {

        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.idEstudiante = idEstudiante;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ocupado = (idEstudiante != null);
    }

    public HorarioTutoria(int idHorario, int idTutor, int idFechaTutoria,
                          Integer idEstudiante,
                          LocalTime horaInicio, LocalTime horaFin) {

        this.idHorario = idHorario;
        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.idEstudiante = idEstudiante;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ocupado = (idEstudiante != null);
    }


    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }

    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
        this.ocupado = (idEstudiante != null);
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
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

    public String getFechaTutoria() {
        return fechaTutoria;
    }

    public void setFechaTutoria(String fechaTutoria) {
        this.fechaTutoria = fechaTutoria;
    }

    public boolean isOcupado() {
        return ocupado;
    }
}
