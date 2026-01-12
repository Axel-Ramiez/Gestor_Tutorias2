package gestor_tutorias.dao;

import gestor_tutorias.pojo.PlaneacionTutoria;
import gestor_tutorias.modelo.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 15/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Clase DAO para gestionar las planeaciones de tutoría.
 */
public class PlaneacionTutoriaDAO {

    private static final String TABLA = "planeacion_tutoria";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_periodo_escolar, id_carrera, fecha_tutoria, numero_sesion, temas) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT p.id_planeacion_tutoria, p.id_periodo_escolar, p.id_carrera, p.fecha_tutoria, p.numero_sesion, p.temas, " +
                    "pe.nombre_periodo_escolar AS periodo_nombre, " +
                    "c.nombre_carrera AS carrera_nombre " +
                    "FROM " + TABLA + " p " +
                    "INNER JOIN periodo_escolar pe ON p.id_periodo_escolar = pe.id_periodo_escolar " +
                    "INNER JOIN carrera c ON p.id_carrera = c.id_carrera";

    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE p.id_planeacion_tutoria = ?";

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_periodo_escolar = ?, id_carrera = ?, fecha_tutoria = ?, numero_sesion = ?, temas = ? WHERE id_planeacion_tutoria = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_planeacion_tutoria = ?";

    private static final String SQL_SELECT_FECHAS =
            "SELECT fecha_tutoria FROM planeacion_tutoria WHERE id_periodo_escolar = ?";

    public int guardarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        int idGenerado = -1;
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, planeacion.getIdPeriodoEscolar());
            ps.setInt(2, planeacion.getIdCarrera());
            ps.setDate(3, Date.valueOf(planeacion.getFechaTutoria()));
            ps.setInt(4, planeacion.getNumeroSesion());
            ps.setString(5, planeacion.getTemas());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }
        }
        return idGenerado;
    }

    public PlaneacionTutoria obtenerPorId(int idPlaneacion) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idPlaneacion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearPlaneacion(rs) : null;
            }
        }
    }

    public List<PlaneacionTutoria> obtenerTodas() throws SQLException {
        List<PlaneacionTutoria> planeaciones = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                planeaciones.add(mapearPlaneacion(rs));
            }
        }
        return planeaciones;
    }

    public boolean actualizarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setInt(1, planeacion.getIdPeriodoEscolar());
            ps.setInt(2, planeacion.getIdCarrera());
            ps.setDate(3, Date.valueOf(planeacion.getFechaTutoria()));
            ps.setInt(4, planeacion.getNumeroSesion());
            ps.setString(5, planeacion.getTemas());
            ps.setInt(6, planeacion.getIdPlaneacionTutoria());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarPlaneacion(int idPlaneacion) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idPlaneacion);
            return ps.executeUpdate() > 0;
        }
    }

    public List<LocalDate> obtenerFechasPorPeriodo(int idPeriodo) throws SQLException {
        List<LocalDate> fechas = new ArrayList<>();
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_FECHAS)) {

            ps.setInt(1, idPeriodo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date fechaSQL = rs.getDate("fecha_tutoria");
                    if (fechaSQL != null) {
                        fechas.add(fechaSQL.toLocalDate());
                    }
                }
            }
        }
        return fechas;
    }


    private PlaneacionTutoria mapearPlaneacion(ResultSet rs) throws SQLException {
        PlaneacionTutoria plan = new PlaneacionTutoria();
        plan.setIdPlaneacionTutoria(rs.getInt("id_planeacion_tutoria"));
        plan.setIdPeriodoEscolar(rs.getInt("id_periodo_escolar"));
        plan.setIdCarrera(rs.getInt("id_carrera"));

        Date fechaSQL = rs.getDate("fecha_tutoria");
        if (fechaSQL != null) {
            plan.setFechaTutoria(fechaSQL.toLocalDate());
        }

        plan.setNumeroSesion(rs.getInt("numero_sesion"));
        plan.setTemas(rs.getString("temas"));
        plan.setPeriodoNombre(rs.getString("periodo_nombre"));
        plan.setCarreraNombre(rs.getString("carrera_nombre"));

        return plan;
    }
}