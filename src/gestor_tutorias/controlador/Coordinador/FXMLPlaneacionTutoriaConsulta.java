package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLPlaneacionTutoriaConsulta {

    @FXML
    private Label idPlaneacion;

    private PlaneacionTutoria planeacionActual;


    @FXML
    private DatePicker fecha;

    @FXML
    private DatePicker fechacierre;

    @FXML
    private TextField periodo;

    @FXML
    private TextField carrera;

    @FXML
    private TextField sesion;

    @FXML
    private TextArea temastratar;

    @FXML
    private TextField id; // corresponde al fx:id="id" en FXML

    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @FXML
    private void guardarPlaneacionTutoria() {
        try {
            if (fecha.getValue() == null || fechacierre.getValue() == null) {
                mostrarAlerta("Fechas obligatorias");
                return;
            }

            int idPeriodo = Integer.parseInt(periodo.getText());
            int idCarrera = Integer.parseInt(carrera.getText());
            int numeroSesion = Integer.parseInt(sesion.getText());
            LocalDate fechaCierre = fechacierre.getValue();

            if (planeacionActual == null) {
                // Crear nueva planeación
                PlaneacionTutoria nueva = new PlaneacionTutoria(
                        idPeriodo,
                        idCarrera,
                        fechaCierre,
                        numeroSesion,
                        temastratar.getText()
                );
                dao.guardarPlaneacion(nueva);
                mostrarAlerta("Planeación guardada correctamente");
            } else {
                // Editar existente
                planeacionActual.setIdPeriodo(idPeriodo);
                planeacionActual.setIdCarrera(idCarrera);
                planeacionActual.setNumeroSesion(numeroSesion);
                planeacionActual.setFechaCierreReportes(fechaCierre);
                planeacionActual.setTemas(temastratar.getText());

                dao.actualizarPlaneacion(planeacionActual);
                mostrarAlerta("Planeación actualizada correctamente");
            }

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Datos numéricos inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar planeación");
        }
    }

    @FXML
    private void eliminarPlaneacionTutoria() {
        try {
            if (planeacionActual != null) {
                dao.eliminarPlaneacion(planeacionActual.getIdFechaTutoria());
                mostrarAlerta("Planeación eliminada");
                limpiarCampos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al eliminar planeación");
        }
    }

    public void cargarPlaneacion(PlaneacionTutoria plan) {
        this.planeacionActual = plan;
        id.setText(String.valueOf(plan.getIdFechaTutoria()));
        periodo.setText(String.valueOf(plan.getIdPeriodo()));
        carrera.setText(String.valueOf(plan.getIdCarrera()));
        sesion.setText(String.valueOf(plan.getNumeroSesion()));
        fecha.setValue(plan.getFechaCierreReportes());
        fechacierre.setValue(plan.getFechaCierreReportes());
        temastratar.setText(plan.getTemas());
    }

    private void limpiarCampos() {
        idPlaneacion.setText("");
        periodo.clear();
        carrera.clear();
        sesion.clear();
        fecha.setValue(null);
        fechacierre.setValue(null);
        temastratar.clear();
        planeacionActual = null;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}