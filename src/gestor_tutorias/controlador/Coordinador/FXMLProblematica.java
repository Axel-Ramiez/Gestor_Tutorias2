package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.Enum.EstatusProblematica; // Asegúrate de usar este Enum, NO EstadoReporte
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

public class FXMLProblematica implements Initializable {

    @FXML
    private TableView<Problematica> tvProblematica;
    @FXML
    private TableColumn colIdProblematica;
    @FXML
    private TableColumn colIdReporte;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colEE;
    @FXML
    private TableColumn colEstado;

    private ObservableList<Problematica> listaProblematicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarProblematicas();
    }

    private void configurarTabla() {
        colIdProblematica.setCellValueFactory(new PropertyValueFactory("idProblematica"));
        colIdReporte.setCellValueFactory(new PropertyValueFactory("idReporte"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colEE.setCellValueFactory(new PropertyValueFactory("idExperienciaEducativa"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }

    private void cargarProblematicas() {
        try {
            ProblematicaDAO dao = new ProblematicaDAO();
            List<Problematica> resultado = dao.obtenerTodas();
            listaProblematicas = FXCollections.observableArrayList(resultado);
            tvProblematica.setItems(listaProblematicas);
        } catch (SQLException ex) {
            mostrarAlerta("Error de conexión", "No se pudieron cargar las problemáticas.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void consultarProblematica(ActionEvent event) {
        Problematica seleccionado = tvProblematica.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Por favor selecciona una problemática de la lista para atenderla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Coordinador/FXMLProblematicaConsulta.fxml"));
            Parent root = loader.load();
            FXMLProblematicaConsulta controlador = loader.getController();
            controlador.inicializarProblematica(seleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Atender Problemática");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarProblematicas();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta.");
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
