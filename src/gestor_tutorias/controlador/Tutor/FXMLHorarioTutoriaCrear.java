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

public class FXMLHorarioTutoriaCrear {

    @FXML private Label lbIdHorario;

    @FXML private DatePicker dpFecha;

    @FXML private ComboBox<Usuario> cbTutor;
    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;

    @FXML private ComboBox<Integer> cbHoraInicio;
    @FXML private ComboBox<Integer> cbMinutoInicio;
    @FXML private ComboBox<Integer> cbHoraFin;
    @FXML private ComboBox<Integer> cbMinutoFin;

    private final HorarioTutoriaDAO horarioDAO = new HorarioTutoriaDAO();

    @FXML
    private void initialize() {
        if(lbIdHorario != null) {
            lbIdHorario.setText("Nuevo");
        }

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
            mostrarAlerta("Error", "No se pudieron cargar las listas de datos.");
            e.printStackTrace();
        }
    }


    public void inicializarTutor(Usuario tutorLogueado) {
        for(Usuario u : cbTutor.getItems()){
            if(u.getIdUsuario() == tutorLogueado.getIdUsuario()){
                cbTutor.setValue(u);
                cbTutor.setDisable(true); // Bloqueamos para que no pueda cambiarlo
                break;
            }
        }
    }

    @FXML
    public void clicGuardar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        try {

            HorarioTutoria nuevoHorario = new HorarioTutoria();


            llenarObjeto(nuevoHorario);
            int idRegistrado = horarioDAO.guardarHorario(nuevoHorario);

            if (idRegistrado > 0) {
                mostrarAlertaInfo("Éxito", "Horario de tutoría registrado correctamente.");
                cerrar(event);
            } else {
                mostrarAlerta("Error", "No se pudo registrar el horario en la base de datos.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Error de BD", "Hubo un error al guardar: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error inesperado.");
            e.printStackTrace();
        }
    }

    private void llenarObjeto(HorarioTutoria horario) {
        horario.setFechaHorarioTutoria(dpFecha.getValue());

        horario.setHoraInicioHorarioTutoria(
                LocalTime.of(cbHoraInicio.getValue(), cbMinutoInicio.getValue())
        );
        horario.setHoraFinHorarioTutoria(
                LocalTime.of(cbHoraFin.getValue(), cbMinutoFin.getValue())
        );

        horario.setIdUsuario(cbTutor.getValue().getIdUsuario());

        if (cbEstudiante.getValue() != null) {
            horario.setIdEstudiante(cbEstudiante.getValue().getIdEstudiante());
        } else {
            horario.setIdEstudiante(null);
        }

        horario.setIdPeriodoEscolar(cbPeriodo.getValue().getIdPeriodoEscolar());
    }

    private boolean validarCampos() {
        if (dpFecha.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Debes seleccionar una fecha.");
            return false;
        }
        if (cbTutor.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Debes seleccionar un tutor.");
            return false;
        }
        if (cbPeriodo.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Debes seleccionar un periodo escolar.");
            return false;
        }
        if (cbHoraInicio.getValue() == null || cbMinutoInicio.getValue() == null ||
                cbHoraFin.getValue() == null || cbMinutoFin.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Debes configurar la hora de inicio y fin.");
            return false;
        }

        LocalTime inicio = LocalTime.of(cbHoraInicio.getValue(), cbMinutoInicio.getValue());
        LocalTime fin = LocalTime.of(cbHoraFin.getValue(), cbMinutoFin.getValue());

        if (!fin.isAfter(inicio)) {
            mostrarAlerta("Hora inválida", "La hora de fin debe ser posterior a la de inicio.");
            return false;
        }

        return true;
    }

    private void cargarCombosHora() {
        cbHoraInicio.setItems(FXCollections.observableArrayList(7,8,9,10,11,12,13,14,15,16,17,18,19,20));
        cbHoraFin.setItems(cbHoraInicio.getItems());

        cbMinutoInicio.setItems(FXCollections.observableArrayList(0, 15, 30, 45));
        cbMinutoFin.setItems(cbMinutoInicio.getItems());
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

    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
