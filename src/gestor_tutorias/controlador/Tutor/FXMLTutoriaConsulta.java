package gestor_tutorias.controlador.Tutor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class FXMLTutoriaConsulta {

    @FXML private DatePicker fecha;
    @FXML private TextField periodoescolar;
    @FXML private TextField idtutor;
    @FXML private TextField idestudiante;
    @FXML private TextField temasatratar;
    @FXML private TextField respuestacoordinador;
    @FXML private TextArea reporte;
    @FXML private CheckBox asistencia;

    @FXML private Button guardar;
    @FXML private Button eliminar;
    @FXML private Button generarproblematica;

    @FXML
    private void guardarProblematica() {

        LocalDate fechaSeleccionada = fecha.getValue();
        String periodo = periodoescolar.getText();
        String tutor = idtutor.getText();
        String estudiante = idestudiante.getText();
        boolean asistio = asistencia.isSelected();

        System.out.println("Guardando reporte de tutoría");
        System.out.println("Fecha: " + fechaSeleccionada);
        System.out.println("Periodo: " + periodo);
        System.out.println("Tutor: " + tutor);
        System.out.println("Estudiante: " + estudiante);
        System.out.println("Asistencia: " + asistio);
    }

    @FXML
    private void eliminarProblematica() {
        System.out.println("Eliminar reporte de tutoría");
    }

    @FXML
    private void generarProblematica() {
        System.out.println("Generar problemática desde reporte");
        System.out.println("Temas: " + temasatratar.getText());
        System.out.println("Reporte: " + reporte.getText());
    }
}
