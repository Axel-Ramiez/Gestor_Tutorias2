package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.HorarioTutoriaDAO;
import gestor_tutorias.pojo.HorarioTutoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FXMLHorarioTutoriaPrincipal {

    @FXML private TableView<HorarioTutoria> tvHorarios;

    @FXML private TableColumn<HorarioTutoria, Integer> colIdHorario;
    @FXML private TableColumn<HorarioTutoria, String> colFecha;
    @FXML private TableColumn<HorarioTutoria, String> colHoraInicio;
    @FXML private TableColumn<HorarioTutoria, String> colHoraFin;
    @FXML private TableColumn<HorarioTutoria, Integer> colTutor;
    @FXML private TableColumn<HorarioTutoria, Integer> colEstudiante;
    @FXML private TableColumn<HorarioTutoria, Integer> colPeriodoEscolar;

    private final HorarioTutoriaDAO horarioDAO = new HorarioTutoriaDAO();
    private ObservableList<HorarioTutoria> listaHorarios;


    @FXML
    private void initialize() {
        configurarTabla();
        cargarHorarios();
    }

    private void configurarTabla() {

        colIdHorario.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdHorarioTutoria()
                ).asObject()
        );

        colFecha.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFechaHorarioTutoria() != null
                                ? c.getValue().getFechaHorarioTutoria()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                : ""
                )
        );

        colHoraInicio.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getHoraInicioHorarioTutoria() != null
                                ? c.getValue().getHoraInicioHorarioTutoria().toString()
                                : ""
                )
        );

        colHoraFin.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getHoraFinHorarioTutoria() != null
                                ? c.getValue().getHoraFinHorarioTutoria().toString()
                                : ""
                )
        );

        colTutor.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdUsuario()
                ).asObject()
        );

        colEstudiante.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdEstudiante() != null
                                ? c.getValue().getIdEstudiante()
                                : 0
                ).asObject()
        );

        colPeriodoEscolar.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdPeriodoEscolar() != null
                                ? c.getValue().getIdPeriodoEscolar()
                                : 0
                ).asObject()
        );
    }


    private void cargarHorarios() {
        try {
            List<HorarioTutoria> horarios = horarioDAO.obtenerTodos();
            listaHorarios = FXCollections.observableArrayList(horarios);
            tvHorarios.setItems(listaHorarios);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los horarios.");
            e.printStackTrace();
        }
    }



    public void clicCrear(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaCrear.fxml",
                "Crear Horario de Tutoría"
        );
        cargarHorarios();
    }

    public void clicEditar(ActionEvent actionEvent) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un horario para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaEditar.fxml")
            );
            Parent root = loader.load();

            FXMLHorarioTutoriaEditar controlador = loader.getController();
            controlador.setIdHorarioTutoria(seleccionado.getIdHorarioTutoria());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Horario de Tutoría");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarHorarios();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de edición.");
            e.printStackTrace();
        }
    }

    public void clicConsultar(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLHorarioTutoriaConsulta.fxml",
                "Consultar Horario de Tutoría"
        );
    }

    public void clicEliminar(ActionEvent actionEvent) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un horario para eliminar.");
            return;
        }

        try {
            horarioDAO.eliminarHorario(seleccionado.getIdHorarioTutoria());
            cargarHorarios();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el horario.");
            e.printStackTrace();
        }
    }


    private void cambiarVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

