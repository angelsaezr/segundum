package segundum.servicio;

import java.util.List;

import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;

public interface IServicioProducto {

    String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
            boolean envioDisponible, String idCategoria, String idVendedor) throws RepositorioException;

    void modificarDatosProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
            throws RepositorioException, EntidadNoEncontrada;

    void asignarLugarRecogida(String idProducto, double longitud, double latitud, String descripcionLugar)
            throws RepositorioException, EntidadNoEncontrada;

    void incrementarVisualizaciones(String idProducto) throws RepositorioException, EntidadNoEncontrada;

    List<Producto> buscarProductos(String idCategoria, String texto, EstadoProducto estado, Double precioMax)
            throws RepositorioException;

    List<ProductoResumenMensual> getResumenMensual(String idVendedor, int mes, int anio)
            throws RepositorioException, EntidadNoEncontrada;
}