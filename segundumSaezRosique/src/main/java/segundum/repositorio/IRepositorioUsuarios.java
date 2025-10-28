package segundum.repositorio;

import segundum.modelo.Usuario;

public interface IRepositorioUsuarios extends RepositorioString<Usuario> {
	Usuario buscarPorEmail(String email) throws RepositorioException;
}
