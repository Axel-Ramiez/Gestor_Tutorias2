package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;
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
public class FXMLPlaneacionTutoria implements Initializable {

    @FXML
    private TableView<PlaneacionTutoria> reportetutoria;

    @FXML
    private TableColumn<PlaneacionTutoria, Integer> periodoescolar;

    @FXML
    private TableColumn<PlaneacionTutoria, Integer> carrera;

    @FXML
    private TableColumn<PlaneacionTutoria, Integer> sesion;

    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();
    private ObservableList<PlaneacionTutoria> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("idPeriodo"));
        carrera.setCellValueFactory(new PropertyValueFactory<>("idCarrera"));
        sesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        cargarPlaneaciones();
    }

    @FXML
    private void consultarreportetutoria() {
        cargarPlaneaciones();
    }

    @FXML
    private void crearreportetutoria() {
        // Navegación a pantalla de creación (FXMLPlaneacionTutoriaConsulta)
        // Se maneja desde el main o gestor de escenas
    }

    private void cargarPlaneaciones() {
        try {
            List<PlaneacionTutoria> datos = dao.obtenerTodas();
            lista = FXCollections.observableArrayList(datos);
            reportetutoria.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
