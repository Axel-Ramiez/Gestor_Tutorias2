package gestor_tutorias.Enum;

public enum TipoProblematica {
    ACADEMICA("Academica"),
    PERSONAL("Personal"),
    SALUD("Salud"),
    ECONOMICA("Economica");


    private final String valorBD;


    TipoProblematica(String valorBD) {
        this.valorBD = valorBD;
    }


    public String getValorBD() {
        return valorBD;
    }


    public static TipoProblematica fromString(String text) {
        for (TipoProblematica tipo : TipoProblematica.values()) {
            if (tipo.valorBD.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de problemática no válido: " + text);
    }
}