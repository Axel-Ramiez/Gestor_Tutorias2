package gestor_tutorias.controlador.Coordinador;

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

public class FXMLPrincipalCoordinador implements Initializable {

    @FXML private Label lbNombreAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void inicializarInformacion(Usuario usuario) {
        if (usuario != null) {
            lbNombreAdmin.setText("Coordinador: " + usuario.getNombreCompleto());
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        // ... (Tu código de cerrar sesión que ya funcionaba) ...
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

    // --- AQUÍ ESTÁ LA MAGIA PARA ABRIR LAS VENTANAS DE TU COMPAÑERO ---

    @FXML
    private void clicPlaneacion(ActionEvent event) {
        // Abre: FXMLPlaneacionTutoriaConsulta.fxml
        abrirVentana("/gestor_tutorias/vista/Coordinador/FXMLPlaneacionTutoria.fxml", "Planeación de Tutoría");
    }

    @FXML
    private void clicReporte(ActionEvent event) {
        // Abre: FXMLReporteTutoriaConsulta.fxml
        abrirVentana("/gestor_tutorias/vista/Coordinador/FXMLReporteTutoria.fxml", "Reporte de Tutoría");
    }

    @FXML
    private void clicEstudiante(ActionEvent event) {
        // Abre: FXMLAlumnoConsulta.fxml
        abrirVentana("/gestor_tutorias/vista/Coordinador/FXMLAlumno.fxml", "Gestión de Alumno");
    }

    @FXML
    private void clicRiesgo(ActionEvent event) {
        abrirVentana("/gestor_tutorias/vista/Coordinador/FXMLEstudiantesRiesgo.fxml", "Estudiantes en Riesgo");
    }

    private void abrirVentana(String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar: " + ruta);
            ex.printStackTrace();
            mostrarAlerta("Error", "No se encontró el archivo: " + ruta);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}