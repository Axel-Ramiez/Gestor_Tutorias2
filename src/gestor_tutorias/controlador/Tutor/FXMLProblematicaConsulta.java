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

public class FXMLProblematicaConsulta {

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

/*
    @FXML private TextField tfIdProblematica;
    @FXML private TextField tfIdReporte;
    @FXML private TextField tfIdExperiencia;
    @FXML private TextField tfTitulo;
    @FXML private ComboBox<EstatusProblematica> cbEstado;
    @FXML private TextArea taDescripcion;

    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    private final ProblematicaDAO dao = new ProblematicaDAO();
    private FXMLProblematicaPrincipal padre;
    private boolean esNuevo = true;

    public void inicializarFormulario(Problematica p, FXMLProblematicaPrincipal padre) {
        this.padre = padre;
        cbEstado.getItems().setAll(EstatusProblematica.values());

        if (p != null) {
            esNuevo = false;
            tfIdProblematica.setText(String.valueOf(p.getIdProblematica()));
            tfIdReporte.setText(String.valueOf(p.getIdReporte()));
            tfIdExperiencia.setText(
                    p.getIdExperienciaEducativa() != null ? String.valueOf(p.getIdExperienciaEducativa()) : ""
            );
            tfTitulo.setText(p.getTitulo());
            taDescripcion.setText(p.getDescripcion());
            cbEstado.setValue(p.getEstado());
        } else {
            cbEstado.setValue(EstatusProblematica.PENDIENTE);
        }
    }

    @FXML
    private void guardarProblematica() {
        if (!validarCampos()) return;

        try {
            Problematica p = extraerDatos();

            boolean exito;
            if (esNuevo) {
                exito = dao.guardarProblematica(p) > 0;
            } else {
                exito = dao.actualizarProblematica(p);
            }

            if (exito) {
                padre.refrescarTabla();
                cerrar();
            } else {
                mostrarAlerta("Error", "No se realizaron cambios.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Error BD", e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los IDs deben ser numéricos.");
        }
    }

    @FXML
    private void eliminarProblematica() {
        if (esNuevo) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar");
        confirm.setContentText("¿Eliminar esta problemática?");
        if (confirm.showAndWait().get() != ButtonType.OK) return;

        try {
            int id = Integer.parseInt(tfIdProblematica.getText());
            if (dao.eliminarProblematica(id)) {
                padre.refrescarTabla();
                cerrar();
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar.");
        }
    }

    private Problematica extraerDatos() {
        int id = esNuevo ? 0 : Integer.parseInt(tfIdProblematica.getText());
        int idReporte = Integer.parseInt(tfIdReporte.getText());
        String titulo = tfTitulo.getText();
        String descripcion = taDescripcion.getText();
        Integer idEE = tfIdExperiencia.getText().isEmpty()
                ? null
                : Integer.parseInt(tfIdExperiencia.getText());

        return new Problematica(id, idReporte, titulo, descripcion, idEE, cbEstado.getValue());
    }

    private boolean validarCampos() {
        if (tfIdReporte.getText().isEmpty()
                || tfTitulo.getText().isEmpty()
                || taDescripcion.getText().isEmpty()) {
            mostrarAlerta("Campos requeridos", "Completa todos los campos obligatorios.");
            return false;
        }
        return true;
    }

    private void cerrar() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


 */

}
