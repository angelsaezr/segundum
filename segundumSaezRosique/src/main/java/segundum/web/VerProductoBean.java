package segundum.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import segundum.modelo.Producto;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;
import segundum.servicio.IServicioProductos;

@Named
@ViewScoped
public class VerProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IServicioProductos servicioProductos;

	private Producto producto;

	@PostConstruct
	public void init() {
		producto = (Producto) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("productoDetalle");

		if (producto != null) {
			try {
				servicioProductos.incrementarVisualizaciones(producto.getId());
				producto.incrementarVisualizaciones(); // Actualiza en UI sin reload
			} catch (RepositorioException | EntidadNoEncontrada e) {
				e.printStackTrace();
			}
		}
	}

	public Producto getProducto() {
		return producto;
	}
}
