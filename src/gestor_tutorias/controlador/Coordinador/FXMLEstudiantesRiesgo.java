package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLEstudiantesRiesgo implements Initializable {

    @FXML private TableView<Estudiante> tbEstudiantes;
    @FXML private TableColumn colMatricula;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellidoPaterno;
    @FXML private TableColumn colApellidoMaterno;
    @FXML private TableColumn colSemestre;
    @FXML private TableColumn colCorreo;
    @FXML private TextField tfBusqueda;

    private ObservableList<Estudiante> estudiantesList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();
    }

    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matriculaEstudiante"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreEstudiante"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaternoEstudiante"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaternoEstudiante"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestreEstudiante"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correoEstudiante"));
    }

    private void cargarDatos() {
        try {
            List<Estudiante> listaRiesgo = EstudianteDAO.obtenerEstudiantesEnRiesgo();
            estudiantesList = FXCollections.observableArrayList(listaRiesgo);
            configurarBusqueda();

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
        }
    }

    private void configurarBusqueda() {
        if (estudiantesList != null) {
            FilteredList<Estudiante> filtro = new FilteredList<>(estudiantesList, p -> true);

            tfBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate(estudiante -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerNewValue = newValue.toLowerCase();

                    if (estudiante.getNombreEstudiante() != null &&
                            estudiante.getNombreEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (estudiante.getApellidoPaternoEstudiante() != null &&
                            estudiante.getApellidoPaternoEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (estudiante.getApellidoMaternoEstudiante() != null &&
                            estudiante.getApellidoMaternoEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (estudiante.getMatriculaEstudiante() != null &&
                            estudiante.getMatriculaEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (estudiante.getCorreoEstudiante() != null &&
                            estudiante.getCorreoEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (String.valueOf(estudiante.getSemestreEstudiante()).contains(lowerNewValue)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<Estudiante> sortedData = new SortedList<>(filtro);

            sortedData.comparatorProperty().bind(tbEstudiantes.comparatorProperty());

            tbEstudiantes.setItems(sortedData);
        }
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        Stage stage = (Stage) tbEstudiantes.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}