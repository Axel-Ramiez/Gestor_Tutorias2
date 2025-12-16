package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.pojo.ReporteTutoria;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLTutoria {

    @FXML
    private TableView<ReporteTutoria> tablareportestutoria;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idtutoria;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idtutor;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idestudiante;

    @FXML
    private TableColumn<ReporteTutoria, Integer> periodoescolar;

    @FXML
    private Button crear;

    @FXML
    private Button consultar;

    private ObservableList<ReporteTutoria> listaReportes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();
        cargarDatosDummy(); // luego va DAO
    }

    private void configurarTabla() {
        idtutoria.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        idtutor.setCellValueFactory(new PropertyValueFactory<>("idTutor"));
        idestudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("idFechaTutoria"));
    }

    private void cargarDatosDummy() {
        listaReportes.add(new ReporteTutoria(
                1, 1, 1, 1, "Descripción ejemplo", true, null
        ));
        tablareportestutoria.setItems(listaReportes);
    }

    @FXML
    private void crearreportetutoria() {
        System.out.println("Crear nuevo reporte de tutoría");
        // Aquí abrirías FXML de creación
    }

    @FXML
    private void consultarreportetutoria() {
        ReporteTutoria seleccionado = tablareportestutoria.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            System.out.println("Consultar reporte ID: " + seleccionado.getIdReporte());
        }
    }
}
