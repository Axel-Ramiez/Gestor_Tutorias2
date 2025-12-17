package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLReporteTutoria implements Initializable {

    @FXML
    private TableView<ReporteTutoria> tablaReporteTutoria;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idReporte;
    @FXML
    private TableColumn<ReporteTutoria, Integer> idTutoria;
    @FXML
    private TableColumn<ReporteTutoria, String> fecha;
    @FXML
    private TableColumn<ReporteTutoria, String> periodoEscolar;
    @FXML
    private TableColumn<ReporteTutoria, Integer> idTutor;
    @FXML
    private TableColumn<ReporteTutoria, Integer> idEstudiante;

    private ObservableList<ReporteTutoria> listaReportes;
    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idReporte.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        idTutoria.setCellValueFactory(new PropertyValueFactory<>("idFechaTutoria"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        periodoEscolar.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        idTutor.setCellValueFactory(new PropertyValueFactory<>("idTutor"));
        idEstudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));

        cargarReportes();
    }

    @FXML
    private void consultarReporteTutoria() throws IOException {
        ReporteTutoria seleccionado = tablaReporteTutoria.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un reporte de la lista");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/gestor_tutorias/vista/Coordinador/FXMLReporteTutoriaConsulta.fxml"));
        Parent root = loader.load();


        FXMLReporteTutoriaConsulta controlador = loader.getController();
        controlador.cargarReporte(seleccionado);


        Stage stage = new Stage();
        stage.setTitle("Consulta de Reporte de Tutoría");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void cargarReportes() {
        try {
            List<ReporteTutoria> reportes = dao.obtenerTodos();
            listaReportes = FXCollections.observableArrayList(reportes);
            tablaReporteTutoria.setItems(listaReportes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


