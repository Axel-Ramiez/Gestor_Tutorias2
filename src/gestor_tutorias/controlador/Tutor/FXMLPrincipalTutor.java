package gestor_tutorias.controlador.Tutor;

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

public class FXMLPrincipalTutor implements Initializable {

    @FXML
    private Label lbNombreAdmin;

    private Usuario usuarioSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuarioSesion = usuario;
        if (usuario != null) {
            lbNombreAdmin.setText("Tutor: " + usuario.getNombreCompleto());
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
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicProblematica(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematica.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Problemáticas");
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Problemáticas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clicReporteTu(ActionEvent event) {
        if (usuarioSesion == null) {
            mostrarAlerta("Sesión no iniciada", "No hay información del tutor.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoria.fxml")
            );
            Parent root = loader.load();

            FXMLReporteTutoria controlador = loader.getController();
            controlador.inicializarInformacion(usuarioSesion);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Reporte de Tutoría");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Reporte de Tutoría.");
            e.printStackTrace();
        }
    }


    @FXML
    private void clicHorarioTu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLHorarioTutoria.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Definición de Horarios");
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Horarios.");
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
}
