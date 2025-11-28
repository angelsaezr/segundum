package segundum.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
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
import segundum.modelo.Usuario;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;
import segundum.servicio.IServicioCategorias;
import segundum.servicio.IServicioProductos;

@Named
@ViewScoped
public class CrearProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IServicioProductos servicioProductos;

	@Inject
	private IServicioCategorias servicioCategorias;

	@Inject
	private FacesContext facesContext;

	// ---------- Datos del formulario ----------
	private String titulo;
	private String descripcion;
	private Double precio;
	private EstadoProducto estado;
	private boolean envioDisponible;

	private List<Categoria> categoriasRaiz;
	private TreeNode<Categoria> raizCategorias;
	private TreeNode<Categoria> nodoSeleccionado;
	private Categoria categoriaSeleccionada;

	// Lugar de recogida
	private String descripcionRecogida;
	private Double longitud;
	private Double latitud;

	@PostConstruct
	public void init() {
		try {
			categoriasRaiz = servicioCategorias.getCategoriasRaiz();

			raizCategorias = new DefaultTreeNode<Categoria>(null, null);
			raizCategorias.setExpanded(true);

			for (Categoria c : categoriasRaiz) {
				TreeNode<Categoria> nodo = new DefaultTreeNode<>(c, raizCategorias);
				nodo.setExpanded(false);

				// Solo si puede tener hijos, añadimos un hijo vacío para marcarlo como
				// expandible
				if (!c.getSubcategorias().isEmpty()) {
					new DefaultTreeNode<Categoria>(null, nodo); // placeholder
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Carga hijos al expandir */
	@SuppressWarnings("unchecked")
	public void onNodeExpand(NodeExpandEvent event) {
		TreeNode<Categoria> nodo = (TreeNode<Categoria>) event.getTreeNode();
		Categoria categoria = nodo.getData();

		// Si el primer hijo es placeholder, lo limpiamos
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

	// ---------- Acción principal ----------
	public void crearProducto() {
		try {
			Usuario vendedor = facesContext.getExternalContext().getSessionMap()
					.get("usuarioLogueado") instanceof Usuario
							? (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogueado")
							: null;

			if (vendedor == null) {
				mostrarMensaje("Error de sesión", "Debe iniciar sesión antes de crear un producto.");
				return;
			}

			if (titulo == null || titulo.isEmpty() || descripcion == null || descripcion.isEmpty() || precio == null
					|| estado == null || descripcionRecogida == null || descripcionRecogida.isEmpty()
					|| longitud == null || latitud == null || categoriaSeleccionada == null) {
				mostrarMensaje("Datos incompletos", "Por favor, complete todos los campos obligatorios.");
				return;
			}

			String idNuevo = servicioProductos.altaProducto(titulo, descripcion, precio, estado, envioDisponible,
					categoriaSeleccionada.getId(), vendedor.getId(), descripcionRecogida, longitud, latitud);

			mostrarMensaje("Producto creado", "El producto se ha creado con ID: " + idNuevo);
			PrimeFaces.current().ajax().update("formCrearProducto");
			limpiarFormulario();

		} catch (RepositorioException | EntidadNoEncontrada | IllegalArgumentException e) {
			mostrarMensaje("Error al crear producto", e.getMessage());
		}
	}

	// ---------- Métodos auxiliares ----------
	private void limpiarFormulario() {
		titulo = null;
		descripcion = null;
		precio = null;
		estado = null;
		envioDisponible = false;
		nodoSeleccionado = null;
		categoriaSeleccionada = null;
		descripcionRecogida = null;
		longitud = null;
		latitud = null;
	}

	private void mostrarMensaje(String titulo, String detalle) {
		// TODO
	}

	// ---------- Getters y Setters ----------
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public EstadoProducto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}

	public boolean isEnvioDisponible() {
		return envioDisponible;
	}

	public void setEnvioDisponible(boolean envioDisponible) {
		this.envioDisponible = envioDisponible;
	}

	public TreeNode<Categoria> getRaizCategorias() {
		return raizCategorias;
	}

	public TreeNode<Categoria> getNodoSeleccionado() {
		return nodoSeleccionado;
	}

	public void setNodoSeleccionado(TreeNode<Categoria> nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;

		if (nodoSeleccionado != null && nodoSeleccionado.getData() != null) {
			categoriaSeleccionada = nodoSeleccionado.getData();
		} else {
			categoriaSeleccionada = null;
		}
	}

	public Categoria getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public String getDescripcionRecogida() {
		return descripcionRecogida;
	}

	public void setDescripcionRecogida(String descripcionRecogida) {
		this.descripcionRecogida = descripcionRecogida;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public EstadoProducto[] getEstadosProducto() {
		return EstadoProducto.values();
	}

}