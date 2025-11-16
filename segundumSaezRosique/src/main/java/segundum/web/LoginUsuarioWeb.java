package segundum.web;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import segundum.servicio.FactoriaServicios;
import segundum.servicio.IServicioUsuarios;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LoginUsuarioWeb implements Serializable {

	private String email;
	private String clave;

	private IServicioUsuarios servicioUsuarios;

	public LoginUsuarioWeb() {
		servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String login() {
		return "/segundum/home?faces-redirect=true";

	}

}
