package gestor_tutorias.pojo;

public class Usuario {

    private int idUsuario;
    private String noPersonalUsuario;
    private String contrasenaUsuario;
    private String nombreUsuario;
    private String apellidoPaternoUsuario;
    private String apellidoMaternoUsuario;
    private String correoUsuario;
    private int activoUsuario;
    private int idRol;

    public Usuario() {
    }

    public Usuario(String noPersonalUsuario, String contrasenaUsuario,
                   String nombreUsuario, String apellidoPaternoUsuario,
                   String apellidoMaternoUsuario, String correoUsuario,
                   int idRol) {
        this.noPersonalUsuario = noPersonalUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
        this.correoUsuario = correoUsuario;
        this.idRol = idRol;
        this.activoUsuario = 1;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNoPersonalUsuario() {
        return noPersonalUsuario;
    }
    public void setNoPersonalUsuario(String noPersonalUsuario) {
        this.noPersonalUsuario = noPersonalUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }
    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoPaternoUsuario() {
        return apellidoPaternoUsuario;
    }
    public void setApellidoPaternoUsuario(String apellidoPaternoUsuario) {
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
    }

    public String getApellidoMaternoUsuario() {
        return apellidoMaternoUsuario;
    }
    public void setApellidoMaternoUsuario(String apellidoMaternoUsuario) {
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public int getActivoUsuario() {
        return activoUsuario;
    }
    public void setActivoUsuario(int activoUsuario) {
        this.activoUsuario = activoUsuario;
    }

    public int getIdRol() {
        return idRol;
    }
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return nombreUsuario + " " + apellidoPaternoUsuario;
    }
}
