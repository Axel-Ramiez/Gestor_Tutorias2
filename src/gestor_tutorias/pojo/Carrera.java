package gestor_tutorias.pojo;

public class Carrera {
    private int idCarrera;
    private String nombre;
    private int idFacultad;

    public Carrera() {
    }


    public Carrera(int idCarrera, String nombre) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
    }


    public Carrera(int idCarrera, String nombre, int idFacultad) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.idFacultad = idFacultad;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
