package gestor_tutorias.controlador.tutor;

import gestor_tutorias.pojo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 08/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador del menú principal para el rol de Tutor Académico.
 * Gestiona la navegación hacia los módulos de Reportes, Problemáticas, Horarios y Planeación.
 */
public class FXMLPrincipalTutor {


    @FXML private Label lbNombreAdmin;
    private Usuario usuarioSesion;

    @FXML
    private void initialize() {

    }


    public void inicializarInformacion(Usuario usuario) {
        this.usuarioSesion = usuario;
        if (usuario != null) {
            lbNombreAdmin.setText("Tutor: " + usuario.getNombreUsuario());
        }
    }

    @FXML
    public void clicReporteTu(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/tutor/FXMLReporteTutoriaPrincipal.fxml",
                "Reporte de Tutoría"
        );
    }

    @FXML
    public void clicProblematica(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/tutor/FXMLProblematicaPrincipal.fxml",
                "Problemáticas"
        );
    }

    @FXML
    public void clicHorarioTu(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/tutor/FXMLHorarioTutoriaPrincipal.fxml",
                "Horarios de Tutoría"
        );
    }

    @FXML
    public void clicPlaneacion(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/tutor/FXMLPlaneacionTutoria.fxml",
                "Planeación de Tutoría"
        );
    }

    @FXML
    public void clicCerrarSesion(ActionEvent actionEvent) {
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
            mostrarAlerta("Error", "No se pudo cerrar la sesión correctamente.");
            ex.printStackTrace();
        }
    }



    private void cambiarVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana: " + titulo);
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