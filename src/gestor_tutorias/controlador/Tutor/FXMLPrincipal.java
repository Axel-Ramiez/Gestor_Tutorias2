package gestor_tutorias.controlador.Tutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class FXMLPrincipal {

    @FXML
    private Button reportetutoria;

    @FXML
    private Button problematica;

    @FXML
    private Button salir;

    @FXML
    private void clickreportetutoria() {
        abrirVentana("/gestor_tutorias/vista/Tutor/FXMLTutoria.fxml", "Reporte de Tutoría");
    }

    @FXML
    private void clicGestionarclickreporteproblematicaUsuarios() {
        abrirVentana("/gestor_tutorias/vista/Tutor/FXMLTutoriaConsulta.fxml", "Consulta Tutoría");
    }

    @FXML
    private void clicCerrarSesion() {
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();
    }

    private void abrirVentana(String rutaFXML, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
