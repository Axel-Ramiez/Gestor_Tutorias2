package gestor_tutorias.validacion;

import gestor_tutorias.pojo.PlaneacionTutoria;

import java.time.LocalDate;

public class ValidoPlaneacionTutoria {

    private PlaneacionTutoria planeacion;
    private boolean valido = true;

    public ValidoPlaneacionTutoria(PlaneacionTutoria planeacion) {
        this.planeacion = planeacion;
    }

    // ================================
    // VALIDACIÓN GENERAL DEL POJO
    // ================================

    public void validarPlaneacion() {
        validarIdPeriodo();
        validarIdCarrera();
        validarFecha();
        validarNumeroSesion();
        validarTemas();
    }

    // ================================
    // id_periodo (FK)
    // ================================

    private void validarIdPeriodo() {
        if (planeacion.getIdPeriodo() <= 0) {
            valido = false;
        }
    }

    // ================================
    // id_carrera (FK)
    // ================================

    private void validarIdCarrera() {
        if (planeacion.getIdCarrera() <= 0) {
            valido = false;
        }
    }

    // ================================
    // fecha (date NOT NULL)
    // ================================

    private void validarFecha() {
        LocalDate fecha = planeacion.getFechaCierreReportes();

        if (fecha == null) {
            valido = false;
            return;
        }

        // No permitir fechas absurdas
        if (fecha.isBefore(LocalDate.of(2000, 1, 1))) {
            valido = false;
        }
    }

    // ================================
    // numero_sesion (1, 2, 3)
    // ================================

    private void validarNumeroSesion() {
        int sesion = planeacion.getNumeroSesion();

        if (sesion < 1 || sesion > 3) {
            valido = false;
        }
    }

    // ================================
    // temas (text NOT NULL)
    // ================================

    private void validarTemas() {
        String temas = planeacion.getTemas();

        if (temas == null || temas.trim().isEmpty()) {
            valido = false;
            return;
        }

        // Longitud razonable (TEXT, pero no infinito en UI)
        if (temas.length() > 2000) {
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
