package gestor_tutorias.controlador.tutor;

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

/**
 * Nombre: Axel Ramírez
 * Fecha de creación: 16/12/2025
 * Fecha de modificación: 16/12/2025
 * Descripción: Controlador para la consulta (solo lectura) de los detalles de una problemática.
 */
public class FXMLProblematicaConsulta {

    @FXML private Label lbIdProblematica;
    @FXML private TextField tfIdReporteTutoria;
    @FXML private TextField tfTitulo;
    @FXML private TextArea taDescripcion;
    @FXML private ComboBox<EstatusProblematica> cbEstado;
    @FXML private ComboBox<Carrera> cbCarrera;
    private int idProblematica;
    private Problematica problematicaActual;
    private final ProblematicaDAO problematicaDAO = new ProblematicaDAO();

    @FXML
    private void initialize() {
        cbEstado.setItems(FXCollections.observableArrayList(EstatusProblematica.values()));
        cargarCarreras();
        configurarCampos();
    }


    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
        cargarProblematica();
    }

    @FXML
    public void clicCerrar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    private void cargarCarreras() {
        try {
            List<Carrera> listaCarreras = CarreraDAO.obtenerTodas();
            cbCarrera.setItems(FXCollections.observableArrayList(listaCarreras));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las carreras de la base de datos.");
        }
    }

    private void cargarProblematica() {
        try {
            problematicaActual = problematicaDAO.obtenerPorId(idProblematica);
            if (problematicaActual != null) {
                cargarDatosEnVista();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la problemática.");
        }
    }

    private void cargarDatosEnVista() {
        lbIdProblematica.setText(String.valueOf(problematicaActual.getIdProblematica()));
        tfIdReporteTutoria.setText(String.valueOf(problematicaActual.getIdReporteTutoria()));

        tfTitulo.setText(problematicaActual.getTitulo());
        taDescripcion.setText(problematicaActual.getDescripcion());
        cbEstado.setValue(problematicaActual.getEstado());

        if (problematicaActual.getIdCarrera() != null) {
            for (Carrera c : cbCarrera.getItems()) {
                if (c.getIdCarrera() == problematicaActual.getIdCarrera()) {
                    cbCarrera.getSelectionModel().select(c);
                    break;
                }
            }
        }
    }

    private void configurarCampos() {
        tfIdReporteTutoria.setEditable(false);
        tfTitulo.setEditable(false);
        taDescripcion.setEditable(false);
        cbEstado.setDisable(true);
        cbCarrera.setDisable(true);
        cbEstado.setStyle("-fx-opacity: 1;");
        cbCarrera.setStyle("-fx-opacity: 1;");
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}