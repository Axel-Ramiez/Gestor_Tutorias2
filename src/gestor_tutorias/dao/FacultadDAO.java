package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Facultad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {

    public static List<Facultad> obtenerTodas() throws SQLException {
        List<Facultad> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String consulta = "SELECT id_facultad, nombre_facultad FROM facultad";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Facultad f = new Facultad();
                    f.setIdFacultad(rs.getInt("id_facultad"));
                    f.setNombreFacultad(rs.getString("nombre_facultad"));
                    lista.add(f);
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
