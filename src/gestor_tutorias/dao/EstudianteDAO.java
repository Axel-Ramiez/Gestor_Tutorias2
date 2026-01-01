package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    /* =========================
       OBTENER TODOS
       ========================= */
    public static List<Estudiante> obtenerTodos() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT e.*, c.nombre_carrera AS nombre_carrera, " +
                                "u.nombre_completo AS nombre_tutor " +
                                "FROM estudiante e " +
                                "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                                "LEFT JOIN usuario u ON e.id_usuario = u.id_usuario " +
                                "WHERE e.activo_estudiante = 1 " +
                                "ORDER BY e.matricula_estudiante ASC";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    lista.add(mapearEstudiante(rs));
                }
                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    /* =========================
       ESTUDIANTES EN RIESGO
       ========================= */
    public static List<Estudiante> obtenerEstudiantesEnRiesgo() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT e.*, c.nombre_carrera AS nombre_carrera " +
                                "FROM estudiante e " +
                                "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                                "WHERE e.riesgo_estudiante = 1 AND e.activo_estudiante = 1";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Estudiante e = new Estudiante();
                    e.setIdEstudiante(rs.getInt("id_estudiante"));
                    e.setMatriculaEstudiante(rs.getString("matricula_estudiante"));
                    e.setNombreEstudiante(rs.getString("nombre_estudiante"));
                    e.setApellidoPaternoEstudiante(rs.getString("apellido_paterno_estudiante"));
                    e.setApellidoMaternoEstudiante(rs.getString("apellido_materno_estudiante"));
                    e.setCorreoEstudiante(rs.getString("correo_estudiante"));
                    e.setSemestreEstudiante(rs.getInt("semestre_estudiante"));
                    e.setIdCarrera(rs.getInt("id_carrera"));
                    e.setCarreraNombre(rs.getString("nombre_carrera"));
                    lista.add(e);
                }
                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    /* =========================
       REGISTRAR
       ========================= */
    public static boolean registrarEstudiante(Estudiante est) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "INSERT INTO estudiante " +
                                "(matricula_estudiante, nombre_estudiante, apellido_paterno_estudiante, " +
                                "apellido_materno_estudiante, correo_estudiante, semestre_estudiante, " +
                                "riesgo_estudiante, activo_estudiante, id_carrera) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?)";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, est.getMatriculaEstudiante());
                ps.setString(2, est.getNombreEstudiante());
                ps.setString(3, est.getApellidoPaternoEstudiante());
                ps.setString(4, est.getApellidoMaternoEstudiante());
                ps.setString(5, est.getCorreoEstudiante());
                ps.setInt(6, est.getSemestreEstudiante());
                ps.setInt(7, est.getRiesgoEstudiante());
                ps.setInt(8, est.getIdCarrera());

                resultado = ps.executeUpdate() > 0;
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    /* =========================
       EDITAR
       ========================= */
    public static boolean editarEstudiante(Estudiante est) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "UPDATE estudiante SET " +
                                "nombre_estudiante = ?, " +
                                "apellido_paterno_estudiante = ?, " +
                                "apellido_materno_estudiante = ?, " +
                                "correo_estudiante = ?, " +
                                "semestre_estudiante = ?, " +
                                "riesgo_estudiante = ?, " +
                                "id_carrera = ? " +
                                "WHERE id_estudiante = ?";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, est.getNombreEstudiante());
                ps.setString(2, est.getApellidoPaternoEstudiante());
                ps.setString(3, est.getApellidoMaternoEstudiante());
                ps.setString(4, est.getCorreoEstudiante());
                ps.setInt(5, est.getSemestreEstudiante());
                ps.setInt(6, est.getRiesgoEstudiante());
                ps.setInt(7, est.getIdCarrera());
                ps.setInt(8, est.getIdEstudiante());

                resultado = ps.executeUpdate() > 0;
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    /* =========================
       ELIMINAR (LÓGICO)
       ========================= */
    public static boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "UPDATE estudiante SET activo_estudiante = 0 WHERE id_estudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idEstudiante);

                resultado = ps.executeUpdate() > 0;
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    /* =========================
       ASIGNAR TUTOR (USUARIO)
       ========================= */
    public static boolean asignarTutor(int idEstudiante, int idUsuario) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "UPDATE estudiante SET id_usuario = ? WHERE id_estudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idUsuario);
                ps.setInt(2, idEstudiante);

                resultado = ps.executeUpdate() > 0;
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    /* =========================
       ACTUALIZAR RIESGO
       ========================= */
    public boolean actualizarEstatusRiesgo(int idEstudiante, boolean enRiesgo) throws SQLException {
        String sql =
                "UPDATE estudiante SET riesgo_estudiante = ? WHERE id_estudiante = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, enRiesgo ? 1 : 0);
            ps.setInt(2, idEstudiante);

            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (conn != null) ConexionBD.cerrarConexion(conn);
        }
    }

    /* =========================
       MAPEO CENTRAL
       ========================= */
    private static Estudiante mapearEstudiante(ResultSet rs) throws SQLException {
        Estudiante est = new Estudiante();
        est.setIdEstudiante(rs.getInt("id_estudiante"));
        est.setMatriculaEstudiante(rs.getString("matricula_estudiante"));
        est.setNombreEstudiante(rs.getString("nombre_estudiante"));
        est.setApellidoPaternoEstudiante(rs.getString("apellido_paterno_estudiante"));
        est.setApellidoMaternoEstudiante(rs.getString("apellido_materno_estudiante"));
        est.setCorreoEstudiante(rs.getString("correo_estudiante"));
        est.setSemestreEstudiante(rs.getInt("semestre_estudiante"));
        est.setActivoEstudiante(rs.getInt("activo_estudiante"));
        est.setRiesgoEstudiante(rs.getInt("riesgo_estudiante"));
        est.setIdCarrera(rs.getInt("id_carrera"));
        est.setIdUsuario((Integer) rs.getObject("id_usuario"));
        est.setCarreraNombre(rs.getString("nombre_carrera"));
        est.setTutorNombre(rs.getString("nombre_tutor"));
        return est;
    }
}
