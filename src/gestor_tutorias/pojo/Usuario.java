package gestor_tutorias.pojo;

public class Usuario {
    private int idUsuario;
    private String matricula;
    private String contrasena;
    private String nombreCompleto;
    private String correo;
    private int idRol;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(String matricula, String contrasena, String nombreCompleto, String correo, int idRol) {
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.idRol = idRol;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombreCompleto; // Para que se vea bonito en los ComboBox
    }
}
