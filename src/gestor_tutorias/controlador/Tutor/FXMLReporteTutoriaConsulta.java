package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
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
    @FXML private Button btnProblematica;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private FXMLReporteTutoria padre;
    private boolean esNuevo = true;

    public void inicializarFormulario(ReporteTutoria r, FXMLReporteTutoria padre) {
        this.padre = padre;
        cbEstado.getItems().setAll(EstadoReporte.values());

        if (r != null) {
            esNuevo = false;
            tfIdReporte.setText(String.valueOf(r.getIdReporte()));
            tfIdEstudiante.setText(String.valueOf(r.getIdEstudiante()));
            tfIdTutor.setText(String.valueOf(r.getIdTutor()));
            tfIdFechaTutoria.setText(String.valueOf(r.getIdFechaTutoria()));
            chbAsistencia.setSelected(r.isAsistencia());
            cbEstado.setValue(r.getEstado());
            taReporte.setText(r.getReporte());
            taRespuesta.setText(r.getRespuestaCoordinador());
        } else {
            cbEstado.setValue(EstadoReporte.PENDIENTE);
        }
    }

    @FXML
    private void guardarReporte() {
        if (!validarCampos()) return;

        try {
            ReporteTutoria r = extraerDatos();

            boolean exito;
            if (esNuevo) {
                exito = dao.guardarReporte(r) > 0;
            } else {
                exito = dao.actualizarReporte(r);
            }

            if (exito) {
                padre.refrescarTabla();
                cerrar();
            } else {
                mostrarAlerta("Error", "No se guardaron cambios.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Error BD", e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los IDs deben ser numéricos.");
        }
    }

    @FXML
    private void eliminarReporte() {
        if (esNuevo) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar");
        confirm.setContentText("¿Eliminar este reporte?");
        if (confirm.showAndWait().get() != ButtonType.OK) return;

        try {
            int id = Integer.parseInt(tfIdReporte.getText());
            if (dao.eliminarReporte(id)) {
                padre.refrescarTabla();
                cerrar();
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar.");
        }
    }

    @FXML
    private void generarProblematica() {
        if (esNuevo) {
            mostrarAlerta("Primero guarda", "Debes guardar el reporte antes.");
            return;
        }

        System.out.println("Generar problemática para reporte ID: " + tfIdReporte.getText());
    }

    private ReporteTutoria extraerDatos() {

        int idReporte = esNuevo ? 0 : Integer.parseInt(tfIdReporte.getText());
        int idTutor = Integer.parseInt(tfIdTutor.getText());
        int idEstudiante = Integer.parseInt(tfIdEstudiante.getText());
        int idFechaTutoria = Integer.parseInt(tfIdFechaTutoria.getText());

        String reporte = taReporte.getText();
        String respuesta = taRespuesta.getText();
        boolean asistencia = chbAsistencia.isSelected();
        EstadoReporte estado = cbEstado.getValue();

        return new ReporteTutoria(
                idReporte,
                idTutor,
                idEstudiante,
                idFechaTutoria,
                reporte,
                respuesta,
                asistencia,
                estado
        );
    }


    private boolean validarCampos() {
        if (tfIdEstudiante.getText().isEmpty()
                || tfIdTutor.getText().isEmpty()
                || tfIdFechaTutoria.getText().isEmpty()
                || taReporte.getText().isEmpty()) {
            mostrarAlerta("Campos requeridos", "Completa todos los campos obligatorios.");
            return false;
        }
        return true;
    }

    private void cerrar() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
