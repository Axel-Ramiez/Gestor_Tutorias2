package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.HorarioTutoriaDAO;
import gestor_tutorias.pojo.HorarioTutoria;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalTime;

public class FXMLHorarioTutoriaConsulta {

    @FXML private TableView<HorarioTutoria> tvHorario;
    @FXML private TableColumn<HorarioTutoria, Integer> colIdHorario;
    @FXML private TableColumn<HorarioTutoria, String> colNombreTutor;
    @FXML private TableColumn<HorarioTutoria, String> colFechaTutoria;
    @FXML private TableColumn<HorarioTutoria, LocalTime> colHoraInicio;
    @FXML private TableColumn<HorarioTutoria, LocalTime> colHoraFin;

    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;

    private boolean modoCrear;
    private HorarioTutoria horarioSeleccionado;

    private final ObservableList<HorarioTutoria> horarios =
            FXCollections.observableArrayList();

    private final HorarioTutoriaDAO dao = new HorarioTutoriaDAO();

    @FXML
    private void initialize() {

        colIdHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        colNombreTutor.setCellValueFactory(new PropertyValueFactory<>("nombreTutor"));
        colFechaTutoria.setCellValueFactory(new PropertyValueFactory<>("fechaTutoria"));

        colHoraInicio.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getHoraInicio()));
        colHoraFin.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getHoraFin()));

        tvHorario.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, nuevo) -> cargarFormulario(nuevo)
        );

        cargarHorarios();
    }

    public void inicializarModo(boolean crear) {
        this.modoCrear = crear;

        if (crear) {
            tvHorario.setDisable(true);
            limpiarFormulario();
        } else {
            tvHorario.setDisable(false);
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            LocalTime inicio = LocalTime.parse(txtHoraInicio.getText());
            LocalTime fin = LocalTime.parse(txtHoraFin.getText());

            if (modoCrear) {
                HorarioTutoria nuevo = new HorarioTutoria();
                nuevo.setHoraInicio(inicio);
                nuevo.setHoraFin(fin);

                dao.guardarHorario(nuevo);
            } else if (horarioSeleccionado != null) {
                horarioSeleccionado.setHoraInicio(inicio);
                horarioSeleccionado.setHoraFin(fin);

                dao.actualizarHorario(horarioSeleccionado);
            }

            cargarHorarios();
            limpiarFormulario();

        } catch (Exception e) {
            mostrarAlerta("Error", "Formato inválido (HH:mm)");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) tvHorario.getScene().getWindow()).close();
    }

    private void cargarHorarios() {
        try {
            horarios.setAll(dao.obtenerTodos());
            tvHorario.setItems(horarios);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los horarios");
        }
    }

    private void cargarFormulario(HorarioTutoria h) {
        if (h != null && !modoCrear) {
            horarioSeleccionado = h;
            txtHoraInicio.setText(h.getHoraInicio().toString());
            txtHoraFin.setText(h.getHoraFin().toString());
        }
    }

    private void limpiarFormulario() {
        horarioSeleccionado = null;
        txtHoraInicio.clear();
        txtHoraFin.clear();
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
