package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.validacion.Validacion;
import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.dao.FacultadDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.Estudiante;
import gestor_tutorias.pojo.Facultad;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLEstudiante implements Initializable {

    @FXML private TextField tfMatricula;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidoPaterno;
    @FXML private TextField tfApellidoMaterno;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfSemestre;
    @FXML private ComboBox<Facultad> cbFacultad;
    @FXML private ComboBox<Carrera> cbCarrera;
    @FXML private Label lblErrorMatricula;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorApellidoPaterno;
    @FXML private Label lblErrorApellidoMaterno;
    @FXML private Label lblErrorCorreo;
    @FXML private Label lblErrorSemestre;
    @FXML private Label lblErrorFacultad;
    @FXML private Label lblErrorCarrera;


    private Estudiante estudianteEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFacultades();


        cbFacultad.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarCarreras(newVal.getIdFacultad());
            } else {
                cbCarrera.getItems().clear();
                cbCarrera.setDisable(true);
            }
        });
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        Estudiante est = obtenerEstudianteDeVista();

        try {
            boolean exito = EstudianteDAO.registrarEstudiante(est);
            if (exito) {
                mostrarAlerta("Éxito", "Estudiante registrado correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo registrar. Verifica que la Matrícula no esté repetida.");
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error al registrar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        Estudiante est = obtenerEstudianteDeVista();
        est.setIdEstudiante(this.estudianteEdicion.getIdEstudiante());
        est.setRiesgoEstudiante(this.estudianteEdicion.getRiesgoEstudiante());

        try {
            boolean exito = EstudianteDAO.editarEstudiante(est);
            if (exito) {
                mostrarAlerta("Éxito", "Estudiante actualizado correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar la información.");
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error al actualizar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Estudiante obtenerEstudianteDeVista() {
        Estudiante est = new Estudiante();
        est.setMatriculaEstudiante(tfMatricula.getText());
        est.setNombreEstudiante(tfNombre.getText());
        est.setApellidoPaternoEstudiante(tfApellidoPaterno.getText());
        est.setApellidoMaternoEstudiante(tfApellidoMaterno.getText());
        est.setCorreoEstudiante(tfCorreo.getText());
        est.setSemestreEstudiante(Integer.parseInt(tfSemestre.getText()));

        if (cbCarrera.getValue() != null) {
            est.setIdCarrera(cbCarrera.getValue().getIdCarrera());
        }
        est.setRiesgoEstudiante(0);
        est.setActivoEstudiante(1);

        return est;
    }

    public void inicializarValores(Estudiante est) {
        this.estudianteEdicion = est;

        tfMatricula.setText(est.getMatriculaEstudiante());
        tfMatricula.setEditable(false);
        tfNombre.setText(est.getNombreEstudiante());
        tfApellidoPaterno.setText(est.getApellidoPaternoEstudiante());
        tfApellidoMaterno.setText(est.getApellidoMaternoEstudiante());
        tfCorreo.setText(est.getCorreoEstudiante());
        tfSemestre.setText(String.valueOf(est.getSemestreEstudiante()));
        seleccionarCarreraEdicion(est.getIdCarrera());

    }

    private void seleccionarCarreraEdicion(int idCarreraEstudiante) {
        try {
            Carrera carrera = CarreraDAO.obtenerPorId(idCarreraEstudiante);
            if (carrera != null) {

                for (Facultad f : cbFacultad.getItems()) {
                    if (f.getIdFacultad() == carrera.getIdFacultad()) {
                        cbFacultad.setValue(f);
                        break;
                    }
                }

                for (Carrera c : cbCarrera.getItems()) {
                    if (c.getIdCarrera() == idCarreraEstudiante) {
                        cbCarrera.setValue(c);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarFacultades() {
        try {
            List<Facultad> facultadesBD = FacultadDAO.obtenerTodas();
            cbFacultad.setItems(FXCollections.observableArrayList(facultadesBD));
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al cargar Facultades: " + ex.getMessage());
        }
    }

    private void cargarCarreras(int idFacultad) {
        try {
            List<Carrera> carrerasBD = CarreraDAO.obtenerPorFacultad(idFacultad);
            cbCarrera.setItems(FXCollections.observableArrayList(carrerasBD));
            cbCarrera.setDisable(false);
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al cargar Carreras: " + ex.getMessage());
        }
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (!Validacion.validarLongitud(tfMatricula, lblErrorMatricula, 1, 20)) valido = false;
        else if (!Validacion.validarMatricula(tfMatricula, lblErrorMatricula)) valido = false;

        if (!Validacion.validarLongitud(tfNombre, lblErrorNombre, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfNombre, lblErrorNombre)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoPaterno, lblErrorApellidoPaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoPaterno, lblErrorApellidoPaterno)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoMaterno, lblErrorApellidoMaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoMaterno, lblErrorApellidoMaterno)) valido = false;

        if (!Validacion.validarLongitud(tfCorreo, lblErrorCorreo, 1, 100)) valido = false;
        else if (!Validacion.validarCorreoEstudiante(tfCorreo, lblErrorCorreo)) valido = false;


        if (!Validacion.validarRequerido(tfSemestre, lblErrorSemestre, "Requerido")) {
            valido = false;
        } else {
            if (!Validacion.esNumeroEntero(tfSemestre.getText())) {
                lblErrorSemestre.setText("Debe ser número");
                tfSemestre.setStyle("-fx-border-color: red;");
                valido = false;
            } else {
                int sem = Integer.parseInt(tfSemestre.getText());
                if (sem < 1 || sem > 12) {
                    lblErrorSemestre.setText("Entre 1 y 12");
                    tfSemestre.setStyle("-fx-border-color: red;");
                    valido = false;
                } else {
                    lblErrorSemestre.setText("");
                    tfSemestre.setStyle("");
                }
            }
        }

        if (!Validacion.validarSeleccion(cbFacultad, lblErrorFacultad, "Selecciona Facultad")) valido = false;
        if (!Validacion.validarSeleccion(cbCarrera, lblErrorCarrera, "Selecciona Carrera")) valido = false;

        return valido;
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}