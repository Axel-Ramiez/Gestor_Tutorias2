package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class FXMLReporteTutoria implements Initializable {

    @FXML
    private TableView<ReporteTutoria> reportetutoria;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idtutoria;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idtutor;

    @FXML
    private TableColumn<ReporteTutoria, Integer> idestudiante;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private ObservableList<ReporteTutoria> listaReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idtutoria.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        idtutor.setCellValueFactory(new PropertyValueFactory<>("idTutor"));
        idestudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));

        cargarReportes();
    }

    @FXML
    private void consultarReporteTutoria() {
        cargarReportes();
    }

    private void cargarReportes() {
        try {
            List<ReporteTutoria> reportes = dao.obtenerTodos();
            listaReportes = FXCollections.observableArrayList(reportes);
            reportetutoria.setItems(listaReportes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
