package gestor_tutorias.controlador.Tutor;

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
import java.sql.SQLException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FXMLProblematicaPrincipal {

    /* ===================== FXML ===================== */

    @FXML private Label lblUsuario;

    @FXML private TableView<Problematica> tvProblematica;

    @FXML private TableColumn<Problematica, Integer> colIdProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdReporteTutoria;
    @FXML private TableColumn<Problematica, String> colTitulo;
    @FXML private TableColumn<Problematica, String> colDescripcion;
    @FXML private TableColumn<Problematica, Integer> colIdCarrera;
    @FXML private TableColumn<Problematica, String> colEstado;

    /* ===================== DATOS ===================== */

    private final ProblematicaDAO problematicaDAO = new ProblematicaDAO();
    private ObservableList<Problematica> listaProblematicas;

    /* ===================== INIT ===================== */

    @FXML
    private void initialize() {
        configurarTabla();
        cargarProblematicas();
    }

    /* ===================== TABLA ===================== */

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

    /* ===================== CARGA ===================== */

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

    /* ===================== BOTONES ===================== */

    public void crearProblematica(ActionEvent actionEvent) {

        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLProblematicaCrear.fxml",
                "Crear Problemática"
        );

        cargarProblematicas();
    }

    public void editarProblematica(ActionEvent actionEvent) {

        Problematica seleccionada =
                tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/gestor_tutorias/vista/Tutor/FXMLProblematicaEditar.fxml"
                    )
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

    public void consultarProblematica(ActionEvent actionEvent) {

        Problematica seleccionada =
                tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para consultar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml"
                    )
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

    public void eliminarProblematica(ActionEvent actionEvent) {

        Problematica seleccionada =
                tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Aviso", "Seleccione una problemática para eliminar.");
            return;
        }

        try {
            problematicaDAO.eliminarProblematica(
                    seleccionada.getIdProblematica()
            );
            cargarProblematicas();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar la problemática.");
            e.printStackTrace();
        }
    }

    /* ===================== UTIL ===================== */

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

/*
    @FXML private TableView<Problematica> tvProblematica;

    @FXML private TableColumn<Problematica, Integer> colIdProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdReporte;
    @FXML private TableColumn<Problematica, String> colTitulo;
    @FXML private TableColumn<Problematica, String> colDescripcion;
    @FXML private TableColumn<Problematica, Integer> colEE;
    @FXML private TableColumn<Problematica, String> colEstado;

    private final ProblematicaDAO dao = new ProblematicaDAO();
    private ObservableList<Problematica> listaProblematica;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        refrescarTabla();
    }

    private void configurarTabla() {
        colIdProblematica.setCellValueFactory(
                new PropertyValueFactory<>("idProblematica")
        );
        colIdReporte.setCellValueFactory(
                new PropertyValueFactory<>("idReporte")
        );
        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo")
        );
        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );
        colEE.setCellValueFactory(
                new PropertyValueFactory<>("idExperienciaEducativa")
        );
        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );
    }

    public void refrescarTabla() {
        try {
            listaProblematica = FXCollections.observableArrayList(
                    dao.obtenerTodas()
            );
            tvProblematica.setItems(listaProblematica);
        } catch (SQLException e) {
            mostrarAlerta("Error BD", e.getMessage());
        }
    }

    @FXML
    private void crearProblematica() {
        abrirFormulario(null);
    }

    @FXML
    private void consultarProblematica() {
        Problematica seleccion = tvProblematica.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAlerta("Selecciona", "Selecciona una problemática.");
            return;
        }
        abrirFormulario(seleccion);
    }

    private void abrirFormulario(Problematica p) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml")
            );
            Parent root = loader.load();

            FXMLProblematicaConsulta controller = loader.getController();
            controller.inicializarFormulario(p, this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle de Problemática");
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

 */

