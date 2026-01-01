package gestor_tutorias.controlador.Coordinador;/*package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.EstudianteDAO;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Estudiante;
import gestor_tutorias.pojo.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLEstudianteConsulta implements Initializable {

    @FXML
    private TextField tfMatricula;
    @FXML
    private ComboBox<String> cbFacultad;
    @FXML
    private ComboBox<String> cbCarrera;
    @FXML
    private TextField tfSemestre;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Label lbError;

    @FXML
    private ComboBox<Usuario> cbTutor;

    private Estudiante estudianteActual;
    private ObservableList<Usuario> listaTutores;

    private final int ID_ROL_TUTOR = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarCamposSoloLectura();


        cargarListaTutores();
    }


    private void configurarCamposSoloLectura() {
        tfMatricula.setDisable(true);
        tfMatricula.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f0f0f0;");
        tfNombre.setDisable(true);
        tfNombre.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f0f0f0;");
        tfCorreo.setDisable(true);
        tfCorreo.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f0f0f0;");
        tfSemestre.setDisable(true);
        tfSemestre.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f0f0f0;");
        cbCarrera.setDisable(true);
        cbCarrera.setStyle("-fx-opacity: 1; -fx-text-fill: black; -fx-background-color: #f0f0f0;");
    }

    @FXML
    private void clicGuardar(ActionEvent event) {

        if (estudianteActual == null) {
            mostrarAlerta("No hay estudiante seleccionado.", Alert.AlertType.WARNING);
            return;
        }

        Usuario tutorSeleccionado = cbTutor.getSelectionModel().getSelectedItem();

        if (tutorSeleccionado == null) {
            lbError.setText("Debe seleccionar un tutor de la lista.");
            return;
        }

        try {

            boolean exito = EstudianteDAO.asignarTutor(estudianteActual.getIdEstudiante(), tutorSeleccionado.getIdUsuario());

            if (exito) {
                mostrarAlerta("El tutor " + tutorSeleccionado.getNombreCompleto() + " ha sido asignado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("No se pudo realizar la asignación. Intente nuevamente.", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión con la base de datos: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cargarEstudiante(Estudiante seleccionado) {
        this.estudianteActual = seleccionado;

        if (this.estudianteActual != null) {
            tfMatricula.setText(estudianteActual.getMatricula());
            tfNombre.setText(estudianteActual.getNombreCompleto());
            tfCorreo.setText(estudianteActual.getCorreo());
            tfSemestre.setText(String.valueOf(estudianteActual.getSemestre()));

            String carreraTexto = estudianteActual.getCarreraNombre() != null
                    ? estudianteActual.getCarreraNombre()
                    : String.valueOf(estudianteActual.getIdCarrera());
            cbCarrera.setValue(carreraTexto);

            if (estudianteActual.getIdTutor() > 0) {
                seleccionarTutorEnCombo(estudianteActual.getIdTutor());
            }
        }
    }

    private void cargarListaTutores() {
        try {
            List<Usuario> resultados = UsuarioDAO.obtenerTutores();
            listaTutores = FXCollections.observableArrayList(resultados);
            cbTutor.setItems(listaTutores);

        } catch (SQLException ex) {
            mostrarAlerta("Error al cargar la lista de tutores disponibles.", Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }

    private void seleccionarTutorEnCombo(int idTutorActual) {
        for (Usuario u : cbTutor.getItems()) {
            if (u.getIdUsuario() == idTutorActual) {
                cbTutor.getSelectionModel().select(u);
                break;
            }
        }
    }



    @FXML
    private void clicAsigTutor(ActionEvent event) {
        lbError.setText("");
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}*/