/*
//    @FXML
//    private ComboBox<PlaneacionTutoria> cbDia;
    @FXML
    private ComboBox<Integer> cbHoraInicio;
    @FXML
    private ComboBox<Integer> cbMinutoInicio;
    @FXML
    private ComboBox<Integer> cbHoraFin;
    @FXML
    private ComboBox<Integer> cbMinutoFin;
    @FXML
    private TableView<HorarioTutoria> tvHorarios;
    @FXML
    private TableColumn<HorarioTutoria, String> colDia;
    @FXML
    private TableColumn<HorarioTutoria, String> colInicio;
    @FXML
    private TableColumn<HorarioTutoria, String> colFin;
    private final int ID_TUTOR_ACTUAL = 3;

    private ObservableList<HorarioTutoria> listaHorarios;
 //   private ObservableList<PlaneacionTutoria> listaFechasDisponibles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarCombosTiempo();
        //cargarFechasDisponibles();
        //cargarHorariosTabla();
    }

    private void configurarTabla() {
        colDia.setCellValueFactory(new PropertyValueFactory<>("fechaTutoria")); // Muestra el String "2025-..."
        colInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
    }

    private void cargarCombosTiempo() {
        ObservableList<Integer> horas = FXCollections.observableArrayList();
        for (int i = 7; i <= 20; i++) {
            horas.add(i);
        }
        cbHoraInicio.setItems(horas);
        cbHoraFin.setItems(horas);
        ObservableList<Integer> minutos = FXCollections.observableArrayList(0, 15, 30, 45);
        cbMinutoInicio.setItems(minutos);
        cbMinutoFin.setItems(minutos);
    }

    private void cargarFechasDisponibles() {
        try {
            PlaneacionTutoriaDAO daoPlaneacion = new PlaneacionTutoriaDAO();
            List<PlaneacionTutoria> fechas = daoPlaneacion.obtenerTodas();
            listaFechasDisponibles = FXCollections.observableArrayList(fechas);
            cbDia.setItems(listaFechasDisponibles);
        } catch (SQLException ex) {
            mostrarAlerta("Error", "No se pudieron cargar las fechas disponibles.");
            ex.printStackTrace();
        }
    }

    private void cargarHorariosTabla() {
        try {
            HorarioTutoriaDAO dao = new HorarioTutoriaDAO();
            List<HorarioTutoria> resultados = dao.obtenerPorTutor(ID_TUTOR_ACTUAL);
            listaHorarios = FXCollections.observableArrayList(resultados);
            tvHorarios.setItems(listaHorarios);
        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error al consultar los horarios registrados.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (cbDia.getValue() == null || cbHoraInicio.getValue() == null || cbMinutoInicio.getValue() == null
                || cbHoraFin.getValue() == null || cbMinutoFin.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Por favor selecciona la fecha y las horas completas.");
            return;
        }

        PlaneacionTutoria fechaSeleccionada = cbDia.getValue();
        LocalTime inicio = LocalTime.of(cbHoraInicio.getValue(), cbMinutoInicio.getValue());
        LocalTime fin = LocalTime.of(cbHoraFin.getValue(), cbMinutoFin.getValue());

        if (!fin.isAfter(inicio)) {
            mostrarAlerta("Horas inválidas", "La hora de fin debe ser posterior a la de inicio.");
            return;
        }
        HorarioTutoria nuevoHorario = new HorarioTutoria();
        nuevoHorario.setIdTutor(ID_TUTOR_ACTUAL);
        nuevoHorario.setIdFechaTutoria(fechaSeleccionada.getIdFechaTutoria());
        nuevoHorario.setIdEstudiante(null);
        nuevoHorario.setHoraInicio(inicio);
        nuevoHorario.setHoraFin(fin);
        try {
            HorarioTutoriaDAO dao = new HorarioTutoriaDAO();
            int idGenerado = dao.guardarHorario(nuevoHorario);

            if (idGenerado > 0) {
                mostrarAlerta("Éxito", "Horario registrado correctamente.");
                cargarHorariosTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo guardar el horario.");
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error de conexión al guardar.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        HorarioTutoria seleccionado = tvHorarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Selecciona un horario de la tabla para eliminar.");
            return;
        }

        if (seleccionado.getIdEstudiante() != null && seleccionado.getIdEstudiante() > 0) {
            mostrarAlerta("Aviso", "No puedes eliminar un horario que ya fue reservado por un estudiante.");
            return;
        }

        try {
            HorarioTutoriaDAO dao = new HorarioTutoriaDAO();
            boolean exito = dao.eliminarHorario(seleccionado.getIdHorario());

            if (exito) {
                mostrarAlerta("Eliminado", "Horario eliminado correctamente.");
                cargarHorariosTabla();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        cbDia.setValue(null);
        cbHoraInicio.setValue(null);
        cbMinutoInicio.setValue(null);
        cbHoraFin.setValue(null);
        cbMinutoFin.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


 */




