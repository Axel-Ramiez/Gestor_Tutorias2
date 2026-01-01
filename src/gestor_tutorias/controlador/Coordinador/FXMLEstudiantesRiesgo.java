package gestor_tutorias.controlador.Coordinador;/*package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLEstudiantesRiesgo implements Initializable {

    @FXML private TableView<Estudiante> tbEstudiantes;
    @FXML private TableColumn colMatricula;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colSemestre;
    @FXML private TableColumn colCorreo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();
    }

    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarDatos() {
        try {
            List<Estudiante> listaRiesgo = EstudianteDAO.obtenerEstudiantesEnRiesgo();
            ObservableList<Estudiante> datos = FXCollections.observableArrayList(listaRiesgo);
            tbEstudiantes.setItems(datos);


        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
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
*/