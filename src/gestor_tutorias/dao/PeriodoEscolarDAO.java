package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.PeriodoEscolar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 13/12/2025
 * Fecha de modificación: 15/12/2025
 * Descripción: DAO para consultar los Periodos Escolares.
 */
public class PeriodoEscolarDAO {

    public static PeriodoEscolar obtenerPeriodoActual() throws SQLException {
        PeriodoEscolar periodo = null;
        String sql = "SELECT * FROM periodo_escolar WHERE activo_periodo_escolar = 1 LIMIT 1";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                periodo = mapearPeriodo(rs);
            }
        }
        return periodo;
    }

    public static List<PeriodoEscolar> obtenerTodos() throws SQLException {
        List<PeriodoEscolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM periodo_escolar ORDER BY fecha_inicio_periodo_escolar DESC";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPeriodo(rs));
            }
        }
        return lista;
    }

    private static PeriodoEscolar mapearPeriodo(ResultSet rs) throws SQLException {
        PeriodoEscolar p = new PeriodoEscolar();
        p.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
        p.setNombrePeriodoEscolar(rs.getString("nombre_periodo_escolar"));
        p.setFechaInicioPeriodoEscolar(rs.getDate("fecha_inicio_periodo_escolar").toLocalDate());
        p.setFechaFinPeriodoEscolar(rs.getDate("fecha_fin_periodo_escolar").toLocalDate());
        p.setActivoPeriodoEscolar(rs.getBoolean("activo_periodo_escolar"));
        return p;
    }
}