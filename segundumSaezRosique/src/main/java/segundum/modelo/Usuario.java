package segundum.modelo;

import java.time.LocalDate;

public class Usuario {

	private String id;

	private String email;

	private String nombre;

	private String apellidos;

	private String clave;

	private LocalDate fechaNacimiento;

	private String telefono;

	private boolean administrador;

	// Constructores
	public Usuario() {

	}

	public Usuario(String id, String email, String nombre, String apellidos, String clave, LocalDate fechaNacimiento,
			String telefono, boolean administrador) {
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.administrador = administrador;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	// Métodos auxiliares
	@Override
	public String toString() {
		return "Usuario{" + "id='" + id + '\'' + ", email='" + email + '\'' + ", nombre='" + nombre + '\''
				+ ", apellidos='" + apellidos + '\'' + ", fechaNacimiento=" + fechaNacimiento + ", telefono='"
				+ telefono + '\'' + ", administrador=" + administrador + '}';
	}
}
