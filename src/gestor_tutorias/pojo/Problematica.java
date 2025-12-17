package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstatusProblematica;

public class Problematica {

    private int idProblematica;
    private int idReporte;
    private String titulo;
    private String descripcion;
    private Integer idExperienciaEducativa;
    private EstatusProblematica estado;


    public Problematica() {
        this.estado = EstatusProblematica.PENDIENTE;
    }

    public Problematica(int idReporte, String titulo,
                        String descripcion, Integer idExperienciaEducativa,
                        EstatusProblematica estado) {

        this.idReporte = idReporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.estado = (estado != null) ? estado : EstatusProblematica.PENDIENTE;
    }

    public Problematica(int idProblematica, int idReporte,
                        String titulo, String descripcion,
                        Integer idExperienciaEducativa,
                        EstatusProblematica estado) {

        this.idProblematica = idProblematica;
        this.idReporte = idReporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.estado = estado;
    }


    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
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

    public Integer getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public EstatusProblematica getEstado() {
        return estado;
    }

    public void setEstado(EstatusProblematica estado) {
        this.estado = estado;
    }
}
