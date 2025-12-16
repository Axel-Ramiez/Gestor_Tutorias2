package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.PlaneacionTutoriaDAO;
import gestor_tutorias.pojo.PlaneacionTutoria;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FXMLPlaneacionTutoriaConsulta {

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

    private final PlaneacionTutoriaDAO dao = new PlaneacionTutoriaDAO();

    @FXML
    private void guardarplaneaciontutoria() {

        try {
            if (fecha.getValue() == null || fechacierre.getValue() == null) {
                mostrarAlerta("Fechas obligatorias");
                return;
            }

            int idPeriodo = Integer.parseInt(periodo.getText());
            int idCarrera = Integer.parseInt(carrera.getText());
            int numeroSesion = Integer.parseInt(sesion.getText());
            LocalDate fechaCierre = fechacierre.getValue();

            PlaneacionTutoria planeacion = new PlaneacionTutoria(
                    idPeriodo,
                    idCarrera,
                    fechaCierre,
                    numeroSesion,
                    temastratar.getText()
            );

            int id = dao.guardarPlaneacion(planeacion);

            if (id > 0) {
                mostrarAlerta("Planeación guardada correctamente");
                limpiarCampos();
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Datos numéricos inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar en BD");
        }
    }

    @FXML
    private void eliminarplaneaciontutoria() {
        // Normalmente se elimina desde selección previa
        mostrarAlerta("Seleccione una planeación desde la lista");
    }

    private void limpiarCampos() {
        periodo.clear();
        carrera.clear();
        sesion.clear();
        temastratar.clear();
        fecha.setValue(null);
        fechacierre.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
