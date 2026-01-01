package gestor_tutorias.controlador.Coordinador;/*package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.Enum.EstatusProblematica; // ¡Ojo aquí! Usar el Enum del POJO
import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLProblematicaConsulta implements Initializable {

    @FXML
    private TextField tfIdProblematica;
    @FXML
    private TextField tfIdReporte;
    @FXML
    private TextField tfIdExperiencia;
    @FXML
    private ComboBox<EstatusProblematica> cbEstado;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taDescripcion;

    private Problematica problematicaActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbEstado.setItems(FXCollections.observableArrayList(EstatusProblematica.values()));
        aplicarEstiloLectura();
    }

    public void inicializarProblematica(Problematica problematica) {
        this.problematicaActual = problematica;

        tfIdProblematica.setText(String.valueOf(problematica.getIdProblematica()));
        tfIdReporte.setText(String.valueOf(problematica.getIdReporteTutoria()));

        if (problematica.getIdCarrera() != null) {
            tfIdExperiencia.setText(String.valueOf(problematica.getIdCarrera()));
        } else {
            tfIdExperiencia.setText("N/A");
        }

        tfTitulo.setText(problematica.getTitulo());
        taDescripcion.setText(problematica.getDescripcion());
        cbEstado.setValue(problematica.getEstado());
    }

    @FXML
    private void guardarProblematica(ActionEvent event) {
        EstatusProblematica nuevoEstado = cbEstado.getValue();

        if (nuevoEstado == null) {
            mostrarAlerta("Error", "Debe seleccionar un estado.");
            return;
        }

        problematicaActual.setEstado(nuevoEstado);

        try {
            ProblematicaDAO dao = new ProblematicaDAO();
            boolean exito = dao.actualizarProblematica(problematicaActual);

            if (exito) {
                mostrarAlerta("Éxito", "El estado de la problemática ha sido actualizado.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar la problemática en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "Hubo un problema al conectar con la base de datos.");
        }
    }

    private void aplicarEstiloLectura() {
        String estiloVisible = "-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f4f4f4;";

        tfIdProblematica.setStyle(estiloVisible);
        tfIdReporte.setStyle(estiloVisible);
        tfIdExperiencia.setStyle(estiloVisible);
        tfTitulo.setStyle(estiloVisible);
        taDescripcion.setStyle(estiloVisible);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfTitulo.getScene().getWindow();
        stage.close();
    }
}


 */