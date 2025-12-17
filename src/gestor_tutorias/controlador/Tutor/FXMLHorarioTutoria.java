package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.HorarioTutoriaDAO;
import gestor_tutorias.pojo.HorarioTutoria;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class FXMLHorarioTutoria {

    @FXML private Label lblUsuario;

    @FXML private TableView<HorarioTutoria> tvHorario;
    @FXML private TableColumn<HorarioTutoria, Integer> colIdHorario;
    @FXML private TableColumn<HorarioTutoria, Integer> colIdTutor;
    @FXML private TableColumn<HorarioTutoria, Integer> colIdFechaTutoria;
    @FXML private TableColumn<HorarioTutoria, LocalTime> colHoraInicio;
    @FXML private TableColumn<HorarioTutoria, LocalTime> colHoraFin;

    private final ObservableList<HorarioTutoria> horarios =
            FXCollections.observableArrayList();

    private final HorarioTutoriaDAO dao = new HorarioTutoriaDAO();

    @FXML
    private void initialize() {
        tvHorario.setEditable(false);

        colIdHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        colIdTutor.setCellValueFactory(new PropertyValueFactory<>("idTutor"));
        colIdFechaTutoria.setCellValueFactory(new PropertyValueFactory<>("idFechaTutoria"));

        colHoraInicio.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getHoraInicio()));
        colHoraFin.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getHoraFin()));

        cargarHorarios();
    }

    private void cargarHorarios() {
        try {
            List<HorarioTutoria> lista = dao.obtenerTodos();
            horarios.setAll(lista);
            tvHorario.setItems(horarios);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los horarios");
        }
    }

    @FXML
    private void consultarHorario(ActionEvent event) {
        abrirConsulta(false);
    }

    @FXML
    private void crearHorario(ActionEvent event) {
        abrirConsulta(true);
    }

    private void abrirConsulta(boolean modoCrear) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaConsulta.fxml")
            );
            Parent root = loader.load();

            FXMLHorarioTutoriaConsulta controller = loader.getController();
            controller.inicializarModo(modoCrear);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Horarios");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarHorarios();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana");
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        ((Stage) lblUsuario.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
