package gestor_tutorias.controlador.Administrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXMLUsuarioConsulta {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<?> tbEstudiantes;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colCarrera;
    @FXML
    private TableColumn<?, ?> colSemestre;
    @FXML
    private TableColumn<?, ?> colNombre1;

    @FXML
    private void clicRegistrar(ActionEvent event) {
    }

    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }
}
