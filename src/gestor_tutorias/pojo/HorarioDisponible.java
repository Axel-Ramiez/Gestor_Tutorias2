package gestor_tutorias.pojo;

import java.sql.Time;

public class HorarioDisponible {
    private int idHorario;
    private int idTutor;
    private String diaSemana;
    private Time horaInicio;
    private Time horaFin;

    public HorarioDisponible() {
    }

    public HorarioDisponible(int idHorario, int idTutor, String diaSemana, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.idTutor = idTutor;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    


// Getters y Setters

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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
    
}
