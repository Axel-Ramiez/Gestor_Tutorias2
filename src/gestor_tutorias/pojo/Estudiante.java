package gestor_tutorias.pojo;

public class Estudiante {
    private int idEstudiante;
    private String matricula;
    private String nombreCompleto;
    private String correo;
    private int idCarrera; // Ahora guardamos el ID, no el nombre
    private int semestre;
    private boolean activo;

    public Estudiante() {
    }

    public Estudiante(String matricula, String nombreCompleto, String correo, int idCarrera, int semestre) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.idCarrera = idCarrera;
        this.semestre = semestre;
    }

    // --- GETTERS Y SETTERS ---
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

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombreCompleto + " (" + matricula + ")";
    }
}