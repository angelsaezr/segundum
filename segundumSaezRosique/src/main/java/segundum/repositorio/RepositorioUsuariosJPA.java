package segundum.repositorio;

import segundum.modelo.Usuario;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> implements IRepositorioUsuarios {

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}

	@Override
	public Usuario buscarPorEmail(String email) throws RepositorioException {
		return null;
	}
}
