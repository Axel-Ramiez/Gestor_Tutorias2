package gestor_tutorias.controlador.Administrador;/*package gestor_tutorias.controlador.Administrador;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLEstudiante implements Initializable {

    @FXML private TextField tfMatricula;
    @FXML private ComboBox<Facultad> cbFacultad;
    @FXML private ComboBox<Carrera> cbCarrera;
    @FXML private TextField tfSemestre;
    @FXML private TextField tfNombre;
    @FXML private TextField tfCorreo;
    @FXML private Label lbError;

    private Estudiante estudianteEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFacultades();
    }

    private void cargarFacultades() {
        try {
            List<Facultad> facultadesBD = FacultadDAO.obtenerTodas();

            ObservableList<Facultad> facultadesList = FXCollections.observableArrayList(facultadesBD);
            cbFacultad.setItems(facultadesList);

        } catch (SQLException ex) {
            mostrarAlerta("Error al cargar Facultades", "Hubo un error de conexión con la base de datos.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void cargarCarreras(ActionEvent event) {
        Facultad facultadSeleccionada = cbFacultad.getValue();
        if (facultadSeleccionada != null) {
            int idFacultad = facultadSeleccionada.getIdFacultad();
            try {
                List<Carrera> carrerasBD = CarreraDAO.obtenerPorFacultad(idFacultad);
                ObservableList<Carrera> carrerasList = FXCollections.observableArrayList(carrerasBD);
                cbCarrera.setItems(carrerasList);
                cbCarrera.setDisable(false);

            } catch (SQLException ex) {
                mostrarAlerta("Error al cargar las Carreras", "No se pudieron recuperar las carreras.");
                ex.printStackTrace();
            }
        } else {
            cbCarrera.getItems().clear();
            cbCarrera.setDisable(true);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        lbError.setText("");
        if (validarCampos()) {
            Estudiante est = new Estudiante();
            if (this.estudianteEdicion != null) {
                est.setIdEstudiante(this.estudianteEdicion.getIdEstudiante());
            }

            est.setMatricula(tfMatricula.getText());
            est.setNombreCompleto(tfNombre.getText());
            est.setCorreo(tfCorreo.getText());
            est.setSemestre(Integer.parseInt(tfSemestre.getText()));
            est.setIdCarrera(cbCarrera.getValue().getIdCarrera());
            est.setRiesgo(0);
            est.setActivo(1);
            try {
                boolean exito;
                if (this.estudianteEdicion == null) {

                    exito = EstudianteDAO.registrarEstudiante(est);
                } else {
                    exito = EstudianteDAO.editarEstudiante(est);
                }
                if (exito) {
                    mostrarAlerta("Éxito", "Estudiante guardado correctamente.");
                    cerrarVentana();
                } else {
                    lbError.setText("No se pudo guardar. Verifica los datos.");
                }
            } catch (SQLException ex) {
                lbError.setText("Error BD: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void inicializarValores(Estudiante est) {
        this.estudianteEdicion = est;
        tfMatricula.setText(est.getMatricula());
        tfNombre.setText(est.getNombreCompleto());
        tfCorreo.setText(est.getCorreo());
        tfSemestre.setText(String.valueOf(est.getSemestre()));
        tfMatricula.setEditable(false);
    }


    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private boolean validarCampos() {

        if (!Validacion.validarMatricula(tfMatricula, lbError)) return false;


        if (!Validacion.validarSeleccion(cbFacultad, lbError, "Selecciona una facultad")) return false;
        if (!Validacion.validarSeleccion(cbCarrera, lbError, "Selecciona una carrera")) return false;

        if (!Validacion.validarRequerido(tfSemestre, lbError, "Semestre es requerido")) return false;
        if (!Validacion.esNumeroEntero(tfSemestre.getText())) {
            lbError.setText("El semestre debe ser un número entero");
            tfSemestre.setStyle("-fx-border-color: red;");
            return false;
        }
        if (!Validacion.validarNombre(tfNombre, lbError)) return false;
        if (!Validacion.validarCorreoEstudiante(tfCorreo, lbError)) return false;

        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

 */