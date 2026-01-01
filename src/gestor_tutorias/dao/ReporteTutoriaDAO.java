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
            "INSERT INTO " + TABLA +
                    " (id_usuario, id_estudiante, id_periodo_escolar, fecha_reporte_tutoria, texto_reporte_tutoria, respuesta_coordinador, asistencia_reporte_tutoria, estado_reporte_tutoria) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA +
                    " SET id_usuario = ?, id_estudiante = ?, id_periodo_escolar = ?, fecha_reporte_tutoria = ?, " +
                    "texto_reporte_tutoria = ?, respuesta_coordinador = ?, asistencia_reporte_tutoria = ?, estado_reporte_tutoria = ? " +
                    "WHERE id_reporte_tutoria = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_reporte_tutoria = ?";

    private static final String SQL_SELECT_DETALLADO_BASE =
            "SELECT r.id_reporte_tutoria, r.id_usuario, r.id_estudiante, r.id_periodo_escolar, " +
                    "r.fecha_reporte_tutoria, r.texto_reporte_tutoria, r.respuesta_coordinador, " +
                    "r.asistencia_reporte_tutoria, r.estado_reporte_tutoria, " +
                    "u.nombre_completo AS nombre_tutor, " +
                    "e.nombre_completo AS nombre_estudiante, " +
                    "pe.nombre AS periodo_nombre " +
                    "FROM " + TABLA + " r " +
                    "INNER JOIN usuario u ON r.id_usuario = u.id_usuario " +
                    "INNER JOIN estudiante e ON r.id_estudiante = e.id_estudiante " +
                    "INNER JOIN periodo_escolar pe ON r.id_periodo_escolar = pe.id_periodo_escolar ";

    /* ========================= GUARDAR ========================= */

    public int guardarReporte(ReporteTutoria reporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, reporte.getIdUsuario());
            ps.setInt(2, reporte.getIdEstudiante());
            ps.setInt(3, reporte.getIdPeriodoEscolar());
            ps.setDate(4, Date.valueOf(reporte.getFechaReporte()));
            ps.setString(5, reporte.getTextoReporte());
            ps.setString(6, reporte.getRespuestaCoordinador());
            ps.setBoolean(7, reporte.isAsistencia());
            ps.setString(8, reporte.getEstado().getValorBD());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return idGenerado;
    }

    /* ========================= ACTUALIZAR ========================= */

    public boolean actualizarReporte(ReporteTutoria reporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, reporte.getIdUsuario());
            ps.setInt(2, reporte.getIdEstudiante());
            ps.setInt(3, reporte.getIdPeriodoEscolar());
            ps.setDate(4, Date.valueOf(reporte.getFechaReporte()));
            ps.setString(5, reporte.getTextoReporte());
            ps.setString(6, reporte.getRespuestaCoordinador());
            ps.setBoolean(7, reporte.isAsistencia());
            ps.setString(8, reporte.getEstado().getValorBD());
            ps.setInt(9, reporte.getIdReporte());

            exito = ps.executeUpdate() > 0;
        } finally {
            cerrarRecursos(conn, ps, null);
        }
        return exito;
    }

    /* ========================= ELIMINAR ========================= */

    public boolean eliminarReporte(int idReporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idReporte);
            return ps.executeUpdate() > 0;
        } finally {
            cerrarRecursos(conn, ps, null);
        }
    }

    /* ========================= CONSULTAS ========================= */

    public List<ReporteTutoria> obtenerTodos() throws SQLException {
        return ejecutarConsultaListado(SQL_SELECT_DETALLADO_BASE);
    }

    public ReporteTutoria obtenerPorId(int idReporte) throws SQLException {
        String sql = SQL_SELECT_DETALLADO_BASE + " WHERE r.id_reporte_tutoria = ?";
        List<ReporteTutoria> lista = ejecutarConsultaListado(sql, idReporte);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<ReporteTutoria> obtenerPorTutor(int idUsuario) throws SQLException {
        String sql = SQL_SELECT_DETALLADO_BASE + " WHERE r.id_usuario = ?";
        return ejecutarConsultaListado(sql, idUsuario);
    }

    /* ========================= MAPEOS ========================= */

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
