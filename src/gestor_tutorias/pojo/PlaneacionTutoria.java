package gestor_tutorias.pojo;

import java.time.LocalDate;

public class PlaneacionTutoria {
    // --- ATRIBUTOS DE LA BASE DE DATOS ---
    private int idFechaTutoria;
    private int idPeriodo;
    private int idCarrera;
    private LocalDate fechaTutoria;        // FECHA REAL DE LA SESIÓN
    private LocalDate fechaCierreReportes; // FECHA LÍMITE PARA ENTREGAR
    private int numeroSesion;
    private String temas;

    // --- ATRIBUTOS AUXILIARES (Para mostrar en Tablas/Vistas) ---
    private String periodoNombre; // Ej. "FEB-JUL 2025"
    private String carreraNombre; // Ej. "Ingeniería de Software"

    public PlaneacionTutoria() {
    }

    // Constructor para inserción (Sin ID)
    public PlaneacionTutoria(int idPeriodo, int idCarrera, LocalDate fechaTutoria, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }

    // Constructor completo (Recuperar de BD)
    public PlaneacionTutoria(int idFechaTutoria, int idPeriodo, int idCarrera, LocalDate fechaTutoria, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idFechaTutoria = idFechaTutoria;
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }

    // --- GETTERS Y SETTERS PRINCIPALES ---

    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

    public LocalDate getFechaTutoria() { return fechaTutoria; }
    public void setFechaTutoria(LocalDate fechaTutoria) { this.fechaTutoria = fechaTutoria; }

    public LocalDate getFechaCierreReportes() { return fechaCierreReportes; }
    public void setFechaCierreReportes(LocalDate fechaCierreReportes) { this.fechaCierreReportes = fechaCierreReportes; }

    public int getNumeroSesion() { return numeroSesion; }
    public void setNumeroSesion(int numeroSesion) { this.numeroSesion = numeroSesion; }

    public String getTemas() { return temas; }
    public void setTemas(String temas) { this.temas = temas; }

    // --- GETTERS Y SETTERS AUXILIARES ---

    public String getPeriodoNombre() { return periodoNombre; }
    public void setPeriodoNombre(String periodoNombre) { this.periodoNombre = periodoNombre; }

    public String getCarreraNombre() { return carreraNombre; }
    public void setCarreraNombre(String carreraNombre) { this.carreraNombre = carreraNombre; }

    @Override
    public String toString() {
        // Esto ayuda a ver qué objeto es si lo imprimes en consola
        return "Sesión " + numeroSesion + " - " + fechaTutoria;
    }
}