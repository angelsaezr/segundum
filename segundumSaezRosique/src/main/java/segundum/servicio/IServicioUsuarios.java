package segundum.servicio;

import java.time.LocalDate;

import segundum.modelo.Usuario;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.RepositorioException;

public interface IServicioUsuarios {
	String altaUsuario(String email, String nombre, String apellidos, String clave, LocalDate fechaNacimiento,
			String telefono) throws RepositorioException;

	void modificarUsuario(String id, String email, String nombre, String apellidos, String clave,
			LocalDate fechaNacimiento, String telefono, boolean administrador)
			throws RepositorioException, EntidadNoEncontrada;

	Usuario login(String email, String clave) throws RepositorioException, EntidadNoEncontrada;
}
