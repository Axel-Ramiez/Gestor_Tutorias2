package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.Problematica;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class FXMLProblematicaCrear {


    @FXML private Label lbIdProblematica;
    @FXML private TextField tfIdReporteTutoria;
    @FXML private TextField tfTitulo;
    @FXML private TextArea taDescripcion;
    @FXML private ComboBox<EstatusProblematica> cbEstado;
    @FXML private ComboBox<Carrera> cbCarrera;


    private Problematica problematicaActual;
    private final ProblematicaDAO problematicaDAO = new ProblematicaDAO();


    @FXML
    private void initialize() {

        problematicaActual = new Problematica();

        cbEstado.setItems(FXCollections.observableArrayList(EstatusProblematica.values()));
        cbEstado.getSelectionModel().selectFirst();

        cargarCarreras();
    }

    private void cargarCarreras() {
        try {
            List<Carrera> listaCarreras = CarreraDAO.obtenerTodas();
            cbCarrera.setItems(FXCollections.observableArrayList(listaCarreras));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las carreras.");
        }
    }


    public void prellenarIdReporte(int idReporte) {
        tfIdReporteTutoria.setText(String.valueOf(idReporte));
        tfIdReporteTutoria.setDisable(true);
    }


    @FXML
    public void clicGuardar(ActionEvent event) {

        if (tfTitulo.getText().isEmpty() || taDescripcion.getText().isEmpty() || tfIdReporteTutoria.getText().isEmpty()) {
            mostrarAlerta("Campos Vacíos", "Por favor llene título, descripción e ID del reporte.");
            return;
        }

        try {
            juntarDatos();


            int idGenerado = problematicaDAO.guardarProblematica(problematicaActual);

            if (idGenerado > 0) {
                mostrarAlerta("Éxito", "Problemática registrada correctamente.");
                cerrar(event);
            } else {
                mostrarAlerta("Error", "No se pudo guardar en la base de datos.");
            }

        } catch (NumberFormatException nfe) {
            mostrarAlerta("Error de Formato", "El ID del Reporte debe ser un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar: " + e.getMessage());
        }
    }


    private void juntarDatos() {

        try {
            int idReporte = Integer.parseInt(tfIdReporteTutoria.getText());
            problematicaActual.setIdReporteTutoria(idReporte);
        } catch (NumberFormatException e) {
            throw e;
        }

        problematicaActual.setTitulo(tfTitulo.getText());
        problematicaActual.setDescripcion(taDescripcion.getText());
        problematicaActual.setEstado(cbEstado.getValue());

        Carrera carrera = cbCarrera.getValue();
        if (carrera != null) {
            problematicaActual.setIdCarrera(carrera.getIdCarrera());
        } else {
            // Dependiendo de tu lógica, puede ser null o obligatorio
            problematicaActual.setIdCarrera(null);
        }
    }


    @FXML
    public void clicCancelar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    @FXML
    public void clicCerrar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (titulo.contains("Error") || titulo.contains("Vacíos")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
