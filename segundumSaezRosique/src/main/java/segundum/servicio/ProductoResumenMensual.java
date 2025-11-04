package segundum.servicio;

public class ProductoResumenMensual {

    private String idProducto;
    private String titulo;
    private int visualizaciones;

    public ProductoResumenMensual() {
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getVisualizaciones() {
        return visualizaciones;
    }

    public void setVisualizaciones(int visualizaciones) {
        this.visualizaciones = visualizaciones;
    }

    @Override
    public String toString() {
        return "ProductoResumenMensual [idProducto=" + idProducto + ", titulo=" + titulo + ", visualizaciones="
                + visualizaciones + "]";
    }
}
