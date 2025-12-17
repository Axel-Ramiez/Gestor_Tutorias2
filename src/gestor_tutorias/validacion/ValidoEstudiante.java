package gestor_tutorias.validacion;

import gestor_tutorias.pojo.Estudiante;

import java.util.regex.Pattern;

public class ValidoEstudiante {

    private Estudiante estudiante;
    private boolean valido = true;

    public ValidoEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }


    public void validarEstudiante() {
        validarMatricula();
        validarNombreCompleto();
        validarCorreo();
        validarIdCarrera();
        validarSemestre();
        validarActivo();
        validarRiesgo();
    }


    private void validarMatricula() {
        String matricula = estudiante.getMatricula();

        if (matricula == null || matricula.trim().isEmpty()) {
            valido = false;
            return;
        }

        if (matricula.length() != 10) {
            valido = false;
            return;
        }

        String regex = "^[a-z][A-Z][0-9]{8}$";
        if (!Pattern.matches(regex, matricula)) {
            valido = false;
        }
    }

    private void validarNombreCompleto() {
        String nombre = estudiante.getNombreCompleto();

        if (nombre == null || nombre.trim().isEmpty()) {
            valido = false;
            return;
        }

        if (nombre.length() < 3 || nombre.length() > 150) {
            valido = false;
            return;
        }

        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        if (!Pattern.matches(regex, nombre)) {
            valido = false;
        }
    }

    private void validarCorreo() {
        String correo = estudiante.getCorreo();

        if (correo == null) {
            return;
        }

        if (correo.trim().isEmpty() || correo.length() > 100) {
            valido = false;
            return;
        }

        String regex = "^[a-zA-Z0-9._%+-]+@estudiantes\\.uv\\.mx$";
        if (!Pattern.matches(regex, correo)) {
            valido = false;
        }
    }


    private void validarIdCarrera() {
        int idCarrera = estudiante.getIdCarrera();

        if (idCarrera <= 0) {
            valido = false;
        }
    }


    private void validarSemestre() {
        int semestre = estudiante.getSemestre();

        // Rango realista UV
        if (semestre < 1 || semestre > 12) {
            valido = false;
        }
    }


    private void validarActivo() {
        int activo = estudiante.getActivo();

        if (activo != 0 && activo != 1) {
            valido = false;
        }
    }


    private void validarRiesgo() {
        int riesgo = estudiante.getRiesgo();

        if (riesgo != 0 && riesgo != 1) {
            valido = false;
        }
    }


    public boolean esValido() {
        return valido;
    }
}

