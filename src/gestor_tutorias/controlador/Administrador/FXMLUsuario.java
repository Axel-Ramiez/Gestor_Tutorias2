package gestor_tutorias.controlador.Administrador;

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

    @FXML private TextField tfNoPersonal;
    @FXML private ComboBox<String> cbRol;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidoPaterno;
    @FXML private TextField tfApellidoMaterno;
    @FXML private TextField tfCorreo;
    @FXML private PasswordField pfContrasena;
    @FXML private Label lblErrorNoPersonal;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorApellidoPaterno;
    @FXML private Label lblErrorApellidoMaterno;
    @FXML private Label lblErrorCorreo;
    @FXML private Label lblErrorContrasena;
    @FXML private Label lblErrorRol;


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
        if (!validarCampos()) {
            return;
        }

        Usuario u = new Usuario();
        u.setNoPersonalUsuario(tfNoPersonal.getText());
        u.setNombreUsuario(tfNombre.getText());
        u.setApellidoPaternoUsuario(tfApellidoPaterno.getText());
        u.setApellidoMaternoUsuario(tfApellidoMaterno.getText());
        u.setCorreoUsuario(tfCorreo.getText());
        u.setContrasenaUsuario(pfContrasena.getText());

        String rolSeleccionado = (String) cbRol.getValue();
        int idRol = 3;
        if ("Administrador".equals(rolSeleccionado)) {
            idRol = 1;
        } else if ("Coordinador".equals(rolSeleccionado)) {
            idRol = 2;
        }
        u.setIdRol(idRol);
        u.setActivoUsuario(1);

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
                mostrarAlerta("Error", "No se pudo guardar. Verifica que el No. Personal o Correo no estén repetidos.");
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error BD", "Error de conexión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void inicializarValores(Usuario usuario) {
        this.usuarioEdicion = usuario;
        tfNoPersonal.setText(usuario.getNoPersonalUsuario());
        tfNombre.setText(usuario.getNombreUsuario());
        tfApellidoPaterno.setText(usuario.getApellidoPaternoUsuario());
        tfApellidoMaterno.setText(usuario.getApellidoMaternoUsuario());
        tfCorreo.setText(usuario.getCorreoUsuario());
        pfContrasena.setText(usuario.getContrasenaUsuario());
        tfNoPersonal.setEditable(false);
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (!Validacion.validarLongitud(tfNoPersonal, lblErrorNoPersonal, 1, 20)) {
            valido = false;
        } else if (!Validacion.validarNoPersonal(tfNoPersonal, lblErrorNoPersonal)) {
            valido = false;
        }

        if (!Validacion.validarLongitud(tfNombre, lblErrorNombre, 1, 150)) {
            valido = false;
        } else if (!Validacion.validarNombre(tfNombre, lblErrorNombre)) {
            valido = false;
        }

        if (!Validacion.validarLongitud(tfApellidoPaterno, lblErrorApellidoPaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoPaterno, lblErrorApellidoPaterno)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoMaterno, lblErrorApellidoMaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoMaterno, lblErrorApellidoMaterno)) valido = false;

        if (!Validacion.validarLongitud(tfCorreo, lblErrorCorreo, 1, 100)) valido = false;
        else if (!Validacion.validarCorreoPersonal(tfCorreo, lblErrorCorreo)) valido = false;

        if (!Validacion.validarLongitud(pfContrasena, lblErrorContrasena, 5, 50)) {
            valido = false;
        }

        if (!Validacion.validarSeleccion(cbRol, lblErrorRol, "Selecciona un rol")) {
            valido = false;
        }

        return valido;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNoPersonal.getScene().getWindow();
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