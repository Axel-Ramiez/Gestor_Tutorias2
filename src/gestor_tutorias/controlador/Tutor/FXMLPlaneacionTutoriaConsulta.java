package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.CarreraDAO;
import gestor_tutorias.dao.PeriodoEscolarDAO;
import gestor_tutorias.pojo.Carrera;
import gestor_tutorias.pojo.PeriodoEscolar;
import gestor_tutorias.pojo.PlaneacionTutoria;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLPlaneacionTutoriaConsulta implements Initializable {

    @FXML private ComboBox<PeriodoEscolar> cbPeriodo;
    @FXML private ComboBox<Carrera> cbCarrera;
    @FXML private DatePicker fechaTutoria;
    @FXML private ComboBox<Integer> cbSesion;
    @FXML private TextArea temastratar;
    @FXML private TextField tfIdPlaneacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCatalogos();
        cbSesion.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
    }

    private void cargarCatalogos() {
        try {
            List<PeriodoEscolar> periodos = PeriodoEscolarDAO.obtenerTodos();
            cbPeriodo.setItems(FXCollections.observableArrayList(periodos));

            List<Carrera> carreras = CarreraDAO.obtenerTodas();
            cbCarrera.setItems(FXCollections.observableArrayList(carreras));
        } catch (SQLException e) {
            e.printStackTrace();

            System.err.println("Error al cargar catálogos para visualización.");
        }
    }


    public void inicializarValores(PlaneacionTutoria planeacion) {
        if (planeacion != null) {
            tfIdPlaneacion.setText(String.valueOf(planeacion.getIdPlaneacionTutoria()));
            fechaTutoria.setValue(planeacion.getFechaTutoria());
            cbSesion.setValue(planeacion.getNumeroSesion());
            temastratar.setText(planeacion.getTemas());

            seleccionarPeriodo(planeacion.getIdPeriodoEscolar());
            seleccionarCarrera(planeacion.getIdCarrera());
        }
    }

    private void seleccionarPeriodo(int idPeriodo) {
        if (cbPeriodo.getItems() != null) {
            for (PeriodoEscolar p : cbPeriodo.getItems()) {
                if (p.getIdPeriodoEscolar() == idPeriodo) {
                    cbPeriodo.setValue(p);
                    break;
                }
            }
        }
    }

    private void seleccionarCarrera(int idCarrera) {
        if (cbCarrera.getItems() != null) {
            for (Carrera c : cbCarrera.getItems()) {
                if (c.getIdCarrera() == idCarrera) {
                    cbCarrera.setValue(c);
                    break;
                }
            }
        }
    }



    @FXML
    private void clicCerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
