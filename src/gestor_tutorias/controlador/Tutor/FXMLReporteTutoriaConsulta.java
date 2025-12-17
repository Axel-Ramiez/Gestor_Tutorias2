package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;

public class FXMLReporteTutoriaConsulta {

    @FXML private TextField tfIdReporte;
    @FXML private TextField tfIdEstudiante;
    @FXML private TextField tfIdTutor;
    @FXML private TextField tfIdFechaTutoria;
    @FXML private CheckBox chbAsistencia;
    @FXML private ComboBox<EstadoReporte> cbEstado;
    @FXML private TextArea taReporte;
    @FXML private TextArea taRespuesta;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private FXMLReporteTutoria padre;
    private Usuario tutorSesion;
    private boolean esNuevo = true;

    /**
     * Sigue el patrón de inicializarFormulario de Problemática
     */
    public void inicializarFormulario(ReporteTutoria reporte, Usuario tutor, FXMLReporteTutoria padre, boolean esEdicion) {
        this.padre = padre;
        this.tutorSesion = tutor;

        // Cargar el ComboBox con los valores del Enum
        cbEstado.getItems().setAll(EstadoReporte.values());

        if (reporte != null) {
            esNuevo = false;
            tfIdReporte.setText(String.valueOf(reporte.getIdReporte()));
            tfIdEstudiante.setText(String.valueOf(reporte.getIdEstudiante()));
            tfIdTutor.setText(String.valueOf(reporte.getIdTutor()));
            tfIdFechaTutoria.setText(String.valueOf(reporte.getIdFechaTutoria()));
            chbAsistencia.setSelected(reporte.isAsistencia());
            taReporte.setText(reporte.getReporte());
            taRespuesta.setText(reporte.getRespuestaCoordinador());
            cbEstado.setValue(reporte.getEstado());
        } else {
            esNuevo = true;
            // Valores por defecto para nuevo registro
            tfIdTutor.setText(String.valueOf(tutorSesion.getIdUsuario()));
            cbEstado.setValue(EstadoReporte.PENDIENTE);
            tfIdReporte.setText("Autogenerado");
        }

        configurarEdicion(esEdicion);
    }

    public void configurarEdicion(boolean editable) {
        tfIdReporte.setEditable(false);
        tfIdTutor.setEditable(false); // El tutor ya viene de la sesión

        tfIdEstudiante.setEditable(editable);
        tfIdFechaTutoria.setEditable(editable);
        chbAsistencia.setDisable(!editable);
        cbEstado.setDisable(!editable);
        taReporte.setEditable(editable);

        // La respuesta del coordinador NUNCA debe ser editable por el tutor
        taRespuesta.setEditable(false);

        btnGuardar.setVisible(editable);
        btnEliminar.setVisible(!esNuevo && editable);
    }

    @FXML
    private void guardarReporte() {
        if (!validarCampos()) return;

        try {
            ReporteTutoria reporte = extraerDatos();
            boolean resultado;

            if (esNuevo) {
                int idGenerado = dao.guardarReporte(reporte);
                resultado = (idGenerado > 0);
            } else {
                resultado = dao.actualizarReporte(reporte);
            }

            if (resultado) {
                if (padre != null) padre.cargarDatos();
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudieron guardar los cambios.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error de BD", "Ocurrió un error: " + e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "Asegúrese de que los IDs sean numéricos.");
        }
    }

    private ReporteTutoria extraerDatos() {
        int id = esNuevo ? 0 : Integer.parseInt(tfIdReporte.getText());
        int idTutor = Integer.parseInt(tfIdTutor.getText());
        int idEst = Integer.parseInt(tfIdEstudiante.getText());
        int idFecha = Integer.parseInt(tfIdFechaTutoria.getText());
        String contenidoReporte = taReporte.getText();
        String respuesta = taRespuesta.getText();
        boolean asistio = chbAsistencia.isSelected();
        EstadoReporte estado = cbEstado.getValue();

        return new ReporteTutoria(id, idTutor, idEst, idFecha, contenidoReporte, respuesta, asistio, estado);
    }

    private boolean validarCampos() {
        if (tfIdEstudiante.getText().isEmpty() || tfIdFechaTutoria.getText().isEmpty() || taReporte.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor completa el ID de estudiante, fecha de planeación y el reporte.");
            return false;
        }
        return true;
    }

    @FXML
    private void eliminarReporte() {
        if (esNuevo) return;
        try {
            int id = Integer.parseInt(tfIdReporte.getText());
            if (dao.eliminarReporte(id)) {
                if (padre != null) padre.cargarDatos();
                cerrarVentana();
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el registro.");
        }
    }

    @FXML
    private void generarProblematica() {
        // Aquí podrías abrir la ventana de Problemática pasando el ID de este reporte
        mostrarAlerta("Información", "Función para vincular problemática en desarrollo.");
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
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