package gestor_tutorias.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.util.jar.Pack200.Packer.PASS;


public class ConexionBD {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NAME_BD = "gestion_tutorias";
    private static final String IP = "localhost";
    private static final String PORT  = "3306";
    private static final String URL = "jdbc:mysql://"+ IP + ":" + PORT + "/" + NAME_BD
            + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=America/Mexico_City";
    private static final String USER = "root";
    private static final String PASS = "Samus_05";
    private static Connection conexion = null;

    public static Connection abrirConexion() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error de conexión: " + ex.getMessage());
            ex.printStackTrace();
        }
        return conexion;
    }


    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}