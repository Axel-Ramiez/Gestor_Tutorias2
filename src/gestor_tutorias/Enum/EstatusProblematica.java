package gestor_tutorias.Enum;

public enum EstatusProblematica {
    ABIERTA("Abierta"),
    EN_SEGUIMIENTO("En Seguimiento"),
    CERRADA("Cerrada");

    private final String valorBD;

    EstatusProblematica(String valorBD) {
        this.valorBD = valorBD;
    }

    public String getValorBD() {
        return valorBD;
    }

    public static EstatusProblematica fromString(String text) {
        for (EstatusProblematica estatus : EstatusProblematica.values()) {
            if (estatus.valorBD.equalsIgnoreCase(text)) {
                return estatus;
            }
        }
        throw new IllegalArgumentException("Estatus no válido: " + text);
    }
}
