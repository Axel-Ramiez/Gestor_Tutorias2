package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.PeriodoEscolar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodoEscolarDAO {

    public static PeriodoEscolar obtenerPeriodoActual() throws SQLException {
        PeriodoEscolar periodo = null;
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT * FROM periodo_escolar WHERE activo_periodo_escolar = 1 LIMIT 1";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    periodo = new PeriodoEscolar();
                    periodo.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
                    periodo.setNombrePeriodoEscolar(rs.getString("nombre_periodo_escolar"));
                    periodo.setFechaInicioPeriodoEscolar(
                            rs.getDate("fecha_inicio_periodo_escolar").toLocalDate()
                    );
                    periodo.setFechaFinPeriodoEscolar(
                            rs.getDate("fecha_fin_periodo_escolar").toLocalDate()
                    );
                    periodo.setActivoPeriodoEscolar(rs.getBoolean("activo_periodo_escolar"));
                }

                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return periodo;
    }

    public static List<PeriodoEscolar> obtenerTodos() throws SQLException {
        List<PeriodoEscolar> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT * FROM periodo_escolar ORDER BY fecha_inicio_periodo_escolar DESC";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    PeriodoEscolar p = new PeriodoEscolar();
                    p.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
                    p.setNombrePeriodoEscolar(rs.getString("nombre_periodo_escolar"));
                    p.setFechaInicioPeriodoEscolar(
                            rs.getDate("fecha_inicio_periodo_escolar").toLocalDate()
                    );
                    p.setFechaFinPeriodoEscolar(
                            rs.getDate("fecha_fin_periodo_escolar").toLocalDate()
                    );
                    p.setActivoPeriodoEscolar(rs.getBoolean("activo_periodo_escolar"));

                    lista.add(p);
                }

                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }
}
