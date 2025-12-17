package gestor_tutorias.filtro;

public class FiltroString {

    private String dato;
    private boolean seguro = true;

    public FiltroString(String dato) {
        this.dato = dato;
    }


    public void filtrarPhishingString() {

        if (dato == null) {
            seguro = false;
            return;
        }

        String texto = dato.toLowerCase();

        if (texto.contains("http://") || texto.contains("https://")) {
            seguro = false;
        }
    }

    public void filtrarXSSString() {
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
        if (!seguro) {
            return;
        }

        for (char c : dato.toCharArray()) {
            if (Character.isISOControl(c) && c != '\n' && c != '\r' && c != '\t') {
                seguro = false;
                break;
            }
        }
    }

    public void filtrarSQLInjectionString() {
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

    public boolean esSeguro() {
        return seguro;
    }
}
