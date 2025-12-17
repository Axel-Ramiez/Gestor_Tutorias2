package gestor_tutorias.dao;

import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.Enum.EstatusProblematica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblematicaDAO {
    private static final String TABLA = "problematica";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_reporte, titulo, descripcion, id_experiencia_educativa, estado) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_problematica, id_reporte, titulo, descripcion, id_experiencia_educativa, estado FROM " + TABLA + " WHERE id_problematica = ?";
    private static final String SQL_SELECT_ALL =
            "SELECT id_problematica, id_reporte, titulo, descripcion, id_experiencia_educativa, estado FROM " + TABLA;
    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_reporte = ?, titulo = ?, descripcion = ?, id_experiencia_educativa = ?, estado = ? WHERE id_problematica = ?";
    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_problematica = ?";

    // Helper para convertir ResultSet a POJO
    private Problematica mapearProblematica(ResultSet rs) throws SQLException {
        int idProblematica = rs.getInt("id_problematica");
        int idReporte = rs.getInt("id_reporte");
        String titulo = rs.getString("titulo");
        String descripcion = rs.getString("descripcion");

        Integer idExperienciaEducativa = rs.getInt("id_experiencia_educativa");
        if (rs.wasNull()) {
            idExperienciaEducativa = null;
        }

        String estadoString = rs.getString("estado");
        EstatusProblematica estado = EstatusProblematica.fromString(estadoString);

        return new Problematica(idProblematica, idReporte, titulo, descripcion, idExperienciaEducativa, estado);
    }

    public int guardarProblematica(Problematica problematica) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, problematica.getIdReporte());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdExperienciaEducativa() != null) {
                ps.setInt(4, problematica.getIdExperienciaEducativa());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, (problematica.getEstado() != null) ?
                    problematica.getEstado().getValorBD() : EstatusProblematica.PENDIENTE.getValorBD());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) idGenerado = rs.getInt(1);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return idGenerado;
    }

    public boolean actualizarProblematica(Problematica problematica) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setInt(1, problematica.getIdReporte());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdExperienciaEducativa() != null) {
                ps.setInt(4, problematica.getIdExperienciaEducativa());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, problematica.getEstado().getValorBD());
            ps.setInt(6, problematica.getIdProblematica());

            exito = ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    // --- MÉTODOS QUE FALTABAN ---

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
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Problematica problematica = null;
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
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
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