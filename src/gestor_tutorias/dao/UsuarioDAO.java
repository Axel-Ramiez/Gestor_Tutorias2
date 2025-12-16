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
        if (conn != null) {
            try {

                String consulta = "SELECT u.*, r.nombre_rol " +
                        "FROM usuario u " +
                        "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                        "WHERE u.matricula = ? AND u.contrasena = ? AND u.activo = 1";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, matricula);
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
                String consulta = "SELECT u.*, r.nombre_rol " +
                        "FROM usuario u " +
                        "INNER JOIN rol r ON u.id_rol = r.id_rol " +
                        "WHERE u.activo = 1 " +
                        "ORDER BY u.nombre_completo ASC";
                PreparedStatement ps = conn.prepareStatement(consulta);
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
                String consulta = "INSERT INTO usuario (matricula, contrasena, nombre_completo, correo, id_rol, activo) " +
                        "VALUES (?, ?, ?, ?, ?, 1)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, u.getMatricula());
                ps.setString(2, u.getContrasena());
                ps.setString(3, u.getNombreCompleto());
                ps.setString(4, u.getCorreo());
                ps.setInt(5, u.getIdRol());

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }


    public static boolean editarUsuario(Usuario u) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();
        if (conn != null) {
            try {
                String consulta = "UPDATE usuario SET matricula = ?, nombre_completo = ?, correo = ?, id_rol = ?, contrasena = ? " +
                        "WHERE id_usuario = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, u.getMatricula());
                ps.setString(2, u.getNombreCompleto());
                ps.setString(3, u.getCorreo());
                ps.setInt(4, u.getIdRol());
                ps.setString(5, u.getContrasena());
                ps.setInt(6, u.getIdUsuario());

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
                String consulta = "UPDATE usuario SET activo = 0 WHERE id_usuario = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idUsuario);
                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    private static Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setMatricula(rs.getString("matricula"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasena(rs.getString("contrasena"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setRolNombre(rs.getString("nombre_rol"));
        return u;
    }//
}