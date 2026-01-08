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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLReporteTutoriaConsulta implements Initializable {

    @FXML private Label lbIdReporte;
    @FXML private DatePicker dpFechaReporte;
    @FXML private ComboBox<EstadoReporte> cbEstado;
    @FXML private ComboBox<String> cbTutor;
    @FXML private ComboBox<String> cbEstudiante;
    @FXML private ComboBox<String> cbPeriodo;
    @FXML private CheckBox chkAsistencia;
    @FXML private TextArea taTextoReporte;
    @FXML private TextArea taRespuestaCoordinador;

    private ReporteTutoria reporteActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));
        configurarModoLectura();
    }

    private void configurarModoLectura() {
        dpFechaReporte.setDisable(true);
        dpFechaReporte.setStyle("-fx-opacity: 1;");

        cbTutor.setDisable(true);
        cbTutor.setStyle("-fx-opacity: 1;");

        cbEstudiante.setDisable(true);
        cbEstudiante.setStyle("-fx-opacity: 1;");

        cbPeriodo.setDisable(true);
        cbPeriodo.setStyle("-fx-opacity: 1;");

        chkAsistencia.setDisable(true);
        chkAsistencia.setStyle("-fx-opacity: 1;");

        taTextoReporte.setEditable(false);
    }

    public void inicializarDatos(ReporteTutoria reporte) {
        this.reporteActual = reporte;

        lbIdReporte.setText(String.valueOf(reporte.getIdReporte()));
        if (reporte.getFechaReporte() != null) {
            dpFechaReporte.setValue(reporte.getFechaReporte());
        }

        cbEstado.setValue(reporte.getEstado());

        cbTutor.getItems().clear();
        cbTutor.getItems().add(reporte.getNombreTutor() != null ? reporte.getNombreTutor() : "N/A");
        cbTutor.getSelectionModel().selectFirst();

        cbEstudiante.getItems().clear();
        cbEstudiante.getItems().add(reporte.getNombreEstudiante() != null ? reporte.getNombreEstudiante() : "N/A");
        cbEstudiante.getSelectionModel().selectFirst();

        cbPeriodo.getItems().clear();
        cbPeriodo.getItems().add(reporte.getPeriodoEscolar() != null ? reporte.getPeriodoEscolar() : "N/A");
        cbPeriodo.getSelectionModel().selectFirst();
        chkAsistencia.setSelected(reporte.isAsistencia());
        taTextoReporte.setText(reporte.getTextoReporte());

        if (reporte.getRespuestaCoordinador() != null) {
            taRespuestaCoordinador.setText(reporte.getRespuestaCoordinador());
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        EstadoReporte nuevoEstado = cbEstado.getValue();
        String respuesta = taRespuestaCoordinador.getText();

        if (nuevoEstado == null) {
            mostrarAlerta("Campo requerido", "Debe seleccionar un estado para el reporte.");
            return;
        }

        reporteActual.setEstado(nuevoEstado);
        reporteActual.setRespuestaCoordinador(respuesta);

        try {
            ReporteTutoriaDAO dao = new ReporteTutoriaDAO();

            boolean exito = dao.actualizarReporte(reporteActual);

            if (exito) {
                mostrarAlerta("Éxito", "La respuesta ha sido guardada correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la información en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "Error al comunicarse con la base de datos: " + ex.getMessage());
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicCerrar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lbIdReporte.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
