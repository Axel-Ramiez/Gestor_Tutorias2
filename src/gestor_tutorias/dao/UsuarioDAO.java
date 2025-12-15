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

    // --- 1. LOGIN (Iniciar Sesión) ---
    public static Usuario iniciarSesion(String matricula, String password) throws SQLException {
        Usuario usuario = null;
        Connection conn = ConexionBD.abrirConexion();

        if (conn == null) {
            throw new SQLException("No hay conexión con la base de datos.");
        }

        // Buscamos por matrícula (o número de personal) y contraseña
        String consulta = "SELECT * FROM usuarios WHERE matricula = ? AND contrasena = ? AND activo = 1";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, matricula);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } finally {
            ConexionBD.cerrarConexion(conn);
        }
        return usuario;
    }

    // --- 2. REGISTRAR USUARIO (Solo Admin, Coord, Tutor) ---
    public static boolean registrarUsuario(Usuario usuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        // NOTA: Ya no pedimos carrera, solo rol.
        String consulta = "INSERT INTO usuarios (matricula, contrasena, nombre_completo, correo, id_rol, activo) VALUES (?, ?, ?, ?, ?, 1)";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, usuario.getMatricula());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getCorreo());
            ps.setInt(5, usuario.getIdRol());

            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion(conn);
        }
    }

    // --- 3. EDITAR USUARIO ---
    public static boolean editarUsuario(Usuario usuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        // Actualizamos nombre, correo y rol. La matrícula no se suele cambiar.
        String consulta = "UPDATE usuarios SET nombre_completo = ?, correo = ?, id_rol = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getCorreo());
            ps.setInt(3, usuario.getIdRol());
            ps.setInt(4, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion(conn);
        }
    }

    // --- 4. ELIMINAR USUARIO (Borrado Lógico) ---
    public static boolean eliminarUsuario(int idUsuario) throws SQLException {
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        String consulta = "UPDATE usuarios SET activo = 0 WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } finally {
            ConexionBD.cerrarConexion(conn);
        }
    }

    // --- 5. OBTENER TODOS (Para la tabla de gestión) ---
    public static List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();
        if (conn == null) throw new SQLException("Sin conexión.");

        // Traemos todos los activos ordenados por nombre
        String consulta = "SELECT * FROM usuarios WHERE activo = 1 ORDER BY nombre_completo ASC";

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } finally {
            ConexionBD.cerrarConexion(conn);
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
        u.setIdRol(rs.getInt("id_rol")); // Ya no hay carrera aquí
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
