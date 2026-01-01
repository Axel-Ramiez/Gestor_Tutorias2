package gestor_tutorias.controlador.Coordinador;/*package gestor_tutorias.controlador.Coordinador;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLEstudiante implements Initializable {

    @FXML
    private TableView<Estudiante> tablaEstudiante;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombreCompleto;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colSemestre;
    @FXML
    private TableColumn colTutor;

    private ObservableList<Estudiante> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarAlumnos();
    }

    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        colCarrera.setCellValueFactory(new PropertyValueFactory("carreraNombre"));
        colTutor.setCellValueFactory(new PropertyValueFactory("tutorNombre"));
    }

    @FXML
    private void clicConsultar() {
        Estudiante seleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un estudiante de la lista para asignar tutor.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLEstudianteConsulta.fxml"));
            Parent root = loader.load();

            FXMLEstudianteConsulta controlador = loader.getController();
            controlador.cargarEstudiante(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Asignar Tutor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarAlumnos();

        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se pudo abrir la ventana de consulta: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarAlumnos() {
        try {
            List<Estudiante> alumnos = EstudianteDAO.obtenerTodos();
            lista = FXCollections.observableArrayList(alumnos);
            tablaEstudiante.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión");
            alert.setContentText("No se pudo conectar con la base de datos.");
            alert.showAndWait();
        }
    }
}*/