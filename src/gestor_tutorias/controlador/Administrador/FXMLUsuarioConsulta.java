package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLUsuarioConsulta implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<Usuario> tbUsuario;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colContrasena;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colCorreo;


    private ObservableList<Usuario> usuariosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarUsuarios();
    }

    private void configurarColumnas() {

        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        colContrasena.setCellValueFactory(new PropertyValueFactory("contrasena"));
        colRol.setCellValueFactory(new PropertyValueFactory("rolNombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarUsuarios() {
        try {
            List<Usuario> resultado = UsuarioDAO.obtenerTodos();
            usuariosList = FXCollections.observableArrayList(resultado);
            configurarBusqueda();
        } catch (SQLException ex) {
            mostrarAlerta("Error de BD", "No se pudieron cargar los usuarios.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLUsuario.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Registrar Usuario");
            stage.showAndWait();

            cargarUsuarios();

        } catch (IOException ex) {
            System.err.println("Error al cargar la ventana de registro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        Usuario usuarioSeleccionado = tbUsuario.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLUsuario.fxml"));
                Parent root = loader.load();

                FXMLUsuario controlador = loader.getController();

                controlador.inicializarValores(usuarioSeleccionado);


                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setTitle("Editar Usuario");
                stage.showAndWait();

                cargarUsuarios();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            mostrarAlerta("Aviso", "Debes seleccionar un usuario de la tabla.");
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        Usuario usuarioSeleccionado = tbUsuario.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de eliminar al usuario " + usuarioSeleccionado.getNombreCompleto() + "?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    boolean exito = UsuarioDAO.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
                    if (exito) {
                        mostrarAlerta("Éxito", "Usuario eliminado correctamente.");
                        cargarUsuarios(); // Recargar tabla
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el usuario.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            mostrarAlerta("Aviso", "Debes seleccionar un usuario para eliminar.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void configurarBusqueda() {
        if (usuariosList != null) {
            FilteredList<Usuario> filtro = new FilteredList<>(usuariosList, p -> true);
            tfBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate(usuario -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerNewValue = newValue.toLowerCase();
                    if (usuario.getNombreCompleto() != null &&
                            usuario.getNombreCompleto().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (usuario.getMatricula() != null &&
                            usuario.getMatricula().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (usuario.getCorreo() != null &&
                            usuario.getCorreo().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    if (usuario.getRolNombre() != null &&
                            usuario.getRolNombre().toLowerCase().contains(lowerNewValue)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Usuario> sortedData = new SortedList<>(filtro);
            sortedData.comparatorProperty().bind(tbUsuario.comparatorProperty());
            tbUsuario.setItems(sortedData);
        }
    }
}
