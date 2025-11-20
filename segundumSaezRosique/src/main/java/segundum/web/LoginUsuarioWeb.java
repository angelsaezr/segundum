package segundum.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import segundum.modelo.Usuario;
import segundum.servicio.FactoriaServicios;
import segundum.servicio.IServicioUsuarios;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LoginUsuarioWeb implements Serializable {

	private String email;
	private String clave;

	private IServicioUsuarios servicioUsuarios;

	@Inject
	private FacesContext facesContext;

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

	public void login() {
		// TODO comprobación de campos
		try {
			Usuario usuario = servicioUsuarios.login(email, clave);

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Usuario " + usuario.getNombre() + " logeado correctamente"));
		} catch (

		Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}

	}

}
