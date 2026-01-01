package gestor_tutorias.controlador.Tutor;

//import gestor_tutorias.dao.PlaneacionTutoriaDAO; // Necesario para llenar el combo de fechas
//import gestor_tutorias.pojo.PlaneacionTutoria;


import javafx.event.ActionEvent;

public class FXMLHorarioTutoriaPrincipal {
    public void clicCrear(ActionEvent actionEvent) {
    }

    public void clicEditar(ActionEvent actionEvent) {
    }

    public void clicConsultar(ActionEvent actionEvent) {
    }

    public void clicEliminar(ActionEvent actionEvent) {

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
}



