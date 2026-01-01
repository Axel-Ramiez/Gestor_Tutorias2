package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstatusProblematica;

public class Problematica {

    private int idProblematica;
    private int idReporteTutoria;
    private String titulo;
    private String descripcion;
    private Integer idCarrera; // ✅ CORRECTO
    private EstatusProblematica estado;

    public Problematica() {
        this.estado = EstatusProblematica.PENDIENTE;
    }

    public Problematica(int idReporteTutoria, String titulo,
                        String descripcion, Integer idCarrera,
                        EstatusProblematica estado) {

        this.idReporteTutoria = idReporteTutoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idCarrera = idCarrera;
        this.estado = (estado != null) ? estado : EstatusProblematica.PENDIENTE;
    }

    public Problematica(int idProblematica, int idReporteTutoria,
                        String titulo, String descripcion,
                        Integer idCarrera,
                        EstatusProblematica estado) {

        this.idProblematica = idProblematica;
        this.idReporteTutoria = idReporteTutoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idCarrera = idCarrera;
        this.estado = estado;
    }

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public EstatusProblematica getEstado() {
        return estado;
    }

    public void setEstado(EstatusProblematica estado) {
        this.estado = estado;
    }
}
