package segundum.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import segundum.modelo.Producto;
import segundum.modelo.Usuario;
import segundum.servicio.IServicioProductos;

@Named
@ViewScoped
public class MisProductosBean implements Serializable {
	private static final long serialVersionUID = -5124141022361969440L;

	@Inject
	private IServicioProductos servicioProductos;

	private List<Producto> misProductos;

	private Producto productoEditar;

	@PostConstruct
	public void init() {
		try {
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuarioLogueado");

			if (usuario != null) {
				misProductos = servicioProductos.getProductosDeVendedor(usuario.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Producto> getMisProductos() {
		return misProductos;
	}

	public Producto getProductoEditar() {
		return productoEditar;
	}

	public void setProductoEditar(Producto productoEditar) {
		this.productoEditar = productoEditar;
	}

	public void editarProducto() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productoEditar", productoEditar);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editarProducto.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
