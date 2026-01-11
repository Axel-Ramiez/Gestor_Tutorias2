package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.dao.PeriodoEscolarDAO;
import gestor_tutorias.dao.PlaneacionTutoriaDAO;
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
import java.time.LocalDate;
import java.util.List;

public class FXMLReporteTutoriaCrear {

    @FXML private Label lbIdReporte;
    @FXML private ComboBox<LocalDate> cbFechaReporte;
    @FXML private ComboBox<EstadoReporte> cbEstado;
    @FXML private ComboBox<Usuario> cbTutor;
    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;
    @FXML private CheckBox chkAsistencia;
    @FXML private TextArea taTextoReporte;
    @FXML private TextArea taRespuestaCoordinador;

    private ReporteTutoria reporteActual;
    private final ReporteTutoriaDAO reporteDAO = new ReporteTutoriaDAO();
    private final PlaneacionTutoriaDAO planeacionDAO = new PlaneacionTutoriaDAO();

    @FXML
    private void initialize() {
        reporteActual = new ReporteTutoria();

        cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));
        cbEstado.setValue(EstadoReporte.PENDIENTE);

        try {
            cargarComboTutores(cbTutor);
            cargarComboEstudiantes(cbEstudiante);
            cargarComboPeriodos(cbPeriodo);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los catálogos.");
        }

        cbPeriodo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFechasDeTutoria(newValue.getIdPeriodoEscolar());
            } else {
                cbFechaReporte.getItems().clear();
            }
        });
    }

    private void cargarFechasDeTutoria(int idPeriodo) {
        try {
            cbFechaReporte.getItems().clear();
            List<LocalDate> fechas = planeacionDAO.obtenerFechasPorPeriodo(idPeriodo);

            if (fechas.isEmpty()) {
                mostrarAlerta("Aviso", "No hay fechas de tutoría planeadas para este periodo.");
            } else {
                cbFechaReporte.setItems(FXCollections.observableArrayList(fechas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar las fechas de la planeación.");
        }
    }

    private void cargarComboTutores(ComboBox<Usuario> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(UsuarioDAO.obtenerTutores()));
    }

    private void cargarComboEstudiantes(ComboBox<Estudiante> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(EstudianteDAO.obtenerTodos()));
    }

    private void cargarComboPeriodos(ComboBox<PeriodoEscolar> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(PeriodoEscolarDAO.obtenerTodos()));
    }

    private <T> int obtenerIdSeleccionado(ComboBox<T> combo, java.util.function.Function<T, Integer> extractorId) {
        T seleccionado = combo.getValue();
        return seleccionado != null ? extractorId.apply(seleccionado) : 0;
    }

    @FXML
    public void clicGuardar(ActionEvent event) {
        if (cbFechaReporte.getValue() == null || cbTutor.getValue() == null ||
                cbEstudiante.getValue() == null || cbPeriodo.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Por favor seleccione Tutor, Estudiante, Periodo y Fecha.");
            return;
        }

        try {
            juntarDatos();
            int idGenerado = reporteDAO.guardarReporte(reporteActual);

            if (idGenerado > 0) {
                if (!chkAsistencia.isSelected()) {
                    EstudianteDAO.cambiarEstadoRiesgo(reporteActual.getIdEstudiante(), true);
                }
                if (chkAsistencia.isSelected()) {
                    EstudianteDAO.cambiarEstadoRiesgo(reporteActual.getIdEstudiante(), false);
                }

                mostrarAlerta("Éxito", "Reporte creado correctamente.");
                cerrar(event);
            } else {
                mostrarAlerta("Error", "No se pudo guardar en la base de datos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar: " + e.getMessage());
        }
    }

    private void juntarDatos() {
        reporteActual.setFechaReporte(cbFechaReporte.getValue());
        reporteActual.setTextoReporte(taTextoReporte.getText());
        reporteActual.setRespuestaCoordinador(taRespuestaCoordinador.getText());
        reporteActual.setAsistencia(chkAsistencia.isSelected());
        reporteActual.setEstado(cbEstado.getValue());

        reporteActual.setIdUsuario(obtenerIdSeleccionado(cbTutor, Usuario::getIdUsuario));
        reporteActual.setIdEstudiante(obtenerIdSeleccionado(cbEstudiante, Estudiante::getIdEstudiante));
        reporteActual.setIdPeriodoEscolar(obtenerIdSeleccionado(cbPeriodo, PeriodoEscolar::getIdPeriodoEscolar));
    }

    @FXML
    public void clicCerrar(ActionEvent event) {
        cerrar(event);
    }

    @FXML
    public void clicCancelar(ActionEvent event) {
        cerrar(event);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (titulo.contains("Error") || titulo.contains("vacíos")) alert.setAlertType(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
