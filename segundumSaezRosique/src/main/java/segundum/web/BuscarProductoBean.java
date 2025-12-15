package segundum.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import segundum.modelo.Categoria;
import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;
import segundum.repositorio.RepositorioException;
import segundum.servicio.IServicioCategorias;
import segundum.servicio.IServicioProductos;

@Named
@ViewScoped
public class BuscarProductoBean implements Serializable {

	private static final long serialVersionUID = 4698737371443778042L;

	@Inject
	private IServicioProductos servicioProductos;

	@Inject
	private IServicioCategorias servicioCategorias;

	@Inject
	private FacesContext facesContext;

	// ------- Filtros -------
	private String descripcion;
	private Double precioMax;
	private EstadoProducto estado;

	// ------- Categorías -------
	private List<Categoria> categoriasRaiz;
	private TreeNode<Categoria> raizCategorias;
	private TreeNode<Categoria> nodoSeleccionado;
	private Categoria categoriaSeleccionada;

	// ------- Resultados -------
	private List<Producto> resultados;

	@PostConstruct
	public void init() {
		try {
			categoriasRaiz = servicioCategorias.getCategoriasRaiz();

			raizCategorias = new DefaultTreeNode<Categoria>(null, null);
			raizCategorias.setExpanded(true);

			for (Categoria c : categoriasRaiz) {
				TreeNode<Categoria> nodo = new DefaultTreeNode<>(c, raizCategorias);
				nodo.setExpanded(false);

				if (!c.getSubcategorias().isEmpty()) {
					new DefaultTreeNode<Categoria>(null, nodo); // placeholder
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Carga hijos al expandir **/
	@SuppressWarnings("unchecked")
	public void onNodeExpand(NodeExpandEvent event) {
		TreeNode<Categoria> nodo = (TreeNode<Categoria>) event.getTreeNode();
		Categoria categoria = nodo.getData();

		if (!nodo.getChildren().isEmpty() && nodo.getChildren().get(0).getData() == null) {
			nodo.getChildren().clear();
		} else if (!nodo.getChildren().isEmpty()) {
			return;
		}

		List<Categoria> subcategorias = categoria.getSubcategorias();

		for (Categoria sub : subcategorias) {
			TreeNode<Categoria> hijo = new DefaultTreeNode<>(sub, nodo);

			if (!sub.getSubcategorias().isEmpty()) {
				new DefaultTreeNode<Categoria>(null, hijo);
			}
		}
	}

	public void onNodeSelect() {
		if (nodoSeleccionado != null && nodoSeleccionado.getData() != null) {
			categoriaSeleccionada = nodoSeleccionado.getData();
		}
	}

	// ------- Acción principal -------
	public void buscar() {
		try {
			String idCategoria = (categoriaSeleccionada != null) ? categoriaSeleccionada.getId() : null;

			resultados = servicioProductos.buscarProductos(descripcion, idCategoria, estado, precioMax);

			if (resultados == null || resultados.isEmpty()) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin resultados",
						"No se han encontrado productos con esos criterios."));
			}

			PrimeFaces.current().ajax().update("resultados");

		} catch (RepositorioException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	// ------- Getters y Setters -------

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(Double precioMax) {
		this.precioMax = precioMax;
	}

	public EstadoProducto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}

	public EstadoProducto[] getEstadosProducto() {
		return EstadoProducto.values();
	}

	public TreeNode<Categoria> getRaizCategorias() {
		return raizCategorias;
	}

	public TreeNode<Categoria> getNodoSeleccionado() {
		return nodoSeleccionado;
	}

	public void setNodoSeleccionado(TreeNode<Categoria> nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;
		if (nodoSeleccionado != null && nodoSeleccionado.getData() != null)
			categoriaSeleccionada = nodoSeleccionado.getData();
		else
			categoriaSeleccionada = null;
	}

	public Categoria getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public List<Producto> getResultados() {
		return resultados;
	}

}
