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


    private Problematica mapearProblematica(ResultSet rs) throws SQLException {
        int idProblematica = rs.getInt("id_problematica");
        int idReporte = rs.getInt("id_reporte");
        String titulo = rs.getString("titulo");
        String descripcion = rs.getString("descripcion");

        // Manejo de NULL para id_experiencia_educativa (campo opcional)
        Integer idExperienciaEducativa = rs.getInt("id_experiencia_educativa");
        if (rs.wasNull()) {
            idExperienciaEducativa = null;
        }

        EstatusProblematica estado = EstatusProblematica.valueOf(rs.getString("estado"));

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

            // Asigna los parámetros
            ps.setInt(1, problematica.getIdReporte());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            // Manejo de Integer (posiblemente NULL)
            if (problematica.getIdExperienciaEducativa() != null) {
                ps.setInt(4, problematica.getIdExperienciaEducativa());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            // Si el POJO viene sin estado (inserción), la DB usará el valor por defecto ('Pendiente')
            if (problematica.getEstado() != null) {
                ps.setString(5, problematica.getEstado().toString());
            } else {
                ps.setString(5, "Pendiente"); // Usar el valor por defecto si el POJO lo permite
            }

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

    public List<Problematica> obtenerTodas() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Problematica> problematicas = new ArrayList<>();

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                problematicas.add(mapearProblematica(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return problematicas;
    }

    public boolean actualizarProblematica(Problematica problematica) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            // 1. Asigna los nuevos valores
            ps.setInt(1, problematica.getIdReporte());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdExperienciaEducativa() != null) {
                ps.setInt(4, problematica.getIdExperienciaEducativa());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, problematica.getEstado().toString());
            // 2. Asigna el ID para la cláusula WHERE
            ps.setInt(6, problematica.getIdProblematica());

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

    public boolean eliminarProblematica(int idProblematica) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idProblematica);

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