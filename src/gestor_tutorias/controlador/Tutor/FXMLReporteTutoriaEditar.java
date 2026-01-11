package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.*;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.PeriodoEscolar;
import gestor_tutorias.pojo.Usuario;
import gestor_tutorias.pojo.Estudiante;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FXMLReporteTutoriaEditar {

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
        cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));

        try {
            cargarComboTutores(cbTutor);
            cargarComboEstudiantes(cbEstudiante);
            cargarComboPeriodos(cbPeriodo);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los datos.");
        }
        cbPeriodo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFechasDeTutoria(newValue.getIdPeriodoEscolar());
            } else {
                cbFechaReporte.getItems().clear();
            }
        });

        configurarCampos();
    }


    private void cargarFechasDeTutoria(int idPeriodo) {
        try {
            cbFechaReporte.getItems().clear();
            List<LocalDate> fechas = planeacionDAO.obtenerFechasPorPeriodo(idPeriodo);

            if (fechas.isEmpty()) {
            } else {
                cbFechaReporte.setItems(FXCollections.observableArrayList(fechas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar las fechas de la planeación.");
        }
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
        cargarReporte();
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
    private <T> void seleccionarPorId(ComboBox<T> combo, int idBuscado, java.util.function.Function<T, Integer> extractorId) {
        for (T item : combo.getItems()) {
            if (extractorId.apply(item) == idBuscado) {
                combo.getSelectionModel().select(item);
                break;
            }
        }
    }

    private void cargarReporte() {
        try {
            reporteActual = reporteDAO.obtenerPorId(idReporte);
            if (reporteActual != null) {
                cargarDatosEnVista();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el reporte.");
        }
    }

    private void cargarDatosEnVista() {
        lbIdReporte.setText(String.valueOf(reporteActual.getIdReporte()));
        taTextoReporte.setText(reporteActual.getTextoReporte());
        taRespuestaCoordinador.setText(reporteActual.getRespuestaCoordinador());
        chkAsistencia.setSelected(reporteActual.isAsistencia());
        cbEstado.setValue(reporteActual.getEstado());
        seleccionarPorId(cbTutor, reporteActual.getIdUsuario(), Usuario::getIdUsuario);
        seleccionarPorId(cbEstudiante, reporteActual.getIdEstudiante(), Estudiante::getIdEstudiante);
        seleccionarPorId(cbPeriodo, reporteActual.getIdPeriodoEscolar(), PeriodoEscolar::getIdPeriodoEscolar);
        cbFechaReporte.setValue(reporteActual.getFechaReporte());
    }

    private void configurarCampos() {
        lbIdReporte.setDisable(false);
        cbFechaReporte.setDisable(false);
        cbEstado.setDisable(false);
        chkAsistencia.setDisable(false);
        taTextoReporte.setEditable(true);
        taRespuestaCoordinador.setEditable(false);
        cbTutor.setDisable(false);
        cbEstudiante.setDisable(false);
        cbPeriodo.setDisable(false);
    }

    @FXML
    public void clicGuardar(ActionEvent event) {
        if (cbTutor.getValue() == null) {
            mostrarAlerta("Campo Requerido", "El campo Tutor es obligatorio.");
            return;
        }
        if (cbEstudiante.getValue() == null) {
            mostrarAlerta("Campo Requerido", "El campo Estudiante es obligatorio.");
            return;
        }
        if (cbPeriodo.getValue() == null) {
            mostrarAlerta("Campo Requerido", "El campo Periodo Escolar es obligatorio.");
            return;
        }
        if (cbFechaReporte.getValue() == null) {
            mostrarAlerta("Campo Requerido", "La fecha de tutoría es obligatoria.");
            return;
        }

        try {
            juntarDatos();

            boolean exito = reporteDAO.actualizarReporte(reporteActual);

            if (exito) {
                boolean asistio = chkAsistencia.isSelected();
                boolean enRiesgo = !asistio;
                EstudianteDAO.cambiarEstadoRiesgo(reporteActual.getIdEstudiante(), enRiesgo);

                mostrarAlerta("Éxito", "Reporte actualizado correctamente.");
                cerrar(event);
            } else {
                mostrarAlerta("Error", "No se pudieron guardar los cambios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Excepción al guardar: " + e.getMessage());
        }
    }

    private void juntarDatos() {
        reporteActual.setFechaReporte(cbFechaReporte.getValue());
        reporteActual.setTextoReporte(taTextoReporte.getText());
        reporteActual.setAsistencia(chkAsistencia.isSelected());
        reporteActual.setEstado(cbEstado.getValue());
        reporteActual.setIdUsuario(obtenerIdSeleccionado(cbTutor, Usuario::getIdUsuario));
        reporteActual.setIdEstudiante(obtenerIdSeleccionado(cbEstudiante, Estudiante::getIdEstudiante));
        reporteActual.setIdPeriodoEscolar(obtenerIdSeleccionado(cbPeriodo, PeriodoEscolar::getIdPeriodoEscolar));
    }

    public void clicCerrar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    public void clicCancelar(ActionEvent event) {
        cerrar(event);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert.AlertType tipo = Alert.AlertType.INFORMATION;
        if (titulo.toLowerCase().contains("error") || titulo.contains("Requerido")) {
            tipo = Alert.AlertType.ERROR;
        }

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
