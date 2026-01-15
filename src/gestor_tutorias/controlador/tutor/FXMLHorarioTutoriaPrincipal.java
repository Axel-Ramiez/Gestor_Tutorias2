package gestor_tutorias.controlador.tutor;

import gestor_tutorias.dao.HorarioTutoriaDAO;
import gestor_tutorias.pojo.HorarioTutoria;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 17/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador principal para la gestión de Horarios de Tutoría.
 * Permite listar, crear, editar, eliminar y consultar horarios.
 */
public class FXMLHorarioTutoriaPrincipal {
    @FXML private TableView<HorarioTutoria> tvHorarios;
    @FXML private TableColumn<HorarioTutoria, Integer> colIdHorario;
    @FXML private TableColumn<HorarioTutoria, String> colFecha;
    @FXML private TableColumn<HorarioTutoria, String> colHoraInicio;
    @FXML private TableColumn<HorarioTutoria, String> colHoraFin;
    @FXML private TableColumn<HorarioTutoria, String> colTutor;
    @FXML private TableColumn<HorarioTutoria, String> colEstudiante;
    @FXML private TableColumn<HorarioTutoria, String> colPeriodoEscolar;
    private final HorarioTutoriaDAO horarioDAO = new HorarioTutoriaDAO();
    private ObservableList<HorarioTutoria> listaHorarios;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarHorarios();
    }



    @FXML
    public void clicCrear(ActionEvent actionEvent) {
        cambiarVentana("/gestor_tutorias/vista/tutor/FXMLHorarioTutoriaCrear.fxml", "Crear Horario");
        cargarHorarios();
    }

    @FXML
    public void clicEditar(ActionEvent actionEvent) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un horario para editar.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/tutor/FXMLHorarioTutoriaEditar.fxml"));
            Parent root = loader.load();

            FXMLHorarioTutoriaEditar controlador = loader.getController();
            controlador.setIdHorarioTutoria(seleccionado.getIdHorarioTutoria());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Horario");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarHorarios();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        }
    }

    @FXML
    public void clicConsultar(ActionEvent actionEvent) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un horario para consultar.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/tutor/FXMLHorarioTutoriaConsultar.fxml"));
            Parent root = loader.load();

            FXMLHorarioTutoriaConsultar controlador = loader.getController();
            controlador.setIdHorarioTutoria(seleccionado.getIdHorarioTutoria());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consulta Horario");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarHorarios();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        }
    }

    @FXML
    public void clicEliminar(ActionEvent actionEvent) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un horario para eliminar.");
            return;
        }
        try {
            horarioDAO.eliminarHorario(seleccionado.getIdHorarioTutoria());
            cargarHorarios();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el horario.");
            e.printStackTrace();
        }
    }

    private void configurarTabla() {
        colIdHorario.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdHorarioTutoria()
                ).asObject()
        );

        colFecha.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFechaHorarioTutoria() != null
                                ? c.getValue().getFechaHorarioTutoria()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                : ""
                )
        );

        colHoraInicio.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getHoraInicioHorarioTutoria() != null
                                ? c.getValue().getHoraInicioHorarioTutoria().toString()
                                : ""
                )
        );

        colHoraFin.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getHoraFinHorarioTutoria() != null
                                ? c.getValue().getHoraFinHorarioTutoria().toString()
                                : ""
                )
        );

        colTutor.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNombreTutor() != null
                                ? c.getValue().getNombreTutor()
                                : "Sin Asignar"
                )
        );

        colEstudiante.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNombreEstudiante() != null
                                ? c.getValue().getNombreEstudiante()
                                : "Sin Asignar"
                )
        );

        colPeriodoEscolar.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNombrePeriodoEscolar() != null
                                ? c.getValue().getNombrePeriodoEscolar()
                                : "Sin Asignar"
                )
        );
    }

    private void cargarHorarios() {
        try {
            List<HorarioTutoria> horarios = horarioDAO.obtenerTodos();
            listaHorarios = FXCollections.observableArrayList(horarios);
            tvHorarios.setItems(listaHorarios);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los horarios.");
            e.printStackTrace();
        }
    }

    private void cambiarVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (titulo.contains("Error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}