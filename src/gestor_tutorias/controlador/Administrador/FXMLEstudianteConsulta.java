package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;

    private ObservableList<Estudiante> estudiantesList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEstudiantes();
    }

    private void configurarColumnas() {
        colMatricula.setCellValueFactory(new PropertyValueFactory("matriculaEstudiante"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreEstudiante"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaternoEstudiante"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaternoEstudiante"));
        colCarrera.setCellValueFactory(new PropertyValueFactory("carreraNombre"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestreEstudiante"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correoEstudiante"));
    }

    private void cargarEstudiantes() {
        try {
            List<Estudiante> resultado = EstudianteDAO.obtenerTodos();
            estudiantesList = FXCollections.observableArrayList(resultado);
            configurarBusqueda();
        } catch (SQLException ex) {
            mostrarAlerta("Error de BD", "No se pudieron cargar los estudiantes.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLEstudiante.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Registrar Estudiante");
            stage.showAndWait();

            cargarEstudiantes();

        } catch (IOException ex) {
            System.err.println("Error al cargar la ventana de registro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tbEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLEstudianteEditar.fxml"));
                Parent root = loader.load();

                FXMLEstudiante controlador = loader.getController();
                controlador.inicializarValores(estudianteSeleccionado);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setTitle("Editar Estudiante");
                stage.showAndWait();

                cargarEstudiantes();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
            alert.setContentText("¿Eliminar al estudiante " + estudianteSeleccionado.getNombreEstudiante() + "?");

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
                    if (estudiante.getCarreraNombre() != null &&
                            estudiante.getCarreraNombre().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (estudiante.getCorreoEstudiante() != null &&
                        estudiante.getCorreoEstudiante().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (String .valueOf(estudiante.getSemestreEstudiante()).toLowerCase().contains(lowerNewValue)) {
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
}
