package gestor_tutorias.pojo;

import java.time.LocalDate;

public class PlaneacionTutoria {

    private int idPlaneacionTutoria;
    private int idPeriodoEscolar;
    private int idCarrera;
    private LocalDate fechaTutoria;
    private int numeroSesion;
    private String temas;
    private String periodoNombre;
    private String carreraNombre;

    public PlaneacionTutoria() {
    }

    public PlaneacionTutoria(int idPeriodoEscolar, int idCarrera, LocalDate fechaTutoria, int numeroSesion, String temas) {
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }


    public PlaneacionTutoria(int idPlaneacionTutoria, int idPeriodoEscolar, int idCarrera, LocalDate fechaTutoria, int numeroSesion, String temas) {
        this.idPlaneacionTutoria = idPlaneacionTutoria;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idCarrera = idCarrera;
        this.fechaTutoria = fechaTutoria;
        this.numeroSesion = numeroSesion;
        this.temas = temas;
    }


    public int getIdPlaneacionTutoria() {
        return idPlaneacionTutoria;
    }

    public void setIdPlaneacionTutoria(int idPlaneacionTutoria) {
        this.idPlaneacionTutoria = idPlaneacionTutoria;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
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
        return "Sesión " + numeroSesion + " (" + fechaTutoria + ")";
    }
}