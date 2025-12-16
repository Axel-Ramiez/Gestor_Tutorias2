package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.pojo.Estudiante;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLAlumnoConsulta {

    @FXML
    private Label idalumno;

    @FXML
    private TextField matricula;

    @FXML
    private TextField nombrecompleto;

    @FXML
    private TextField correo;

    @FXML
    private TextField idcarrera;

    @FXML
    private TextField semestre;

    @FXML
    private ComboBox<String> activo;

    @FXML
    private Label riesgo;

    private Estudiante estudianteActual;

    @FXML
    private void initialize() {
        activo.getItems().addAll("Activo", "Inactivo");
        activo.getSelectionModel().select("Activo");
    }

    @FXML
    private void guardarEstudiante() {

        try {
            String mat = matricula.getText();
            String nombre = nombrecompleto.getText();
            String mail = correo.getText();
            int carrera = Integer.parseInt(idcarrera.getText());
            int sem = Integer.parseInt(semestre.getText());
            int riesgoValor = 0;

            if (estudianteActual == null) {
                Estudiante nuevo = new Estudiante(mat, nombre, mail, carrera, sem, riesgoValor);
                EstudianteDAO.registrarEstudiante(nuevo);
            } else {
                estudianteActual.setNombreCompleto(nombre);
                estudianteActual.setCorreo(mail);
                estudianteActual.setIdCarrera(carrera);
                estudianteActual.setSemestre(sem);
                EstudianteDAO.editarEstudiante(estudianteActual);
            }

            mostrarAlerta("Alumno guardado correctamente");
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Datos numéricos inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar alumno");
        }
    }

    @FXML
    private void eliminarEstudiante() {
        try {
            if (estudianteActual != null) {
                EstudianteDAO.eliminarEstudiante(estudianteActual.getIdEstudiante());
                mostrarAlerta("Alumno eliminado");
                limpiarCampos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al eliminar alumno");
        }
    }

    public void cargarEstudiante(Estudiante est) {
        this.estudianteActual = est;
        idalumno.setText(String.valueOf(est.getIdEstudiante()));
        matricula.setText(est.getMatricula());
        nombrecompleto.setText(est.getNombreCompleto());
        correo.setText(est.getCorreo());
        idcarrera.setText(String.valueOf(est.getIdCarrera()));
        semestre.setText(String.valueOf(est.getSemestre()));
        riesgo.setText(est.getRiesgo() == 1 ? "En riesgo" : "Sin riesgo");
        activo.getSelectionModel().select(est.getActivo() == 1 ? "Activo" : "Inactivo");
    }

    private void limpiarCampos() {
        idalumno.setText("");
        matricula.clear();
        nombrecompleto.clear();
        correo.clear();
        idcarrera.clear();
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
