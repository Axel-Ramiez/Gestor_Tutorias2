package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.Problematica;
import gestor_tutorias.dao.CarreraDAO;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class FXMLProblematicaEditar {

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
    }


    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
        cargarProblematica();
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

    private void cargarProblematica() {
        try {
            problematicaActual = problematicaDAO.obtenerPorId(idProblematica);
            if (problematicaActual != null) {
                cargarDatosEnVista();
            } else {
                mostrarAlerta("Error", "No se encontró la problemática seleccionada.");

            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la problemática.");
        }
    }


    private void cargarDatosEnVista() {
        lbIdProblematica.setText(String.valueOf(problematicaActual.getIdProblematica()));
        tfIdReporteTutoria.setText(String.valueOf(problematicaActual.getIdReporteTutoria()));
        tfIdReporteTutoria.setDisable(true); // Generalmente al editar no cambias a qué reporte pertenece

        tfTitulo.setText(problematicaActual.getTitulo());
        taDescripcion.setText(problematicaActual.getDescripcion());
        cbEstado.setValue(problematicaActual.getEstado());

        // Seleccionar carrera en el combo
        if (problematicaActual.getIdCarrera() != null) {
            for (Carrera c : cbCarrera.getItems()) {
                if (c.getIdCarrera() == problematicaActual.getIdCarrera()) { // Asegúrate que getIdCarrera devuelva int/Integer
                    cbCarrera.getSelectionModel().select(c);
                    break;
                }
            }
        }
    }


    @FXML
    public void clicGuardar(ActionEvent event) {
        if (tfTitulo.getText().isEmpty() || taDescripcion.getText().isEmpty()) {
            mostrarAlerta("Campos Vacíos", "Título y descripción son obligatorios.");
            return;
        }

        try {
            juntarDatos();


            boolean exito = problematicaDAO.actualizarProblematica(problematicaActual);

            if (exito) {
                mostrarAlerta("Éxito", "Problemática actualizada correctamente.");
                cerrar(event);
            } else {
                mostrarAlerta("Error", "No se pudo actualizar la información.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al actualizar: " + e.getMessage());
        }
    }


    private void juntarDatos() {
        problematicaActual.setTitulo(tfTitulo.getText());
        problematicaActual.setDescripcion(taDescripcion.getText());
        problematicaActual.setEstado(cbEstado.getValue());

        Carrera carrera = cbCarrera.getValue();
        if (carrera != null) {
            problematicaActual.setIdCarrera(carrera.getIdCarrera());
        } else {
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