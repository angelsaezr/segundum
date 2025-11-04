package segundum.repositorio;

import segundum.modelo.Usuario;

public class RepositorioUsuariosAdHocJPA extends RepositorioJPA<Usuario> implements RepositorioUsuariosAdHoc {

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}

	@Override
	public Usuario buscarPorEmail(String email) throws RepositorioException {
		return null;
	}
}
