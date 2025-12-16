package gestor_tutorias.controlador.Coordinador;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.filtro.FiltroString;
import gestor_tutorias.pojo.ReporteTutoria;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FXMLReporteTutoriaConsulta {

    @FXML
    private Label idtutoria;

    @FXML
    private TextField periodoescolar;

    @FXML
    private TextField idtutor;

    @FXML
    private TextField idestudiante;

    @FXML
    private DatePicker fecha;

    @FXML
    private TextField temastratar;

    @FXML
    private TextField respuestacoordinador;

    @FXML
    private TextArea reporte;

    @FXML
    private CheckBox estudiante;

    private final ReporteTutoriaDAO dao = new ReporteTutoriaDAO();

    @FXML
    private void guardarreportetutoria() {

        try {
            // ===== VALIDACIONES BÁSICAS =====
            if (idtutor.getText().isEmpty() || idestudiante.getText().isEmpty()) {
                mostrarAlerta("Campos obligatorios vacíos");
                return;
            }

            // ===== FILTRO STRING =====
            FiltroString filtro = new FiltroString(reporte.getText());
            filtro.filtrarSQLInjectionString();
            filtro.filtrarXSSString();

            int idTutor = Integer.parseInt(idtutor.getText());
            int idEstudiante = Integer.parseInt(idestudiante.getText());

            // En tu DB id_fecha_tutoria es FK numérica
            int idFechaTutoria = 1; // <-- normalmente se obtiene de planeacion_tutoria

            boolean asistencia = estudiante.isSelected();

            ReporteTutoria nuevo = new ReporteTutoria(
                    idTutor,
                    idEstudiante,
                    idFechaTutoria,
                    reporte.getText(),
                    asistencia
            );

            int idGenerado = dao.guardarReporte(nuevo);

            if (idGenerado > 0) {
                idtutoria.setText(String.valueOf(idGenerado));
                mostrarAlerta("Reporte guardado correctamente");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("IDs inválidos");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar en BD");
        }
    }

    @FXML
    private void eliminarreportetutoria() {

        try {
            if (idtutoria.getText() == null || idtutoria.getText().isEmpty()) {
                mostrarAlerta("No hay reporte seleccionado");
                return;
            }

            int id = Integer.parseInt(idtutoria.getText());

            if (dao.eliminarReporte(id)) {
                mostrarAlerta("Reporte eliminado");
                limpiarCampos();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al eliminar");
        }
    }

    private void limpiarCampos() {
        idtutoria.setText("");
        idtutor.clear();
        idestudiante.clear();
        periodoescolar.clear();
        temastratar.clear();
        respuestacoordinador.clear();
        reporte.clear();
        estudiante.setSelected(false);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
