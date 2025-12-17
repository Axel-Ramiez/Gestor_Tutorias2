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
            "INSERT INTO " + TABLA + " (id_tutor, id_estudiante, id_fecha_tutoria, reporte, respuesta_coordinador, asistencia, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_tutor = ?, id_estudiante = ?, id_fecha_tutoria = ?, reporte = ?, respuesta_coordinador = ?, asistencia = ?, estado = ? " +
                    "WHERE id_reporte = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_reporte = ?";

    private static final String SQL_SELECT_DETALLADO_BASE =
            "SELECT r.id_reporte, r.id_tutor, r.id_estudiante, r.id_fecha_tutoria, " +
                    "r.reporte, r.respuesta_coordinador, r.asistencia, r.estado, " +
                    "u.nombre_completo AS nombre_tutor, " +
                    "e.nombre_completo AS nombre_estudiante, " +
                    "p.fecha AS fecha_tutoria, " +
                    "pe.nombre AS periodo_nombre " +
                    "FROM " + TABLA + " r " +
                    "INNER JOIN usuario u ON r.id_tutor = u.id_usuario " +
                    "INNER JOIN estudiante e ON r.id_estudiante = e.id_estudiante " +
                    "INNER JOIN planeacion_tutoria p ON r.id_fecha_tutoria = p.id_fecha_tutoria " +
                    "INNER JOIN periodo_escolar pe ON p.id_periodo = pe.id_periodo ";

    public boolean actualizarRespuesta(int idReporte, String respuesta) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        String sql = "UPDATE reporte_tutoria SET respuesta_coordinador = ?, estado = ? WHERE id_reporte = ?";

        try {
            conn = ConexionBD.abrirConexion();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setString(1, respuesta);
                ps.setString(2, EstadoReporte.REVISADO.getValorBD());
                ps.setInt(3, idReporte);

                exito = ps.executeUpdate() > 0;
            }
        } finally {
            if (ps != null) ps.close();
            if (conn != null) ConexionBD.cerrarConexion(conn);
        }
        return exito;
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

            // CORRECCIÓN: Usar getValorBD() ("Pendiente" / "Revisado")
            String estadoStr = (reporteObj.getEstado() != null) ?
                    reporteObj.getEstado().getValorBD() : EstadoReporte.PENDIENTE.getValorBD();
            ps.setString(7, estadoStr);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) idGenerado = rs.getInt(1);
            }
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return idGenerado;
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
            // CORRECCIÓN: getValorBD()
            ps.setString(7, reporteObj.getEstado().getValorBD());
            ps.setInt(8, reporteObj.getIdReporte());

            exito = ps.executeUpdate() > 0;
        } finally {
            cerrarRecursos(conn, ps, null);
        }
        return exito;
    }

    public List<ReporteTutoria> obtenerTodos() throws SQLException {
        return ejecutarConsultaListado(SQL_SELECT_DETALLADO_BASE);
    }

    public ReporteTutoria obtenerPorId(int idReporte) throws SQLException {
        String sql = SQL_SELECT_DETALLADO_BASE + " WHERE r.id_reporte = ?";
        List<ReporteTutoria> resultados = ejecutarConsultaListado(sql, idReporte);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

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

    private List<ReporteTutoria> ejecutarConsultaListado(String sql, Object... parametros) throws SQLException {
        List<ReporteTutoria> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReporteConDetalles(rs));
            }
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return lista;
    }

    private ReporteTutoria mapearReporteConDetalles(ResultSet rs) throws SQLException {
        int idReporte = rs.getInt("id_reporte");
        int idTutor = rs.getInt("id_tutor");
        int idEstudiante = rs.getInt("id_estudiante");
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");
        String reporte = rs.getString("reporte");
        String respuestaCoordinador = rs.getString("respuesta_coordinador");
        boolean asistencia = rs.getBoolean("asistencia");

        // CORRECCIÓN: Usar fromString() para manejar "Pendiente"/"Revisado"
        EstadoReporte estado = EstadoReporte.fromString(rs.getString("estado"));

        ReporteTutoria r = new ReporteTutoria(idReporte, idTutor, idEstudiante, idFechaTutoria,
                reporte, respuestaCoordinador, asistencia, estado);

        r.setNombreTutor(rs.getString("nombre_tutor"));
        r.setNombreEstudiante(rs.getString("nombre_estudiante"));
        r.setFecha(rs.getString("fecha_tutoria"));
        r.setPeriodoEscolar(rs.getString("periodo_nombre"));

        return r;
    }

    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) ConexionBD.cerrarConexion(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ReporteTutoria> obtenerPorTutor(int idTutor) throws SQLException {
        List<ReporteTutoria> reportes = new ArrayList<>();
        // Se asume que tienes una clase de conexión llamada ConexionBD
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            try {
                // Consulta que une (JOIN) con la tabla estudiante para obtener el nombre
                String consulta = "SELECT r.id_reporte, r.fecha, r.periodo_escolar, r.estado, r.asistencia, " +
                        "e.nombre_completo AS nombre_estudiante " +
                        "FROM reporte_tutoria r " +
                        "INNER JOIN estudiante e ON r.id_estudiante = e.id_estudiante " +
                        "WHERE r.id_tutor = ?";

                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTutor);
                ResultSet resultado = prepararSentencia.executeQuery();

                while (resultado.next()) {
                    ReporteTutoria reporte = new ReporteTutoria();
                    reporte.setIdReporte(resultado.getInt("id_reporte"));
                    reporte.setFecha(resultado.getString("fecha"));
                    reporte.setPeriodoEscolar(resultado.getString("periodo_escolar"));
                    reporte.setEstado(EstadoReporte.valueOf(resultado.getString("estado")));
                    reporte.setAsistencia(resultado.getBoolean("asistencia"));
                    reporte.setNombreEstudiante(resultado.getString("nombre_estudiante"));
                    reportes.add(reporte);
                }
            } finally {
                conexion.close();
            }
        }
        return reportes;
    }
}
