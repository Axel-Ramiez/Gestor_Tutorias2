package gestor_tutorias.pojo;

import gestor_tutorias.Enum.EstatusProblematica;

public class Problematica {
    private int idProblematica;
    private int idReporte;
    private String titulo; // Mapea a 'titulo' en la DB
    private String descripcion;
    private Integer idExperienciaEducativa; // Puede ser NULL, por eso se usa Integer
    private EstatusProblematica estado; // Mapea a 'estado' en la DB

    public Problematica() {
    }
    // Constructor para la inserción (sin idProblematica y sin estado, usando el valor por defecto de la DB)
    public Problematica(int idReporte, String titulo, String descripcion, Integer idExperienciaEducativa) {
        this.idReporte = idReporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idExperienciaEducativa = idExperienciaEducativa;
        // El campo 'estado' se deja para el valor por defecto de la DB ('Pendiente')
    }

    // Constructor completo (útil para recuperar de la DB)
    public Problematica(int idProblematica, int idReporte, String titulo, String descripcion, Integer idExperienciaEducativa, EstatusProblematica estado) {
        this.idProblematica = idProblematica;
        this.idReporte = idReporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---

    // id_problematica (int NOT NULL AUTO_INCREMENT) -> idProblematica
    public int getIdProblematica() { return idProblematica; }
    public void setIdProblematica(int idProblematica) { this.idProblematica = idProblematica; }

    // id_reporte (int NOT NULL) -> idReporte
    public int getIdReporte() { return idReporte; }
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }

    // titulo (varchar(100) NOT NULL) -> titulo
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    // descripcion (text NOT NULL) -> descripcion
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // id_experiencia_educativa (int DEFAULT NULL) -> idExperienciaEducativa
    public Integer getIdExperienciaEducativa() { return idExperienciaEducativa; }
    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) { this.idExperienciaEducativa = idExperienciaEducativa; }

    // estado (ENUM('Pendiente', 'Atendida') NOT NULL DEFAULT 'Pendiente') -> estado
    public EstatusProblematica getEstado() { return estado; }
    public void setEstado(EstatusProblematica estado) { this.estado = estado; }
}
