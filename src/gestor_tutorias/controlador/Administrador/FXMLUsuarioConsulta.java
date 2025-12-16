package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        colRol.setCellValueFactory(new PropertyValueFactory("idRol"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarUsuarios() {
        try {
            List<Usuario> resultado = UsuarioDAO.obtenerTodos();
            usuariosList = FXCollections.observableArrayList(resultado);
            tbUsuario.setItems(usuariosList);
        } catch (SQLException ex) {
            mostrarAlerta("Error de BD", "No se pudieron cargar los usuarios.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {

        mostrarAlerta("Navegación", "Ir a pantalla de registrar Usuario.");
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        Usuario usuarioSeleccionado = tbUsuario.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            mostrarAlerta("Selección", "Vas a editar a: " + usuarioSeleccionado.getNombreCompleto());
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
}
