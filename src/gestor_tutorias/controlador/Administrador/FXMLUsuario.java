package gestor_tutorias.controlador.Administrador;/*package gestor_tutorias.controlador.Administrador;


import gestor_tutorias.validacion.Validacion;
import javafx.stage.Stage;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLUsuario implements Initializable {

    @FXML private TextField tfMatricula;
    @FXML private ComboBox<String> cbRol;
    @FXML private TextField tfNombre;
    @FXML private TextField tfCorreo;
    @FXML private PasswordField pfContrasena;
    @FXML private Label lbError;

    private Usuario usuarioEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
    }

    private void cargarRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList(
                "Administrador",
                "Coordinador",
                "Tutor"
        );
        cbRol.setItems(roles);
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        lbError.setText("");
        if (validarCampos()) {

            Usuario u = new Usuario();
            u.setMatricula(tfMatricula.getText());
            u.setNombreCompleto(tfNombre.getText());
            u.setCorreo(tfCorreo.getText());
            u.setContrasena(pfContrasena.getText());
            String rolSeleccionado = cbRol.getValue();
            int idRol = (rolSeleccionado.equals("Administrador")) ? 1 :
                    (rolSeleccionado.equals("Coordinador")) ? 2 : 3;
            u.setIdRol(idRol);
            u.setActivo(1);

            try {
                boolean exito;
                if (usuarioEdicion == null) {
                    exito = UsuarioDAO.registrarUsuario(u);
                } else {
                    u.setIdUsuario(usuarioEdicion.getIdUsuario());
                    exito = UsuarioDAO.editarUsuario(u);
                }
                if (exito) {
                    mostrarAlerta("Éxito", "Usuario guardado correctamente.");
                    cerrarVentana();
                } else {
                    lbError.setText("Error al guardar.");
                }
            } catch (SQLException ex) {
                lbError.setText("Error BD: " + ex.getMessage());
            }
        }
    }

    public void inicializarValores(Usuario usuario) {
        this.usuarioEdicion = usuario;
        tfMatricula.setText(usuario.getMatricula());
        tfNombre.setText(usuario.getNombreCompleto());
        tfCorreo.setText(usuario.getCorreo());
        pfContrasena.setText(usuario.getContrasena());
        cbRol.setValue(usuario.getRolNombre());
        tfMatricula.setEditable(false);
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private boolean validarCampos() {
        if (!Validacion.validarRequerido(tfMatricula, lbError, "El No. Personal es obligatorio")) return false;
        if (!Validacion.validarSeleccion(cbRol, lbError, "Selecciona un rol")) return false;
        if (!Validacion.validarNombre(tfNombre, lbError)) return false;
        if (!Validacion.validarCorreoPersonal(tfCorreo, lbError)) return false;
        if (!Validacion.validarRequerido(pfContrasena, lbError, "La contraseña es obligatoria")) return false;
        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
*/