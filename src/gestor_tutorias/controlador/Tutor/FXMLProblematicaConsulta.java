package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.Enum.EstatusProblematica;
import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FXMLProblematicaConsulta {

    @FXML private TextField tfIdProblematica;
    @FXML private TextField tfIdReporte;
    @FXML private TextField tfIdExperiencia;
    @FXML private TextField tfTitulo;
    @FXML private ComboBox<EstatusProblematica> cbEstado;
    @FXML private TextArea taDescripcion;

    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    private final ProblematicaDAO dao = new ProblematicaDAO();
    private FXMLProblematica padre;
    private boolean esNuevo = true;

    public void inicializarFormulario(Problematica p, FXMLProblematica padre) {
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
}
