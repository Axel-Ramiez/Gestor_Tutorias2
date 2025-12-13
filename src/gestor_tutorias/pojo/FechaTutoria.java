package gestor_tutorias.pojo;

import java.time.LocalDate;

public class FechaTutoria {
    private int idFecha;
    private int numeroSesion;
    private LocalDate fechaCierre;
    private String periodoEscolar;
    private String titulo;

    public FechaTutoria() {
    }

    public FechaTutoria(int idFecha, int numeroSesion, LocalDate fechaCierre, String periodoEscolar, String titulo) {
        this.idFecha = idFecha;
        this.numeroSesion = numeroSesion;
        this.fechaCierre = fechaCierre;
        this.periodoEscolar = periodoEscolar;
        this.titulo = titulo;
    }
    
    
    
    // Getters y Setters

    public int getIdFecha() {
        return idFecha;
    }

    public void setIdFecha(int idFecha) {
        this.idFecha = idFecha;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
