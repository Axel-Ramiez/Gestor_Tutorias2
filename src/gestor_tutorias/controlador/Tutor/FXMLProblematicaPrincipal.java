package gestor_tutorias.controlador.Tutor;

import javafx.event.ActionEvent;

public class FXMLProblematicaPrincipal {
    public void eliminarProblematica(ActionEvent actionEvent) {
    }

    public void editarProblematica(ActionEvent actionEvent) {
    }

    public void consultarProblematica(ActionEvent actionEvent) {
    }

    public void crearProblematica(ActionEvent actionEvent) {

    }
/*
    @FXML private TableView<Problematica> tvProblematica;

    @FXML private TableColumn<Problematica, Integer> colIdProblematica;
    @FXML private TableColumn<Problematica, Integer> colIdReporte;
    @FXML private TableColumn<Problematica, String> colTitulo;
    @FXML private TableColumn<Problematica, String> colDescripcion;
    @FXML private TableColumn<Problematica, Integer> colEE;
    @FXML private TableColumn<Problematica, String> colEstado;

    private final ProblematicaDAO dao = new ProblematicaDAO();
    private ObservableList<Problematica> listaProblematica;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        refrescarTabla();
    }

    private void configurarTabla() {
        colIdProblematica.setCellValueFactory(
                new PropertyValueFactory<>("idProblematica")
        );
        colIdReporte.setCellValueFactory(
                new PropertyValueFactory<>("idReporte")
        );
        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo")
        );
        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );
        colEE.setCellValueFactory(
                new PropertyValueFactory<>("idExperienciaEducativa")
        );
        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );
    }

    public void refrescarTabla() {
        try {
            listaProblematica = FXCollections.observableArrayList(
                    dao.obtenerTodas()
            );
            tvProblematica.setItems(listaProblematica);
        } catch (SQLException e) {
            mostrarAlerta("Error BD", e.getMessage());
        }
    }

    @FXML
    private void crearProblematica() {
        abrirFormulario(null);
    }

    @FXML
    private void consultarProblematica() {
        Problematica seleccion = tvProblematica.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAlerta("Selecciona", "Selecciona una problemática.");
            return;
        }
        abrirFormulario(seleccion);
    }

    private void abrirFormulario(Problematica p) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml")
            );
            Parent root = loader.load();

            FXMLProblematicaConsulta controller = loader.getController();
            controller.inicializarFormulario(p, this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle de Problemática");
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

 */
}
