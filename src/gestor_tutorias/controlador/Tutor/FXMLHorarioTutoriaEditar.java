package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.*;
import gestor_tutorias.pojo.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalTime;

public class FXMLHorarioTutoriaEditar {

    @FXML private Label lbIdHorario;
    @FXML private DatePicker dpFecha;

    @FXML private ComboBox<Usuario> cbTutor;
    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;

    @FXML private ComboBox<Integer> cbHoraInicio;
    @FXML private ComboBox<Integer> cbMinutoInicio;
    @FXML private ComboBox<Integer> cbHoraFin;
    @FXML private ComboBox<Integer> cbMinutoFin;

    private int idHorarioTutoria;
    private HorarioTutoria horarioActual;

    private final HorarioTutoriaDAO horarioDAO = new HorarioTutoriaDAO();


    @FXML
    private void initialize() {

        cargarCombosHora();

        try {
            cbTutor.setItems(FXCollections.observableArrayList(
                    UsuarioDAO.obtenerTutores()
            ));
            cbEstudiante.setItems(FXCollections.observableArrayList(
                    EstudianteDAO.obtenerTodos()
            ));
            cbPeriodo.setItems(FXCollections.observableArrayList(
                    PeriodoEscolarDAO.obtenerTodos()
            ));
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los combos.");
        }
    }


    public void setIdHorarioTutoria(int id) {
        this.idHorarioTutoria = id;
        cargarHorario();
    }


    private void cargarHorario() {
        try {
            horarioActual = horarioDAO.obtenerPorId(idHorarioTutoria);
            if (horarioActual != null) {
                pintarDatos();
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar el horario.");
        }
    }


    private void pintarDatos() {
        lbIdHorario.setText(String.valueOf(horarioActual.getIdHorarioTutoria()));
        dpFecha.setValue(horarioActual.getFechaHorarioTutoria());

        seleccionarPorId(cbTutor, horarioActual.getIdUsuario(), Usuario::getIdUsuario);
        seleccionarPorId(cbEstudiante, horarioActual.getIdEstudiante(), Estudiante::getIdEstudiante);
        seleccionarPorId(cbPeriodo, horarioActual.getIdPeriodoEscolar(), PeriodoEscolar::getIdPeriodoEscolar);

        LocalTime ini = horarioActual.getHoraInicioHorarioTutoria();
        LocalTime fin = horarioActual.getHoraFinHorarioTutoria();

        cbHoraInicio.setValue(ini.getHour());
        cbMinutoInicio.setValue(ini.getMinute());
        cbHoraFin.setValue(fin.getHour());
        cbMinutoFin.setValue(fin.getMinute());
    }


    public void clicGuardar(ActionEvent event) {
        try {
            juntarDatos();
            horarioDAO.actualizarHorario(horarioActual);
            cerrar(event);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo guardar el horario.");
        }
    }

    private void juntarDatos() {
        horarioActual.setFechaHorarioTutoria(dpFecha.getValue());

        horarioActual.setHoraInicioHorarioTutoria(
                LocalTime.of(cbHoraInicio.getValue(), cbMinutoInicio.getValue())
        );
        horarioActual.setHoraFinHorarioTutoria(
                LocalTime.of(cbHoraFin.getValue(), cbMinutoFin.getValue())
        );

        horarioActual.setIdUsuario(cbTutor.getValue().getIdUsuario());
        horarioActual.setIdEstudiante(
                cbEstudiante.getValue() != null
                        ? cbEstudiante.getValue().getIdEstudiante()
                        : null
        );
        horarioActual.setIdPeriodoEscolar(cbPeriodo.getValue().getIdPeriodoEscolar());
    }


    private void cargarCombosHora() {
        cbHoraInicio.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23));
        cbHoraFin.setItems(cbHoraInicio.getItems());
        cbMinutoInicio.setItems(FXCollections.observableArrayList(0,15,30,45));
        cbMinutoFin.setItems(cbMinutoInicio.getItems());
    }

    private <T> void seleccionarPorId(
            ComboBox<T> combo,
            Integer id,
            java.util.function.Function<T, Integer> extractor
    ) {
        if (id == null) return;
        for (T item : combo.getItems()) {
            if (extractor.apply(item).equals(id)) {
                combo.getSelectionModel().select(item);
                break;
            }
        }
    }

    public void clicCancelar(ActionEvent event) {
        cerrar(event);
    }

    public void clicCerrar(ActionEvent event) {
        cerrar(event);
    }

    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
