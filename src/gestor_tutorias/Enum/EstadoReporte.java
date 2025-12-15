package gestor_tutorias.Enum;

public enum EstadoReporte {
    PENDIENTE("Pendiente"),
    REVISADO("Revisado");

    private final String valorBD;

    EstadoReporte(String valorBD) {
        this.valorBD = valorBD;
    }

    public String getValorBD() {
        return valorBD;
    }

    public static EstadoReporte fromString(String text) {
        for (EstadoReporte estado : EstadoReporte.values()) {
            if (estado.valorBD.equalsIgnoreCase(text)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de reporte no válido: " + text);
    }
}
