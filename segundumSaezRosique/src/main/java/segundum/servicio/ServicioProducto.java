package segundum.servicio;

import java.util.List;

import segundum.modelo.Categoria;
import segundum.modelo.EstadoProducto;
import segundum.modelo.LugarDeRecogida;
import segundum.modelo.Producto;
import segundum.modelo.Usuario;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.FactoriaRepositorios;
import segundum.repositorio.Repositorio;
import segundum.repositorio.RepositorioException;

public class ServicioProducto implements IServicioProducto {

	private Repositorio<Usuario, String> repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	private Repositorio<Producto, String> repositorioProductos = FactoriaRepositorios.getRepositorio(Producto.class);

	@Override
	public String altaProducto(String titulo, String descripcion, double precio, EstadoProducto estado,
			boolean envioDisponible, String idCategoria, String idVendedor, String descripcionRecogida, double longitud,
			double latitud) throws RepositorioException, EntidadNoEncontrada {
		Categoria categoria = null; // TODO obtener categoria por idCategoria
		Usuario vendedor = repositorioUsuarios.getById(idVendedor);
		LugarDeRecogida lugarDeRecogida = new LugarDeRecogida(descripcionRecogida, longitud, latitud);
		Producto producto = new Producto(titulo, descripcion, precio, estado, envioDisponible, categoria, vendedor,
				lugarDeRecogida);
		return repositorioProductos.add(producto);
	}

	@Override
	public void modificarDatosProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
			throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub

	}

	@Override
	public void asignarLugarRecogida(String idProducto, double longitud, double latitud, String descripcionLugar)
			throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementarVisualizaciones(String idProducto) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductoResumenMensual> getResumenMensual(String idVendedor)
			throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarProductos(String descripcion, Categoria categoria, EstadoProducto estado,
			Double precioMax) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

}
