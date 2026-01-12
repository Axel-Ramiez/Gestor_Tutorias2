package gestor_tutorias.pojo;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 13/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Modelo de datos para el catálogo Carrera.
 */
public class Carrera {

    private int idCarrera;
    private String nombreCarrera;
    private int idFacultad;

    public Carrera() {
    }

    public Carrera(int idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }

    public Carrera(int idCarrera, String nombreCarrera, int idFacultad) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.idFacultad = idFacultad;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }

    @Override
    public String toString() {
        return nombreCarrera;
    }
}
