package gestor_tutorias.filtro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

public class General {
    public String quitarEspacioBorde(String texto){
        return texto;
    }
    public String quitarEspacioMedio(String texto){
        return texto;
    }
    public String cambiarLetra(String texto){
        return texto;
    }
    public String quitarCaracterIlegible(String texto){
        return texto;
    }
    public String quitarCaracterHtml(String texto){
        return texto;
    }
    public String quitarHtml(String texto){
        return texto;
    }
    public String normalizarRuta(String ruta){

        return ruta;
    }



    public boolean validarDato(String datoString, Object tipoDatoEsperado) {
        String datoLimpio = (datoString != null) ? datoString.trim() : null;

        boolean esValido = false;

        if (tipoDatoEsperado == null || tipoDatoEsperado instanceof String) {
            esValido = validarTipoString(datoLimpio);
        } else if (tipoDatoEsperado instanceof Integer) {
            esValido = validarTipoInt(datoLimpio);
        } else if (tipoDatoEsperado instanceof Double) {
            esValido = validarTipoDouble(datoLimpio);
        } else if (tipoDatoEsperado instanceof Float) {
            esValido = validarTipoFloat(datoLimpio);
        } else if (tipoDatoEsperado instanceof LocalDate) {
            esValido = validarTipoDate(datoLimpio);
        } else if (tipoDatoEsperado instanceof Character) {
            esValido = validarTipoChar(datoLimpio);
        } else if (tipoDatoEsperado instanceof Class && ((Class<?>) tipoDatoEsperado).isEnum()) {
            esValido = validarTipoEnum(datoLimpio, (Class<? extends Enum>) tipoDatoEsperado);
        }

        return esValido;
    }


