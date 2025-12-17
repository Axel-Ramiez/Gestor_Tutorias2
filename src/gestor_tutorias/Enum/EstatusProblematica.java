package gestor_tutorias.Enum;

public enum EstatusProblematica {
    PENDIENTE("Pendiente"),
    ATENDIDA("Atendida");

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
        return PENDIENTE;
    }
}