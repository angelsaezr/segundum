package segundum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import segundum.repositorio.Identificable;

@Entity
@Table(name = "lugares_de_recogida")
public class LugarDeRecogida implements Identificable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;

	@Lob
	private String descripcion;

	private double longitud;

	private double latitud;

	// Constructores
	public LugarDeRecogida() {

	}

	public LugarDeRecogida(String descripcion, double longitud, double latitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
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

	// Métodos auxiliares
	@Override
	public String toString() {
		return "LugarDeRecogida{" + "id=" + id + ", descripcion='" + descripcion + '\'' + ", longitud=" + longitud
				+ ", latitud=" + latitud + '}';
	}
}
