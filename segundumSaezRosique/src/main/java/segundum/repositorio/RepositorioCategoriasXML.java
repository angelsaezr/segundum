package segundum.repositorio;

import segundum.modelo.Categoria;

public class RepositorioCategoriasXML extends RepositorioXML<Categoria> {

	@Override
	public Class<Categoria> getClase() {
		return Categoria.class;
	}

}
