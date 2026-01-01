package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.HorarioTutoria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HorarioTutoriaDAO {

    private static final String TABLA = "horario_tutoria";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA +
                    " (fecha_horario_tutoria, hora_inicio_horario_tutoria, hora_fin_horario_tutoria, id_usuario, id_estudiante) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM " + TABLA + " WHERE id_horario_tutoria = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM " + TABLA;

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA +
                    " SET fecha_horario_tutoria = ?, hora_inicio_horario_tutoria = ?, hora_fin_horario_tutoria = ?, " +
                    "id_usuario = ?, id_estudiante = ? WHERE id_horario_tutoria = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_horario_tutoria = ?";

    /* ===================== MAPEADOR ===================== */
    private HorarioTutoria mapearHorario(ResultSet rs) throws SQLException {

        HorarioTutoria h = new HorarioTutoria();

        h.setIdHorarioTutoria(rs.getInt("id_horario_tutoria"));
        h.setFechaHorarioTutoria(rs.getDate("fecha_horario_tutoria").toLocalDate());
        h.setHoraInicioHorarioTutoria(rs.getTime("hora_inicio_horario_tutoria").toLocalTime());
        h.setHoraFinHorarioTutoria(rs.getTime("hora_fin_horario_tutoria").toLocalTime());
        h.setIdUsuario(rs.getInt("id_usuario"));

        int idEst = rs.getInt("id_estudiante");
        h.setIdEstudiante(rs.wasNull() ? null : idEst);

        return h;
    }

    /* ===================== CRUD ===================== */

    public int guardarHorario(HorarioTutoria horario) throws SQLException {

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(horario.getFechaHorarioTutoria()));
            ps.setTime(2, Time.valueOf(horario.getHoraInicioHorarioTutoria()));
            ps.setTime(3, Time.valueOf(horario.getHoraFinHorarioTutoria()));
            ps.setInt(4, horario.getIdUsuario());

            if (horario.getIdEstudiante() != null) {
                ps.setInt(5, horario.getIdEstudiante());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public HorarioTutoria obtenerPorId(int idHorario) throws SQLException {

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idHorario);
            ResultSet rs = ps.executeQuery();

            return rs.next() ? mapearHorario(rs) : null;
        }
    }

    public List<HorarioTutoria> obtenerTodos() throws SQLException {

        List<HorarioTutoria> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearHorario(rs));
            }
        }
        return lista;
    }

    public boolean actualizarHorario(HorarioTutoria horario) throws SQLException {

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setDate(1, Date.valueOf(horario.getFechaHorarioTutoria()));
            ps.setTime(2, Time.valueOf(horario.getHoraInicioHorarioTutoria()));
            ps.setTime(3, Time.valueOf(horario.getHoraFinHorarioTutoria()));
            ps.setInt(4, horario.getIdUsuario());

            if (horario.getIdEstudiante() != null) {
                ps.setInt(5, horario.getIdEstudiante());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, horario.getIdHorarioTutoria());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarHorario(int idHorario) throws SQLException {

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idHorario);
            return ps.executeUpdate() > 0;
        }
    }

    /* ===================== MÉTODOS EXTRA (CORREGIDOS) ===================== */

    public List<HorarioTutoria> obtenerHorariosDisponiblesPorFecha(LocalDate fecha) throws SQLException {

        String sql =
                "SELECT * FROM " + TABLA +
                        " WHERE fecha_horario_tutoria = ? AND id_estudiante IS NULL";

        List<HorarioTutoria> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearHorario(rs));
            }
        }
        return lista;
    }
}
