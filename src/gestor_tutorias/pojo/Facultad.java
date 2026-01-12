package gestor_tutorias.pojo;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 13/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Modelo de datos para el catálogo Facultad.
 */
public class Facultad {
    private int idFacultad;
    private String nombreFacultad;

    public Facultad() {
    }

    public Facultad(int idFacultad, String nombreFacultad) {
        this.idFacultad = idFacultad;
        this.nombreFacultad = nombreFacultad;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    @Override
    public String toString() {
        return nombreFacultad;
    }
}
