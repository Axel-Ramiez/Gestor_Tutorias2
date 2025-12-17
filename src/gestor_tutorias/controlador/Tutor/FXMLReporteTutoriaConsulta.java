package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void inicializarFormulario(ReporteTutoria reporte, Usuario tutor, FXMLReporteTutoria padre, boolean esEdicion) {
        this.padre = padre;
        this.tutorSesion = tutor;
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
            tfIdTutor.setText(String.valueOf(tutorSesion.getIdUsuario()));
            cbEstado.setValue(EstadoReporte.PENDIENTE);
            tfIdReporte.setText("Nuevo");
        }
        configurarEdicion(esEdicion);
    }

    public void configurarEdicion(boolean editable) {
        tfIdReporte.setEditable(false);
        tfIdTutor.setEditable(false);
        tfIdEstudiante.setEditable(editable);
        tfIdFechaTutoria.setEditable(editable);
        chbAsistencia.setDisable(!editable);
        cbEstado.setDisable(!editable);
        taReporte.setEditable(editable);
        taRespuesta.setEditable(false);
        btnGuardar.setVisible(editable);
        btnEliminar.setVisible(!esNuevo && editable);
    }

    @FXML
    private void guardarReporte() {
        if (!validarCampos()) return;
        try {
            ReporteTutoria reporte = extraerDatos();
            boolean exito = esNuevo ? (dao.guardarReporte(reporte) > 0) : dao.actualizarReporte(reporte);
            if (exito) {
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se guardaron los datos.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error BD", e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error Formato", "Los ID deben ser numericos.");
        }
    }

    private ReporteTutoria extraerDatos() {
        int id = esNuevo ? 0 : Integer.parseInt(tfIdReporte.getText());
        int idTutor = Integer.parseInt(tfIdTutor.getText());
        int idEst = Integer.parseInt(tfIdEstudiante.getText());
        int idFecha = Integer.parseInt(tfIdFechaTutoria.getText());
        return new ReporteTutoria(id, idTutor, idEst, idFecha, taReporte.getText(), taRespuesta.getText(), chbAsistencia.isSelected(), cbEstado.getValue());
    }

    private boolean validarCampos() {
        if (tfIdEstudiante.getText().isEmpty() || tfIdFechaTutoria.getText().isEmpty() || taReporte.getText().isEmpty()) {
            mostrarAlerta("Campos requeridos", "Completa la informacion faltante.");
            return false;
        }
        return true;
    }

    @FXML
    private void eliminarReporte() {
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Confirmar");
        conf.setContentText("Deseas eliminar este reporte?");
        if (conf.showAndWait().get() == ButtonType.OK) {
            try {
                if (dao.eliminarReporte(Integer.parseInt(tfIdReporte.getText()))) {
                    cerrarVentana();
                }
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo eliminar.");
            }
        }
    }

    @FXML
    private void generarProblematica() {
        if (esNuevo) {
            mostrarAlerta("Atencion", "Primero debes guardar el reporte para vincular una problematica.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de problematica
            // Se asume que este controlador tiene un metodo inicializarParaReporte
            Object controlador = loader.getController();

            // Ejemplo de como pasarle los datos (deberas adaptar el nombre del metodo segun tu controlador de problematicas)
            // controlador.inicializarParaReporte(Integer.parseInt(tfIdReporte.getText()), tutorSesion);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Problematica Academica");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario de problematicas.");
            e.printStackTrace();
        }
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