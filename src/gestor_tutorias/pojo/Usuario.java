package gestor_tutorias.pojo;

public class Usuario {
    private int idUsuario;
    private String matricula;
    private String contrasena;
    private String nombreCompleto;
    private String correo;
    private int idRol;
    private int activo; // 1 = Activo, 0 = Inactivo


    private String rolNombre;

    public Usuario() {
    }

    public Usuario(String matricula, String contrasena, String nombreCompleto, String correo, int idRol) {
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.idRol = idRol;
        this.activo = 1;
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

    public int getActivo() { return activo; }
    public void setActivo(int activo) { this.activo = activo; }

    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}