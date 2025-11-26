package segundum.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

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
	private LoginUsuarioWeb loginUsuarioWeb;

	// ---------- Datos del formulario ----------
	private String titulo;
	private String descripcion;
	private double precio;
	private EstadoProducto estado;
	private boolean envioDisponible;

	private String idCategoriaSeleccionada;
	private List<Categoria> categoriasDisponibles;
	private List<Categoria> subcategorias;

	// Lugar de recogida
	private String descripcionRecogida;
	private double longitud;
	private double latitud;

	// ---------- Inicialización ----------
	@PostConstruct
	public void init() {
		try {
			categoriasDisponibles = servicioCategorias.getCategoriasRaiz();
		} catch (RepositorioException | EntidadNoEncontrada e) {
			e.printStackTrace();
		}
	}

	// ---------- Acción principal ----------
	public void crearProducto() {
		try {
			Usuario vendedor = loginUsuarioWeb.getUsuarioLogueado();

			String idNuevo = servicioProductos.altaProducto(titulo, descripcion, precio, estado, envioDisponible,
					idCategoriaSeleccionada, vendedor.getId(), descripcionRecogida, longitud, latitud);

			mostrarMensaje("Producto creado", "El producto se ha creado con ID: " + idNuevo);
			PrimeFaces.current().ajax().update("formCrearProducto");
			limpiarFormulario();

		} catch (RepositorioException | EntidadNoEncontrada | IllegalArgumentException e) {
			mostrarMensaje("Error al crear producto", e.getMessage());
		}
	}

	public void cargarSubcategorias() {
		try {
			if (idCategoriaSeleccionada != null && !idCategoriaSeleccionada.isEmpty()) {
				subcategorias = servicioCategorias.getDescendientesCategoria(idCategoriaSeleccionada);
			}
		} catch (Exception e) {
			subcategorias = null;
		}
	}

	// ---------- Métodos auxiliares ----------
	private void limpiarFormulario() {
		titulo = null;
		descripcion = null;
		precio = 0;
		estado = null;
		envioDisponible = false;
		idCategoriaSeleccionada = null;
		descripcionRecogida = null;
		longitud = 0;
		latitud = 0;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
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

	public String getIdCategoriaSeleccionada() {
		return idCategoriaSeleccionada;
	}

	public void setIdCategoriaSeleccionada(String idCategoriaSeleccionada) {
		this.idCategoriaSeleccionada = idCategoriaSeleccionada;
	}

	public List<Categoria> getCategoriasDisponibles() {
		return categoriasDisponibles;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public String getDescripcionRecogida() {
		return descripcionRecogida;
	}

	public void setDescripcionRecogida(String descripcionRecogida) {
		this.descripcionRecogida = descripcionRecogida;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public EstadoProducto[] getEstadosProducto() {
		return EstadoProducto.values();
	}

}