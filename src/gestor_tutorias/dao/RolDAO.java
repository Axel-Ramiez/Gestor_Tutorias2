package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public static List<Rol> obtenerRoles() throws SQLException {
        List<Rol> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if (conn != null) {
            try {
                String sql = "SELECT id_rol, nombre_rol FROM rol";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Rol r = new Rol();
                    r.setIdRol(rs.getInt("id_rol"));
                    r.setNombreRol(rs.getString("nombre_rol"));
                    lista.add(r);
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
