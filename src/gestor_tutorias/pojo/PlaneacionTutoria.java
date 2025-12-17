package gestor_tutorias.pojo;

import java.time.LocalDate;

public class PlaneacionTutoria {
    private int idFechaTutoria;
    private int idPeriodo;
    private int idCarrera;
    private LocalDate fechaTutoria;
    private LocalDate fechaCierreReportes;
    private int numeroSesion;
    private String temas;

    private String periodoNombre;
    private String carreraNombre;

    public PlaneacionTutoria() {
    }

    public PlaneacionTutoria(int idPeriodo, int idCarrera, LocalDate fechaTutoria, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }

    public PlaneacionTutoria(int idFechaTutoria, int idPeriodo, int idCarrera, LocalDate fechaTutoria, LocalDate fechaCierreReportes, int numeroSesion, String temas) {
        this.idFechaTutoria = idFechaTutoria;
        this.idPeriodo = idPeriodo;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.fechaCierreReportes = fechaCierreReportes;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }


    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }
    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }
    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public LocalDate getFechaTutoria() {
        return fechaTutoria;
    }
    public void setFechaTutoria(LocalDate fechaTutoria) {
        this.fechaTutoria = fechaTutoria;
    }

    public LocalDate getFechaCierreReportes() {
        return fechaCierreReportes;
    }
    public void setFechaCierreReportes(LocalDate fechaCierreReportes) {
        this.fechaCierreReportes = fechaCierreReportes;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }
    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public String getTemas() {
        return temas;
    }
    public void setTemas(String temas) {
        this.temas = temas;
    }


    public String getPeriodoNombre() {
        return periodoNombre;
    }
    public void setPeriodoNombre(String periodoNombre) {
        this.periodoNombre = periodoNombre;
    }

    public String getCarreraNombre() {
        return carreraNombre;
    }
    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }

    @Override
    public String toString() {
        return "Sesión " + numeroSesion + " - " + fechaTutoria;
    }
}