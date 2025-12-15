/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gestor_tutorias.controlador;

import gestor_tutorias.dao.UsuarioDAO;
import gestor_tutorias.pojo.Usuario;
import gestor_tutorias.utilidades.Sesion;
import gestor_tutorias.validacion.Validacion; // <--- AQUÍ CAMBIÓ EL IMPORT
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

    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
    }

    @FXML
    private void clicIniciar(ActionEvent event) {
        // 1. Usamos tu clase Validacion (nombre corregido)
        boolean isUsuarioValido = Validacion.validarRequerido(tfUsuario, lbErrorUsuario, "Usuario requerido");
        boolean isPasswordValido = Validacion.validarRequerido(pfPassword, lbErrorPassword, "Contraseña requerida");

        if (isUsuarioValido && isPasswordValido) {
            verificarCredenciales(tfUsuario.getText(), pfPassword.getText());
        }
    }

    private void verificarCredenciales(String matricula, String password) {
        try {
            Usuario usuarioLogin = UsuarioDAO.iniciarSesion(matricula, password);

            if (usuarioLogin != null) {
                Sesion.getInstancia().login(usuarioLogin);
                irPantallaPrincipal(usuarioLogin.getIdRol());
            } else {
                mostrarAlerta("Credenciales incorrectas", "El usuario o contraseña no coinciden.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de conexión", "No se pudo conectar con la base de datos.");
        }
    }

    private void irPantallaPrincipal(int idRol) {
        String ruta = "";

        switch (idRol) {
            case 1: // Administrador
                ruta = "/gestor_tutorias/vista/GestionUsuarios.fxml";
                break;
            case 2: // Coordinador
                ruta = "/gestor_tutorias/vista/GestionUsuarios.fxml";
                break;
            case 3: // Tutor
                ruta = "/gestor_tutorias/vista/MenuTutor.fxml";
                break;
            default:
                mostrarAlerta("Acceso Denegado", "Rol no reconocido.");
                return;
        }

        abrirVentana(ruta);
    }

    private void abrirVentana(String rutaFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sistema de Gestión de Tutorías");
            stage.show();

            Stage ventanaActual = (Stage) tfUsuario.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de navegación", "No se pudo cargar la vista: " + rutaFXML);
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
