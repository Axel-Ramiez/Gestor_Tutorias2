package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class FXMLReporteTutoriaConsulta {

    @FXML
    private Label idTutoriaLabel;

    @FXML
    private TextField periodoEscolarField;

    @FXML
    private TextField idTutorField;

    @FXML
    private TextField idEstudianteField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextArea reporteTextArea;

    @FXML
    private CheckBox asistenciaCheckBox;

    @FXML
    private TextField respuestaCoordinadorField; // Único editable

    private final ReporteTutoriaDAO reporteDao = new ReporteTutoriaDAO();
    private ReporteTutoria reporteActual;

    @FXML
    private void initialize() {
        // Campos no editables
        periodoEscolarField.setEditable(false);
        idTutorField.setEditable(false);
        idEstudianteField.setEditable(false);
        fechaPicker.setDisable(true);
        reporteTextArea.setEditable(false);
        asistenciaCheckBox.setDisable(true);

        // Único editable
        respuestaCoordinadorField.setEditable(true);
    }

    public void cargarReporte(ReporteTutoria reporte) {
        this.reporteActual = reporte;

        idTutoriaLabel.setText(String.valueOf(reporte.getIdReporte()));
        idTutorField.setText(String.valueOf(reporte.getIdTutor()));
        idEstudianteField.setText(String.valueOf(reporte.getIdEstudiante()));
        periodoEscolarField.setText(reporte.getPeriodoEscolar());
        respuestaCoordinadorField.setText(reporte.getRespuestaCoordinador());
        reporteTextArea.setText(reporte.getReporte());
        asistenciaCheckBox.setSelected(reporte.isAsistencia());

        if (reporte.getIdFechaTutoria() != null) {
            fechaPicker.setValue(reporte.getIdFechaTutoria().toLocalDate());
        }
    }

    @FXML
    private void guardarReporteTutoria() {
        if (reporteActual != null) {
            String respuesta = respuestaCoordinadorField.getText().trim();
            reporteActual.setRespuestaCoordinador(respuesta);

            try {
                // Llamada al DAO para actualizar solo la respuesta del coordinador
                reporteDao.actualizarRespuesta(reporteActual.getIdReporte(), respuesta);
                mostrarAlerta("Respuesta del coordinador actualizada correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error al actualizar la respuesta en la base de datos");
            }
        } else {
            mostrarAlerta("No hay reporte seleccionado");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
