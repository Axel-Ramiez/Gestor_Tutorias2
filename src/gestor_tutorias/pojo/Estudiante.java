package gestor_tutorias.pojo;

public class Estudiante {

    private int idEstudiante;
    private String matriculaEstudiante;
    private String nombreEstudiante;
    private String apellidoPaternoEstudiante;
    private String apellidoMaternoEstudiante;
    private String correoEstudiante;
    private int semestreEstudiante;
    private int activoEstudiante;
    private int riesgoEstudiante;
    private int idCarrera;
    private Integer idUsuario;
    private String carreraNombre;
    private String tutorNombre;

    public Estudiante() {
    }

    public Estudiante(String matriculaEstudiante, String nombreEstudiante,
                      String apellidoPaternoEstudiante, String apellidoMaternoEstudiante,
                      String correoEstudiante, int semestreEstudiante,
                      int riesgoEstudiante, int idCarrera) {

        this.matriculaEstudiante = matriculaEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaternoEstudiante = apellidoPaternoEstudiante;
        this.apellidoMaternoEstudiante = apellidoMaternoEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.semestreEstudiante = semestreEstudiante;
        this.riesgoEstudiante = riesgoEstudiante;
        this.idCarrera = idCarrera;
        this.activoEstudiante = 1;
    }


    public int getIdEstudiante() {
        return idEstudiante;
    }
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMatriculaEstudiante() {
        return matriculaEstudiante;
    }
    public void setMatriculaEstudiante(String matriculaEstudiante) {
        this.matriculaEstudiante = matriculaEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoPaternoEstudiante() {
        return apellidoPaternoEstudiante;
    }
    public void setApellidoPaternoEstudiante(String apellidoPaternoEstudiante) {
        this.apellidoPaternoEstudiante = apellidoPaternoEstudiante;
    }

    public String getApellidoMaternoEstudiante() {
        return apellidoMaternoEstudiante;
    }
    public void setApellidoMaternoEstudiante(String apellidoMaternoEstudiante) {
        this.apellidoMaternoEstudiante = apellidoMaternoEstudiante;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }
    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public int getSemestreEstudiante() {
        return semestreEstudiante;
    }
    public void setSemestreEstudiante(int semestreEstudiante) {
        this.semestreEstudiante = semestreEstudiante;
    }

    public int getActivoEstudiante() {
        return activoEstudiante;
    }
    public void setActivoEstudiante(int activoEstudiante) {
        this.activoEstudiante = activoEstudiante;
    }

    public int getRiesgoEstudiante() {
        return riesgoEstudiante;
    }
    public void setRiesgoEstudiante(int riesgoEstudiante) {
        this.riesgoEstudiante = riesgoEstudiante;
    }

    public int getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getCarreraNombre() {
        return carreraNombre;
    }
    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }

    public String getTutorNombre() {
        return tutorNombre;
    }
    public void setTutorNombre(String tutorNombre) {
        this.tutorNombre = tutorNombre;
    }

    @Override
    public String toString() {
        return nombreEstudiante + " " + apellidoPaternoEstudiante + " (" + matriculaEstudiante + ")";
    }
}
