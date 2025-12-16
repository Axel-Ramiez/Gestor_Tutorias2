package gestor_tutorias.controlador.Tutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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

public class FXMLPrincipalTutor implements Initializable {

    @FXML
    private Label lbNombreAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void inicializarInformacion(Usuario usuario) {
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

            // Cerrar ventana actual
            Stage stageActual = (Stage) lbNombreAdmin.getScene().getWindow();
            stageActual.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicProblematica(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematica.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Problemáticas");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Problemáticas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clicReporteTu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoria.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Reporte de Tutoría");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Reporte de Tutoría.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clicHorarioTu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLHorarios.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Definición de Horarios");
            stage.show();

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
