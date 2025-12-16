package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ProblematicaDAO;
import gestor_tutorias.pojo.Problematica;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FXMLProblematica {

    @FXML
    private TableView<Problematica> tablaproblematica;

    private final ProblematicaDAO dao = new ProblematicaDAO();

    @FXML
    private void consultarproblematica() {
        try {
            List<Problematica> lista = dao.obtenerTodas();
            tablaproblematica.getItems().setAll(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void crearproblematica() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLProblematicaConsulta.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Problematica");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
