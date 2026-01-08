package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.Problematica;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class FXMLProblematicaCrear {

    /* ===================== FXML ===================== */

    @FXML
    private Label lbIdProblematica;
    @FXML private TextField tfIdReporteTutoria;

    @FXML private TextField tfTitulo;
    @FXML private TextArea taDescripcion;

    @FXML private ComboBox<EstatusProblematica> cbEstado;
    @FXML private ComboBox<Carrera> cbCarrera;

    /* ===================== DATOS ===================== */

    private int idProblematica;
    private Problematica problematicaActual;

    private final ProblematicaDAO problematicaDAO = new ProblematicaDAO();

    /* ===================== INIT ===================== */

    @FXML
    private void initialize() {

        cbEstado.setItems(
                FXCollections.observableArrayList(EstatusProblematica.values())
        );
        cargarCarreras();
        configurarCampos();
    }

    /* ===================== RECIBE ID ===================== */

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
        cargarProblematica();
    }

    /* ===================== CARGA BD ===================== */
    private void cargarCarreras() {

        try {

            List<Carrera> listaCarreras = CarreraDAO.obtenerTodas();

            cbCarrera.setItems(FXCollections.observableArrayList(listaCarreras));

        } catch (SQLException e) {

            e.printStackTrace();

            mostrarAlerta("Error", "No se pudieron cargar las carreras de la base de datos.");

        }

    }
    private void cargarProblematica() {
        try {
            problematicaActual = problematicaDAO.obtenerPorId(idProblematica);
            if (problematicaActual != null) {
                cargarDatosEnVista();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la problemática.");
        }
    }

    /* ===================== PINTA DATOS ===================== */

    private void cargarDatosEnVista() {

        lbIdProblematica.setText(
                String.valueOf(problematicaActual.getIdProblematica())
        );

        tfIdReporteTutoria.setText(
                String.valueOf(problematicaActual.getIdReporteTutoria())
        );

        tfTitulo.setText(problematicaActual.getTitulo());
        taDescripcion.setText(problematicaActual.getDescripcion());

        cbEstado.setValue(problematicaActual.getEstado());

        if (problematicaActual.getIdCarrera() != null) {

            for (Carrera c : cbCarrera.getItems()) {

                if (c.getIdCarrera() == problematicaActual.getIdCarrera()) {

                    cbCarrera.getSelectionModel().select(c);

                    break;
                }
            }
        }
    }

    /* ===================== CONFIG CAMPOS ===================== */

    private void configurarCampos() {

        lbIdProblematica.setDisable(false);

        tfIdReporteTutoria.setEditable(true);
        tfTitulo.setEditable(true);
        taDescripcion.setEditable(true);

        cbEstado.setDisable(false);
        cbCarrera.setDisable(false);
    }

    /* ===================== GUARDAR ===================== */

    public void clicGuardar(ActionEvent event) {

        try {
            juntarDatos();

            if (problematicaActual.getIdProblematica() > 0) {
                problematicaDAO.actualizarProblematica(problematicaActual);
            } else {
                int idGenerado =
                        problematicaDAO.guardarProblematica(problematicaActual);
                problematicaActual.setIdProblematica(idGenerado);
            }

            cerrar(event);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo guardar la problemática.");
        }
    }

    /* ===================== JUNTAR DATOS ===================== */

    private void juntarDatos() {

        if (problematicaActual == null) {
            problematicaActual = new Problematica();
        }

        problematicaActual.setIdReporteTutoria(
                Integer.parseInt(tfIdReporteTutoria.getText())
        );

        problematicaActual.setTitulo(tfTitulo.getText());
        problematicaActual.setDescripcion(taDescripcion.getText());
        problematicaActual.setEstado(cbEstado.getValue());

        Carrera carrera = cbCarrera.getValue();
        if (carrera != null) {
            problematicaActual.setIdCarrera(carrera.getIdCarrera());
        } else {
            problematicaActual.setIdCarrera(null);
        }
    }

    /* ===================== BOTONES ===================== */

    public void clicCancelar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    public void clicCerrar(ActionEvent actionEvent) {
        cerrar(actionEvent);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        stage.close();
    }

    /* ===================== ALERTA ===================== */

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
