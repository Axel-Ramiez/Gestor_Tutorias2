package gestor_tutorias.pojo;

import java.sql.Time;
public class HorarioTutoria {
    private int idHorario;
    private int idTutor;
    private int idFechaTutoria;
    private Integer idEstudiante;
    private Time horaInicio;
    private Time horaFin;

    public HorarioTutoria() {
    }


    public HorarioTutoria(int idTutor, int idFechaTutoria, Time horaInicio, Time horaFin) {
        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;

    }


    public HorarioTutoria(int idHorario, int idTutor, int idFechaTutoria, Integer idEstudiante, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.idEstudiante = idEstudiante;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }


    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }


    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }


    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }


    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }


    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }
}