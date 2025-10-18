package segundum.modelo;

public class LugarDeRecogida {

	private String id;

	private String descripcion;

	private double longitud;

	private double latitud;

	private Producto producto;

	// Constructores
	public LugarDeRecogida() {

	}

	public LugarDeRecogida(String id, String descripcion, double longitud, double latitud, Producto producto) {
		this.id = id;
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
		this.producto = producto;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	// Métodos auxiliares
	@Override
	public String toString() {
		return "LugarDeRecogida{" + "id=" + id + ", descripcion='" + descripcion + '\'' + ", longitud=" + longitud
				+ ", latitud=" + latitud + '}';
	}
}
