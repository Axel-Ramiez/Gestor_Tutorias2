package gestor_tutorias.controlador.coordinador;

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
/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 08/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador del menú principal para el rol de Coordinador.
 * Gestiona la navegación hacia los módulos de Planeación, Reportes, Estudiantes y Problemáticas.
 */
public class FXMLPrincipalCoordinador implements Initializable {

    @FXML private Label lbNombreAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void inicializarInformacion(Usuario usuario) {
        if (usuario != null) {
            lbNombreAdmin.setText("Coordinador: " + usuario.getNombreUsuario());
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        try {
            Stage stageActual = (Stage) lbNombreAdmin.getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/FXMLInicioSesion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) { ex.printStackTrace(); }
    }



    @FXML
    private void clicPlaneacion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLPlaneacionTutoria.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Planeación de Tutoría");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir Planeación de Tutoría.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clicReporte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLReporteTutoria.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reporte de Tutoría");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir Reporte de Tutoría.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clickEstudiante(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLEstudiante.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Alumno");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir Gestión de Alumno.");
            e.printStackTrace();
        }
    }



    @FXML
    private void clicRiesgo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLEstudiantesRiesgo.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Problematica");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir Problematica.");
            e.printStackTrace();
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void clicProblematica(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLProblematicaPrincipal.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Problematica");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir Problematica.");
            e.printStackTrace();
        }
    }
}