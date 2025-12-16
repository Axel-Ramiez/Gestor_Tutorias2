package gestor_tutorias.pojo;

import java.time.LocalDate;

public class PlaneacionTutoria {
    private int idFechaTutoria;
    private int idPeriodo;
    private int idCarrera;
    private LocalDate fechaCierreReportes;
    private int numeroSesion;
    private String temas;

    public PlaneacionTutoria() {
    }
    // Constructor para la inserción (sin idFechaTutoria)
    public PlaneacionTutoria(int idPeriodo, int idCarrera, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }

    // Constructor completo (útil para recuperar de la DB)
    public PlaneacionTutoria(int idFechaTutoria, int idPeriodo, int idCarrera, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idFechaTutoria = idFechaTutoria;
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }

    // --- GETTERS Y SETTERS ---

    // id_fecha_tutoria (int NOT NULL AUTO_INCREMENT) -> idFechaTutoria
    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    // id_periodo (int NOT NULL) -> idPeriodo
    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    // id_carrera (int NOT NULL) -> idCarrera
    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

    // fecha_cierre_reportes (date NOT NULL) -> fechaCierreReportes
    public LocalDate getFechaCierreReportes() { return fechaCierreReportes; }
    public void setFechaCierreReportes(LocalDate fechaCierreReportes) { this.fechaCierreReportes = fechaCierreReportes; }

    // numero_sesion (int NOT NULL) -> numeroSesion
    public int getNumeroSesion() { return numeroSesion; }
    public void setNumeroSesion(int numeroSesion) { this.numeroSesion = numeroSesion; }

    // temas (text NOT NULL) -> temas
    public String getTemas() { return temas; }
    public void setTemas(String temas) { this.temas = temas; }
}