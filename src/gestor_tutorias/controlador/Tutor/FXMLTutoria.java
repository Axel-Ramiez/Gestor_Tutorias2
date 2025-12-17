package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import gestor_tutorias.pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLTutoria implements Initializable {

    // Componentes vinculados al FXML
    @FXML private TableView<ReporteTutoria> tvReportes;
    @FXML private TableColumn<ReporteTutoria, Integer> colIdReporte;
    @FXML private TableColumn<ReporteTutoria, String> colFecha;
    @FXML private TableColumn<ReporteTutoria, String> colPeriodo;
    @FXML private TableColumn<ReporteTutoria, String> colEstudiante;
    @FXML private TableColumn<ReporteTutoria, String> colEstado;
    @FXML private TableColumn<ReporteTutoria, Boolean> colAsistencia;
    @FXML private Button btnConsultar;
    @FXML private Button btnCrear;

    private ObservableList<ReporteTutoria> listaReportes;
    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();
    private Usuario tutorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    private void configurarTabla() {
        // Asegúrate de que estos nombres coincidan con los atributos de tu POJO ReporteTutoria
        colIdReporte.setCellValueFactory(new PropertyValueFactory<>("idReporte"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistencia"));
    }

    public void inicializarInformacion(Usuario usuario) {
        this.tutorSesion = usuario;
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            List<ReporteTutoria> reportes;
            if (tutorSesion != null) {
                // Filtra por el ID del tutor que inició sesión
                reportes = dao.obtenerPorTutor(tutorSesion.getIdUsuario());
            } else {
                reportes = dao.obtenerTodos();
            }
            listaReportes = FXCollections.observableArrayList(reportes);
            tvReportes.setItems(listaReportes);
        } catch (SQLException e) {
            mostrarAlerta("Error de BD", "No se pudieron cargar los reportes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void consultarReporteTutoria() {
        ReporteTutoria seleccionado = tvReportes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Se pasa false para que sea solo lectura o según tu lógica de "Consulta"
            abrirFormulario(seleccionado, false);
        } else {
            mostrarAlerta("Selección requerida", "Por favor, selecciona un reporte de la tabla.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void crearReporteTutoria() {
        // Se pasa un nuevo objeto o null para indicar creación
        abrirFormulario(null, true);
    }

    private void abrirFormulario(ReporteTutoria reporte, boolean esEdicion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLTutoriaConsulta.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana hija
            FXMLTutoriaConsulta controlador = loader.getController();
            // Método para pasar los datos necesarios
            controlador.inicializarFormulario(reporte, tutorSesion, this, esEdicion);

            Stage stage = new Stage();
            stage.setTitle(reporte == null ? "Nuevo Reporte" : "Detalle de Reporte");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refrescar la tabla al cerrar la ventana hija
            cargarDatos();

        } catch (IOException e) {
            mostrarAlerta("Error de interfaz", "No se pudo abrir la ventana: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // Variables para mantener la referencia de los datos recibidos
    private ReporteTutoria reporteSeleccionado;
    private Usuario tutorSesion;
    private FXMLTutoriaControlador controladorPadre;
    private boolean esEdicion;

    public void inicializarFormulario(ReporteTutoria reporte, Usuario tutor, FXMLTutoriaControlador padre, boolean editable) {
        this.reporteSeleccionado = reporte;
        this.tutorSesion = tutor;
        this.controladorPadre = padre;
        this.esEdicion = editable;

        if (reporte != null) {
            // Modo Consulta/Edición: Llenar los campos con los datos existentes
            cargarDatosEnCampos();
        } else {
            // Modo Creación: Limpiar campos o poner valores por defecto
            prepararParaNuevoRegistro();
        }

        // Configurar si los campos de texto/combos son editables o no
        configurarInteractividad(editable);
    }

    private void cargarDatosEnCampos() {
        // Ejemplo de llenado (ajusta según tus fx:id de la vista consulta)
        txtFecha.setText(reporteSeleccionado.getFecha());
        txtPeriodo.setText(reporteSeleccionado.getPeriodoEscolar());
        lblNombreEstudiante.setText(reporteSeleccionado.getNombreEstudiante());
        chbAsistencia.setSelected(reporteSeleccionado.isAsistencia());
    }

    private void configurarInteractividad(boolean editable) {
        // Si no es editable, desactivamos los controles
        btnGuardar.setVisible(editable);
        txtFecha.setEditable(editable);
        txtPeriodo.setEditable(editable);
        // ... desactivar más componentes según sea necesario
    }
}