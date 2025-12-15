package gestor_tutorias.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NAME_BD = "gestion_tutoria";
    private static final String IP = "localhost";
    private static final String PORT  = "3306";
    private static final String URL = "jdbc:mysql://"+ IP + ":" + PORT + "/" + NAME_BD
            + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=America/Mexico_City";
    private static final String USER = "root";
    private static final String PASSWORD = "Samus_05";
    private static Connection CONEXION = null;

    public static Connection abrirConexion(){

        try{
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(URL, USER, PASSWORD);

        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return CONEXION;

    }

    public static void cerrarConexion(){
        try{
            if(CONEXION!=null){
                CONEXION.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

}
