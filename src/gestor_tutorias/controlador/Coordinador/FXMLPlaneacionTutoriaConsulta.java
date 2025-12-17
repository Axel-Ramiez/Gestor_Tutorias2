package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.PeriodoEscolarDAO;
import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.PeriodoEscolar;
import gestor_tutorias.pojo.PlaneacionTutoria;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLPlaneacionTutoriaConsulta implements Initializable {
    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;
    @FXML private ComboBox<Carrera> cbCarrera;
    @FXML private ComboBox<Integer> cbSesion;
    @FXML private DatePicker fechaTutoria;
    @FXML private DatePicker fechaCierre;
    @FXML private TextArea temastratar;
    @FXML private TextField tfIdPlaneacion;


    private PlaneacionTutoria planeacionActual;
    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCatalogos();
        cbSesion.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
    }

    private void cargarCatalogos() {
        try {
            List<PeriodoEscolar> periodos = PeriodoEscolarDAO.obtenerTodos();
            cbPeriodo.setItems(FXCollections.observableArrayList(periodos));
            List<Carrera> carreras = CarreraDAO.obtenerTodas();
            cbCarrera.setItems(FXCollections.observableArrayList(carreras));

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los catálogos (Periodos/Carreras).");
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarPlaneacionTutoria() {
        if (cbPeriodo.getValue() == null || cbCarrera.getValue() == null || cbSesion.getValue() == null) {
            mostrarAlerta("Advertencia", "Seleccione Periodo, Carrera y Número de Sesión.");
            return;
        }
        if (fechaTutoria.getValue() == null) {
            mostrarAlerta("Advertencia", "Seleccione la fecha de la tutoría.");
            return;
        }

        try {
            int idPeriodo = cbPeriodo.getValue().getIdPeriodo();
            int idCarrera = cbCarrera.getValue().getIdCarrera();
            int numSesion = cbSesion.getValue();
            LocalDate fechaT = fechaTutoria.getValue();
            LocalDate fechaC = (fechaCierre.getValue() != null) ? fechaCierre.getValue() : fechaT;
            String temas = temastratar.getText();


            if (planeacionActual == null) {
                PlaneacionTutoria nueva = new PlaneacionTutoria();
                nueva.setIdPeriodo(idPeriodo);
                nueva.setIdCarrera(idCarrera);
                nueva.setNumeroSesion(numSesion);
                nueva.setFechaTutoria(fechaT);
                nueva.setTemas(temas);

                dao.guardarPlaneacion(nueva);
                mostrarAlerta("Éxito", "Planeación registrada.");
            } else {
                planeacionActual.setIdPeriodo(idPeriodo);
                planeacionActual.setIdCarrera(idCarrera);
                planeacionActual.setNumeroSesion(numSesion);
                planeacionActual.setFechaTutoria(fechaT);
                planeacionActual.setTemas(temas);

                dao.actualizarPlaneacion(planeacionActual);
                mostrarAlerta("Éxito", "Planeación actualizada.");
            }
            cerrarVentana();

        } catch (SQLException e) {
            mostrarAlerta("Error BD", "Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarPlaneacionTutoria() {
        if (planeacionActual == null) return;

        try {
            dao.eliminarPlaneacion(planeacionActual.getIdFechaTutoria());
            mostrarAlerta("Información", "Planeación eliminada.");
            cerrarVentana();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar (quizás ya tiene reportes asociados).");
        }
    }

    public void cargarPlaneacion(PlaneacionTutoria plan) {
        this.planeacionActual = plan;
        tfIdPlaneacion.setText(String.valueOf(plan.getIdFechaTutoria()));
        temastratar.setText(plan.getTemas());
        seleccionarEnComboPeriodo(plan.getIdPeriodo());
        seleccionarEnComboCarrera(plan.getIdCarrera());
        cbSesion.setValue(plan.getNumeroSesion());
    }

    private void seleccionarEnComboPeriodo(int idBuscado) {
        for (PeriodoEscolar p : cbPeriodo.getItems()) {
            if (p.getIdPeriodo() == idBuscado) {
                cbPeriodo.setValue(p);
                break;
            }
        }
    }

    private void seleccionarEnComboCarrera(int idBuscado) {
        for (Carrera c : cbCarrera.getItems()) {
            if (c.getIdCarrera() == idBuscado) {
                cbCarrera.setValue(c);
                break;
            }
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfIdPlaneacion.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}