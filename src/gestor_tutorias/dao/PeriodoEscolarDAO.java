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

                String consulta = "SELECT * FROM periodo_escolar WHERE activo = 1 LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    periodo = new PeriodoEscolar();
                    periodo.setIdPeriodo(rs.getInt("id_periodo"));
                    periodo.setNombre(rs.getString("nombre"));
                    periodo.setFechaInicio(rs.getString("fecha_inicio"));
                    periodo.setFechaFin(rs.getString("fecha_fin"));
                    periodo.setActivo(1);
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
                String consulta = "SELECT * FROM periodo_escolar ORDER BY fecha_inicio DESC";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    PeriodoEscolar p = new PeriodoEscolar();
                    p.setIdPeriodo(rs.getInt("id_periodo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setFechaInicio(rs.getString("fecha_inicio"));
                    p.setFechaFin(rs.getString("fecha_fin"));
                    p.setActivo(rs.getInt("activo"));
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
