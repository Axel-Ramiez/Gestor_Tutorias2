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

    public static Usuario iniciarSesion(String noPersonal, String password) throws SQLException {
        Usuario usuario = null;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                // CAMBIO 1: Agregamos el INNER JOIN y seleccionamos r.nombre_rol
                String sql =
                        "SELECT u.*, r.nombre_rol " +
                                "FROM usuario u " +
                                "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                                "WHERE u.no_Personal_usuario = ? " +
                                "AND u.contrasena_usuario = ? " +
                                "AND u.activo_usuario = 1";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, noPersonal);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return usuario;
    }

    public static List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String sql =
                        "SELECT u.*, r.nombre_rol " +
                                "FROM usuario u " +
                                "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                                "WHERE u.activo_usuario = 1 " +
                                "ORDER BY u.nombre_usuario, u.apellido_paterno_usuario";

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    lista.add(mapearUsuario(rs));
                }
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    public static boolean registrarUsuario(Usuario u) throws SQLException {

        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String sql =
                        "INSERT INTO usuario " +
                                "(no_Personal_usuario, contrasena_usuario, nombre_usuario, " +
                                "apellido_paterno_usuario, apellido_materno_usuario, " +
                                "correo_usuario, id_rol, activo_usuario) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u.getNoPersonalUsuario());
                ps.setString(2, u.getContrasenaUsuario());
                ps.setString(3, u.getNombreUsuario());
                ps.setString(4, u.getApellidoPaternoUsuario());
                ps.setString(5, u.getApellidoMaternoUsuario());
                ps.setString(6, u.getCorreoUsuario());
                ps.setInt(7, u.getIdRol());

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    public static List<Usuario> obtenerTutores() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {

                String sql =
                        "SELECT u.*, r.nombre_rol " +
                                "FROM usuario u " +
                                "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                                "WHERE u.activo_usuario = 1 AND u.id_rol = 3 " +
                                "ORDER BY u.nombre_usuario, u.apellido_paterno_usuario";

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    lista.add(mapearUsuario(rs));
                }
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    public static boolean editarUsuario(Usuario u) throws SQLException {
        // Este se queda igual
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String sql =
                        "UPDATE usuario SET " +
                                "no_Personal_usuario = ?, " +
                                "nombre_usuario = ?, " +
                                "apellido_paterno_usuario = ?, " +
                                "apellido_materno_usuario = ?, " +
                                "correo_usuario = ?, " +
                                "id_rol = ?, " +
                                "contrasena_usuario = ? " +
                                "WHERE id_usuario = ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u.getNoPersonalUsuario());
                ps.setString(2, u.getNombreUsuario());
                ps.setString(3, u.getApellidoPaternoUsuario());
                ps.setString(4, u.getApellidoMaternoUsuario());
                ps.setString(5, u.getCorreoUsuario());
                ps.setInt(6, u.getIdRol());
                ps.setString(7, u.getContrasenaUsuario());
                ps.setInt(8, u.getIdUsuario());

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    public static boolean eliminarUsuario(int idUsuario) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {

                String sql = "UPDATE usuario SET activo_usuario = 0 WHERE id_usuario = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idUsuario);

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    /* ================= MAPEO ================= */

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

