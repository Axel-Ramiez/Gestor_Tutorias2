package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPlaneacionTutoria implements Initializable {

    @FXML
    private TableView<PlaneacionTutoria> reportetutoria;
    @FXML private TableColumn<PlaneacionTutoria, Integer> fechainicio; // Columna para ID
    @FXML private TableColumn<PlaneacionTutoria, String> periodoescolar;
    @FXML private TableColumn<PlaneacionTutoria, String> carrera;
    @FXML private TableColumn<PlaneacionTutoria, String> fecha;
    @FXML private TableColumn<PlaneacionTutoria, Integer> sesion;
    @FXML private TableColumn<PlaneacionTutoria, String> temas;

    private ObservableList<PlaneacionTutoria> listaPlaneaciones;
    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }

    private void configurarTabla() {

        fechainicio.setCellValueFactory(new PropertyValueFactory<>("idPlaneacionTutoria"));
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("periodoNombre"));
        carrera.setCellValueFactory(new PropertyValueFactory<>("carreraNombre"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fechaTutoria"));
        sesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        temas.setCellValueFactory(new PropertyValueFactory<>("temas"));
    }

    private void cargarInformacionTabla() {
        try {
            List<PlaneacionTutoria> resultados = dao.obtenerTodas();
            listaPlaneaciones = FXCollections.observableArrayList(resultados);
            reportetutoria.setItems(listaPlaneaciones);
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "No se pudo cargar la lista de planeaciones.");
        }
    }

    @FXML
    private void consultarPlaneacionTutoria(ActionEvent event) {
        PlaneacionTutoria seleccion = reportetutoria.getSelectionModel().getSelectedItem();

        if (seleccion != null) {
            irPantallaConsulta(seleccion);
        } else {
            mostrarAlerta("Selección requerida", "Por favor selecciona una fila de la tabla para consultar.");
        }
    }

    private void irPantallaConsulta(PlaneacionTutoria planeacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLPlaneacionTutoriaConsulta.fxml"));
            Parent root = loader.load();

            FXMLPlaneacionTutoriaConsulta controlador = loader.getController();
            controlador.inicializarValores(planeacion);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle de Planeación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (titulo.toLowerCase().contains("error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}