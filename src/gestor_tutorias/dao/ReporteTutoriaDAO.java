package gestor_tutorias.dao;

import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.Enum.EstadoReporte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteTutoriaDAO {
    private static final String TABLA = "reporte_tutoria";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_tutor, id_estudiante, id_fecha_tutoria, reporte, respuesta_coordinador, asistencia) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id_reporte, id_tutor, id_estudiante, id_fecha_tutoria, reporte, respuesta_coordinador, asistencia, estado " +
                    "FROM " + TABLA + " WHERE id_reporte = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT id_reporte, id_tutor, id_estudiante, id_fecha_tutoria, reporte, respuesta_coordinador, asistencia, estado " +
                    "FROM " + TABLA;

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_tutor = ?, id_estudiante = ?, id_fecha_tutoria = ?, reporte = ?, respuesta_coordinador = ?, asistencia = ?, estado = ? " +
                    "WHERE id_reporte = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_reporte = ?";

    private ReporteTutoria mapearReporte(ResultSet rs) throws SQLException {
        int idReporte = rs.getInt("id_reporte");
        int idTutor = rs.getInt("id_tutor");
        int idEstudiante = rs.getInt("id_estudiante");
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");
        String reporte = rs.getString("reporte");
        String respuestaCoordinador = rs.getString("respuesta_coordinador");
        boolean asistencia = rs.getBoolean("asistencia");
        EstadoReporte estado = EstadoReporte.valueOf(rs.getString("estado").toUpperCase());

        return new ReporteTutoria(idReporte, idTutor, idEstudiante, idFechaTutoria,
                reporte, respuestaCoordinador, asistencia, estado);
    }

    public int guardarReporte(ReporteTutoria reporteObj) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reporteObj.getIdTutor());
            ps.setInt(2, reporteObj.getIdEstudiante());
            ps.setInt(3, reporteObj.getIdFechaTutoria());
            ps.setString(4, reporteObj.getReporte());
            ps.setString(5, reporteObj.getRespuestaCoordinador());
            ps.setBoolean(6, reporteObj.isAsistencia());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return idGenerado;
    }

    public ReporteTutoria obtenerPorId(int idReporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ReporteTutoria reporte = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idReporte);
            rs = ps.executeQuery();

            if (rs.next()) {
                reporte = mapearReporte(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return reporte;
    }
    public boolean actualizarRespuesta(int idReporte, String respuestaCoordinador) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET respuesta_coordinador = ? WHERE id_reporte = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, respuestaCoordinador);
            ps.setInt(2, idReporte);

            int filasAfectadas = ps.executeUpdate();
            exito = filasAfectadas > 0;
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    public List<ReporteTutoria> obtenerTodos() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReporteTutoria> reportes = new ArrayList<>();

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return reportes;
    }

    public boolean actualizarReporte(ReporteTutoria reporteObj) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, reporteObj.getIdTutor());
            ps.setInt(2, reporteObj.getIdEstudiante());
            ps.setInt(3, reporteObj.getIdFechaTutoria());
            ps.setString(4, reporteObj.getReporte());
            ps.setString(5, reporteObj.getRespuestaCoordinador());
            ps.setBoolean(6, reporteObj.isAsistencia());
            ps.setString(7, reporteObj.getEstado().toString());
            ps.setInt(8, reporteObj.getIdReporte());

            int filasAfectadas = ps.executeUpdate();
            exito = filasAfectadas > 0;
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    public boolean eliminarReporte(int idReporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idReporte);

            int filasAfectadas = ps.executeUpdate();
            exito = filasAfectadas > 0;
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }
}

