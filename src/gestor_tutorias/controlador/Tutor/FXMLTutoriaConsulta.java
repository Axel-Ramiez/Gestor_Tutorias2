package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstadoReporte;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLTutoriaConsulta {

    // Campos de texto y entrada
    @FXML private TextField tfIdReporte;
    @FXML private TextField tfIdEstudiante;
    @FXML private TextField tfIdTutor;
    @FXML private TextField tfIdFechaTutoria;
    @FXML private CheckBox chbAsistencia;
    @FXML private ComboBox<EstadoReporte> cbEstado;

    // Áreas de texto
    @FXML private TextArea taReporte;
    @FXML private TextArea taRespuesta;

    // Botones
    @FXML private Button btnProblematica;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    @FXML
    private void initialize() {
        // Por defecto, definimos todo como editable para cumplir tu solicitud inicial
        configurarEdicion(true);
    }

    /**
     * Define el estado de edición de todos los elementos de la vista
     * @param editable true para habilitar edición, false para solo lectura
     */
    public void configurarEdicion(boolean editable) {
        // TextFields
        tfIdReporte.setEditable(false); // Siempre falso por ser PK Autoincremental
        tfIdEstudiante.setEditable(editable);
        tfIdTutor.setEditable(editable);
        tfIdFechaTutoria.setEditable(editable);

        // Controles de selección
        chbAsistencia.setDisable(!editable);
        cbEstado.setDisable(!editable);

        // Áreas de texto
        taReporte.setEditable(editable);
        taRespuesta.setEditable(editable);

        // Botones
        btnGuardar.setDisable(!editable);
        btnEliminar.setDisable(!editable);
        btnProblematica.setDisable(!editable);
    }

    @FXML
    private void guardarReporte() {
        // Método vacío para implementación
    }

    @FXML
    private void eliminarReporte() {
        // Método vacío para implementación
    }

    @FXML
    private void generarProblematica() {
        // Método vacío para implementación
    }
}