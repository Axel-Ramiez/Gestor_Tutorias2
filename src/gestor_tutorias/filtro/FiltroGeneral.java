package gestor_tutorias.filtro;/*package gestor_tutorias.filtro;

public class FiltroGeneral {

    private Object dato;
    private boolean seguro = true;

    public FiltroGeneral(Object dato) {
        this.dato = dato;
    }

    public void filtrarVacio() {
        if (dato == null) {
            seguro = false;
        }
    }

    public void filtrarDenialOfService() {
        if (dato instanceof String) {
            String valor = (String) dato;
            if (valor.length() > 5000) { // límite defensivo
                seguro = false;
            }
        }
    }

    public void filtrarZeroDay() {
        if (!seguro) {
            return;
        }
    }

    public void aplicarFiltroPorTipo() {
        if (!seguro) {
            return;
        }

        if (dato instanceof String) {
            aplicarFiltroString((String) dato);
        }
    }

    private void aplicarFiltroString(String valor) {
        FiltroString filtroString = new FiltroString(valor);

        filtroString.filtrarPhishingString();
        filtroString.filtrarXSSString();
        filtroString.filtrarSpoofingString();
        filtroString.filtrarSQLInjectionString();
        filtroString.filtrarDivulgacionInformacionString();
    }

    public boolean esSeguro() {
        return seguro;
    }
}


 */