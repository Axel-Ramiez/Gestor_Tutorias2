package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    public static Usuario iniciarSesion(String matricula, String password) throws SQLException {
        Usuario usuario = null;
        Connection conn = ConexionBD.abrirConexion();

        if (conn == null) {
            throw new SQLException("No se pudo conectar con la base de datos.");
        }

        String consulta = "SELECT * FROM usuarios WHERE matricula = ? AND contrasena = ? AND activo = 1";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, matricula);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } finally {
            ConexionBD.cerrarConexion();
        }
        return usuario;
    }


    public static boolean registrarUsuario(Usuario usuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        String consulta = "INSERT INTO usuarios (matricula, contrasena, nombre_completo, correo, carrera, id_rol, activo) VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, usuario.getMatricula());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getCarrera());
            ps.setInt(6, usuario.getIdRol());

            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion();
        }
    }


    public static boolean editarUsuario(Usuario usuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        String consulta = "UPDATE usuarios SET matricula = ?, nombre_completo = ?, correo = ?, carrera = ?, id_rol = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, usuario.getMatricula());
            ps.setString(2, usuario.getNombreCompleto());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getCarrera());
            ps.setInt(5, usuario.getIdRol());
            ps.setInt(6, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion();
        }
    }


    public static boolean eliminarUsuario(int idUsuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        String consulta = "UPDATE usuarios SET activo = 0 WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion();
        }
    }


    public static List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        String consulta = "SELECT * FROM usuarios WHERE activo = 1";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } finally {
            ConexionBD.cerrarConexion();
        }
        return usuarios;
    }


    private static Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setMatricula(rs.getString("matricula"));
        u.setContrasena(rs.getString("contrasena"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setCorreo(rs.getString("correo"));
        u.setCarrera(rs.getString("carrera"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
