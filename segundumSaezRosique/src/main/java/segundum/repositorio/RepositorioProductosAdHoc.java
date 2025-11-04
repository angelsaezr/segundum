package segundum.repositorio;

import java.util.List;

import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;

public interface RepositorioProductosAdHoc extends RepositorioString<Producto> {

	// Buscar productos a la venta con filtros opcionales.
	List<Producto> buscarProductos(String idCategoria, String texto, EstadoProducto estado, Double precioMax)
			throws RepositorioException;

	// Incrementar visualizaciones de un producto.
	void incrementarVisualizaciones(String idProducto) throws RepositorioException, EntidadNoEncontrada;

	// Modificar precio y descripción de un producto.
	void modificarDatosProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
			throws RepositorioException, EntidadNoEncontrada;

	// Asignar lugar de recogida a un producto.
	void asignarLugarRecogida(String idProducto, double longitud, double latitud, String descripcionLugar)
			throws RepositorioException, EntidadNoEncontrada;

	// TODO Falta un método para el resumen mensual de productos a la venta por
	// usuario, para un mes/año concretos.
}
