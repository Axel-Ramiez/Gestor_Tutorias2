package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLPlaneacionTutoria implements Initializable {

    @FXML
    private TableView<PlaneacionTutoria> reportetutoria;

    @FXML
    private TableColumn<PlaneacionTutoria, Integer> fechainicio; // id_fecha_tutoria
    @FXML
    private TableColumn<PlaneacionTutoria, Integer> periodoescolar; // id_periodo
    @FXML
    private TableColumn<PlaneacionTutoria, Integer> carrera; // id_carrera
    @FXML
    private TableColumn<PlaneacionTutoria, Integer> sesion; // numero_sesion
    @FXML
    private TableColumn<PlaneacionTutoria, String> fecha; // fecha
    @FXML
    private TableColumn<PlaneacionTutoria, String> temas; // temas

    private ObservableList<PlaneacionTutoria> lista;

    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechainicio.setCellValueFactory(new PropertyValueFactory<>("idFechaTutoria"));
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("idPeriodo"));
        carrera.setCellValueFactory(new PropertyValueFactory<>("idCarrera"));
        sesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha")); // corresponde a la columna 'fecha' en DB
        temas.setCellValueFactory(new PropertyValueFactory<>("temas"));

        cargarPlaneaciones();
    }

    @FXML
    private void consultarPlaneacionTutoria() throws IOException {
        PlaneacionTutoria seleccionada = reportetutoria.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una planeación de la lista");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/gestor_tutorias/vista/Coordinador/FXMLPlaneacionTutoriaConsulta.fxml"));
        Parent root = loader.load();

        // Pasar la planeación seleccionada al controlador de edición
        FXMLPlaneacionTutoriaConsulta controlador = loader.getController();
        controlador.cargarPlaneacion(seleccionada);

        Stage stage = new Stage();
        stage.setTitle("Consulta / Edición de Planeación de Tutorías");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void crearPlaneacionTutoria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/gestor_tutorias/vista/Coordinador/FXMLPlaneacionTutoriaConsulta.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nueva Planeación de Tutorías");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No se pudo abrir la ventana de planeación");
            alert.showAndWait();
        }
    }

    private void cargarPlaneaciones() {
        try {
            List<PlaneacionTutoria> planeaciones = dao.obtenerTodas();
            lista = FXCollections.observableArrayList(planeaciones);
            reportetutoria.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
