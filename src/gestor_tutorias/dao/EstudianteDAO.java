package gestor_tutorias.dao;

import gestor_tutorias.modelo.ConexionBD;
import gestor_tutorias.pojo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EstudianteDAO {


    public static List<Estudiante> obtenerTodos() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();

        if(conn != null){
            try {
                String consulta = "SELECT e.*, c.nombre AS nombre_carrera, " +
                        "u.nombre_completo AS nombre_tutor " +
                        "FROM estudiante e " +
                        "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                        "LEFT JOIN usuario u ON e.id_tutor = u.id_usuario " +
                        "WHERE e.activo = 1 " +
                        "ORDER BY e.matricula ASC";

                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    lista.add(mapearEstudiante(rs));
                }
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }

    public static List<Estudiante> obtenerEstudiantesEnRiesgo() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        Connection conn = ConexionBD.abrirConexion();
        if (conn != null) {
            try {
                String consulta = "SELECT e.*, c.nombre AS nombre_carrera " +
                        "FROM estudiante e " +
                        "INNER JOIN carrera c ON e.id_carrera = c.id_carrera " +
                        "WHERE e.riesgo = 1 AND e.activo = 1";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Estudiante e = new Estudiante();
                    e.setIdEstudiante(rs.getInt("id_estudiante"));
                    e.setMatricula(rs.getString("matricula"));
                    e.setNombreCompleto(rs.getString("nombre_completo"));
                    e.setSemestre(rs.getInt("semestre"));
                    e.setCorreo(rs.getString("correo"));
                    e.setIdCarrera(rs.getInt("id_carrera"));
                    lista.add(e);
                }
                rs.close();
                ps.close();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return lista;
    }


    public static boolean registrarEstudiante(Estudiante est) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();
        if(conn != null){
            try{
                String consulta = "INSERT INTO estudiante (matricula, nombre_completo, correo, id_carrera, semestre, riesgo, activo) " +
                        "VALUES (?, ?, ?, ?, ?, ?, 1)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, est.getMatricula());
                ps.setString(2, est.getNombreCompleto());
                ps.setString(3, est.getCorreo());
                ps.setInt(4, est.getIdCarrera());
                ps.setInt(5, est.getSemestre());
                ps.setInt(6, est.getRiesgo());

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }


    public static boolean editarEstudiante(Estudiante est) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();
        if(conn != null){
            try{
                String consulta = "UPDATE estudiante SET nombre_completo = ?, correo = ?, id_carrera = ?, semestre = ?, riesgo = ? " +
                        "WHERE id_estudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, est.getNombreCompleto());
                ps.setString(2, est.getCorreo());
                ps.setInt(3, est.getIdCarrera());
                ps.setInt(4, est.getSemestre());
                ps.setInt(5, est.getRiesgo());
                ps.setInt(6, est.getIdEstudiante());

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }


    public static boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();
        if(conn != null){
            try{
                String consulta = "UPDATE estudiante SET activo = 0 WHERE id_estudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idEstudiante);

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    public static boolean asignarTutor(int idEstudiante, int idTutor) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionBD.abrirConexion();
        if (conn != null) {
            try {
                String consulta = "UPDATE estudiante SET id_tutor = ? WHERE id_estudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idTutor);
                ps.setInt(2, idEstudiante);

                resultado = ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
        return resultado;
    }

    public boolean actualizarEstatusRiesgo(int idEstudiante, boolean enRiesgo) throws SQLException {
        String sql = "UPDATE estudiante SET riesgo = ? WHERE id_estudiante = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.abrirConexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, enRiesgo ? 1 : 0);
            ps.setInt(2, idEstudiante);

            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (conn != null) ConexionBD.cerrarConexion(conn);
        }
    }

    private static Estudiante mapearEstudiante(ResultSet rs) throws SQLException {
        Estudiante est = new Estudiante();
        est.setIdEstudiante(rs.getInt("id_estudiante"));
        est.setMatricula(rs.getString("matricula"));
        est.setNombreCompleto(rs.getString("nombre_completo"));
        est.setCorreo(rs.getString("correo"));
        est.setIdCarrera(rs.getInt("id_carrera"));
        est.setSemestre(rs.getInt("semestre"));
        est.setRiesgo(rs.getInt("riesgo"));
        est.setCarreraNombre(rs.getString("nombre_carrera"));
        est.setIdTutor(rs.getInt("id_tutor"));
        est.setTutorNombre(rs.getString("nombre_tutor"));
        return est;
    }
}//