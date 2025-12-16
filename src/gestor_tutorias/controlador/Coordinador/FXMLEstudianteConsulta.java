package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLEstudianteConsulta {

    @FXML
    private Label idEstudiante;

    @FXML
    private TextField matricula;

    @FXML
    private TextField nombreCompleto;

    @FXML
    private TextField correo;

    @FXML
    private TextField idCarrera;

    @FXML
    private TextField semestre;

    @FXML
    private ComboBox<String> activo;

    @FXML
    private Label riesgo;

    @FXML
    private TextField tutor; // Nuevo campo editable


    private Estudiante estudianteActual;

    @FXML
    private void initialize() {
        activo.getItems().addAll("Activo", "Inactivo");
        activo.getSelectionModel().select("Activo");

        // Campos no editables
        matricula.setEditable(false);
        nombreCompleto.setEditable(false);
        correo.setEditable(false);
        idCarrera.setEditable(false);
        semestre.setEditable(false);
        activo.setDisable(true);
    }

    @FXML
    private void guardarEstudiante() {
        if (estudianteActual != null) {
            String tutorAsignado = tutor.getText().trim();

            // Aquí debes llamar a tu DAO para actualizar el tutor, ejemplo:
            try {
                // EstudianteDAO.actualizarTutor(estudianteActual.getIdEstudiante(), tutorAsignado);
                mostrarAlerta("Tutor actualizado correctamente: " + tutorAsignado);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error al actualizar tutor");
            }
        } else {
            mostrarAlerta("No hay estudiante seleccionado");
        }
    }


    public void cargarEstudiante(Estudiante est) {
        this.estudianteActual = est;
        idEstudiante.setText(String.valueOf(est.getIdEstudiante()));
        matricula.setText(est.getMatricula());
        nombreCompleto.setText(est.getNombreCompleto());
        correo.setText(est.getCorreo());
        idCarrera.setText(String.valueOf(est.getIdCarrera()));
        semestre.setText(String.valueOf(est.getSemestre()));
        riesgo.setText(est.getRiesgo() == 1 ? "En riesgo" : "Sin riesgo");
        activo.getSelectionModel().select(est.getActivo() == 1 ? "Activo" : "Inactivo");

        tutor.setText(""); // Inicializa vacío o con valor actual del tutor
        tutor.setEditable(true);
    }


    private void limpiarCampos() {
        idEstudiante.setText("");
        matricula.clear();
        nombreCompleto.clear();
        correo.clear();
        idCarrera.clear();
        semestre.clear();
        riesgo.setText("");
        activo.getSelectionModel().select("Activo");
        estudianteActual = null;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}