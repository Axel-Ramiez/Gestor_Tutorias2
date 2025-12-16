package gestor_tutorias.pojo;

public class PeriodoEscolar {
    private int idPeriodo;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private int activo;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(int idPeriodo, String nombre) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public int getActivo() { return activo; }
    public void setActivo(int activo) { this.activo = activo; }


    @Override
    public String toString() {
        return nombre;
    }
}