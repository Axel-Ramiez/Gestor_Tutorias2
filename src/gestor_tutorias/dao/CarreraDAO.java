package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Carrera;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 13/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: DAO para consultar el catálogo de Carreras.
 */
public class CarreraDAO {

    public static List<Carrera> obtenerPorFacultad(int idFacultad) throws SQLException {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT id_carrera, nombre_carrera, id_facultad FROM carrera WHERE id_facultad = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacultad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCarrera(rs));
                }
            }
        }
        return lista;
    }

    public static List<Carrera> obtenerTodas() throws SQLException {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT id_carrera, nombre_carrera, id_facultad FROM carrera";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCarrera(rs));
            }
        }
        return lista;
    }

    public static Carrera obtenerPorId(int idCarrera) throws SQLException {
        Carrera carrera = null;
        String sql = "SELECT * FROM carrera WHERE id_carrera = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    carrera = mapearCarrera(rs);
                }
            }
        }
        return carrera;
    }

    private static Carrera mapearCarrera(ResultSet rs) throws SQLException {
        Carrera c = new Carrera();
        c.setIdCarrera(rs.getInt("id_carrera"));
        c.setNombreCarrera(rs.getString("nombre_carrera"));
        c.setIdFacultad(rs.getInt("id_facultad"));
        return c;
    }
}