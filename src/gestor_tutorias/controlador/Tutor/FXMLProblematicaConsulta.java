package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.Enum.EstatusProblematica;
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
            tfIdExperiencia.setText(p.getIdExperienciaEducativa() != null ? String.valueOf(p.getIdExperienciaEducativa()) : "");
            tfTitulo.setText(p.getTitulo());
            taDescripcion.setText(p.getDescripcion());
            cbEstado.setValue(p.getEstado());
        } else {
            cbEstado.setValue(EstatusProblematica.PENDIENTE);
        }
    }

    @FXML
    private void guardarProblematica() {
        // 1. Validar que los campos obligatorios no estén vacíos
        if (tfIdReporte.getText().isEmpty() || tfTitulo.getText().isEmpty() || taDescripcion.getText().isEmpty()) {
            mostrarAlerta("Campos Requeridos", "Por favor, llena el ID de Reporte, Título y Descripción.");
            return;
        }

        try {
            // 2. Extraer y convertir datos
            int idReporteVal = Integer.parseInt(tfIdReporte.getText());
            String tituloVal = tfTitulo.getText();
            String descripcionVal = taDescripcion.getText();

            // Manejo de nulo para Experiencia Educativa
            Integer idEE = null;
            if (!tfIdExperiencia.getText().trim().isEmpty()) {
                idEE = Integer.parseInt(tfIdExperiencia.getText());
            }

            EstatusProblematica estadoVal = cbEstado.getValue();

            // 3. Crear objeto POJO
            // Si es nuevo, el ID se ignora en el INSERT del DAO, pero pasamos 0
            int idActual = esNuevo ? 0 : Integer.parseInt(tfIdProblematica.getText());
            Problematica problematica = new Problematica(idActual, idReporteVal, tituloVal, descripcionVal, idEE, estadoVal);

            // 4. Ejecutar operación según el modo (Crear o Editar)
            boolean resultado;
            if (esNuevo) {
                int idGenerado = dao.guardarProblematica(problematica);
                resultado = (idGenerado > 0);
            } else {
                resultado = dao.actualizarProblematica(problematica);
            }

            // 5. Feedback al usuario y cierre
            if (resultado) {
                // No es necesario llamar a padre.cargarProblematica() aquí si usas showAndWait()
                // en el controlador anterior, pero lo dejamos por seguridad.
                if (padre != null) padre.cargarProblematica();

                cerrarVentana();
            } else {
                mostrarAlerta("Error", "La operación se realizó pero no hubo cambios en la base de datos.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "Los campos ID Reporte e ID Experiencia deben ser números enteros.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Base de Datos", "Ocurrió un error al conectar con la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarProblematica() {
        if (esNuevo) return;

        try {
            int id = Integer.parseInt(tfIdProblematica.getText());
            if (dao.eliminarProblematica(id)) {
                cerrarVentana();
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el registro.");
        }
    }

    private Problematica extraerDatos() {
        int id = esNuevo ? 0 : Integer.parseInt(tfIdProblematica.getText());
        int idRep = Integer.parseInt(tfIdReporte.getText());
        String tit = tfTitulo.getText();
        String desc = taDescripcion.getText();
        Integer idEE = tfIdExperiencia.getText().isEmpty() ? null : Integer.parseInt(tfIdExperiencia.getText());
        EstatusProblematica est = cbEstado.getValue();

        return new Problematica(id, idRep, tit, desc, idEE, est);
    }

    private boolean validarCampos() {
        if (tfIdReporte.getText().isEmpty() || tfTitulo.getText().isEmpty() || taDescripcion.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor completa el ID de reporte, título y descripción.");
            return false;
        }
        try {
            Integer.parseInt(tfIdReporte.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Dato inválido", "El ID de reporte debe ser un número.");
            return false;
        }
        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfTitulo.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
