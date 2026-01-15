package gestor_tutorias.controlador.tutor;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
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

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 16/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador principal para la gestión de Problemáticas Académicas.
 * Permite listar, filtrar y acceder a las operaciones CRUD de las problemáticas.
 */
public class FXMLProblematicaPrincipal {


    @FXML private Label lblUsuario;
    @FXML private TableView<Problematica> tvProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdReporteTutoria;
    @FXML private TableColumn<Problematica, String> colTitulo;
    @FXML private TableColumn<Problematica, String> colDescripcion;
    @FXML private TableColumn<Problematica, Integer> colIdCarrera;
    @FXML private TableColumn<Problematica, String> colEstado;


    private final ProblematicaDAO problematicaDAO = new ProblematicaDAO();
    private ObservableList<Problematica> listaProblematicas;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarProblematicas();
    }


    @FXML
    public void crearProblematica(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/tutor/FXMLProblematicaCrear.fxml",
                "Crear Problemática"
        );
        cargarProblematicas();
    }

    @FXML
    public void editarProblematica(ActionEvent actionEvent) {
        Problematica seleccionada = tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/tutor/FXMLProblematicaEditar.fxml")
            );
            Parent root = loader.load();

            FXMLProblematicaEditar controlador = loader.getController();
            controlador.setIdProblematica(seleccionada.getIdProblematica());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Problemática");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarProblematicas();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de edición.");
            e.printStackTrace();
        }
    }

    @FXML
    public void consultarProblematica(ActionEvent actionEvent) {
        Problematica seleccionada = tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para consultar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/tutor/FXMLProblematicaConsulta.fxml")
            );
            Parent root = loader.load();

            FXMLProblematicaConsulta controlador = loader.getController();
            controlador.setIdProblematica(seleccionada.getIdProblematica());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar Problemática");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta.");
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarProblematica(ActionEvent actionEvent) {
        Problematica seleccionada = tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para eliminar.");
            return;
        }

        try {
            problematicaDAO.eliminarProblematica(seleccionada.getIdProblematica());
            cargarProblematicas();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar la problemática.");
            e.printStackTrace();
        }
    }


    private void configurarTabla() {
        colIdProblematica.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdProblematica()
                ).asObject()
        );

        colIdReporteTutoria.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdReporteTutoria()
                ).asObject()
        );

        colTitulo.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTitulo() != null
                                ? c.getValue().getTitulo()
                                : ""
                )
        );

        colDescripcion.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDescripcion() != null
                                ? c.getValue().getDescripcion()
                                : ""
                )
        );

        colIdCarrera.setCellValueFactory(c -> {
            if (c.getValue().getIdCarrera() != null) {
                return new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdCarrera()
                ).asObject();
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });

        colEstado.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEstado() != null
                                ? c.getValue().getEstado().name()
                                : ""
                )
        );
    }

    private void cargarProblematicas() {
        try {
            List<Problematica> lista = problematicaDAO.obtenerTodas();
            listaProblematicas = FXCollections.observableArrayList(lista);
            tvProblematica.setItems(listaProblematicas);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las problemáticas.");
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
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}