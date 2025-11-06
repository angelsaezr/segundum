package segundum.servicio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import segundum.modelo.Categoria;
import segundum.repositorio.EntidadNoEncontrada;
import segundum.repositorio.FactoriaRepositorios;
import segundum.repositorio.Repositorio;
import segundum.repositorio.RepositorioException;

public class ServicioCategorias implements IServicioCategorias {

	private Repositorio<Categoria, String> repositorioCategorias = FactoriaRepositorios.getRepositorio(Categoria.class);

	@Override
	public void cargarJerarquiaCategorias(String ruta) throws RepositorioException, JAXBException {
		if (ruta == null || ruta.isEmpty())
			throw new IllegalArgumentException("ruta: no debe ser nula ni vacía");

		// Leer XML con JAXB
		File archivo = new File(ruta);
		if (!archivo.exists())
			throw new IllegalArgumentException("El fichero indicado no existe: " + ruta);

		JAXBContext contexto = JAXBContext.newInstance(Categoria.class);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();

		Categoria categoriaRaiz = (Categoria) unmarshaller.unmarshal(archivo);

		// Evitar duplicar categorías raíz
		List<Categoria> existentes = repositorioCategorias.getAll();
		for (Categoria c : existentes) {
			if (c.getNombre().equalsIgnoreCase(categoriaRaiz.getNombre())) {
				return;
			}
		}

		// Guardar toda la jerarquía recursivamente
		guardarJerarquia(categoriaRaiz, null);

	}

	private void guardarJerarquia(Categoria categoria, Categoria padre) throws RepositorioException, JAXBException {

		if (categoria == null)
			throw new IllegalArgumentException("categoria: no debe ser nula");
		if (categoria.getNombre() == null || categoria.getNombre().isEmpty())
			throw new IllegalArgumentException("categoria.nombre: no debe ser nulo ni vacío");
		if (padre != null && (padre.getId() == null || padre.getId().isEmpty()))
			throw new IllegalArgumentException("padre.id: no debe ser nulo ni vacío si padre no es nulo");

		categoria.setCategoriaPadre(padre);
		repositorioCategorias.add(categoria);

		if (categoria.getSubcategorias() != null) {
			for (Categoria sub : categoria.getSubcategorias()) {
				guardarJerarquia(sub, categoria);
			}
		}

	}

	@Override
	public void modificarDescripcionCategoria(String idCategoria, String nuevaDescripcion)
			throws RepositorioException, EntidadNoEncontrada {
		if (idCategoria == null || idCategoria.isEmpty())
			throw new IllegalArgumentException("idCategoria: no debe ser nulo ni vacío");
		if (nuevaDescripcion == null || nuevaDescripcion.isEmpty())
			throw new IllegalArgumentException("nuevaDescripcion: no debe ser nula ni vacía");

		Categoria categoria = repositorioCategorias.getById(idCategoria);
		categoria.setDescripcion(nuevaDescripcion);
		repositorioCategorias.update(categoria);
	}

	@Override
	public List<Categoria> getCategoriasRaiz() throws RepositorioException {
		List<Categoria> todas = repositorioCategorias.getAll();
		List<Categoria> raiz = new ArrayList<>();

		for (Categoria c : todas) {
			if (c.getCategoriaPadre() == null) {
				raiz.add(c);
			}
		}

		return raiz;
	}

	@Override
	public List<Categoria> getDescendientesCategoria(String idCategoria)
			throws RepositorioException, EntidadNoEncontrada {
		if (idCategoria == null || idCategoria.isEmpty())
			throw new IllegalArgumentException("idCategoria: no debe ser nulo ni vacío");

		Categoria categoria = repositorioCategorias.getById(idCategoria);
		List<Categoria> descendientes = new ArrayList<>();
		obtenerDescendientesRecursivos(categoria, descendientes);

		return descendientes;
	}

	private void obtenerDescendientesRecursivos(Categoria categoria, List<Categoria> lista) {
		if (categoria.getSubcategorias() == null)
			return;

		for (Categoria sub : categoria.getSubcategorias()) {
			lista.add(sub);
			obtenerDescendientesRecursivos(sub, lista);
		}
	}
}
