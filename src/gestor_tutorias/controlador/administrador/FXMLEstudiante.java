package gestor_tutorias.controlador.administrador;

import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.dao.FacultadDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.Estudiante;
import gestor_tutorias.pojo.Facultad;
import gestor_tutorias.validacion.Validacion;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Nombre: Axel Ramírez / Alberto Villalba
 * Fecha de creación: 17/12/2025
 * Fecha de modificación: 11/01/2026
 * Descripción: Controlador para el formulario de registro y edición de Estudiantes (Administrador).
 * Incluye validación de duplicidad para Matrícula y Correo.
 */
public class FXMLEstudiante implements Initializable {

    @FXML private TextField tfMatricula;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidoPaterno;
    @FXML private TextField tfApellidoMaterno;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfSemestre;
    @FXML private ComboBox<Facultad> cbFacultad;
    @FXML private ComboBox<Carrera> cbCarrera;
    @FXML private Label lbErrorMatricula;
    @FXML private Label lblErrorNombre;
    @FXML private Label lbErrorApellidoPaterno;
    @FXML private Label lbErrorApellidoMaterno;
    @FXML private Label lbErrorCorreo;
    @FXML private Label lbErrorSemestre;
    @FXML private Label lbErrorFacultad;
    @FXML private Label lbErrorCarrera;

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


    public void inicializarValores(Estudiante est) {
        this.estudianteEdicion = est;

        tfMatricula.setText(est.getMatriculaEstudiante());
        tfMatricula.setEditable(false); // No editar la PK
        tfNombre.setText(est.getNombreEstudiante());
        tfApellidoPaterno.setText(est.getApellidoPaternoEstudiante());
        tfApellidoMaterno.setText(est.getApellidoMaternoEstudiante());
        tfCorreo.setText(est.getCorreoEstudiante());
        tfSemestre.setText(String.valueOf(est.getSemestreEstudiante()));

        seleccionarCarreraEdicion(est.getIdCarrera());
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        int idAExcluir = (this.estudianteEdicion != null) ? this.estudianteEdicion.getIdEstudiante() : 0;

        try {
            if (EstudianteDAO.esMatriculaRegistrada(tfMatricula.getText(), idAExcluir)) {
                lbErrorMatricula.setText("Matrícula ya registrada.");
                tfMatricula.setStyle("-fx-border-color: red;");
                return;
            }

            if (EstudianteDAO.esCorreoRegistrado(tfCorreo.getText(), idAExcluir)) {
                lbErrorCorreo.setText("Correo ya registrado.");
                tfCorreo.setStyle("-fx-border-color: red;");
                return;
            }

        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al validar duplicados: " + ex.getMessage());
            return;
        }

        Estudiante est = obtenerEstudianteDeVista();

        try {
            boolean exito;

            if (this.estudianteEdicion == null) {

                exito = EstudianteDAO.registrarEstudiante(est);
            } else {
                est.setIdEstudiante(this.estudianteEdicion.getIdEstudiante());
                est.setRiesgoEstudiante(this.estudianteEdicion.getRiesgoEstudiante());
                est.setActivoEstudiante(this.estudianteEdicion.getActivoEstudiante());

                exito = EstudianteDAO.editarEstudiante(est);
            }

            if (exito) {
                mostrarAlerta("Éxito", "La información se guardó correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la información.");
            }

        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error SQL: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
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

    private void seleccionarCarreraEdicion(int idCarreraEstudiante) {
        try {
            Carrera carrera = CarreraDAO.obtenerPorId(idCarreraEstudiante);
            if (carrera != null) {
                // Seleccionar Facultad
                for (Facultad f : cbFacultad.getItems()) {
                    if (f.getIdFacultad() == carrera.getIdFacultad()) {
                        cbFacultad.setValue(f);
                        break;
                    }
                }
                // Seleccionar Carrera
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

        if (!Validacion.validarLongitud(tfMatricula, lbErrorMatricula, 1, 20)) valido = false;
        else if (!Validacion.validarMatricula(tfMatricula, lbErrorMatricula)) valido = false;

        if (!Validacion.validarLongitud(tfNombre, lblErrorNombre, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfNombre, lblErrorNombre)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoPaterno, lbErrorApellidoPaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoPaterno, lbErrorApellidoPaterno)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoMaterno, lbErrorApellidoMaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoMaterno, lbErrorApellidoMaterno)) valido = false;

        if (!Validacion.validarLongitud(tfCorreo, lbErrorCorreo, 1, 100)) valido = false;
        else if (!Validacion.validarCorreoEstudiante(tfCorreo, lbErrorCorreo)) valido = false;

        if (!Validacion.validarRequerido(tfSemestre, lbErrorSemestre, "Requerido")) {
            valido = false;
        } else {
            if (!Validacion.esNumeroEntero(tfSemestre.getText())) {
                lbErrorSemestre.setText("Debe ser número");
                tfSemestre.setStyle("-fx-border-color: red;");
                valido = false;
            } else {
                int sem = Integer.parseInt(tfSemestre.getText());
                if (sem < 1 || sem > 12) {
                    lbErrorSemestre.setText("Entre 1 y 12");
                    tfSemestre.setStyle("-fx-border-color: red;");
                    valido = false;
                } else {
                    lbErrorSemestre.setText("");
                    tfSemestre.setStyle("");
                }
            }
        }

        if (!Validacion.validarSeleccion(cbFacultad, lbErrorFacultad, "Selecciona Facultad")) valido = false;
        if (!Validacion.validarSeleccion(cbCarrera, lbErrorCarrera, "Selecciona Carrera")) valido = false;

        return valido;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert.AlertType tipo = Alert.AlertType.INFORMATION;
        if (titulo.contains("Error")) {
            tipo = Alert.AlertType.ERROR;
        }
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}