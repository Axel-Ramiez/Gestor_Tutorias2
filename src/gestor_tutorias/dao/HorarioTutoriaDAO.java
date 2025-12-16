package gestor_tutorias.dao;

import gestor_tutorias.pojo.HorarioTutoria;
import gestor_tutorias.modelo.ConexionBD; // Asumiendo que esta clase existe
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioTutoriaDAO {

    private static final String TABLA = "horario_tutoria";


    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA + " (id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin FROM " + TABLA + " WHERE id_horario = ?";
    private static final String SQL_SELECT_ALL =
            "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin FROM " + TABLA;
    private static final String SQL_UPDATE =
            "UPDATE " + TABLA + " SET id_tutor = ?, id_fecha_tutoria = ?, id_estudiante = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?";
    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_horario = ?";


    private HorarioTutoria mapearHorario(ResultSet rs) throws SQLException {
        int idHorario = rs.getInt("id_horario");
        int idTutor = rs.getInt("id_tutor");
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");
        Time horaInicio = rs.getTime("hora_inicio");
        Time horaFin = rs.getTime("hora_fin");
        Integer idEstudiante = rs.getInt("id_estudiante");
        if (rs.wasNull()) {
            idEstudiante = null;
        }

        return new HorarioTutoria(idHorario, idTutor, idFechaTutoria, idEstudiante, horaInicio, horaFin);
    }

    public int guardarHorario(HorarioTutoria horario) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, horario.getIdTutor());
            ps.setInt(2, horario.getIdFechaTutoria());
            if (horario.getIdEstudiante() != null) {
                ps.setInt(3, horario.getIdEstudiante());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setTime(4, horario.getHoraInicio());
            ps.setTime(5, horario.getHoraFin());

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

    public HorarioTutoria obtenerPorId(int idHorario) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HorarioTutoria horario = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idHorario);
            rs = ps.executeQuery();

            if (rs.next()) {
                horario = mapearHorario(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return horario;
    }

    public List<HorarioTutoria> obtenerTodos() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<HorarioTutoria> horarios = new ArrayList<>();

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                horarios.add(mapearHorario(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return horarios;
    }

    public boolean actualizarHorario(HorarioTutoria horario) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, horario.getIdTutor());
            ps.setInt(2, horario.getIdFechaTutoria());

            if (horario.getIdEstudiante() != null) {
                ps.setInt(3, horario.getIdEstudiante());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setTime(4, horario.getHoraInicio());
            ps.setTime(5, horario.getHoraFin());
            ps.setInt(6, horario.getIdHorario());
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

    public boolean eliminarHorario(int idHorario) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idHorario);

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


    public List<HorarioTutoria> obtenerHorariosDisponiblesPorFecha(int idFechaTutoria) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<HorarioTutoria> horarios = new ArrayList<>();
        String SQL_SELECT_DISPONIBLES = "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin FROM " + TABLA + " WHERE id_fecha_tutoria = ? AND id_estudiante IS NULL";
        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(SQL_SELECT_DISPONIBLES);
            ps.setInt(1, idFechaTutoria);
            rs = ps.executeQuery();

            while (rs.next()) {
                horarios.add(mapearHorario(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.cerrarConexion(conn);
        }
        return horarios;
    }
}