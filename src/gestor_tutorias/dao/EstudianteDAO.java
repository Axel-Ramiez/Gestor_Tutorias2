package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 15/12/2025
 * Fecha de modificación: 17/12/2025
 * Descripción: DAO para la gestión de Estudiantes, asignación de tutores y gestión de riesgos.
 */
public class EstudianteDAO {


    private static final String SQL_SELECT_ALL =
            "SELECT e.*, c.nombre_carrera AS nombre_carrera, " +
                    "CONCAT(u.nombre_usuario, ' ', u.apellido_paterno_usuario) AS nombre_tutor " +
                    "FROM estudiante e " +
                    "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                    "LEFT JOIN usuario u ON e.id_usuario = u.id_usuario " +
                    "WHERE e.activo_estudiante = 1 ORDER BY e.matricula_estudiante ASC";

    private static final String SQL_SELECT_RIESGO =
            "SELECT e.*, c.nombre_carrera AS nombre_carrera, NULL AS nombre_tutor " +
                    "FROM estudiante e " +
                    "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                    "WHERE e.riesgo_estudiante = 1 AND e.activo_estudiante = 1";

    private static final String SQL_INSERT =
            "INSERT INTO estudiante " +
                    "(matricula_estudiante, nombre_estudiante, apellido_paterno_estudiante, " +
                    "apellido_materno_estudiante, correo_estudiante, semestre_estudiante, " +
                    "riesgo_estudiante, activo_estudiante, id_carrera) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?)";

    private static final String SQL_UPDATE =
            "UPDATE estudiante SET nombre_estudiante = ?, apellido_paterno_estudiante = ?, " +
                    "apellido_materno_estudiante = ?, correo_estudiante = ?, semestre_estudiante = ?, " +
                    "riesgo_estudiante = ?, id_carrera = ? WHERE id_estudiante = ?";

    private static final String SQL_DELETE =
            "UPDATE estudiante SET activo_estudiante = 0 WHERE id_estudiante = ?";

    private static final String SQL_ASIGNAR_TUTOR =
            "UPDATE estudiante SET id_usuario = ? WHERE id_estudiante = ?";

    private static final String SQL_CHECK_MATRICULA =
            "SELECT COUNT(*) FROM estudiante WHERE matricula_estudiante = ? AND id_estudiante <> ?";

    private static final String SQL_CHECK_CORREO =
            "SELECT COUNT(*) FROM estudiante WHERE correo_estudiante = ? AND id_estudiante <> ?";

    private static final String SQL_UPDATE_RIESGO =
            "UPDATE estudiante SET riesgo_estudiante = ? WHERE id_estudiante = ?";


    public static List<Estudiante> obtenerTodos() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearEstudiante(rs));
            }
        }
        return lista;
    }

    public static List<Estudiante> obtenerEstudiantesEnRiesgo() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_RIESGO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearEstudiante(rs));
            }
        }
        return lista;
    }

    public static boolean registrarEstudiante(Estudiante est) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            ps.setString(1, est.getMatriculaEstudiante());
            ps.setString(2, est.getNombreEstudiante());
            ps.setString(3, est.getApellidoPaternoEstudiante());
            ps.setString(4, est.getApellidoMaternoEstudiante());
            ps.setString(5, est.getCorreoEstudiante());
            ps.setInt(6, est.getSemestreEstudiante());
            ps.setInt(7, est.getRiesgoEstudiante());
            ps.setInt(8, est.getIdCarrera());

            return ps.executeUpdate() > 0;
        }
    }

    public static boolean editarEstudiante(Estudiante est) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, est.getNombreEstudiante());
            ps.setString(2, est.getApellidoPaternoEstudiante());
            ps.setString(3, est.getApellidoMaternoEstudiante());
            ps.setString(4, est.getCorreoEstudiante());
            ps.setInt(5, est.getSemestreEstudiante());
            ps.setInt(6, est.getRiesgoEstudiante());
            ps.setInt(7, est.getIdCarrera());
            ps.setInt(8, est.getIdEstudiante());

            return ps.executeUpdate() > 0;
        }
    }

    public static boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idEstudiante);
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean asignarTutor(int idEstudiante, int idUsuario) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_ASIGNAR_TUTOR)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idEstudiante);
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean esMatriculaRegistrada(String matricula, int idEstudianteExcluir) throws SQLException {
        boolean existe = false;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_CHECK_MATRICULA)) {

            ps.setString(1, matricula);
            ps.setInt(2, idEstudianteExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        }
        return existe;
    }

    public static boolean esCorreoRegistrado(String correo, int idEstudianteExcluir) throws SQLException {
        boolean existe = false;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_CHECK_CORREO)) {

            ps.setString(1, correo);
            ps.setInt(2, idEstudianteExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        }
        return existe;
    }

    public static boolean cambiarEstadoRiesgo(int idEstudiante, boolean enRiesgo) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RIESGO)) {

            ps.setInt(1, enRiesgo ? 1 : 0);
            ps.setInt(2, idEstudiante);

            return ps.executeUpdate() > 0;
        }
    }


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

        int idUsr = rs.getInt("id_usuario");
        est.setIdUsuario(rs.wasNull() ? null : idUsr);

        est.setCarreraNombre(rs.getString("nombre_carrera"));

        try {
            est.setTutorNombre(rs.getString("nombre_tutor"));
        } catch (SQLException e) {

        }

        return est;
    }
}
