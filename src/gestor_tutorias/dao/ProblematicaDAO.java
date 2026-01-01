package gestor_tutorias.dao;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Problematica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblematicaDAO {

    private static final String TABLA = "problematica";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA +
                    " (id_reporte_tutoria, titulo, descripcion, id_carrera, estado) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id_problematica, id_reporte_tutoria, titulo, descripcion, id_carrera, estado " +
                    "FROM " + TABLA + " WHERE id_problematica = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT id_problematica, id_reporte_tutoria, titulo, descripcion, id_carrera, estado " +
                    "FROM " + TABLA;

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA +
                    " SET id_reporte_tutoria = ?, titulo = ?, descripcion = ?, id_carrera = ?, estado = ? " +
                    "WHERE id_problematica = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_problematica = ?";

    /* ===================== MAPEO ===================== */

    private Problematica mapearProblematica(ResultSet rs) throws SQLException {

        int idProblematica = rs.getInt("id_problematica");
        int idReporteTutoria = rs.getInt("id_reporte_tutoria");
        String titulo = rs.getString("titulo");
        String descripcion = rs.getString("descripcion");

        Integer idCarrera = rs.getInt("id_carrera");
        if (rs.wasNull()) {
            idCarrera = null;
        }

        EstatusProblematica estado =
                EstatusProblematica.fromString(rs.getString("estado"));

        return new Problematica(
                idProblematica,
                idReporteTutoria,
                titulo,
                descripcion,
                idCarrera,
                estado
        );
    }

    /* ===================== CRUD ===================== */

    public int guardarProblematica(Problematica problematica) throws SQLException {

        int idGenerado = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, problematica.getIdReporteTutoria());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdCarrera() != null) {
                ps.setInt(4, problematica.getIdCarrera());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5,
                    problematica.getEstado() != null
                            ? problematica.getEstado().getValorBD()
                            : EstatusProblematica.PENDIENTE.getValorBD()
            );

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }

        return idGenerado;
    }

    public boolean actualizarProblematica(Problematica problematica) throws SQLException {

        boolean exito;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, problematica.getIdReporteTutoria());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdCarrera() != null) {
                ps.setInt(4, problematica.getIdCarrera());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5,
                    problematica.getEstado() != null
                            ? problematica.getEstado().getValorBD()
                            : EstatusProblematica.PENDIENTE.getValorBD()
            );

            ps.setInt(6, problematica.getIdProblematica());

            exito = ps.executeUpdate() > 0;

        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }

        return exito;
    }

    public List<Problematica> obtenerTodas() throws SQLException {

        List<Problematica> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearProblematica(rs));
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }

        return lista;
    }

    public Problematica obtenerPorId(int idProblematica) throws SQLException {

        Problematica problematica = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idProblematica);
            rs = ps.executeQuery();

            if (rs.next()) {
                problematica = mapearProblematica(rs);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }

        return problematica;
    }

    public boolean eliminarProblematica(int idProblematica) throws SQLException {

        boolean exito;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idProblematica);
            exito = ps.executeUpdate() > 0;

        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }

        return exito;
    }
}
