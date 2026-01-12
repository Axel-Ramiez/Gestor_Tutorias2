package gestor_tutorias.controlador.coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 17/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador para visualizar el listado general de estudiantes (Vista Coordinador).
 * Permite seleccionar un alumno para consultar y editar sus detalles.
 */
public class FXMLEstudiante implements Initializable {

    @FXML private TableView<Estudiante> tvEstudiante;
    @FXML private TableColumn<Estudiante, String> colMatricula;
    @FXML private TableColumn<Estudiante, String> colNombreCompleto;
    @FXML private TableColumn<Estudiante, String> colCarrera;
    @FXML private TableColumn<Estudiante, Integer> colSemestre;
    @FXML private TableColumn<Estudiante, String> colTutor;
    private ObservableList<Estudiante> listaEstudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarEstudiantes();
    }

    @FXML
    private void clicConsultar(ActionEvent event) {
        Estudiante seleccionado = tvEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un estudiante de la tabla.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/coordinador/FXMLEstudianteConsulta.fxml"));
            Parent root = loader.load();

            FXMLEstudianteConsulta controlador = loader.getController();
            controlador.inicializarInformacion(seleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Detalles del Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            cargarEstudiantes();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de detalles.");
        }
    }


    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaEstudiante"));
        colNombreCompleto.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getNombreEstudiante() + " " + c.getValue().getApellidoPaternoEstudiante()
        ));

        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carreraNombre"));
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestreEstudiante"));
        colTutor.setCellValueFactory(new PropertyValueFactory<>("tutorNombre"));
    }

    private void cargarEstudiantes() {
        try {
            List<Estudiante> resultado = EstudianteDAO.obtenerTodos();
            listaEstudiantes = FXCollections.observableArrayList(resultado);
            tvEstudiante.setItems(listaEstudiantes);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
