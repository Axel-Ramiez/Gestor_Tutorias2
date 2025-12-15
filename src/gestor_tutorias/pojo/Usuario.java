/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor_tutorias.pojo;

public class Usuario {
    private int idUsuario;
    private String matricula;
    private String contrasena;
    private String nombreCompleto;
    private String correo;
    private String carrera;
    private int idRol;
    private boolean activo;


    public Usuario() {
    }


    public Usuario(int idUsuario, String matricula, String contrasena, String nombreCompleto, String correo, String carrera, int idRol) {
        this.idUsuario = idUsuario;
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.carrera = carrera;
        this.idRol = idRol;
        this.activo = true;
    }

    // Getters y Setters
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

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}
