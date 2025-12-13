package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.Enum.TipoProblematica;

public class Problematica {
    private int idProblematica;
    private int idReporte;
    private TipoProblematica tipo;       // Enum
    private String descripcion;
    private EstatusProblematica estatus; // Enum

    public Problematica() {
    }

    public Problematica(int idProblematica, int idReporte, TipoProblematica tipo, String descripcion, EstatusProblematica estatus) {
        this.idProblematica = idProblematica;
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }
    




// Getters y Setters

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

    public TipoProblematica getTipo() {
        return tipo;
    }

    public void setTipo(TipoProblematica tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstatusProblematica getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusProblematica estatus) {
        this.estatus = estatus;
    }


}
