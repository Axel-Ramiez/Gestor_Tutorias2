package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLTutoria implements Initializable {

    @FXML
    private TableView<ReporteTutoria> tablareportestutoria;
    @FXML
    private TableColumn<ReporteTutoria, Integer> idtutoria;
    @FXML
    private TableColumn<ReporteTutoria, String> idtutor;
    @FXML
    private TableColumn<ReporteTutoria, String> idestudiante;
    @FXML
    private TableColumn<ReporteTutoria, String> periodoescolar;

    @FXML
    private Button crear;

    @FXML
    private Button consultar;

    private ObservableList<ReporteTutoria> listaReportes;
    private Usuario tutorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarInformacion(Usuario usuario) {
        this.tutorSesion = usuario;
        if (tutorSesion != null) {
            cargarReportesBD();
        } else {
            mostrarAlerta("Error", "No se pudo identificar al tutor conectado.");
        }
    }

    private void configurarTabla() {
        idtutoria.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        idtutor.setCellValueFactory(new PropertyValueFactory<>("nombreTutor"));
        idestudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
    }

    private void cargarReportesBD() {
        try {
            ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
            List<ReporteTutoria> reportes = dao.obtenerPorTutor(tutorSesion.getIdUsuario());

            listaReportes = FXCollections.observableArrayList(reportes);
            tablareportestutoria.setItems(listaReportes);

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de Conexión", "No se pudieron cargar los reportes: " + ex.getMessage());
        }
    }

    @FXML
    private void crearReporteTutoria() {
        System.out.println("Navegar a pantalla de creación...");
    }

    @FXML
    private void consultarReporteTutoria() {
        ReporteTutoria seleccionado = tablareportestutoria.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            mostrarAlerta("Selección", "Has seleccionado el reporte de: " + seleccionado.getNombreEstudiante());
        } else {
            mostrarAlerta("Aviso", "Selecciona un reporte de la tabla primero.");
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
