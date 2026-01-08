package gestor_tutorias.controlador.Administrador;

import gestor_tutorias.dao.RolDAO;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Rol;
import gestor_tutorias.pojo.Usuario;
import gestor_tutorias.validacion.Validacion;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLUsuario implements Initializable {

    @FXML private TextField tfNoPersonal;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidoPaterno;
    @FXML private TextField tfApellidoMaterno;
    @FXML private TextField tfCorreo;
    @FXML private PasswordField pfContrasena;
    @FXML private ComboBox<Rol> cbRol;

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

    @FXML
    private void clicGuardar(ActionEvent event) {

        if (!validarCampos()) {
            return;
        }

        int idExcluir = (this.usuarioEdicion != null) ? this.usuarioEdicion.getIdUsuario() : 0;

        try {
            if (UsuarioDAO.esNoPersonalRegistrado(tfNoPersonal.getText(), idExcluir)) {
                lblErrorNoPersonal.setText("Este No. Personal ya está registrado.");
                tfNoPersonal.setStyle("-fx-border-color: red;");
                return;
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al validar duplicados: " + ex.getMessage());
            return;
        }

        Usuario u = obtenerUsuarioDeVista();

        try {
            boolean exito = false;

            if (this.usuarioEdicion == null) {

                exito = UsuarioDAO.registrarUsuario(u);
            } else {
                u.setIdUsuario(this.usuarioEdicion.getIdUsuario());
                exito = UsuarioDAO.editarUsuario(u);
            }

            if (exito) {
                mostrarAlerta("Éxito", "La información se guardó correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la información.");
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

        cargarRoles();

        for (Rol r : cbRol.getItems()) {
            if (r.getIdRol() == usuario.getIdRol()) {
                cbRol.setValue(r);
                break;
            }
        }
    }

    private Usuario obtenerUsuarioDeVista() {
        Usuario u = new Usuario();
        u.setNoPersonalUsuario(tfNoPersonal.getText());
        u.setNombreUsuario(tfNombre.getText());
        u.setApellidoPaternoUsuario(tfApellidoPaterno.getText());
        u.setApellidoMaternoUsuario(tfApellidoMaterno.getText());
        u.setCorreoUsuario(tfCorreo.getText());
        u.setContrasenaUsuario(pfContrasena.getText());

        Rol rolSeleccionado = cbRol.getValue();
        if (rolSeleccionado != null) {
            u.setIdRol(rolSeleccionado.getIdRol());
        }
        u.setActivoUsuario(1);
        return u;
    }

    private void cargarRoles() {
        try {
            List<Rol> rolesBD = RolDAO.obtenerRoles();
            cbRol.setItems(FXCollections.observableArrayList(rolesBD));
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al cargar roles.");
        }
    }

    private boolean validarCampos() {
        boolean valido = true;

        tfNoPersonal.setStyle("");

        if (!Validacion.validarLongitud(tfNoPersonal, lblErrorNoPersonal, 1, 20)) valido = false;
        else if (!Validacion.validarNoPersonal(tfNoPersonal, lblErrorNoPersonal)) valido = false;

        if (!Validacion.validarLongitud(tfNombre, lblErrorNombre, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfNombre, lblErrorNombre)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoPaterno, lblErrorApellidoPaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoPaterno, lblErrorApellidoPaterno)) valido = false;

        if (!Validacion.validarLongitud(tfApellidoMaterno, lblErrorApellidoMaterno, 1, 150)) valido = false;
        else if (!Validacion.validarNombre(tfApellidoMaterno, lblErrorApellidoMaterno)) valido = false;

        if (!Validacion.validarLongitud(tfCorreo, lblErrorCorreo, 1, 100)) valido = false;
        else if (!Validacion.validarCorreoPersonal(tfCorreo, lblErrorCorreo)) valido = false;

        if (!Validacion.validarLongitud(pfContrasena, lblErrorContrasena, 5, 50)) valido = false;

        if (!Validacion.validarSeleccion(cbRol, lblErrorRol, "Selecciona un rol")) valido = false;

        return valido;
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNoPersonal.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}