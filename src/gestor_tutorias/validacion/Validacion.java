package gestor_tutorias.validacion;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class Validacion {


    private static final String PATRON_MATRICULA = "^zS\\d{8}$";


    private static final String PATRON_CORREO_ESTUDIANTE = "^zS\\d{8}@estudiantes\\.uv\\.mx$";


    private static final String PATRON_CORREO_PERSONAL = "^[a-zA-Z0-9._-]+@uv\\.mx$";


    private static final String PATRON_NOMBRE = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s]+$";

    private static final String PATRON_TELEFONO = "^\\d{10}$";



    public static boolean esNuloOVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esMatriculaValida(String matricula) {
        return matricula != null && Pattern.matches(PATRON_MATRICULA, matricula);
    }

    public static boolean esCorreoEstudianteValido(String correo) {
        return correo != null && Pattern.matches(PATRON_CORREO_ESTUDIANTE, correo);
    }

    public static boolean esCorreoPersonalValido(String correo) {
        return correo != null && Pattern.matches(PATRON_CORREO_PERSONAL, correo);
    }

    public static boolean esNombreValido(String nombre) {
        return nombre != null && Pattern.matches(PATRON_NOMBRE, nombre);
    }

    public static boolean esNumeroEntero(String texto) {
        if (esNuloOVacio(texto)) return false;
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    public static boolean validarRequerido(TextInputControl campo, Label etiquetaError, String mensaje) {
        if (esNuloOVacio(campo.getText())) {
            marcarError(campo, etiquetaError, mensaje);
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }


    public static boolean validarMatricula(TextInputControl campo, Label etiquetaError) {
        if (!esMatriculaValida(campo.getText())) {
            marcarError(campo, etiquetaError, "Formato inválido (Ej: zS12345678)");
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }


    public static boolean validarCorreoEstudiante(TextInputControl campo, Label etiquetaError) {
        if (!esCorreoEstudianteValido(campo.getText())) {
            marcarError(campo, etiquetaError, "Debe ser: zS########@estudiantes.uv.mx");
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }


    public static boolean validarCorreoPersonal(TextInputControl campo, Label etiquetaError) {
        if (!esCorreoPersonalValido(campo.getText())) {
            marcarError(campo, etiquetaError, "Debe ser correo institucional (@uv.mx)");
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }


    public static boolean validarNombre(TextInputControl campo, Label etiquetaError) {
        if (!esNombreValido(campo.getText())) {
            marcarError(campo, etiquetaError, "Solo letras y espacios");
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }


    public static boolean validarSeleccion(ComboBox<?> combo, Label etiquetaError, String mensaje) {
        if (combo.getSelectionModel().getSelectedItem() == null) {
            etiquetaError.setText(mensaje);
            combo.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
            return false;
        } else {
            etiquetaError.setText("");
            combo.setStyle("");
            return true;
        }
    }


    public static boolean validarFecha(DatePicker fecha, Label etiquetaError, String mensaje) {
        if (fecha.getValue() == null) {
            etiquetaError.setText(mensaje);
            fecha.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
            return false;
        } else {
            etiquetaError.setText("");
            fecha.setStyle("");
            return true;
        }
    }


    public static boolean validarLongitud(TextInputControl campo, Label etiquetaError, int min, int max) {
        String texto = campo.getText();
        if (texto == null || texto.length() < min || texto.length() > max) {
            marcarError(campo, etiquetaError, "Debe tener entre " + min + " y " + max + " caracteres");
            return false;
        } else {
            limpiarError(campo, etiquetaError);
            return true;
        }
    }



    private static void marcarError(javafx.scene.control.Control control, Label etiqueta, String mensaje) {
        etiqueta.setText(mensaje);

        control.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
    }

    private static void limpiarError(javafx.scene.control.Control control, Label etiqueta) {
        etiqueta.setText("");
        control.setStyle("");
    }
}
