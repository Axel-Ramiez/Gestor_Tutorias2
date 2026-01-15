package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 08/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Clase de acceso a datos para la gestión de Usuarios.
 * Maneja el inicio de sesión y operaciones CRUD sobre la tabla 'usuario'.
 */
public class UsuarioDAO {

    private static final String SQL_LOGIN =
            "SELECT u.*, r.nombre_rol FROM usuario u " +
                    "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                    "WHERE u.no_Personal_usuario = ? AND u.contrasena_usuario = ? AND u.activo_usuario = 1";

    private static final String SQL_OBTENER_TODOS =
            "SELECT u.*, r.nombre_rol FROM usuario u " +
                    "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                    "WHERE u.activo_usuario = 1 ORDER BY u.nombre_usuario, u.apellido_paterno_usuario";

    private static final String SQL_REGISTRAR =
            "INSERT INTO usuario (no_Personal_usuario, contrasena_usuario, nombre_usuario, " +
                    "apellido_paterno_usuario, apellido_materno_usuario, correo_usuario, id_rol, activo_usuario) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

    private static final String SQL_OBTENER_TUTORES =
            "SELECT u.*, r.nombre_rol FROM usuario u " +
                    "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                    "WHERE u.activo_usuario = 1 AND u.id_rol = 3 " + // Rol 3 = Tutor
                    "ORDER BY u.nombre_usuario, u.apellido_paterno_usuario";

    private static final String SQL_EDITAR =
            "UPDATE usuario SET no_Personal_usuario = ?, nombre_usuario = ?, " +
                    "apellido_paterno_usuario = ?, apellido_materno_usuario = ?, " +
                    "correo_usuario = ?, id_rol = ?, contrasena_usuario = ? WHERE id_usuario = ?";

    private static final String SQL_ELIMINAR =
            "UPDATE usuario SET activo_usuario = 0 WHERE id_usuario = ?";

    private static final String SQL_VALIDAR_DUPLICADO =
            "SELECT COUNT(*) FROM usuario WHERE no_Personal_usuario = ? AND id_usuario <> ?";

    /**
     * Verifica las credenciales del usuario para permitir el acceso al sistema.
     */
    public static Usuario iniciarSesion(String noPersonal, String password) throws SQLException {
        Usuario usuario = null;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_LOGIN)) {

            ps.setString(1, noPersonal);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }
        }
        return usuario;
    }

    public static List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_OBTENER_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }
        return lista;
    }

    public static boolean registrarUsuario(Usuario u) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_REGISTRAR)) {

            ps.setString(1, u.getNoPersonalUsuario());
            ps.setString(2, u.getContrasenaUsuario());
            ps.setString(3, u.getNombreUsuario());
            ps.setString(4, u.getApellidoPaternoUsuario());
            ps.setString(5, u.getApellidoMaternoUsuario());
            ps.setString(6, u.getCorreoUsuario());
            ps.setInt(7, u.getIdRol());

            return ps.executeUpdate() > 0;
        }
    }

    public static List<Usuario> obtenerTutores() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_OBTENER_TUTORES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }
        return lista;
    }

    public static boolean editarUsuario(Usuario u) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_EDITAR)) {

            ps.setString(1, u.getNoPersonalUsuario());
            ps.setString(2, u.getNombreUsuario());
            ps.setString(3, u.getApellidoPaternoUsuario());
            ps.setString(4, u.getApellidoMaternoUsuario());
            ps.setString(5, u.getCorreoUsuario());
            ps.setInt(6, u.getIdRol());
            ps.setString(7, u.getContrasenaUsuario());
            ps.setInt(8, u.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    public static boolean eliminarUsuario(int idUsuario) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Valida si un número de personal ya existe en otro usuario (para evitar duplicados al editar).
     */
    public static boolean esNoPersonalRegistrado(String noPersonal, int idUsuarioExcluir) throws SQLException {
        boolean existe = false;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_VALIDAR_DUPLICADO)) {

            ps.setString(1, noPersonal);
            ps.setInt(2, idUsuarioExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        }
        return existe;
    }

    private static Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNoPersonalUsuario(rs.getString("no_Personal_usuario"));
        u.setContrasenaUsuario(rs.getString("contrasena_usuario"));
        u.setNombreUsuario(rs.getString("nombre_usuario"));
        u.setApellidoPaternoUsuario(rs.getString("apellido_paterno_usuario"));
        u.setApellidoMaternoUsuario(rs.getString("apellido_materno_usuario"));
        u.setCorreoUsuario(rs.getString("correo_usuario"));
        u.setActivoUsuario(rs.getInt("activo_usuario"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setNombreRol(rs.getString("nombre_rol"));
        return u;
    }
}

