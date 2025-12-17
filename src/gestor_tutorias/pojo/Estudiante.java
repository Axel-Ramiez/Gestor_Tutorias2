package gestor_tutorias.pojo;

public class Estudiante {
    private int idEstudiante;
    private String matricula;
    private String nombreCompleto;
    private String correo;
    private int idCarrera;
    private int semestre;
    private int activo;
    private int riesgo;
    private int idTutor;
    private String tutorNombre;
    private String carreraNombre;

    public Estudiante() {
    }

    public Estudiante(String matricula, String nombreCompleto, String correo, int idCarrera, int semestre, int riesgo) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.idCarrera = idCarrera;
        this.semestre = semestre;
        this.riesgo = riesgo;
        this.activo = 1;
    }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public int getActivo() { return activo; }
    public void setActivo(int activo) { this.activo = activo; }

    public int getRiesgo() { return riesgo; }
    public void setRiesgo(int riesgo) {
        this.riesgo = riesgo;
    }

    public String getCarreraNombre() {
        return carreraNombre;
    }
    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }

    public int getIdTutor() { return idTutor; }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }
    public String getTutorNombre() {
        return tutorNombre;
    }

    public void setTutorNombre(String tutorNombre) {
        this.tutorNombre = tutorNombre;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + matricula + ")";
    }
}