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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPlaneacionTutoria implements Initializable {

    @FXML
    private TableView<PlaneacionTutoria> reportetutoria;

    @FXML
    private TableColumn<PlaneacionTutoria, Integer> fechainicio; // Muestra ID
    @FXML
    private TableColumn<PlaneacionTutoria, String> periodoescolar; // Muestra Nombre Periodo
    @FXML
    private TableColumn<PlaneacionTutoria, String> carrera;        // Muestra Nombre Carrera
    @FXML
    private TableColumn<PlaneacionTutoria, String> fecha;          // Muestra Fecha
    @FXML
    private TableColumn<PlaneacionTutoria, Integer> sesion;        // Muestra # Sesión
    @FXML
    private TableColumn<PlaneacionTutoria, String> temas;          // Muestra Temas

    @FXML
    private Button crear;
    @FXML
    private Button consultar;

    private ObservableList<PlaneacionTutoria> listaPlaneaciones;
    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    }

    private void configurarTabla() {
        // Aquí conectamos las columnas con los atributos del POJO
        fechainicio.setCellValueFactory(new PropertyValueFactory<>("idFechaTutoria"));

        // ¡OJO AQUÍ! Usamos los atributos auxiliares para que salgan los nombres
        periodoescolar.setCellValueFactory(new PropertyValueFactory<>("periodoNombre"));
        carrera.setCellValueFactory(new PropertyValueFactory<>("carreraNombre"));

        fecha.setCellValueFactory(new PropertyValueFactory<>("fechaTutoria"));
        sesion.setCellValueFactory(new PropertyValueFactory<>("numeroSesion"));
        temas.setCellValueFactory(new PropertyValueFactory<>("temas"));
    }

    private void cargarDatosTabla() {
        try {
            List<PlaneacionTutoria> resultados = dao.obtenerTodas();
            listaPlaneaciones = FXCollections.observableArrayList(resultados);
            reportetutoria.setItems(listaPlaneaciones);
        } catch (SQLException ex) {
            mostrarAlerta("Error", "No se pudo cargar la lista de planeaciones.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void crearPlaneacionTutoria(ActionEvent event) {
        abrirFormulario(null); // Null significa "Nuevo registro"
    }

    @FXML
    private void consultarPlaneacionTutoria(ActionEvent event) {
        PlaneacionTutoria seleccion = reportetutoria.getSelectionModel().getSelectedItem();

        if (seleccion != null) {
            abrirFormulario(seleccion); // Pasamos la selección para editar/consultar
        } else {
            mostrarAlerta("Aviso", "Selecciona una fila de la tabla primero.");
        }
    }

    // Método para abrir la ventana de formulario (la que hicimos antes)
    private void abrirFormulario(PlaneacionTutoria planeacion) {
        try {
            // 1. IMPRIMIR RUTA PARA VERIFICAR
            String ruta = "/gestor_tutorias/vista/Coordinador/FXMLPlaneacionTutoriaConsulta.fxml";
            URL url = getClass().getResource(ruta);

            if (url == null) {
                System.out.println(" ERROR FATAL: No se encuentra el archivo FXML en: " + ruta);
                mostrarAlerta("Error Crítico", "No se encuentra el archivo FXML.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load(); // <--- AQUÍ SUELE TRONAR

            if (planeacion != null) {
                FXMLPlaneacionTutoriaConsulta controlador = loader.getController();
                controlador.cargarPlaneacion(planeacion);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(planeacion == null ? "Nueva Planeación" : "Consultar Planeación");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarDatosTabla();

        } catch (IOException ex) {
            // ESTO NOS DIRÁ POR QUÉ FALLA
            System.err.println(" ERROR AL ABRIR VENTANA:");
            ex.printStackTrace();
            mostrarAlerta("Error de Carga", "Revisa la consola (letras rojas) para ver el detalle del error.");
        } catch (Exception ex) {
            System.err.println(" ERROR DESCONOCIDO:");
            ex.printStackTrace();
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