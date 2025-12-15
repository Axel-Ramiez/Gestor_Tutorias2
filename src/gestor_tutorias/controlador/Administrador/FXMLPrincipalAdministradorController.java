package gestor_tutorias.controlador.Administrador; // <--- AQUÍ ESTABA EL DETALLE

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
        mostrarAlerta("Navegación", "Aquí se abrirá la pantalla de Gestión de Usuarios.");
    }

    @FXML
    private void clicGestionarTutorados(ActionEvent event) {
        mostrarAlerta("Navegación", "Aquí se abrirá la pantalla de Asignar Tutores.");
    }

    private void mostrarAlerta(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