    public boolean validarTipoInt(String dato) {
        if (!campoVacioInt(dato)) return false;
        if (!puroEspacioInt(dato)) return false;
        if (!datoCompatibleInt(dato)) return false; // Debe ser llamado antes que longitud
        if (!longitudMaximaInt(dato, 10)) return false; // 10 dígitos es un int seguro
        if (!longitudMinimaInt(dato, 1)) return false;
        if (!caracterDifusoInt(dato)) return false;

        try {
            int valor = Integer.parseInt(dato);
            if (!reglaDeNegocioInt(valor)) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean campoVacioInt(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioInt(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaInt(String dato, int longitudMaxima) { return dato.length() <= longitudMaxima; }
    public boolean longitudMinimaInt(String dato, int longitudMinima) { return dato.length() >= longitudMinima; }
    public boolean caracterDifusoInt(String dato) { return dato.matches("^[0-9\\-]+$"); }
    public boolean datoCompatibleInt(String dato) {
        try {
            Integer.parseInt(dato);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean reglaDeNegocioInt(int valor) { return valor >= 0; }


    public boolean validarTipoDouble(String dato) {
        if (!campoVacioDouble(dato)) return false;
        if (!puroEspacioDouble(dato)) return false;
        if (!datoCompatibleDouble(dato)) return false;
        if (!longitudMaximaDouble(dato, 18)) return false;
        if (!longitudMinimaDouble(dato, 1)) return false;
        if (!caracterDifusoDouble(dato)) return false;

        try {
            double valor = Double.parseDouble(dato);
            if (!reglaDeNegocioDouble(valor)) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean campoVacioDouble(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioDouble(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaDouble(String dato, int longitudMaxima) { return dato.length() <= longitudMaxima; }
    public boolean longitudMinimaDouble(String dato, int longitudMinima) { return dato.length() >= longitudMinima; }
    public boolean caracterDifusoDouble(String dato) { return dato.matches("^[0-9\\.\\-]+$"); }
    public boolean datoCompatibleDouble(String dato) {
        try {
            Double.parseDouble(dato);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean reglaDeNegocioDouble(double valor) { return valor >= 0.0; }


    public boolean validarTipoFloat(String dato) {
        if (!campoVacioFloat(dato)) return false;
        if (!puroEspacioFloat(dato)) return false;
        if (!datoCompatibleFloat(dato)) return false;
        if (!longitudMaximaFloat(dato, 15)) return false;
        if (!longitudMinimaFloat(dato, 1)) return false;
        if (!caracterDifusoFloat(dato)) return false;

        try {
            float valor = Float.parseFloat(dato);
            if (!reglaDeNegocioFloat(valor)) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean campoVacioFloat(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioFloat(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaFloat(String dato, int longitudMaxima) { return dato.length() <= longitudMaxima; }
    public boolean longitudMinimaFloat(String dato, int longitudMinima) { return dato.length() >= longitudMinima; }
    public boolean caracterDifusoFloat(String dato) { return dato.matches("^[0-9\\.\\-]+$"); }
    public boolean datoCompatibleFloat(String dato) {
        try {
            Float.parseFloat(dato);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean reglaDeNegocioFloat(float valor) { return valor >= 0.0f; }


    public boolean validarTipoDate(String dato) {
        if (!campoVacioDate(dato)) return false;
        if (!puroEspacioDate(dato)) return false;
        if (!datoCompatibleDate(dato)) return false;
        if (!caracterDifusoDate(dato)) return false;

        try {
            LocalDate fecha = LocalDate.parse(dato, DateTimeFormatter.ISO_LOCAL_DATE);
            if (!reglaDeNegocioDate(fecha)) return false;
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public boolean campoVacioDate(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioDate(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaDate(String dato, int longitudMaxima) { return dato.length() <= 10; }
    public boolean longitudMinimaDate(String dato, int longitudMinima) { return dato.length() >= 10; }
    public boolean caracterDifusoDate(String dato) { return dato.matches("^\\d{4}-\\d{2}-\\d{2}$"); }
    public boolean datoCompatibleDate(String dato) {
        try {
            LocalDate.parse(dato, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public boolean reglaDeNegocioDate(LocalDate fecha) { return !fecha.isAfter(LocalDate.now()); }


    public boolean validarTipoChar(String dato) {
        if (!campoVacioChar(dato)) return false;
        if (!puroEspacioChar(dato)) return false;
        if (!datoCompatibleChar(dato)) return false;
        if (!longitudMaximaChar(dato, 1)) return false;
        if (!longitudMinimaChar(dato, 1)) return false;
        if (!caracterDifusoChar(dato)) return false;

        return true;
    }

    public boolean campoVacioChar(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioChar(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaChar(String dato, int longitudMaxima) { return dato.length() == 1; }
    public boolean longitudMinimaChar(String dato, int longitudMinima) { return dato.length() == 1; }
    public boolean caracterDifusoChar(String dato) { return dato.matches("^\\S$"); }
    public boolean datoCompatibleChar(String dato) { return dato.length() == 1; }


    public boolean validarTipoString(String dato) {
        if (!campoVacioString(dato)) return false;
        if (!puroEspacioString(dato)) return false;
        if (!longitudMaximaString(dato, 255)) return false;
        if (!longitudMinimaString(dato, 1)) return false;
        if (!caracterDifusoString(dato)) return false;
        if (!datoCompatibleString(dato)) return false;
        if (!reglaDeNegocioString(dato)) return false;

        return true;
    }

    public boolean campoVacioString(String dato) { return dato != null && !dato.isEmpty(); }
    public boolean puroEspacioString(String dato) { return !dato.trim().isEmpty(); }
    public boolean longitudMaximaString(String dato, int longitudMaxima) { return dato.length() <= longitudMaxima; }
    public boolean longitudMinimaString(String dato, int longitudMinima) { return dato.length() >= longitudMinima; }
    public boolean caracterDifusoString(String dato) { return !dato.contains("<") && !dato.contains(">"); }
    public boolean datoCompatibleString(String dato) { return true; }
    public boolean reglaDeNegocioString(String dato) { return true; }
    public boolean validacionEspecificaString(String dato) { return true; }


    public boolean validarTipoEnum(String dato, Class<? extends Enum> enumClass) {
        if (!campoVacioString(dato)) return false;
        if (!puroEspacioString(dato)) return false;
        if (!datoCompatibleEnum(dato, enumClass)) return false;

        return true;
    }

    public boolean datoCompatibleEnum(String dato, Class<? extends Enum> enumClass) {
        try {
            Enum.valueOf(enumClass, dato.trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public boolean autenticacionInicioSecion(String matricula, String contrasena) {
        return false;
    }

    public boolean validacionEspecificaUsuario(String matricula, String correo) {
        return false;
    }

    public boolean validacionEspecificaReporteTutoria(int idTutor, int idEstudiante, LocalDate fecha) {
        return false;
    }
}