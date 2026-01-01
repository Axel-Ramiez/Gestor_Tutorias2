package gestor_tutorias.controlador.Tutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLReporteTutoriaPrincipal {


    public void crearReporte(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaCrear.fxml",
                "Reporte de Tutoria crear"
        );
    }

    public void editarReporte(ActionEvent actionEvent) {
        cambiarVentana(
            "/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaEditar.fxml",
            "Reporte de Tutoria editar"
        );
    }

    public void consultarReporte(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaConsulta.fxml",
                "Reporte de Tutoria consultar"
        );
    }

    public void eliminarReporte(ActionEvent actionEvent) {

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

}
