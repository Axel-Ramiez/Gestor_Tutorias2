package gestor_tutorias.pojo;

import java.sql.Time;

public class HorarioTutoria { // Se renombra a HorarioTutoria para que sea más específico
    private int idHorario;
    private int idTutor;
    private int idFechaTutoria; // Mapea a id_fecha_tutoria
    private Integer idEstudiante; // Mapea a id_estudiante (Integer para permitir NULL)
    private Time horaInicio;
    private Time horaFin;

    public HorarioTutoria() {
    }

    // Constructor para la inserción de un slot disponible (sin idHorario y sin idEstudiante)
    public HorarioTutoria(int idTutor, int idFechaTutoria, Time horaInicio, Time horaFin) {
        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        // idEstudiante queda como NULL por defecto
    }

    // Constructor completo (útil para recuperar de la DB)
    public HorarioTutoria(int idHorario, int idTutor, int idFechaTutoria, Integer idEstudiante, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.idTutor = idTutor;
        this.idFechaTutoria = idFechaTutoria;
        this.idEstudiante = idEstudiante;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // --- GETTERS Y SETTERS ---

    // id_horario (int NOT NULL AUTO_INCREMENT) -> idHorario
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    // id_tutor (int NOT NULL) -> idTutor
    public int getIdTutor() { return idTutor; }
    public void setIdTutor(int idTutor) { this.idTutor = idTutor; }

    // id_fecha_tutoria (int NOT NULL) -> idFechaTutoria
    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    // id_estudiante (int DEFAULT NULL) -> idEstudiante
    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    // hora_inicio (time NOT NULL) -> horaInicio
    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    // hora_fin (time NOT NULL) -> horaFin
    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }
}