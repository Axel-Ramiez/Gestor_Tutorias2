package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;

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

public class FXMLEstudiante implements Initializable {

    @FXML
    private TableView<Estudiante> tablaEstudiante;

    @FXML
    private TableColumn<Estudiante, Integer> id;

    @FXML
    private TableColumn<Estudiante, String> matricula;

    @FXML
    private TableColumn<Estudiante, String> nombre;

    @FXML
    private TableColumn<Estudiante, String> correo;

    @FXML
    private TableColumn<Estudiante, Integer> idCarrera;

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
        idCarrera.setCellValueFactory(new PropertyValueFactory<>("idCarrera"));
        semestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        activo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        cargarAlumnos();
    }

    @FXML
    private void consultarAlumno() throws IOException {
        Estudiante seleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un estudiante de la lista");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLEstudianteConsulta.fxml"));
        Parent root = loader.load();

        FXMLEstudianteConsulta controlador = loader.getController();
        controlador.cargarEstudiante(seleccionado);

        Stage stage = new Stage();
        stage.setTitle("Consulta de Estudiante");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void cargarAlumnos() {
        try {
            List<Estudiante> alumnos = EstudianteDAO.obtenerTodos();
            lista = FXCollections.observableArrayList(alumnos);
            tablaEstudiante.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}