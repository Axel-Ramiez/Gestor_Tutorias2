package gestor_tutorias.pojo;

import java.time.LocalDate;

public class PeriodoEscolar {

    private int idPeriodoEscolar;
    private String nombrePeriodoEscolar;
    private LocalDate fechaInicioPeriodoEscolar;
    private LocalDate fechaFinPeriodoEscolar;
    private boolean activoPeriodoEscolar;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(int idPeriodoEscolar,
                          String nombrePeriodoEscolar,
                          LocalDate fechaInicioPeriodoEscolar,
                          LocalDate fechaFinPeriodoEscolar,
                          boolean activoPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
        this.fechaInicioPeriodoEscolar = fechaInicioPeriodoEscolar;
        this.fechaFinPeriodoEscolar = fechaFinPeriodoEscolar;
        this.activoPeriodoEscolar = activoPeriodoEscolar;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getNombrePeriodoEscolar() {
        return nombrePeriodoEscolar;
    }

    public void setNombrePeriodoEscolar(String nombrePeriodoEscolar) {
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
    }

    public LocalDate getFechaInicioPeriodoEscolar() {
        return fechaInicioPeriodoEscolar;
    }

    public void setFechaInicioPeriodoEscolar(LocalDate fechaInicioPeriodoEscolar) {
        this.fechaInicioPeriodoEscolar = fechaInicioPeriodoEscolar;
    }

    public LocalDate getFechaFinPeriodoEscolar() {
        return fechaFinPeriodoEscolar;
    }

    public void setFechaFinPeriodoEscolar(LocalDate fechaFinPeriodoEscolar) {
        this.fechaFinPeriodoEscolar = fechaFinPeriodoEscolar;
    }

    public boolean isActivoPeriodoEscolar() {
        return activoPeriodoEscolar;
    }

    public void setActivoPeriodoEscolar(boolean activoPeriodoEscolar) {
        this.activoPeriodoEscolar = activoPeriodoEscolar;
    }

    @Override
    public String toString() {
        return nombrePeriodoEscolar;
    }
}
