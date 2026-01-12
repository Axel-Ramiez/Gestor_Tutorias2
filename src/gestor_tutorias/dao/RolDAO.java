package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 08/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Clase DAO para consultar el catálogo de Roles del sistema.
 */
public class RolDAO {

    private static final String SQL_OBTENER_ROLES = "SELECT id_rol, nombre_rol FROM rol";

    public static List<Rol> obtenerRoles() throws SQLException {
        List<Rol> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_OBTENER_ROLES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombreRol(rs.getString("nombre_rol"));
                lista.add(r);
            }
        }
        return lista;
    }
}