package segundum.servicio;

import java.util.List;

import segundum.modelo.Categoria;

public interface IServicioCategorias {

	void cargarJerarquiaCategorias(String ruta);

	void modificarDescripcionCategoria(String idCategoria, String nuevaDescripcion);

	List<Categoria> getCategoriasRaiz();

	List<Categoria> getDescendientesCategoria(String idCategoria);
}
