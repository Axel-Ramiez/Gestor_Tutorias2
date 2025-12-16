package gestor_tutorias.dao;

import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.modelo.ConexionBD; // Asumiendo que esta clase existe
import gestor_tutorias.Enum.EstadoReporte; // Asumiendo que esta clase existe
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteTutoriaDAO {
    private static final String TABLA = "reporte_tutoria";

    // Consultas SQL usando ? para PreparedStatement
    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_tutor, id_estudiante, id_fecha_tutoria, descripcion_general, asistencia) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_reporte, id_tutor, id_estudiante, id_fecha_tutoria, descripcion_general, asistencia, estado FROM " + TABLA + " WHERE id_reporte = ?";
    private static final String SQL_SELECT_ALL =
            "SELECT id_reporte, id_tutor, id_estudiante, id_fecha_tutoria, descripcion_general, asistencia, estado FROM " + TABLA;
    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_tutor = ?, id_estudiante = ?, id_fecha_tutoria = ?, descripcion_general = ?, asistencia = ?, estado = ? WHERE id_reporte = ?";
    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_reporte = ?";


    /**
     * Método auxiliar para mapear un ResultSet a un objeto ReporteTutoria.
     */
    private ReporteTutoria mapearReporte(ResultSet rs) throws SQLException {
        int idReporte = rs.getInt("id_reporte");
        int idTutor = rs.getInt("id_tutor");
        int idEstudiante = rs.getInt("id_estudiante");
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");
        String descripcionGeneral = rs.getString("descripcion_general");
        boolean asistencia = rs.getBoolean("asistencia");

        // Mapeo de ENUM
        EstadoReporte estado = EstadoReporte.valueOf(rs.getString("estado"));

        return new ReporteTutoria(idReporte, idTutor, idEstudiante, idFechaTutoria, descripcionGeneral, asistencia, estado);
    }

    public int guardarReporte(ReporteTutoria reporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            // La columna 'estado' tiene DEFAULT 'Pendiente', no se incluye en el INSERT
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            // Asigna los parámetros
            ps.setInt(1, reporte.getIdTutor());
            ps.setInt(2, reporte.getIdEstudiante());
            ps.setInt(3, reporte.getIdFechaTutoria());
            ps.setString(4, reporte.getDescripcionGeneral());
            ps.setBoolean(5, reporte.isAsistencia());

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

    public boolean actualizarReporte(ReporteTutoria reporte) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            // 1. Asigna los nuevos valores
            ps.setInt(1, reporte.getIdTutor());
            ps.setInt(2, reporte.getIdEstudiante());
            ps.setInt(3, reporte.getIdFechaTutoria());
            ps.setString(4, reporte.getDescripcionGeneral());
            ps.setBoolean(5, reporte.isAsistencia());
            ps.setString(6, reporte.getEstado().toString()); // Guarda el ENUM como String

            // 2. Asigna el ID para la cláusula WHERE
            ps.setInt(7, reporte.getIdReporte());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
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
            if (filasAfectadas > 0) {
                exito = true;
            }
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }
}
