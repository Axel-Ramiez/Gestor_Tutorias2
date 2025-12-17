package gestor_tutorias.validacion;

import java.sql.Time;
import java.time.LocalDate;

public class Valido {

    private Object dato;
    private boolean valido = true;

    public Valido(Object dato) {
        this.dato = dato;
    }


    public void validacionGeneral() {
        validarNoNulo();
        validarRangoGeneral();
    }

    public void validarNoNulo() {
        if (dato == null) {
            valido = false;
        }
    }

    public void validarRangoGeneral() {

        if (!valido) {
            return;
        }

        if (dato instanceof String && ((String) dato).trim().isEmpty()) {
            valido = false;
        }
    }

    public void aplicarValidacionPorTipo() {
        if (!valido) {
            return;
        }

        if (dato instanceof String) {
            validacionTipoString((String) dato);
        } else if (dato instanceof Integer) {
            validacionTipoInt((Integer) dato);
        } else if (dato instanceof Float || dato instanceof Double) {
            validacionTipoDecimal((Number) dato);
        } else if (dato instanceof Boolean) {
            validacionTipoBoolean((Boolean) dato);
        } else if (dato instanceof Enum<?>) {
            validacionTipoEnum((Enum<?>) dato);
        } else if (dato instanceof LocalDate) {
            validacionTipoFecha((LocalDate) dato);
        } else if (dato instanceof Time) {
            validacionTipoHora((Time) dato);
        }
    }


    private void validacionTipoString(String valor) {
        validarStringNoVacio(valor);
        validarStringLongitudMinima(valor);
        validarStringLongitudMaxima(valor);
        validarStringFormato(valor);
    }

    public void validarStringLongitudMinima(String valor) {
        if (!valido) return;

        if (valor.length() < 1) {
            valido = false;
        }
    }

    public void validarStringLongitudMaxima(String valor) {
        if (!valido) return;

        if (valor.length() > 255) {
            valido = false;
        }
    }

    public void validarStringFormato(String valor) {
        if (!valido) return;


        for (char c : valor.toCharArray()) {
            if (Character.isISOControl(c)) {
                valido = false;
                break;
            }
        }
    }

    public void validarStringNoVacio(String valor) {
        if (valor.trim().isEmpty()) {
            valido = false;
        }
    }


    private void validacionTipoInt(Integer valor) {
        validarNumeroMinimo(valor);
        validarNumeroMaximo(valor);
        validarNumeroRango(valor);
    }

    public void validarNumeroMinimo(Integer valor) {
        if (valor < 0) {
            valido = false;
        }
    }

    public void validarNumeroMaximo(Integer valor) {
        if (valor > Integer.MAX_VALUE) {
            valido = false;
        }
    }

    public void validarNumeroRango(Integer valor) {

        if (valor < -1_000_000 || valor > 1_000_000) {
            valido = false;
        }
    }


    private void validacionTipoDecimal(Number valor) {
        validarDecimalMinimo(valor);
        validarDecimalMaximo(valor);
        validarDecimalRango(valor);
    }

    public void validarDecimalMinimo(Number valor) {
        if (valor.doubleValue() < 0.0) {
            valido = false;
        }
    }

    public void validarDecimalMaximo(Number valor) {
        if (valor.doubleValue() > 1_000_000.0) {
            valido = false;
        }
    }

    public void validarDecimalRango(Number valor) {
        if (Double.isNaN(valor.doubleValue()) || Double.isInfinite(valor.doubleValue())) {
            valido = false;
        }
    }


    private void validacionTipoBoolean(Boolean valor) {
        validarBooleanPermitido(valor);
    }

    public void validarBooleanPermitido(Boolean valor) {
        if (valor == null) {
            valido = false;
        }
    }


    private void validacionTipoEnum(Enum<?> valor) {
        validarEnumPermitido(valor);
    }

    public void validarEnumPermitido(Enum<?> valor) {
        if (valor.name() == null || valor.name().isEmpty()) {
            valido = false;
        }
    }


    private void validacionTipoFecha(LocalDate fecha) {
        validarFechaNoFutura(fecha);
        validarFechaRango(fecha);
    }

    public void validarFechaNoFutura(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())) {
            valido = false;
        }
    }

    public void validarFechaRango(LocalDate fecha) {
        if (fecha.isBefore(LocalDate.of(1900, 1, 1))) {
            valido = false;
        }
    }


    private void validacionTipoHora(Time hora) {
        validarHoraInicio(hora);
        validarHoraFin(hora);
        validarRangoHorario(hora);
    }

    public void validarHoraInicio(Time hora) {
        if (hora == null) {
            valido = false;
        }
    }

    public void validarHoraFin(Time hora) {
        if (hora == null) {
            valido = false;
        }
    }

    public void validarRangoHorario(Time hora) {

        if (hora.getTime() < 0) {
            valido = false;
        }
    }


    public boolean esValido() {
        return valido;
    }
}

