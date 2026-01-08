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

    public int guardarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, planeacion.getIdPeriodoEscolar());
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
            cerrarRecursos(conn, ps, rs);
        }
        return idGenerado;
    }

    public PlaneacionTutoria obtenerPorId(int idPlaneacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PlaneacionTutoria planeacion = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idPlaneacion);
            rs = ps.executeQuery();

            if (rs.next()) {
                planeacion = mapearPlaneacion(rs);
            }
        } finally {
            cerrarRecursos(conn, ps, rs);
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
            cerrarRecursos(conn, ps, rs);
        }
        return planeaciones;
    }

    public boolean actualizarPlaneacion(PlaneacionTutoria planeacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, planeacion.getIdPeriodoEscolar());
            ps.setInt(2, planeacion.getIdCarrera());
            ps.setDate(3, Date.valueOf(planeacion.getFechaTutoria()));
            ps.setInt(4, planeacion.getNumeroSesion());
            ps.setString(5, planeacion.getTemas());
            ps.setInt(6, planeacion.getIdPlaneacionTutoria());

            return ps.executeUpdate() > 0;
        } finally {
            cerrarRecursos(conn, ps, null);
        }
    }

    public boolean eliminarPlaneacion(int idPlaneacion) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idPlaneacion);

            return ps.executeUpdate() > 0;
        } finally {
            cerrarRecursos(conn, ps, null);
        }
    }


    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null && !conn.isClosed()) ConexionBD.cerrarConexion(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}