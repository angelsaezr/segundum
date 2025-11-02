package segundum.servicio.test;

import java.time.LocalDate;

import segundum.servicio.FactoriaServicios;
import segundum.servicio.IServicioUsuarios;

public class Programa {

	public static void main(String[] args) throws Exception {
		IServicioUsuarios usuario = FactoriaServicios.getServicio(IServicioUsuarios.class);

		// Configuración de datos del usuario
		String email = "juan@um.es";
		String nombre = "Juan";
		String apellidos = "Perez Gomez";
		String clave = "1234";
		LocalDate fechaNacimiento = LocalDate.of(2000, 1, 1);

		// Alta del usuario
		System.out.println("CREANDO USUARIO...");
		String id = usuario.altaUsuario(email, nombre, apellidos, clave, fechaNacimiento, null);
		System.out.println("USUARIO CREADO CON ID:  " + id);
		System.out
				.println("DATOS: " + email + ", " + nombre + ", " + apellidos + ", " + clave + ", " + fechaNacimiento);

		// Modificacion del usuario
		System.out.println("MODIFICANDO EL CORREO DEL USUARIO...");
		String emailModificado = "juan2@um.es";
		usuario.modificarUsuario(id, emailModificado, null, null, null, null, null, false);
		System.out.println("CORREO MODIFICADO: " + emailModificado);

		System.out.println("FIN.");

	}

}
