package gestor_tutorias.validacion;/*package gestor_tutorias.validacion;

import gestor_tutorias.Enum.EstatusProblematica;

public class ValidoProblematica {

    private boolean valido = true;



    public boolean validarTodo(
            String idReporte,
            String titulo,
            String descripcion,
            String idExperienciaEducativa,
            EstatusProblematica estado
    ) {
        validarIdReporte(idReporte);
        validarTitulo(titulo);
        validarDescripcion(descripcion);
        validarIdExperienciaEducativa(idExperienciaEducativa);
        validarEstado(estado);

        return valido;
    }



    public void validarIdReporte(String valor) {
        if (!valido) return;

        if (valor == null || valor.trim().isEmpty()) {
            valido = false;
            return;
        }

        try {
            int id = Integer.parseInt(valor);
            if (id <= 0) {
                valido = false;
            }
        } catch (NumberFormatException e) {
            valido = false;
        }
    }

    public void validarTitulo(String valor) {
        if (!valido) return;

        if (valor == null || valor.trim().isEmpty()) {
            valido = false;
            return;
        }

        if (valor.length() < 3 || valor.length() > 100) {
            valido = false;
        }
    }

    public void validarDescripcion(String valor) {
        if (!valido) return;

        if (valor == null || valor.trim().isEmpty()) {
            valido = false;
            return;
        }

        if (valor.length() > 500) {
            valido = false;
        }
    }

    public void validarIdExperienciaEducativa(String valor) {
        if (!valido) return;

        if (valor == null || valor.trim().isEmpty()) {
            return; // opcional → permitido null
        }

        try {
            int id = Integer.parseInt(valor);
            if (id <= 0) {
                valido = false;
            }
        } catch (NumberFormatException e) {
            valido = false;
        }
    }

    public void validarEstado(EstatusProblematica estado) {
        if (!valido) return;

        if (estado == null) {
            valido = false;
        }
    }



    public boolean esValido() {
        return valido;
    }
}


 */