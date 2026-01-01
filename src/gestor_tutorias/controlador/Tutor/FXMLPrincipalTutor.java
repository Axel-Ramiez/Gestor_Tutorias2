package gestor_tutorias.controlador.Tutor;

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

public class FXMLPrincipalTutor {

    @FXML
    private Label lbNombreAdmin;

    private Usuario usuarioSesion;

    @FXML
    private void initialize() {
        // inicialización básica
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuarioSesion = usuario;
        if (usuario != null) {
            lbNombreAdmin.setText("Tutor: " + usuario.getNombreUsuario());
        }
    }

    public void clicReporteTu(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaPrincipal.fxml",
                "Reporte de Tutoría"
        );
    }

    public void clicProblematica(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLProblematicaPrincipal.fxml",
                "Problemáticas"
        );
    }

    public void clicHorarioTu(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaPrincipal.fxml",
                "Horarios de Tutoría"
        );
    }

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
            ex.printStackTrace();
        }
    }

    public void cambiarVentana(String rutaFXML, String titulo){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }catch(RuntimeException e){
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    private void clicCerrarSesion(ActionEvent event) {

    }

    @FXML
    private void clicProblematica(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaPrincipal.fxml"));
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
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaPrincipal.fxml")
            );
            Parent root = loader.load();

            FXMLReporteTutoriaPrincipal controlador = loader.getController();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaPrincipal.fxml"));
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



 */




}

