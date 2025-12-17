package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.Enum.EstadoReporte;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLReporteTutoria implements Initializable {

    @FXML
    private TableView<ReporteTutoria> tvReportes;
    @FXML
    private TableColumn colIdReporte;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colPeriodo;
    @FXML
    private TableColumn colEstudiante;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colAsistencia;

    private ObservableList<ReporteTutoria> listaReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarReportes();
    }

    private void configurarColumnas() {
        colIdReporte.setCellValueFactory(new PropertyValueFactory("idReporte"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory("periodoEscolar"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory("nombreEstudiante"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colAsistencia.setCellValueFactory(new PropertyValueFactory("asistencia"));
        colAsistencia.setCellFactory(col -> new TableCell<ReporteTutoria, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Asistió" : "Falta");
                }
            }
        });
    }

    private void cargarReportes() {
        try {
            ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
            List<ReporteTutoria> resultados = dao.obtenerTodos();
            listaReportes = FXCollections.observableArrayList(resultados);
            tvReportes.setItems(listaReportes);
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "No se pudieron cargar los reportes.");
        }
    }

    @FXML
    private void consultarReporteTutoria(ActionEvent event) {
        ReporteTutoria seleccionado = tvReportes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Selecciona un reporte de la tabla para consultarlo.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLReporteTutoriaConsulta.fxml"));
            Parent root = loader.load();

            FXMLReporteTutoriaConsulta controlador = loader.getController();
            controlador.inicializarDatos(seleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Responder Reporte");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarReportes();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta.");
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


