package gestor_tutorias.filtro;

public class FiltroGeneral {

    private Object dato;
    private boolean seguro = true;

    public FiltroGeneral(Object dato) {
        this.dato = dato;
    }

    // ================================
    // FILTROS GENERALES (TODOS LOS TIPOS)
    // ================================

    public void filtrarVacio() {
        if (dato == null) {
            seguro = false;
        }
    }

    public void filtrarDenialOfService() {
        /*
         Ataque: Denial of Service (DoS).
         En frontend: se mitiga evitando datos excesivamente grandes.
        */
        if (dato instanceof String) {
            String valor = (String) dato;
            if (valor.length() > 5000) { // límite defensivo
                seguro = false;
            }
        }
    }

    public void filtrarZeroDay() {
        /*
         Zero-day:
         Defensa genérica: si el sistema ya marcó el dato como inseguro,
         se corta el flujo y no se sigue procesando.
        */
        if (!seguro) {
            return;
        }
    }

    // ================================
    // DECISIÓN POR TIPO
    // ================================

    public void aplicarFiltroPorTipo() {
        if (!seguro) {
            return;
        }

        if (dato instanceof String) {
            aplicarFiltroString((String) dato);
        }
    }

    // ================================
    // DELEGACIÓN A FILTRO STRING
    // ================================

    private void aplicarFiltroString(String valor) {
        FiltroString filtroString = new FiltroString(valor);

        filtroString.filtrarPhishingString();
        filtroString.filtrarXSSString();
        filtroString.filtrarSpoofingString();
        filtroString.filtrarSQLInjectionString();
        filtroString.filtrarDivulgacionInformacionString();
    }

    // ================================
    // ESTADO DEL FILTRO
    // ================================

    public boolean esSeguro() {
        return seguro;
    }
}
