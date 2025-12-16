package gestor_tutorias.validacion;

import gestor_tutorias.pojo.Estudiante;

import java.util.regex.Pattern;

public class ValidoEstudiante {

    private Estudiante estudiante;
    private boolean valido = true;

    public ValidoEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // ================================
    // VALIDACIÓN GENERAL DEL POJO
    // ================================

    public void validarEstudiante() {
        validarMatricula();
        validarNombreCompleto();
        validarCorreo();
        validarIdCarrera();
        validarSemestre();
        validarActivo();
        validarRiesgo();
    }

    // ================================
    // MATRÍCULA
    // ================================

    private void validarMatricula() {
        String matricula = estudiante.getMatricula();

        if (matricula == null || matricula.trim().isEmpty()) {
            valido = false;
            return;
        }

        // Longitud exacta
        if (matricula.length() != 10) {
            valido = false;
            return;
        }

        // Patrón institucional: zS20015001
        String regex = "^[a-z][A-Z][0-9]{8}$";
        if (!Pattern.matches(regex, matricula)) {
            valido = false;
        }
    }

    // ================================
    // NOMBRE COMPLETO
    // ================================

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

        // Solo letras y espacios (incluye acentos)
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        if (!Pattern.matches(regex, nombre)) {
            valido = false;
        }
    }

    // ================================
    // CORREO
    // ================================

    private void validarCorreo() {
        String correo = estudiante.getCorreo();

        // Puede ser null (según BD)
        if (correo == null) {
            return;
        }

        if (correo.trim().isEmpty() || correo.length() > 100) {
            valido = false;
            return;
        }

        // Dominio institucional obligatorio
        String regex = "^[a-zA-Z0-9._%+-]+@estudiantes\\.uv\\.mx$";
        if (!Pattern.matches(regex, correo)) {
            valido = false;
        }
    }

    // ================================
    // ID CARRERA
    // ================================

    private void validarIdCarrera() {
        int idCarrera = estudiante.getIdCarrera();

        if (idCarrera <= 0) {
            valido = false;
        }
    }

    // ================================
    // SEMESTRE
    // ================================

    private void validarSemestre() {
        int semestre = estudiante.getSemestre();

        // Rango realista UV
        if (semestre < 1 || semestre > 12) {
            valido = false;
        }
    }

    // ================================
    // ACTIVO
    // ================================

    private void validarActivo() {
        int activo = estudiante.getActivo();

        if (activo != 0 && activo != 1) {
            valido = false;
        }
    }

    // ================================
    // RIESGO
    // ================================

    private void validarRiesgo() {
        int riesgo = estudiante.getRiesgo();

        if (riesgo != 0 && riesgo != 1) {
            valido = false;
        }
    }

    // ================================
    // ESTADO FINAL
    // ================================

    public boolean esValido() {
        return valido;
    }
}

