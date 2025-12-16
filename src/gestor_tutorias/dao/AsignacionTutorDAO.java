package gestor_tutorias.dao;

import gestor_tutorias.pojo.AsignacionTutor;
import gestor_tutorias.modelo.ConexionBD; // Usando tu clase de conexión
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionTutorDAO {
    private static final String TABLA = "asignacion_tutor";

    // Consultas SQL usando ? para PreparedStatement
    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_tutor, id_estudiante, id_periodo) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_asignacion, id_tutor, id_estudiante, id_periodo FROM " + TABLA + " WHERE id_asignacion = ?";
    private static final String SQL_SELECT_ALL =
            "SELECT id_asignacion, id_tutor, id_estudiante, id_periodo FROM " + TABLA;
    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_tutor = ?, id_estudiante = ?, id_periodo = ? WHERE id_asignacion = ?";
    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_asignacion = ?";


    /**
     * Mapea un ResultSet a un objeto AsignacionTutor.
     */
    private AsignacionTutor mapearAsignacion(ResultSet rs) throws SQLException {
        int idAsignacion = rs.getInt("id_asignacion");
        int idTutor = rs.getInt("id_tutor");
        int idEstudiante = rs.getInt("id_estudiante");
        int idPeriodo = rs.getInt("id_periodo");

        return new AsignacionTutor(idAsignacion, idTutor, idEstudiante, idPeriodo);
    }

    public int guardarAsignacion(AsignacionTutor asignacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            // Asigna los parámetros
            ps.setInt(1, asignacion.getIdTutor());
            ps.setInt(2, asignacion.getIdEstudiante());
            ps.setInt(3, asignacion.getIdPeriodo());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } finally {
            // Cierra recursos de manera segura
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return idGenerado;
    }

    public AsignacionTutor obtenerPorId(int idAsignacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AsignacionTutor asignacion = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idAsignacion);
            rs = ps.executeQuery();

            if (rs.next()) {
                asignacion = mapearAsignacion(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return asignacion;
    }

    public List<AsignacionTutor> obtenerTodas() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AsignacionTutor> asignaciones = new ArrayList<>();

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                asignaciones.add(mapearAsignacion(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return asignaciones;
    }

    public boolean actualizarAsignacion(AsignacionTutor asignacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            // 1. Asigna los nuevos valores
            ps.setInt(1, asignacion.getIdTutor());
            ps.setInt(2, asignacion.getIdEstudiante());
            ps.setInt(3, asignacion.getIdPeriodo());
            // 2. Asigna el ID para la cláusula WHERE
            ps.setInt(4, asignacion.getIdAsignacion());

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

    public boolean eliminarAsignacion(int idAsignacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idAsignacion);

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