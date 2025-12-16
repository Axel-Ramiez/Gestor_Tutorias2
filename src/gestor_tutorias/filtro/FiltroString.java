package gestor_tutorias.filtro;

public class FiltroString {

    private String dato;
    private boolean seguro = true;

    public FiltroString(String dato) {
        this.dato = dato;
    }

    // ================================
    // FILTROS STRING
    // ================================

    public void filtrarPhishingString() {
        /*
         Ataque: Phishing.
         Detección básica de URLs sospechosas o intentos de engaño.
        */
        if (dato == null) {
            seguro = false;
            return;
        }

        String texto = dato.toLowerCase();

        if (texto.contains("http://") || texto.contains("https://")) {
            // Presencia de enlaces en campos que no deberían tenerlos
            seguro = false;
        }
    }

    public void filtrarXSSString() {
        /*
         Ataque: Cross-Site Scripting (XSS).
         Detección básica de etiquetas y scripts.
        */
        if (!seguro) {
            return;
        }

        String texto = dato.toLowerCase();

        if (texto.contains("<script") ||
                texto.contains("</script") ||
                texto.contains("<iframe") ||
                texto.contains("javascript:")) {

            seguro = false;
        }
    }

    public void filtrarSpoofingString() {
        /*
         Ataque: Spoofing.
         Uso de caracteres invisibles o confusos.
        */
        if (!seguro) {
            return;
        }

        // Caracteres de control no imprimibles
        for (char c : dato.toCharArray()) {
            if (Character.isISOControl(c) && c != '\n' && c != '\r' && c != '\t') {
                seguro = false;
                break;
            }
        }
    }

    public void filtrarSQLInjectionString() {
        /*
         Ataque: SQL Injection.
         Detección de palabras clave y patrones comunes.
        */
        if (!seguro) {
            return;
        }

        String texto = dato.toLowerCase();

        if (texto.contains("select ") ||
                texto.contains("insert ") ||
                texto.contains("update ") ||
                texto.contains("delete ") ||
                texto.contains("drop ") ||
                texto.contains("--") ||
                texto.contains(";")) {

            seguro = false;
        }
    }

    public void filtrarDivulgacionInformacionString() {
        /*
         Ataque: Divulgación de información.
         Evita exponer datos sensibles.
        */
        if (!seguro) {
            return;
        }

        String texto = dato.toLowerCase();

        if (texto.contains("password") ||
                texto.contains("contraseña") ||
                texto.contains("token") ||
                texto.contains("apikey") ||
                texto.contains("secret")) {

            seguro = false;
        }
    }

    // ================================
    // ESTADO DEL FILTRO
    // ================================

    public boolean esSeguro() {
        return seguro;
    }
}
