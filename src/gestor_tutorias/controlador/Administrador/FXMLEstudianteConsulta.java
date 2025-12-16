package gestor_tutorias.controlador.Administrador;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLEstudianteConsulta implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<Estudiante> tbEstudiantes;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colSemestre;
    @FXML
    private TableColumn colCorreo;

    private ObservableList<Estudiante> estudiantesList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEstudiantes();
    }

    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        colCarrera.setCellValueFactory(new PropertyValueFactory("idCarrera"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarEstudiantes() {
        try {
            // Este método debes tenerlo en tu EstudianteDAO
            List<Estudiante> resultado = EstudianteDAO.obtenerTodos();
            estudiantesList = FXCollections.observableArrayList(resultado);
            tbEstudiantes.setItems(estudiantesList);
        } catch (SQLException ex) {
            mostrarAlerta("Error de BD", "No se pudieron cargar los estudiantes.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        mostrarAlerta("Navegación", "Ir a pantalla de registrar Estudiante.");
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tbEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            mostrarAlerta("Selección", "Vas a editar a: " + estudianteSeleccionado.getNombreCompleto());
        } else {
            mostrarAlerta("Aviso", "Selecciona un estudiante.");
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tbEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Eliminar al estudiante " + estudianteSeleccionado.getNombreCompleto() + "?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {

                    boolean exito = EstudianteDAO.eliminarEstudiante(estudianteSeleccionado.getIdEstudiante());
                    if (exito) {
                        mostrarAlerta("Éxito", "Estudiante eliminado.");
                        cargarEstudiantes();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona un estudiante para eliminar.");
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
