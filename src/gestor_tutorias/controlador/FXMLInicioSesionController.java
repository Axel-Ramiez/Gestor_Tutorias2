package gestor_tutorias.controlador;

//import gestor_tutorias.controlador.Administrador.FXMLPrincipalAdministradorController;
//import gestor_tutorias.controlador.Coordinador.FXMLPrincipalCoordinador;

import gestor_tutorias.controlador.Administrador.FXMLPrincipalAdministradorController;
import gestor_tutorias.controlador.Coordinador.FXMLPrincipalCoordinador;
import gestor_tutorias.controlador.Tutor.FXMLPrincipalTutor;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLInicioSesionController {
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
    }

    public void clicIniciarSesion(ActionEvent event){
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");

        String usuario = tfUsuario.getText().trim();
        String password = pfPassword.getText().trim();

        boolean valido = true;

        if (usuario.isEmpty()) {
            lbErrorUsuario.setText("Campo obligatorio");
            valido = false;
        }

        if (password.isEmpty()) {
            lbErrorPassword.setText("Campo obligatorio");
            valido = false;
        }

        if (!valido) return;

        try {
            Usuario usuarioSesion = UsuarioDAO.iniciarSesion(usuario, password);

            if (usuarioSesion == null) {
                lbErrorPassword.setText("Usuario o contraseña incorrectos");
                return;
            }

            switch (usuarioSesion.getIdRol()) {
                case 1:
                    abrirPrincipalAdministrador(usuarioSesion);
                    break;

                case 2:
                    //  abrirPrincipalCoordinador(usuarioSesion);
                    break;

                case 3:
                    abrirPrincipalTutor(usuarioSesion);
                    break;

                default:
                    mostrarAlerta("Error", "Rol no reconocido");
            }


        } catch (SQLException e) {
            mostrarAlerta("Error", "Error de conexión con la base de datos");
            e.printStackTrace();
        }
    }

    private void abrirPrincipalAdministrador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLPrincipalAdministrador.fxml")
            );
            Parent root = loader.load();

            FXMLPrincipalAdministradorController controller = loader.getController();
            controller.inicializarInformacion(usuario); // preparado para futuro

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Principal Administrador");
            stage.show();

            Stage actual = (Stage) tfUsuario.getScene().getWindow();
            actual.close();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana principal del administrador");
            e.printStackTrace();
        }
    }


    private void abrirPrincipalCoordinador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLPrincipalCoordinador.fxml")
            );
            Parent root = loader.load();

            FXMLPrincipalCoordinador controller = loader.getController();
            controller.inicializarInformacion(usuario); // aunque esté vacío

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Principal Coordinador");
            stage.show();

            Stage actual = (Stage) tfUsuario.getScene().getWindow();
            actual.close();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana principal del coordinador");
            e.printStackTrace();
        }
    }


    private void abrirPrincipalTutor(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLPrincipalTutor.fxml")
            );
            Parent root = loader.load();

            // AQUÍ SE CARGA EL CONTROLADOR
            FXMLPrincipalTutor controller = loader.getController();
            controller.inicializarInformacion(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Principal Tutor");
            stage.show();

            // cerrar login
            Stage actual = (Stage) tfUsuario.getScene().getWindow();
            actual.close();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana principal del tutor");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

/*




    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");

        String noPersonal = tfUsuario.getText();
        String password = pfPassword.getText();

        boolean isValido = true;

        if(noPersonal.isEmpty()){
            lbErrorUsuario.setText("Campo obligatorio");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("Campo obligatorio");
            isValido = false;
        }

        if(isValido){
            validarCredenciales(noPersonal, password);
        }
    }

    private void validarCredenciales(String usuario, String password) {
        try {
            Usuario usuarioSesion = UsuarioDAO.iniciarSesion(usuario, password);

            if (usuarioSesion != null) {
                System.out.println("Usuario encontrado: " + usuarioSesion.getNoPersonalUsuario());
                System.out.println("ID ROL detectado: " + usuarioSesion.getIdRol());

                switch (usuarioSesion.getIdRol()) {
                    case 1:
                       // irPantallaPrincipal("Administrador/FXMLPrincipalAdministrador.fxml", usuarioSesion);
                        break;
                    case 2:
                      //  irPantallaPrincipal("Coordinador/FXMLPrincipalCoordinador.fxml", usuarioSesion);
                        break;
                    case 3:
                        irPantallaPrincipal("Tutor/FXMLPrincipalTutor.fxml", usuarioSesion);
                        break;
                    default:
                        lbErrorUsuario.setText("Rol no reconocido (ID: " + usuarioSesion.getIdRol() + ")");
                }
            } else {
                lbErrorPassword.setText("Usuario y/o contraseña incorrectos.");
            }
        } catch (SQLException ex) {
           System.out.println("Error de conexión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void irPantallaPrincipal(String rutaFXML, Usuario usuario) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/" + rutaFXML));
            Parent root = loader.load();

            if (usuario.getIdRol() == 1) {
                //FXMLPrincipalAdministradorController controller = loader.getController();
               // controller.inicializarInformacion(usuario);
            } else if (usuario.getIdRol() == 2) {
               // FXMLPrincipalCoordinador controller = loader.getController();
               // controller.inicializarInformacion(usuario);
            } else if (usuario.getIdRol() == 3) {
                FXMLPrincipalTutor controller = loader.getController();
                controller.inicializarInformacion(usuario);
            }
            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Sistema de Gestión de Tutorías - " + usuario.getIdRol());
            escenario.show();
            Stage escenarioActual = (Stage) tfUsuario.getScene().getWindow();
            escenarioActual.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error al cargar el menú: " + ex.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

 */
}
