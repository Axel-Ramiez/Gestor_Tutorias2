package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FXMLReporteTutoriaConsulta {

    @FXML
    private Label idTutoriaLabel;
    @FXML
    private TextField periodoEscolarField;
    @FXML
    private TextField idTutorField;      // Mostrará el Nombre del Tutor
    @FXML
    private TextField idEstudianteField; // Mostrará el Nombre del Estudiante
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
        // Bloquear edición de campos informativos
        periodoEscolarField.setEditable(false);
        idTutorField.setEditable(false);
        idEstudianteField.setEditable(false);
        fechaPicker.setDisable(true); // DatePicker deshabilitado (solo lectura)
        reporteTextArea.setEditable(false);
        asistenciaCheckBox.setDisable(true);

        // Habilitar el campo de respuesta
        respuestaCoordinadorField.setEditable(true);
    }

    public void cargarReporte(ReporteTutoria reporte) {
        this.reporteActual = reporte;

        // Cargar datos básicos
        idTutoriaLabel.setText(String.valueOf(reporte.getIdReporte()));
        periodoEscolarField.setText(reporte.getPeriodoEscolar());
        reporteTextArea.setText(reporte.getReporte());
        asistenciaCheckBox.setSelected(reporte.isAsistencia());

        // CORRECCIÓN 1: Mostrar Nombres en lugar de IDs (Más amigable)
        idTutorField.setText(reporte.getNombreTutor());
        idEstudianteField.setText(reporte.getNombreEstudiante());

        // Cargar respuesta existente (si la hay)
        if (reporte.getRespuestaCoordinador() != null) {
            respuestaCoordinadorField.setText(reporte.getRespuestaCoordinador());
        }

        // CORRECCIÓN 2: Manejo de la Fecha
        // El POJO tiene la fecha como String (yyyy-MM-dd) gracias al JOIN del DAO.
        // getIdFechaTutoria() devuelve un INT, por eso te daba error .toLocalDate()
        if (reporte.getFecha() != null && !reporte.getFecha().isEmpty()) {
            try {
                // Parseamos el String a LocalDate para el DatePicker
                LocalDate fecha = LocalDate.parse(reporte.getFecha());
                fechaPicker.setValue(fecha);
            } catch (Exception e) {
                System.out.println("Error al parsear la fecha: " + e.getMessage());
            }
        }
    }

    @FXML
    private void guardarReporteTutoria() {
        if (reporteActual != null) {
            String respuesta = respuestaCoordinadorField.getText().trim();

            if (respuesta.isEmpty()) {
                mostrarAlerta("Por favor, escribe una respuesta antes de guardar.", Alert.AlertType.WARNING);
                return;
            }

            try {
                // Actualizar en BD
                boolean exito = reporteDao.actualizarRespuesta(reporteActual.getIdReporte(), respuesta);

                if (exito) {
                    mostrarAlerta("Respuesta registrada y estado actualizado a REVISADO.", Alert.AlertType.INFORMATION);
                    cerrarVentana(); // CORRECCIÓN 3: Cerrar ventana al terminar
                } else {
                    mostrarAlerta("No se pudo guardar la respuesta.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error de conexión: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("No hay reporte seleccionado.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) respuestaCoordinadorField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}