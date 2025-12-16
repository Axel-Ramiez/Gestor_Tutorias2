package gestor_tutorias.dao;

import gestor_tutorias.pojo.PlaneacionTutoria;
import gestor_tutorias.modelo.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaneacionTutoriaDAO {
    private static final String TABLA = "planeacion_tutoria";
    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_periodo, id_carrera, fecha, numero_sesion, temas) VALUES (?, ?, ?, ?, ?)";


    private static final String SQL_SELECT_BY_ID =
            "SELECT p.id_fecha_tutoria, p.id_periodo, p.id_carrera, p.fecha, p.numero_sesion, p.temas, " +
                    "pe.nombre AS periodo_nombre, c.nombre AS carrera_nombre " +
                    "FROM " + TABLA + " p " +
                    "INNER JOIN periodo_escolar pe ON p.id_periodo = pe.id_periodo " +
                    "INNER JOIN carrera c ON p.id_carrera = c.id_carrera " +
                    "WHERE p.id_fecha_tutoria = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT p.id_fecha_tutoria, p.id_periodo, p.id_carrera, p.fecha, p.numero_sesion, p.temas, " +
                    "pe.nombre AS periodo_nombre, c.nombre AS carrera_nombre " +
                    "FROM " + TABLA + " p " +
                    "INNER JOIN periodo_escolar pe ON p.id_periodo = pe.id_periodo " +
                    "INNER JOIN carrera c ON p.id_carrera = c.id_carrera";

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_periodo = ?, id_carrera = ?, fecha = ?, numero_sesion = ?, temas = ? WHERE id_fecha_tutoria = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_fecha_tutoria = ?";


    private PlaneacionTutoria mapearPlaneacion(ResultSet rs) throws SQLException {
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");
        int idPeriodo = rs.getInt("id_periodo");
        int idCarrera = rs.getInt("id_carrera");
        LocalDate fechaTutoria = rs.getDate("fecha").toLocalDate();
        LocalDate fechaCierre = fechaTutoria;

        int numeroSesion = rs.getInt("numero_sesion");
        String temas = rs.getString("temas");
        PlaneacionTutoria plan = new PlaneacionTutoria(idFechaTutoria, idPeriodo, idCarrera, fechaTutoria, fechaCierre, numeroSesion, temas);
        plan.setPeriodoNombre(rs.getString("periodo_nombre"));
        plan.setCarreraNombre(rs.getString("carrera_nombre"));

        return plan;
    }

    public int guardarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, planeacion.getIdPeriodo());
            ps.setInt(2, planeacion.getIdCarrera());
            ps.setDate(3, Date.valueOf(planeacion.getFechaTutoria()));
            ps.setInt(4, planeacion.getNumeroSesion());
            ps.setString(5, planeacion.getTemas());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return idGenerado;
    }

    public PlaneacionTutoria obtenerPorId(int idFechaTutoria) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PlaneacionTutoria planeacion = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idFechaTutoria);
            rs = ps.executeQuery();

            if (rs.next()) {
                planeacion = mapearPlaneacion(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return planeacion;
    }

    public List<PlaneacionTutoria> obtenerTodas() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PlaneacionTutoria> planeaciones = new ArrayList<>();

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                planeaciones.add(mapearPlaneacion(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return planeaciones;
    }

    public boolean actualizarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, planeacion.getIdPeriodo());
            ps.setInt(2, planeacion.getIdCarrera());
            // Actualizamos la fecha principal
            ps.setDate(3, Date.valueOf(planeacion.getFechaTutoria()));
            ps.setInt(4, planeacion.getNumeroSesion());
            ps.setString(5, planeacion.getTemas());
            ps.setInt(6, planeacion.getIdFechaTutoria());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    public boolean eliminarPlaneacion(int idFechaTutoria) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idFechaTutoria);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } finally {
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }
}