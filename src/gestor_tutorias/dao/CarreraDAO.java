package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Carrera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

    public static List<Carrera> obtenerPorFacultad(int idFacultad) throws SQLException {
        List<Carrera> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT id_carrera, nombre_carrera, id_facultad " +
                                "FROM carrera WHERE id_facultad = ?";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idFacultad);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Carrera c = new Carrera();
                    c.setIdCarrera(rs.getInt("id_carrera"));
                    c.setNombreCarrera(rs.getString("nombre_carrera"));
                    c.setIdFacultad(rs.getInt("id_facultad"));
                    lista.add(c);
                }

                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    public static List<Carrera> obtenerTodas() throws SQLException {
        List<Carrera> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta =
                        "SELECT id_carrera, nombre_carrera, id_facultad FROM carrera";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Carrera c = new Carrera();
                    c.setIdCarrera(rs.getInt("id_carrera"));
                    c.setNombreCarrera(rs.getString("nombre_carrera"));
                    c.setIdFacultad(rs.getInt("id_facultad"));
                    lista.add(c);
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
