package gestor_tutorias.controlador;

import gestor_tutorias.controlador.Administrador.FXMLPrincipalAdministradorController;
import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLInicioSesionController implements Initializable {

    // Variables que coinciden con TU FXML
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPassword; // Antes se llamaba tfPassword
    @FXML
    private Label lbErrorUsuario;     // Etiqueta roja debajo del usuario
    @FXML
    private Label lbErrorPassword;    // Etiqueta roja debajo de la contraseña

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Limpiamos los mensajes de error al iniciar
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
    }

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        // 1. Limpiar errores previos
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");

        String matricula = tfUsuario.getText();
        String password = pfPassword.getText();

        boolean isValido = true;

        // 2. Validaciones básicas en la interfaz
        if(matricula.isEmpty()){
            lbErrorUsuario.setText("Campo obligatorio");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("Campo obligatorio");
            isValido = false;
        }

        if(isValido){
            validarCredencialesBD(matricula, password);
        }
    }

    private void validarCredencialesBD(String matricula, String password) {
        try {
            Usuario usuarioSesion = UsuarioDAO.iniciarSesion(matricula, password);

            if(usuarioSesion != null){
                irMenuPrincipal(usuarioSesion);
            } else {
                // Si el usuario no existe, mostramos alerta o mensaje en etiquetas
                mostrarAlerta("Credenciales incorrectas", "El usuario o contraseña no son válidos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "No se pudo conectar con la base de datos.");
        }
    }

    private void irMenuPrincipal(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestor_tutorias/vista/Administrador/FXMLPrincipalAdministrador.fxml"));
            Parent root = loader.load();

            FXMLPrincipalAdministradorController controlador = loader.getController();
            controlador.inicializarInformacion(usuario);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Menú Principal - Sistema de Tutorías");
            escenario.show();

            Stage escenarioActual = (Stage) tfUsuario.getScene().getWindow();
            escenarioActual.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana del menú principal.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}