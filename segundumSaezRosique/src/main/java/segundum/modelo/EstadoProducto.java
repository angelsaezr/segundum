package segundum.modelo;

public enum EstadoProducto {
    NUEVO("Nuevo"),
    COMO_NUEVO("Como nuevo"),
    BUEN_ESTADO("Buen estado"),
    ACEPTABLE("Aceptable"),
    PARA_PIEZAS_O_REPARAR("Para piezas o reparar");

    private final String label;

    EstadoProducto(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}