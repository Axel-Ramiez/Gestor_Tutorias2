package gestor_tutorias.pojo;

public class Estudiante {
    private int idEstudiante;
    private String matricula;
    private String nombreCompleto;
    private String correoInstitucional;
    private int semestre;
    private String carrera;
    private boolean situacionRiesgo;

    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String matricula, String nombreCompleto, String correoInstitucional, int semestre, String carrera, boolean situacionRiesgo) {
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.correoInstitucional = correoInstitucional;
        this.semestre = semestre;
        this.carrera = carrera;
        this.situacionRiesgo = situacionRiesgo;
    }

    
    
    // Getters y Setters

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public boolean isSituacionRiesgo() {
        return situacionRiesgo;
    }

    public void setSituacionRiesgo(boolean situacionRiesgo) {
        this.situacionRiesgo = situacionRiesgo;
    }
    
    
}
