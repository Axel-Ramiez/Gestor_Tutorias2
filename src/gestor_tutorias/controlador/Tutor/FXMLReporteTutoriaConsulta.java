package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.*;
import gestor_tutorias.pojo.Estudiante;
import gestor_tutorias.pojo.PeriodoEscolar;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FXMLReporteTutoriaConsulta {

    @FXML private Label lbIdReporte;
    @FXML private ComboBox<LocalDate> cbFechaReporte;
    @FXML private ComboBox<EstadoReporte> cbEstado;
    @FXML private ComboBox<Usuario> cbTutor;
    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;
    @FXML private CheckBox chkAsistencia;
    @FXML private TextArea taTextoReporte;
    @FXML private TextArea taRespuestaCoordinador;

    private int idReporte;
    private ReporteTutoria reporteActual;

    private final ReporteTutoriaDAO reporteDAO = new ReporteTutoriaDAO();
    private final PlaneacionTutoriaDAO planeacionDAO = new PlaneacionTutoriaDAO();

    @FXML
    private void initialize() {
        cargarDatosCombos();
        configurarCamposSoloLectura();
    }

    private void cargarDatosCombos() {
        try {
            cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));
            cbTutor.setItems(FXCollections.observableArrayList(UsuarioDAO.obtenerTutores()));
            cbEstudiante.setItems(FXCollections.observableArrayList(EstudianteDAO.obtenerTodos()));
            cbPeriodo.setItems(FXCollections.observableArrayList(PeriodoEscolarDAO.obtenerTodos()));
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las listas de referencia.");
        }
    }

    private void configurarCamposSoloLectura() {
        cbFechaReporte.setDisable(true);
        cbFechaReporte.setStyle("-fx-opacity: 1;");
        cbEstado.setDisable(true);
        cbEstado.setStyle("-fx-opacity: 1;");
        cbTutor.setDisable(true);
        cbTutor.setStyle("-fx-opacity: 1;");
        cbEstudiante.setDisable(true);
        cbEstudiante.setStyle("-fx-opacity: 1;");
        cbPeriodo.setDisable(true);
        cbPeriodo.setStyle("-fx-opacity: 1;");
        chkAsistencia.setDisable(true);
        chkAsistencia.setStyle("-fx-opacity: 1;");
        taTextoReporte.setEditable(false);
        taRespuestaCoordinador.setEditable(false);
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
        cargarReporte();
    }

    private void cargarReporte() {
        try {
            reporteActual = reporteDAO.obtenerPorId(idReporte);
            if (reporteActual != null) {
                pintarDatosEnVista();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo recuperar la información del reporte.");
        }
    }

    private void pintarDatosEnVista() {
        lbIdReporte.setText(String.valueOf(reporteActual.getIdReporte()));

        taTextoReporte.setText(reporteActual.getTextoReporte());
        taRespuestaCoordinador.setText(reporteActual.getRespuestaCoordinador());
        chkAsistencia.setSelected(reporteActual.isAsistencia());
        cbEstado.setValue(reporteActual.getEstado());

        seleccionarPorId(cbTutor, reporteActual.getIdUsuario(), Usuario::getIdUsuario);
        seleccionarPorId(cbEstudiante, reporteActual.getIdEstudiante(), Estudiante::getIdEstudiante);
        seleccionarPorId(cbPeriodo, reporteActual.getIdPeriodoEscolar(), PeriodoEscolar::getIdPeriodoEscolar);
        cargarFechasDelPeriodo(reporteActual.getIdPeriodoEscolar());
        cbFechaReporte.setValue(reporteActual.getFechaReporte());
    }

    private void cargarFechasDelPeriodo(int idPeriodo) {
        try {
            cbFechaReporte.getItems().clear();
            List<LocalDate> fechas = planeacionDAO.obtenerFechasPorPeriodo(idPeriodo);
            cbFechaReporte.setItems(FXCollections.observableArrayList(fechas));
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar las fechas para visualización.");
        }
    }

    private <T> void seleccionarPorId(ComboBox<T> combo, int idBuscado, java.util.function.Function<T, Integer> extractorId) {
        for (T item : combo.getItems()) {
            if (extractorId.apply(item) == idBuscado) {
                combo.getSelectionModel().select(item);
                break;
            }
        }
    }

    public void clicCerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(titulo.contains("Error")) alert.setAlertType(Alert.AlertType.ERROR);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
