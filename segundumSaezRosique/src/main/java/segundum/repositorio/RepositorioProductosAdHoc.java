package segundum.repositorio;

import java.util.List;

import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;

public interface RepositorioProductosAdHoc extends RepositorioString<Producto> {

	// Buscar productos a la venta con filtros opcionales.
	List<Producto> buscarProductos(String idCategoria, String texto, EstadoProducto estado, Double precioMax)
			throws RepositorioException;
}
