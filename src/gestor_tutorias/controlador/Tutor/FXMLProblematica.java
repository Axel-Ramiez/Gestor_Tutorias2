package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.Enum.EstatusProblematica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FXMLProblematica {

    @FXML private TableView<Problematica> tvProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdReporte;
    @FXML private TableColumn<Problematica, String> colTitulo;
    @FXML private TableColumn<Problematica, String> colDescripcion;
    @FXML private TableColumn<Problematica, Integer> colEE;
    @FXML private TableColumn<Problematica, EstatusProblematica> colEstado;

    private final ProblematicaDAO dao = new ProblematicaDAO();
    private ObservableList<Problematica> listaObservable;
    private Problematica selectedProblematica;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarProblematica();
    }

    private void configurarTabla() {
        colIdProblematica.setCellValueFactory(new PropertyValueFactory<>("idProblematica"));
        colIdReporte.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEE.setCellValueFactory(new PropertyValueFactory<>("idExperienciaEducativa"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tvProblematica.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedProblematica = newSel;
        });
    }

    public void cargarProblematica() {
        try {
            List<Problematica> lista = dao.obtenerTodas();
            listaObservable = FXCollections.observableArrayList(lista);
            tvProblematica.setItems(listaObservable);
        } catch (SQLException e) {
            mostrarAlerta("Error de conexión", "No se pudieron obtener los datos de la base de datos.");
        }
    }

    @FXML
    private void consultarProblematica() {
        if (selectedProblematica == null) {
            mostrarAlerta("Selección requerida", "Por favor, selecciona una problemática de la lista.");
            return;
        }
        abrirVentanaProblematica(selectedProblematica);
    }

    @FXML
    private void crearProblematica() {
        abrirVentanaProblematica(null);
    }

    private void abrirVentanaProblematica(Problematica p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml"));
            Parent root = loader.load();

            FXMLProblematicaConsulta controlador = loader.getController();
            controlador.inicializarFormulario(p, this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(p == null ? "Registrar Problemática" : "Consultar/Editar Problemática");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarProblematica(); // Refrescar tras cerrar
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de detalle.");
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