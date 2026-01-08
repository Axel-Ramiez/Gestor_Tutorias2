package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;

import gestor_tutorias.pojo.Estudiante;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.stage.Modality;

import javafx.stage.Stage;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

public class FXMLEstudiante {

    @FXML private TableView<Estudiante> tablaEstudiante;

    @FXML private TableColumn<Estudiante, String> colMatricula;

    @FXML private TableColumn<Estudiante, String> colNombreCompleto;

    @FXML private TableColumn<Estudiante, String> colCarrera;

    @FXML private TableColumn<Estudiante, Integer> colSemestre;

    @FXML private TableColumn<Estudiante, String> colTutor;

    private ObservableList<Estudiante> listaEstudiantes;

    @FXML

    private void initialize() {

        configurarTabla();

        cargarEstudiantes();

    }

    private void configurarTabla() {

        colMatricula.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("matriculaEstudiante"));

        colNombreCompleto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(

                c.getValue().getNombreEstudiante() + " " + c.getValue().getApellidoPaternoEstudiante()

        ));

        colCarrera.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("carreraNombre"));

        colSemestre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("semestreEstudiante"));

        colTutor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("tutorNombre"));

    }

    private void cargarEstudiantes() {

        try {

            List<Estudiante> resultado = EstudianteDAO.obtenerTodos();

            listaEstudiantes = FXCollections.observableArrayList(resultado);

            tablaEstudiante.setItems(listaEstudiantes);

        } catch (SQLException e) {

            mostrarAlerta("Error", "No se pudo conectar con la base de datos.");

        }

    }

    @FXML

    private void clicConsultar(ActionEvent event) {

        Estudiante seleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            mostrarAlerta("Aviso", "Seleccione un estudiante de la tabla.");

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLEstudianteConsulta.fxml"));

            Parent root = loader.load();

            FXMLEstudianteConsulta controlador = loader.getController();

            controlador.inicializarInformacion(seleccionado);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Detalles del Estudiante");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarEstudiantes(); // Recargar por si se editó

        } catch (IOException e) {

            e.printStackTrace();

            mostrarAlerta("Error", "No se pudo abrir la ventana de detalles.");

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
