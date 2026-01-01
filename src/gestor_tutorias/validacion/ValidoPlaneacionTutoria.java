package gestor_tutorias.validacion;/*package gestor_tutorias.validacion;

import gestor_tutorias.pojo.PlaneacionTutoria;

import java.time.LocalDate;

public class ValidoPlaneacionTutoria {

    private PlaneacionTutoria planeacion;
    private boolean valido = true;

    public ValidoPlaneacionTutoria(PlaneacionTutoria planeacion) {
        this.planeacion = planeacion;
    }


    public void validarPlaneacion() {
        validarIdPeriodo();
        validarIdCarrera();
        validarFecha();
        validarNumeroSesion();
        validarTemas();
    }


    private void validarIdPeriodo() {
        if (planeacion.getIdPeriodo() <= 0) {
            valido = false;
        }
    }


    private void validarIdCarrera() {
        if (planeacion.getIdCarrera() <= 0) {
            valido = false;
        }
    }


    private void validarFecha() {
        LocalDate fecha = planeacion.getFechaCierreReportes();

        if (fecha == null) {
            valido = false;
            return;
        }

        if (fecha.isBefore(LocalDate.of(2000, 1, 1))) {
            valido = false;
        }
    }


    private void validarNumeroSesion() {
        int sesion = planeacion.getNumeroSesion();

        if (sesion < 1 || sesion > 3) {
            valido = false;
        }
    }


    private void validarTemas() {
        String temas = planeacion.getTemas();

        if (temas == null || temas.trim().isEmpty()) {
            valido = false;
            return;
        }

        if (temas.length() > 2000) {
            valido = false;
        }
    }


    public boolean esValido() {
        return valido;
    }
}


 */