package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLReporteTutoria implements Initializable {

    @FXML private TableView<ReporteTutoria> tvReportes;
    @FXML private TableColumn<ReporteTutoria, Integer> colIdReporte;
    @FXML private TableColumn<ReporteTutoria, String> colFecha;
    @FXML private TableColumn<ReporteTutoria, String> colPeriodo;
    @FXML private TableColumn<ReporteTutoria, String> colEstudiante;
    @FXML private TableColumn<ReporteTutoria, String> colEstado;
    @FXML private TableColumn<ReporteTutoria, Boolean> colAsistencia;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private ObservableList<ReporteTutoria> listaObservable;
    private ReporteTutoria reporteSeleccionado;
    private Usuario tutorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    private void configurarTabla() {
        colIdReporte.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistencia"));

        tvReportes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            reporteSeleccionado = newSel;
        });

        tvReportes.setRowFactory(tv -> {
            TableRow<ReporteTutoria> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    consultarReporteTutoria();
                }
            });
            return row;
        });
    }

    public void inicializarInformacion(Usuario usuario) {
        this.tutorSesion = usuario;
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            List<ReporteTutoria> lista;
            if (tutorSesion != null) {
                lista = dao.obtenerPorTutor(tutorSesion.getIdUsuario());
            } else {
                lista = dao.obtenerTodos();
            }
            listaObservable = FXCollections.observableArrayList(lista);
            tvReportes.setItems(listaObservable);
        } catch (SQLException e) {
            mostrarAlerta("Error de datos", "Error al conectar con la base de datos.");
        }
    }

    @FXML
    private void consultarReporteTutoria() {
        if (reporteSeleccionado == null) {
            mostrarAlerta("Seleccion requerida", "Selecciona un reporte de la tabla.");
            return;
        }
        abrirVentanaTutoria(reporteSeleccionado, false);
    }

    @FXML
    private void crearReporteTutoria() {
        abrirVentanaTutoria(null, true);
    }

    private void abrirVentanaTutoria(ReporteTutoria reporte, boolean esEdicion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaConsulta.fxml"));
            Parent root = loader.load();
            FXMLReporteTutoriaConsulta controlador = loader.getController();
            controlador.inicializarFormulario(reporte, tutorSesion, this, esEdicion);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Detalle de Reporte");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            cargarDatos();
        } catch (IOException e) {
            mostrarAlerta("Error de sistema", "No se pudo cargar la vista de consulta.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}