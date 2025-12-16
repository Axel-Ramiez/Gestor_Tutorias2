package gestor_tutorias.controlador;

import gestor_tutorias.controlador.Administrador.FXMLPrincipalAdministradorController;
import gestor_tutorias.controlador.Coordinador.FXMLPrincipalCoordinador;
import gestor_tutorias.controlador.Tutor.FXMLPrincipalTutor;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLInicioSesionController implements Initializable {

    // Variables que coinciden con TU FXML
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
    }

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");

        String matricula = tfUsuario.getText();
        String password = pfPassword.getText();

        boolean isValido = true;

        if(matricula.isEmpty()){
            lbErrorUsuario.setText("Campo obligatorio");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("Campo obligatorio");
            isValido = false;
        }

        if(isValido){
            validarCredenciales(matricula, password);
        }
    }

    private void validarCredenciales(String usuario, String password) {
        try {
            Usuario usuarioSesion = UsuarioDAO.iniciarSesion(usuario, password);

            if (usuarioSesion != null) {
                System.out.println("Usuario encontrado: " + usuarioSesion.getNombreCompleto());
                System.out.println("ID ROL detectado: " + usuarioSesion.getIdRol());

                switch (usuarioSesion.getIdRol()) {
                    case 1:
                        irPantallaPrincipal("Administrador/FXMLPrincipalAdministrador.fxml", usuarioSesion);
                        break;
                    case 2:
                        irPantallaPrincipal("Coordinador/FXMLPrincipalCoordinador.fxml", usuarioSesion);
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
                FXMLPrincipalAdministradorController controller = loader.getController();
                controller.inicializarInformacion(usuario);
            } else if (usuario.getIdRol() == 2) {
                FXMLPrincipalCoordinador controller = loader.getController();
                controller.inicializarInformacion(usuario);
            } else if (usuario.getIdRol() == 3) {
                FXMLPrincipalTutor controller = loader.getController();
                controller.inicializarInformacion(usuario);
            }
            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Sistema de Gestión de Tutorías - " + usuario.getRolNombre());
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
}