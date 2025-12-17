package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FXMLReporteTutoria {

    @FXML private TableView<ReporteTutoria> tvReportes;

    @FXML private TableColumn<ReporteTutoria, Integer> colIdReporte;
    @FXML private TableColumn<ReporteTutoria, String> colFecha;
    @FXML private TableColumn<ReporteTutoria, String> colPeriodo;
    @FXML private TableColumn<ReporteTutoria, String> colEstudiante;
    @FXML private TableColumn<ReporteTutoria, String> colEstado;
    @FXML private TableColumn<ReporteTutoria, Boolean> colAsistencia;

    @FXML private Button btnConsultar;
    @FXML private Button btnCrear;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private ObservableList<ReporteTutoria> lista;
    private ReporteTutoria seleccionado;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarReportes();
    }
    public void inicializarInformacion(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario tutor no puede ser null");
        }
        configurarTabla();
        cargarReportes();
    }

    private void configurarTabla() {
        colIdReporte.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistencia"));

        tvReportes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> seleccionado = newVal
        );
    }

    private void cargarReportes() {
        try {
            List<ReporteTutoria> datos = dao.obtenerTodos();
            lista = FXCollections.observableArrayList(datos);
            tvReportes.setItems(lista);
        } catch (SQLException e) {
            mostrarAlerta("Error BD", "No se pudieron cargar los reportes.");
        }
    }

    @FXML
    private void crearReporteTutoria() {
        abrirFormulario(null);
    }

    @FXML
    private void consultarReporteTutoria() {
        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Selecciona un reporte.");
            return;
        }
        abrirFormulario(seleccionado);
    }

    private void abrirFormulario(ReporteTutoria r) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaConsulta.fxml")
            );
            Parent root = loader.load();

            FXMLReporteTutoriaConsulta controlador = loader.getController();
            controlador.inicializarFormulario(r, this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(r == null ? "Nuevo Reporte" : "Editar Reporte");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarReportes();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario.");
        }
    }

    public void refrescarTabla() {
        cargarReportes();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
