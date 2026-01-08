package gestor_tutorias.controlador.Tutor;

import gestor_tutorias.dao.ReporteTutoriaDAO;
import gestor_tutorias.pojo.ReporteTutoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FXMLReporteTutoriaPrincipal {

    @FXML private TableView<ReporteTutoria> tvReportes;
    @FXML private TableColumn<ReporteTutoria, Integer> colIdReporte;
    @FXML private TableColumn<ReporteTutoria, String> colFechaReporte;
    @FXML private TableColumn<ReporteTutoria, String> colTextoReporte;
    @FXML private TableColumn<ReporteTutoria, String> colRespuesta;
    @FXML private TableColumn<ReporteTutoria, Boolean> colAsistencia;
    @FXML private TableColumn<ReporteTutoria, String> colEstado;

    private final ReporteTutoriaDAO reporteDAO = new ReporteTutoriaDAO();
    private ObservableList<ReporteTutoria> listaReportes;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarReportes();
    }

    private void configurarTabla() {

        colIdReporte.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getIdReporte()
                ).asObject()
        );

        colFechaReporte.setCellValueFactory(c -> {
            if (c.getValue().getFechaReporte() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFechaReporte()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        colTextoReporte.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTextoReporte() != null
                                ? c.getValue().getTextoReporte()
                                : ""
                )
        );

        colRespuesta.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getRespuestaCoordinador() != null
                                ? c.getValue().getRespuestaCoordinador()
                                : ""
                )
        );

        colAsistencia.setCellValueFactory(c ->
                new javafx.beans.property.SimpleBooleanProperty(
                        c.getValue().isAsistencia()
                ).asObject()
        );

        colEstado.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEstado() != null
                                ? c.getValue().getEstado().name()
                                : ""
                )
        );
    }


    private void cargarReportes() {
        try {
            List<ReporteTutoria> reportes = reporteDAO.obtenerTodos();
            listaReportes = FXCollections.observableArrayList(reportes);
            tvReportes.setItems(listaReportes);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los reportes.");
            e.printStackTrace();
        }
    }

    public void crearReporte(ActionEvent actionEvent) {
        cambiarVentana(
                "/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaCrear.fxml",
                "Reporte de Tutoria crear"
        );
        cargarReportes();
    }

    public void editarReporte(ActionEvent actionEvent) {
        ReporteTutoria seleccionado = tvReportes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un reporte para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaEditar.fxml"));
            Parent root = loader.load();
            FXMLReporteTutoriaEditar controlador = loader.getController();
            controlador.setIdReporte(seleccionado.getIdReporte());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Reporte de Tutoria editar");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            cargarReportes();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de edición.");
            e.printStackTrace();
        }
    }

    public void consultarReporte(ActionEvent actionEvent) {

        ReporteTutoria seleccionado = tvReportes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            mostrarAlerta("Aviso", "Seleccione un reporte para consultar.");

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Tutor/FXMLReporteTutoriaConsulta.fxml"));

            Parent root = loader.load();

// Recuperar el controlador de consulta y pasarle el ID

            FXMLReporteTutoriaConsulta controlador = loader.getController();

            controlador.setIdReporte(seleccionado.getIdReporte());

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Consultar Reporte de Tutoría");

            stage.setScene(new Scene(root));

            stage.showAndWait();

        } catch (IOException e) {

            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta.");

            e.printStackTrace();

        }

    }

    public void eliminarReporte(ActionEvent actionEvent) {
        ReporteTutoria seleccionado = tvReportes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Seleccione un reporte para eliminar.");
            return;
        }

        try {
            reporteDAO.eliminarReporte(seleccionado.getIdReporte());
            cargarReportes();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el reporte.");
            e.printStackTrace();
        }
    }
    public void cambiarVentana(String rutaFXML, String titulo){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }catch(RuntimeException e){
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
