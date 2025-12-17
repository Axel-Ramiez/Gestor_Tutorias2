package gestor_tutorias.dao;

import gestor_tutorias.pojo.HorarioTutoria;
import gestor_tutorias.modelo.ConexionBD;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioTutoriaDAO {

    private static final String TABLA = "horario_tutoria";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLA +
                    " (id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin " +
                    "FROM " + TABLA + " WHERE id_horario = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin FROM " + TABLA;

    private static final String SQL_UPDATE =
            "UPDATE " + TABLA +
                    " SET id_tutor = ?, id_fecha_tutoria = ?, id_estudiante = ?, hora_inicio = ?, hora_fin = ? " +
                    "WHERE id_horario = ?";

    private static final String SQL_DELETE =
            "DELETE FROM " + TABLA + " WHERE id_horario = ?";

    private HorarioTutoria mapearHorario(ResultSet rs) throws SQLException {
        int idHorario = rs.getInt("id_horario");
        int idTutor = rs.getInt("id_tutor");
        int idFechaTutoria = rs.getInt("id_fecha_tutoria");

        Integer idEstudiante = rs.getInt("id_estudiante");
        if (rs.wasNull()) idEstudiante = null;

        LocalTime horaInicio = rs.getTime("hora_inicio").toLocalTime();
        LocalTime horaFin = rs.getTime("hora_fin").toLocalTime();

        return new HorarioTutoria(
                idHorario,
                idTutor,
                idFechaTutoria,
                idEstudiante,
                horaInicio,
                horaFin
        );
    }

    public int guardarHorario(HorarioTutoria horario) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, horario.getIdTutor());
            ps.setInt(2, horario.getIdFechaTutoria());

            if (horario.getIdEstudiante() != null) {
                ps.setInt(3, horario.getIdEstudiante());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setTime(4, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(5, Time.valueOf(horario.getHoraFin()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public HorarioTutoria obtenerPorId(int idHorario) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idHorario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearHorario(rs) : null;
            }
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

    public List<HorarioTutoria> obtenerPorTutor(int idTutor) throws SQLException {
        List<HorarioTutoria> lista = new ArrayList<>();
        String sql = "SELECT h.*, p.fecha AS fecha_texto " +
                "FROM horario_tutoria h " +
                "INNER JOIN planeacion_tutoria p ON h.id_fecha_tutoria = p.id_fecha_tutoria " +
                "WHERE h.id_tutor = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTutor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HorarioTutoria horario = mapearHorario(rs);
                horario.setFechaTutoria(rs.getString("fecha_texto"));
                lista.add(horario);
            }
        }
        return lista;
    }

    public boolean actualizarHorario(HorarioTutoria horario) throws SQLException {
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setInt(1, horario.getIdTutor());
            ps.setInt(2, horario.getIdFechaTutoria());

            if (horario.getIdEstudiante() != null) {
                ps.setInt(3, horario.getIdEstudiante());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setTime(4, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(5, Time.valueOf(horario.getHoraFin()));
            ps.setInt(6, horario.getIdHorario());

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

    public List<HorarioTutoria> obtenerHorariosDisponiblesPorFecha(int idFechaTutoria) throws SQLException {
        String sql =
                "SELECT id_horario, id_tutor, id_fecha_tutoria, id_estudiante, hora_inicio, hora_fin " +
                        "FROM " + TABLA + " WHERE id_fecha_tutoria = ? AND id_estudiante IS NULL";

        List<HorarioTutoria> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFechaTutoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearHorario(rs));
                }
            }
        }
        return lista;
    }
}
