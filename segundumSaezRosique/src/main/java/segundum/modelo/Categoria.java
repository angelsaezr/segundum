package segundum.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import segundum.repositorio.Identificable;

@Entity
@Table(name = "categorias")
public class Categoria implements Identificable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;

	private String nombre;

	private String descripcion;

	private String ruta;

	private List<Categoria> subcategorias;

	private Categoria categoriaPadre;

	// Constructores
	public Categoria() {

	}

	public Categoria(String id, String nombre, String descripcion, String ruta) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
		this.subcategorias = new LinkedList<Categoria>();
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Categoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public Categoria getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(Categoria categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	// Métodos auxiliares
	public void addSubcategoria(Categoria subcategoria) {
		subcategorias.add(subcategoria);
		subcategoria.setCategoriaPadre(this);
	}

	public void removeSubcategoria(Categoria subcategoria) {
		subcategorias.remove(subcategoria);
		subcategoria.setCategoriaPadre(null);
	}

	@Override
	public String toString() {
		return "Categoria{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + ", descripcion='" + descripcion + '\''
				+ ", ruta='" + ruta + '\'' + ", subcategorias=" + subcategorias.size() + '}';
	}
}
