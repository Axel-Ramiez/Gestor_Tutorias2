package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Facultad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 13/12/2025
 * Fecha de modificación:11/01/2026
 * Descripción: Clase DAO para consultar el catálogo de Facultades.
 */
public class FacultadDAO {

    private static final String SQL_SELECT_ALL = "SELECT id_facultad, nombre_facultad FROM facultad";

    public static List<Facultad> obtenerTodas() throws SQLException {
        List<Facultad> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearFacultad(rs));
            }
        }
        return lista;
    }


    private static Facultad mapearFacultad(ResultSet rs) throws SQLException {
        Facultad f = new Facultad();
        f.setIdFacultad(rs.getInt("id_facultad"));
        f.setNombreFacultad(rs.getString("nombre_facultad"));
        return f;
    }
}
