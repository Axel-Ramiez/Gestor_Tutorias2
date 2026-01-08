package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.CarreraDAO;

import gestor_tutorias.dao.EstudianteDAO;

import gestor_tutorias.dao.UsuarioDAO;

import gestor_tutorias.pojo.Carrera;

import gestor_tutorias.pojo.Estudiante;

import gestor_tutorias.pojo.Usuario;

import javafx.collections.FXCollections;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.control.*;

import javafx.stage.Stage;

import java.sql.SQLException;

public class FXMLEstudianteConsulta {

    @FXML private TextField tfMatricula, tfNombre, tfApellidoPaterno, tfApellidoMaterno, tfCorreo, tfSemestre;

    @FXML private ComboBox<Carrera> cbCarrera;

    @FXML private ComboBox<Usuario> cbTutor;

    private Estudiante estudianteEdicion;

    @FXML

    private void initialize() {

        cargarCombos();

    }

    private void cargarCombos() {

        try {

            cbCarrera.setItems(FXCollections.observableArrayList(CarreraDAO.obtenerTodas()));

            cbTutor.setItems(FXCollections.observableArrayList(UsuarioDAO.obtenerTutores()));

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void inicializarInformacion(Estudiante estudiante) {

        this.estudianteEdicion = estudiante;

        if (estudiante != null) {

            tfMatricula.setText(estudiante.getMatriculaEstudiante());

            tfNombre.setText(estudiante.getNombreEstudiante());

            tfApellidoPaterno.setText(estudiante.getApellidoPaternoEstudiante());

            tfApellidoMaterno.setText(estudiante.getApellidoMaternoEstudiante());

            tfCorreo.setText(estudiante.getCorreoEstudiante());

            tfSemestre.setText(String.valueOf(estudiante.getSemestreEstudiante()));

// Seleccionar en combos

            for (Carrera c : cbCarrera.getItems()) {

                if (c.getIdCarrera() == estudiante.getIdCarrera()) {

                    cbCarrera.getSelectionModel().select(c);

                    break;

                }

            }

            if (estudiante.getIdUsuario() != null) {

                for (Usuario u : cbTutor.getItems()) {

                    if (u.getIdUsuario() == (int) estudiante.getIdUsuario()) {

                        cbTutor.getSelectionModel().select(u);

                        break;

                    }

                }

            }

        }

    }

    @FXML

    private void clicGuardar(ActionEvent event) {

        try {

// Actualizar objeto con datos de la interfaz

            estudianteEdicion.setNombreEstudiante(tfNombre.getText());

            estudianteEdicion.setApellidoPaternoEstudiante(tfApellidoPaterno.getText());

            estudianteEdicion.setApellidoMaternoEstudiante(tfApellidoMaterno.getText());

            estudianteEdicion.setCorreoEstudiante(tfCorreo.getText());

            estudianteEdicion.setSemestreEstudiante(Integer.parseInt(tfSemestre.getText()));

            estudianteEdicion.setIdCarrera(cbCarrera.getValue().getIdCarrera());

            if (cbTutor.getValue() != null) {

                estudianteEdicion.setIdUsuario(cbTutor.getValue().getIdUsuario());

            }

// Guardar en BD

            if (EstudianteDAO.editarEstudiante(estudianteEdicion)) {

// Si el tutor cambió, llamar al método específico del DAO

                if (cbTutor.getValue() != null) {

                    EstudianteDAO.asignarTutor(estudianteEdicion.getIdEstudiante(), cbTutor.getValue().getIdUsuario());

                }

                cerrar(event);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML

    private void clicCancelar(ActionEvent event) {

        cerrar(event);

    }

    private void cerrar(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();

    }

}
