package gestor_tutorias.dao;

import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.Enum.EstatusProblematica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 15/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: DAO para gestionar la información de las problemáticas de tutoría.
 */
public class ProblematicaDAO {

    private static final String SQL_INSERT =
            "INSERT INTO problematica (id_reporte_tutoria, titulo_problematica, descripcion_problematica, id_carrera, estado_problematica) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id_problematica, id_reporte_tutoria, titulo_problematica, " +
                    "descripcion_problematica, id_carrera, estado_problematica FROM problematica WHERE id_problematica = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT id_problematica, id_reporte_tutoria, titulo_problematica, " +
                    "descripcion_problematica, id_carrera, estado_problematica FROM problematica";

    private static final String SQL_UPDATE =
            "UPDATE problematica SET id_reporte_tutoria = ?, titulo_problematica = ?, " +
                    "descripcion_problematica = ?, id_carrera = ?, estado_problematica = ? WHERE id_problematica = ?";

    private static final String SQL_DELETE =
            "DELETE FROM problematica WHERE id_problematica = ?";

    public int guardarProblematica(Problematica problematica) throws SQLException {
        int idGenerado = -1;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, problematica.getIdReporteTutoria());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdCarrera() != null) {
                ps.setInt(4, problematica.getIdCarrera());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, problematica.getEstado() != null
                    ? problematica.getEstado().getValorBD()
                    : EstatusProblematica.PENDIENTE.getValorBD());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        }
        return idGenerado;
    }

    public boolean actualizarProblematica(Problematica problematica) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setInt(1, problematica.getIdReporteTutoria());
            ps.setString(2, problematica.getTitulo());
            ps.setString(3, problematica.getDescripcion());

            if (problematica.getIdCarrera() != null) {
                ps.setInt(4, problematica.getIdCarrera());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, problematica.getEstado() != null
                    ? problematica.getEstado().getValorBD()
                    : EstatusProblematica.PENDIENTE.getValorBD());
            ps.setInt(6, problematica.getIdProblematica());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Problematica> obtenerTodas() throws SQLException {
        List<Problematica> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProblematica(rs));
            }
        }
        return lista;
    }

    public Problematica obtenerPorId(int idProblematica) throws SQLException {
        Problematica problematica = null;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idProblematica);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    problematica = mapearProblematica(rs);
                }
            }
        }
        return problematica;
    }

    public boolean eliminarProblematica(int idProblematica) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idProblematica);
            return ps.executeUpdate() > 0;
        }
    }


    private Problematica mapearProblematica(ResultSet rs) throws SQLException {
        int idProblematica = rs.getInt("id_problematica");
        int idReporteTutoria = rs.getInt("id_reporte_tutoria");
        String titulo = rs.getString("titulo_problematica");
        String descripcion = rs.getString("descripcion_problematica");

        Integer idCarrera = rs.getInt("id_carrera");
        if (rs.wasNull()) {
            idCarrera = null;
        }

        EstatusProblematica estado = EstatusProblematica.fromString(rs.getString("estado_problematica"));

        return new Problematica(idProblematica, idReporteTutoria, titulo, descripcion, idCarrera, estado);
    }
}
