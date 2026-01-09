package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.EstudianteDAO;

import gestor_tutorias.dao.HorarioTutoriaDAO;

import gestor_tutorias.dao.PeriodoEscolarDAO;

import gestor_tutorias.dao.UsuarioDAO;

import gestor_tutorias.pojo.Estudiante;

import gestor_tutorias.pojo.HorarioTutoria;

import gestor_tutorias.pojo.PeriodoEscolar;

import gestor_tutorias.pojo.Usuario;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;

import javafx.stage.Stage;

import java.sql.SQLException;

import java.time.LocalTime;

public class FXMLHorarioTutoriaConsultar {

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

    public void initialize() {

        cargarCombosHora();

        cargarDatosCombos();

        deshabilitarCampos();

    }

    private void deshabilitarCampos() {

        dpFecha.setDisable(true);

        cbTutor.setDisable(true);

        cbEstudiante.setDisable(true);

        cbPeriodo.setDisable(true);

        cbHoraInicio.setDisable(true);

        cbMinutoInicio.setDisable(true);

        cbHoraFin.setDisable(true);

        cbMinutoFin.setDisable(true);

        dpFecha.setStyle("-fx-opacity: 1; -fx-text-fill: black;");

    }

    private void cargarDatosCombos() {

        try {

            cbTutor.setItems(FXCollections.observableArrayList(UsuarioDAO.obtenerTutores()));

            cbEstudiante.setItems(FXCollections.observableArrayList(EstudianteDAO.obtenerTodos()));

            cbPeriodo.setItems(FXCollections.observableArrayList(PeriodoEscolarDAO.obtenerTodos()));

        } catch (SQLException e) {

            mostrarAlerta("Error", "No se pudieron cargar las listas de datos.");

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

            mostrarAlerta("Error", "No se pudo cargar la información del horario.");

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

        if (ini != null) {

            cbHoraInicio.setValue(ini.getHour());

            cbMinutoInicio.setValue(ini.getMinute());

        }

        if (fin != null) {

            cbHoraFin.setValue(fin.getHour());

            cbMinutoFin.setValue(fin.getMinute());

        }

    }

    private void cargarCombosHora() {

        ObservableList<Integer> horas = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);

        ObservableList<Integer> minutos = FXCollections.observableArrayList(0,15,30,45);

        cbHoraInicio.setItems(horas);

        cbHoraFin.setItems(horas);

        cbMinutoInicio.setItems(minutos);

        cbMinutoFin.setItems(minutos);

    }

    private <T> void seleccionarPorId(ComboBox<T> combo, Integer id, java.util.function.Function<T, Integer> extractor) {

        if (id == null) return;

        for (T item : combo.getItems()) {

            if (id.equals(extractor.apply(item))) {

                combo.getSelectionModel().select(item);

                break;

            }

        }

    }

    public void clicCerrar(ActionEvent event) {

        cerrar(event);

    }

    private void cerrar(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();

    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
