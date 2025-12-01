package segundum.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import segundum.modelo.Producto;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;
import segundum.servicio.IServicioProductos;

@Named
@ViewScoped
public class EditarProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IServicioProductos servicioProductos;

	@Inject
	private FacesContext facesContext;

	private Producto producto;
	private Double nuevoPrecio;
	private String nuevaDescripcion;

	@PostConstruct
	public void init() {
		producto = (Producto) facesContext.getExternalContext().getSessionMap().get("productoEditar");
		if (producto != null) {
			nuevoPrecio = producto.getPrecio();
			nuevaDescripcion = producto.getDescripcion();
		}
	}

	public void guardarCambios() {
		try {
			servicioProductos.modificarDatosProducto(producto.getId(), nuevoPrecio, nuevaDescripcion);

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambios guardados",
					"El producto ha sido actualizado."));
			PrimeFaces.current().ajax().update("formEditar:msgs");

		} catch (RepositorioException | EntidadNoEncontrada | IllegalArgumentException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Producto getProducto() {
		return producto;
	}

	public Double getNuevoPrecio() {
		return nuevoPrecio;
	}

	public void setNuevoPrecio(Double nuevoPrecio) {
		this.nuevoPrecio = nuevoPrecio;
	}

	public String getNuevaDescripcion() {
		return nuevaDescripcion;
	}

	public void setNuevaDescripcion(String nuevaDescripcion) {
		this.nuevaDescripcion = nuevaDescripcion;
	}
}
