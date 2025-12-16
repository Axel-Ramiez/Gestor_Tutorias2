package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPrincipalAdministradorController implements Initializable {

    @FXML
    private Label lbNombreAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void inicializarInformacion(Usuario usuarioAdmin){
        if(usuarioAdmin != null){
            lbNombreAdmin.setText("Usuario: " + usuarioAdmin.getNombreCompleto());
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/FXMLInicioSesion.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stageLogin = new Stage();
            stageLogin.setScene(scene);
            stageLogin.setTitle("Iniciar Sesión");
            stageLogin.show();


            Stage stageActual = (Stage) lbNombreAdmin.getScene().getWindow();
            stageActual.close();

        } catch (IOException ex) {
            System.err.println("Error al cargar la ventana de Login: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicGestionarUsuarios(ActionEvent event) {
        irPantalla("/gestor_tutorias/vista/Administrador/FXMLUsuarioConsulta.fxml", "Gestión de Usuarios");
    }

    @FXML
    private void clicGestionarEstudiante(ActionEvent event) {

        irPantalla("/gestor_tutorias/vista/Administrador/FXMLEstudianteConsulta.fxml", "Gestión de Estudiantes");
    }


    private void irPantalla(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo);


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de Navegación", "No se pudo cargar la ventana: " + rutaFXML);
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
