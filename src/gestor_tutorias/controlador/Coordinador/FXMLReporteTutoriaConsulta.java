package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLReporteTutoriaConsulta implements Initializable {

    @FXML
    private TextField tfIdReporte;
    @FXML
    private TextField tfIdEstudiante;
    @FXML
    private TextField tfIdTutor;
    @FXML
    private TextField tfIdFechaTutoria;
    @FXML
    private CheckBox chbAsistencia;
    @FXML
    private ComboBox<EstadoReporte> cbEstado;
    @FXML
    private TextArea taReporte;
    @FXML
    private TextArea taRespuesta;

    private ReporteTutoria reporteActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));
        aplicarEstiloLectura();
    }

    public void inicializarDatos(ReporteTutoria reporte) {
        this.reporteActual = reporte;

        tfIdReporte.setText(String.valueOf(reporte.getIdReporte()));

        if(reporte.getNombreEstudiante() != null)
            tfIdEstudiante.setText(reporte.getNombreEstudiante());
        else
            tfIdEstudiante.setText(String.valueOf(reporte.getIdEstudiante()));

        if(reporte.getNombreTutor() != null)
            tfIdTutor.setText(reporte.getNombreTutor());
        else
            tfIdTutor.setText(String.valueOf(reporte.getIdTutor()));

        tfIdFechaTutoria.setText(reporte.getFecha() != null ? reporte.getFecha() : String.valueOf(reporte.getIdFechaTutoria()));

        chbAsistencia.setSelected(reporte.isAsistencia());
        cbEstado.setValue(reporte.getEstado());
        taReporte.setText(reporte.getReporte());

        if (reporte.getRespuestaCoordinador() != null) {
            taRespuesta.setText(reporte.getRespuestaCoordinador());
        }
    }

    @FXML
    private void guardarReporte(ActionEvent event) {
        String respuesta = taRespuesta.getText();
        EstadoReporte nuevoEstado = cbEstado.getValue();

        if (nuevoEstado == null) {
            mostrarAlerta("Error", "Debe seleccionar un estado.");
            return;
        }

        reporteActual.setRespuestaCoordinador(respuesta);
        reporteActual.setEstado(nuevoEstado);

        try {
            ReporteTutoriaDAO dao = new ReporteTutoriaDAO();

            boolean exito = dao.actualizarReporte(reporteActual);

            if (exito) {
                mostrarAlerta("Éxito", "El reporte ha sido respondido y actualizado.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la respuesta en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "Error al conectar con la base de datos.");
        }
    }

    private void aplicarEstiloLectura() {
        String estiloVisible = "-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f4f4f4;";

        tfIdReporte.setStyle(estiloVisible);
        tfIdEstudiante.setStyle(estiloVisible);
        tfIdTutor.setStyle(estiloVisible);
        tfIdFechaTutoria.setStyle(estiloVisible);
        taReporte.setStyle(estiloVisible);
        chbAsistencia.setStyle("-fx-opacity: 1;");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfIdReporte.getScene().getWindow();
        stage.close();
    }
}
