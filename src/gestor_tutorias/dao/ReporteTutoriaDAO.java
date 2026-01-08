package gestor_tutorias.dao;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.ReporteTutoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteTutoriaDAO {

    private static final String TABLA = "reporte_tutoria";

    private static final String SQL_INSERT =
            "INSERT INTO reporte_tutoria " +
                    "(fecha_reporte_tutoria, texto_reporte_tutoria, respuesta_coordinador, " +
                    "asistencia_reporte_tutoria, estado_reporte_tutoria, id_usuario, id_estudiante, id_periodo_escolar) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE reporte_tutoria SET " +
                    "fecha_reporte_tutoria = ?, texto_reporte_tutoria = ?, respuesta_coordinador = ?, " +
                    "asistencia_reporte_tutoria = ?, estado_reporte_tutoria = ?, " +
                    "id_usuario = ?, id_estudiante = ?, id_periodo_escolar = ? " +
                    "WHERE id_reporte_tutoria = ?";

    private static final String SQL_DELETE =
            "DELETE FROM reporte_tutoria WHERE id_reporte_tutoria = ?";

    private static final String SQL_SELECT_BASE =
            "SELECT * FROM reporte_tutoria";

    private static final String SQL_SELECT_DETALLADO_BASE =
            "SELECT r.id_reporte_tutoria, r.id_usuario, r.id_estudiante, r.id_periodo_escolar, " +
                    "r.fecha_reporte_tutoria, r.texto_reporte_tutoria, r.respuesta_coordinador, " +
                    "r.asistencia_reporte_tutoria, r.estado_reporte_tutoria, " +
                    "CONCAT(u.nombre_usuario, ' ', u.apellido_paterno_usuario, ' ', u.apellido_materno_usuario) AS nombre_tutor, " +
                    "CONCAT(e.nombre_estudiante, ' ', e.apellido_paterno_estudiante, ' ', e.apellido_materno_estudiante) AS nombre_estudiante, " +
                    "pe.nombre_periodo_escolar AS periodo_nombre " +
                    "FROM " + TABLA + " r " +
                    "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario " +
                    "LEFT JOIN estudiante e ON r.id_estudiante = e.id_estudiante " +
                    "LEFT JOIN periodo_escolar pe ON r.id_periodo_escolar = pe.id_periodo_escolar ";

    /* ========================= GUARDAR ========================= */

    public int guardarReporte(ReporteTutoria r) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getFechaReporte()));
            ps.setString(2, r.getTextoReporte());
            ps.setString(3, r.getRespuestaCoordinador());
            ps.setBoolean(4, r.isAsistencia());
            ps.setString(5, r.getEstado().getValorBD());
            ps.setInt(6, r.getIdUsuario());
            ps.setInt(7, r.getIdEstudiante());
            ps.setInt(8, r.getIdPeriodoEscolar());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }
    /* ========================= ACTUALIZAR ========================= */

    public boolean actualizarReporte(ReporteTutoria r) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setDate(1, Date.valueOf(r.getFechaReporte()));
            ps.setString(2, r.getTextoReporte());
            ps.setString(3, r.getRespuestaCoordinador());
            ps.setBoolean(4, r.isAsistencia());
            ps.setString(5, r.getEstado().getValorBD());
            ps.setInt(6, r.getIdUsuario());
            ps.setInt(7, r.getIdEstudiante());
            ps.setInt(8, r.getIdPeriodoEscolar());
            ps.setInt(9, r.getIdReporte());

            return ps.executeUpdate() > 0;
        }
    }

    /* ========================= ELIMINAR ========================= */

    public boolean eliminarReporte(int idReporte) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idReporte);
            return ps.executeUpdate() > 0;
        }
    }

    /* ========================= CONSULTAS ========================= */

    public List<ReporteTutoria> obtenerTodos() throws SQLException {
        return ejecutarConsulta(SQL_SELECT_BASE);
    }

    public ReporteTutoria obtenerPorId(int idReporte) throws SQLException {
        List<ReporteTutoria> lista =
                ejecutarConsulta(SQL_SELECT_BASE + " WHERE id_reporte_tutoria = ?", idReporte);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<ReporteTutoria> obtenerPorIdUsuario(int idUsuario) throws SQLException {
        return ejecutarConsulta(SQL_SELECT_BASE + " WHERE id_usuario = ?", idUsuario);
    }

    public List<ReporteTutoria> obtenerPorTutor(int idUsuario) throws SQLException {
        String sql = SQL_SELECT_DETALLADO_BASE + " WHERE r.id_usuario = ?";
        return ejecutarConsultaListado(sql, idUsuario);
    }

    /* ========================= MAPEOS ========================= */

    private List<ReporteTutoria> ejecutarConsulta(String sql, Object... params) throws SQLException {
        List<ReporteTutoria> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReporteTutoria r = new ReporteTutoria();
                    r.setIdReporte(rs.getInt("id_reporte_tutoria"));
                    r.setFechaReporte(rs.getDate("fecha_reporte_tutoria").toLocalDate());
                    r.setTextoReporte(rs.getString("texto_reporte_tutoria"));
                    r.setRespuestaCoordinador(rs.getString("respuesta_coordinador"));
                    r.setAsistencia(rs.getBoolean("asistencia_reporte_tutoria"));
                    r.setEstado(EstadoReporte.fromString(rs.getString("estado_reporte_tutoria")));
                    r.setIdUsuario(rs.getInt("id_usuario"));
                    r.setIdEstudiante(rs.getInt("id_estudiante"));
                    r.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    private List<ReporteTutoria> ejecutarConsultaListado(String sql, Object... params) throws SQLException {
        List<ReporteTutoria> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReporte(rs));
            }
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return lista;
    }

    private ReporteTutoria mapearReporte(ResultSet rs) throws SQLException {
        ReporteTutoria r = new ReporteTutoria();

        r.setIdReporte(rs.getInt("id_reporte_tutoria"));
        r.setIdUsuario(rs.getInt("id_usuario"));
        r.setIdEstudiante(rs.getInt("id_estudiante"));
        r.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
        r.setFechaReporte(rs.getDate("fecha_reporte_tutoria").toLocalDate());
        r.setTextoReporte(rs.getString("texto_reporte_tutoria"));
        r.setRespuestaCoordinador(rs.getString("respuesta_coordinador"));
        r.setAsistencia(rs.getBoolean("asistencia_reporte_tutoria"));
        r.setEstado(EstadoReporte.fromString(rs.getString("estado_reporte_tutoria")));

        r.setNombreTutor(rs.getString("nombre_tutor"));
        r.setNombreEstudiante(rs.getString("nombre_estudiante"));
        r.setPeriodoEscolar(rs.getString("periodo_nombre"));

        return r;
    }

    /* ========================= UTILIDAD ========================= */

    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) ConexionBD.cerrarConexion(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
