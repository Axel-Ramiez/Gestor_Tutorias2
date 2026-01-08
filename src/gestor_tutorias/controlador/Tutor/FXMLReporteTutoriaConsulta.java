package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;

import gestor_tutorias.dao.EstudianteDAO;

import gestor_tutorias.dao.PeriodoEscolarDAO;

import gestor_tutorias.dao.ReporteTutoriaDAO;

import gestor_tutorias.dao.UsuarioDAO;

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

public class FXMLReporteTutoriaConsulta {

    @FXML private Label lbIdReporte;

    @FXML private DatePicker dpFechaReporte;

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

        dpFechaReporte.setDisable(true);

        cbEstado.setDisable(true);

        cbTutor.setDisable(true);

        cbEstudiante.setDisable(true);

        cbPeriodo.setDisable(true);

        chkAsistencia.setDisable(true);

        taTextoReporte.setEditable(false);

        taRespuestaCoordinador.setEditable(false);

        dpFechaReporte.setStyle("-fx-opacity: 1;");

        cbEstado.setStyle("-fx-opacity: 1;");

        cbTutor.setStyle("-fx-opacity: 1;");

        cbEstudiante.setStyle("-fx-opacity: 1;");

        cbPeriodo.setStyle("-fx-opacity: 1;");

        chkAsistencia.setStyle("-fx-opacity: 1;");

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

            mostrarAlerta("Error", "No se pudo recuperar la información del reporte.");

        }

    }

    private void pintarDatosEnVista() {

        lbIdReporte.setText(String.valueOf(reporteActual.getIdReporte()));

        dpFechaReporte.setValue(reporteActual.getFechaReporte());

        taTextoReporte.setText(reporteActual.getTextoReporte());

        taRespuestaCoordinador.setText(reporteActual.getRespuestaCoordinador());

        chkAsistencia.setSelected(reporteActual.isAsistencia());

        cbEstado.setValue(reporteActual.getEstado());

        seleccionarPorId(cbTutor, reporteActual.getIdUsuario(), Usuario::getIdUsuario);

        seleccionarPorId(cbEstudiante, reporteActual.getIdEstudiante(), Estudiante::getIdEstudiante);

        seleccionarPorId(cbPeriodo, reporteActual.getIdPeriodoEscolar(), PeriodoEscolar::getIdPeriodoEscolar);

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

        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
