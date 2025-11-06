package segundum.servicio.test;

import java.time.LocalDate;

import segundum.servicio.FactoriaServicios;
import segundum.servicio.IServicioCategorias;
import segundum.servicio.IServicioUsuarios;

public class Programa {

	public static void main(String[] args) throws Exception {

		System.out.println("----------- USUARIOS -----------\n");

		IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);

		// Configuración de datos del usuario
		String email = "juan@um.es";
		String nombre = "Juan";
		String apellidos = "Perez Gomez";
		String clave = "1234";
		LocalDate fechaNacimiento = LocalDate.of(2000, 1, 1);

		// Alta del usuario
		System.out.println("Creando usuario...");
		String id = servicioUsuarios.altaUsuario(email, nombre, apellidos, clave, fechaNacimiento, null);
		System.out.println("Usuario creado con id:  " + id);
		System.out
				.println("Datos: " + email + ", " + nombre + ", " + apellidos + ", " + clave + ", " + fechaNacimiento);

		// Modificacion del usuario
		System.out.println("Modificando el correo del usuario...");
		String emailModificado = "juan2@um.es";
		servicioUsuarios.modificarUsuario(id, emailModificado, null, null, null, null, null, false);
		System.out.println("Correo modificado: " + emailModificado);

		System.out.println("\n----------- CATEGORIAS -----------\n");

		IServicioCategorias servicioCategorias = FactoriaServicios.getServicio(IServicioCategorias.class);

		System.out.println("Cargando jerarquia de categorias desde XML Software...");
		servicioCategorias.cargarJerarquiaCategorias("categoriasXML/Software.xml");
		System.out.println("Categorias cargadas.");

		System.out.println("Modificando la descripcion de la categoria con ID 4951...");
		servicioCategorias.modificarDescripcionCategoria("4951", "Nueva descripcion de la categoria 4951");
		System.out.println("Descripcion modificada.");

		System.out.println("Listando todas las categorias raíz:");
		servicioCategorias.getCategoriasRaiz().forEach(c -> {
			System.out.println(" - " + c.getNombre() + " (ID: " + c.getId() + ")");
		});

		System.out.println("Listando los descendientes de la categoria con ID 1279:");
		servicioCategorias.getDescendientesCategoria("1279").forEach(c -> {
			System.out.println(" - " + c.getNombre() + " (ID: " + c.getId() + ")");
		});
		System.out.println(" - Ok no tiene ninguna");

		System.out.println("\n------------ FIN -------------------");

	}

}
