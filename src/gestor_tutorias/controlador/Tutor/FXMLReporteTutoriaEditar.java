package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import gestor_tutorias.dao.ReporteTutoriaDAO;
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
import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.dao.PeriodoEscolarDAO;
import gestor_tutorias.dao.UsuarioDAO;
import javafx.scene.control.*;
import java.sql.SQLException;

public class FXMLReporteTutoriaEditar {

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
        cbEstado.setItems(FXCollections.observableArrayList(EstadoReporte.values()));

        try {
            cargarComboTutores(cbTutor);
            cargarComboEstudiantes(cbEstudiante);
            cargarComboPeriodos(cbPeriodo);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los datos.");
        }

        configurarCampos();
    }

    // ===================== RECIBE ID DESDE PRINCIPAL =====================
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
        cargarReporte();
    }

    private void cargarComboTutores(ComboBox<Usuario> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(
                UsuarioDAO.obtenerTutores()
        ));
    }

    private void cargarComboEstudiantes(ComboBox<Estudiante> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(
                EstudianteDAO.obtenerTodos()
        ));
    }

    private void cargarComboPeriodos(ComboBox<PeriodoEscolar> combo) throws SQLException {
        combo.setItems(FXCollections.observableArrayList(
                PeriodoEscolarDAO.obtenerTodos()
        ));
    }

    private <T> int obtenerIdSeleccionado(
            ComboBox<T> combo,
            java.util.function.Function<T, Integer> extractorId
    ) {
        T seleccionado = combo.getValue();
        return seleccionado != null ? extractorId.apply(seleccionado) : 0;
    }

    private <T> void seleccionarPorId(
            ComboBox<T> combo,
            int idBuscado,
            java.util.function.Function<T, Integer> extractorId
    ) {
        for (T item : combo.getItems()) {
            if (extractorId.apply(item) == idBuscado) {
                combo.getSelectionModel().select(item);
                break;
            }
        }
    }


    // ===================== CARGA DESDE BD =====================
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

    // ===================== PINTA DATOS =====================
    private void cargarDatosEnVista() {
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


    // ===================== CONFIG CAMPOS =====================
    private void configurarCampos() {

        // ID siempre solo lectura
        lbIdReporte.setDisable(false);

        // Campos editables
        dpFechaReporte.setDisable(false);
        cbEstado.setDisable(false);
        chkAsistencia.setDisable(false);

        taTextoReporte.setEditable(true);
        taRespuestaCoordinador.setEditable(true);

        // Combos solo informativos (no editables sin DAOs extra)
        cbTutor.setDisable(false);
        cbEstudiante.setDisable(false);
        cbPeriodo.setDisable(false);
    }

    // ===================== GUARDAR =====================
    public void clicGuardar(ActionEvent event) {
        try {
            juntarDatos();
            reporteDAO.actualizarReporte(reporteActual);
            cerrar(event);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo guardar el reporte.");
        }
    }

    // ===================== JUNTAR DATOS =====================
    private void juntarDatos() {
        reporteActual.setFechaReporte(dpFechaReporte.getValue());
        reporteActual.setTextoReporte(taTextoReporte.getText());
        reporteActual.setRespuestaCoordinador(taRespuestaCoordinador.getText());
        reporteActual.setAsistencia(chkAsistencia.isSelected());
        reporteActual.setEstado(cbEstado.getValue());

        reporteActual.setIdUsuario(
                obtenerIdSeleccionado(cbTutor, Usuario::getIdUsuario)
        );
        reporteActual.setIdEstudiante(
                obtenerIdSeleccionado(cbEstudiante, Estudiante::getIdEstudiante)
        );
        reporteActual.setIdPeriodoEscolar(
                obtenerIdSeleccionado(cbPeriodo, PeriodoEscolar::getIdPeriodoEscolar)
        );
    }


    public void clicCerrar(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource())
                .getScene().getWindow();
        stage.close();
    }

    // ===================== CERRAR =====================
    public void clicCancelar(ActionEvent event) {
        cerrar(event);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        stage.close();
    }

    // ===================== ALERTA =====================
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
