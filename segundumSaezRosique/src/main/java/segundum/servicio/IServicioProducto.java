package segundum.servicio;

import java.util.List;

import segundum.modelo.Categoria;
import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;

public interface IServicioProducto {

	String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
			boolean envioDisponible, String idCategoria, String idVendedor, String descripcionRecogida, double longitud,
			double latitud) throws RepositorioException, EntidadNoEncontrada;

	void modificarDatosProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
			throws RepositorioException, EntidadNoEncontrada;

	void asignarLugarRecogida(String idProducto, double longitud, double latitud, String descripcionLugar)
			throws RepositorioException, EntidadNoEncontrada;

	void incrementarVisualizaciones(String idProducto) throws RepositorioException, EntidadNoEncontrada;

	List<ProductoResumenMensual> getResumenMensual(String idVendedor) throws RepositorioException, EntidadNoEncontrada;

	List<Producto> buscarProductos(String descripcion, Categoria categoria, EstadoProducto estado, Double precioMax)
			throws RepositorioException;
}