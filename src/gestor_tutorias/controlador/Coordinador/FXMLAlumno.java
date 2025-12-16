package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
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

public class FXMLAlumno implements Initializable {

    @FXML
    private TableView<Estudiante> tablaalumno;

    @FXML
    private TableColumn<Estudiante, Integer> id;

    @FXML
    private TableColumn<Estudiante, String> matricula;

    @FXML
    private TableColumn<Estudiante, String> nombre;

    @FXML
    private TableColumn<Estudiante, String> correo;

    @FXML
    private TableColumn<Estudiante, Integer> idcarrera;

    @FXML
    private TableColumn<Estudiante, Integer> semestre;

    @FXML
    private TableColumn<Estudiante, Integer> activo;

    private ObservableList<Estudiante> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));
        matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        idcarrera.setCellValueFactory(new PropertyValueFactory<>("idCarrera"));
        semestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        activo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        cargarAlumnos();
    }

    @FXML
    private void consultarAlumno() {
        cargarAlumnos();
    }

    @FXML
    private void crearAlumno() {
        // Navegación a FXMLAlumnoConsulta
        // Controlada desde el gestor de escenas
    }

    private void cargarAlumnos() {
        try {
            List<Estudiante> alumnos = EstudianteDAO.obtenerTodos();
            lista = FXCollections.observableArrayList(alumnos);
            tablaalumno.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
