package segundum.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import segundum.modelo.Usuario;
import segundum.servicio.FactoriaServicios;
import segundum.servicio.IServicioUsuarios;

@Named
@SessionScoped
public class LoginUsuarioWeb implements Serializable {
	private static final long serialVersionUID = -3858691394760749923L;
	
	Usuario usuarioLogueado;
	private String email;
	private String clave;

	private IServicioUsuarios servicioUsuarios;

	@Inject
	private FacesContext facesContext;

	public LoginUsuarioWeb() {
		servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
	}
	
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}
	
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
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
		try {
			usuarioLogueado = servicioUsuarios.login(email, clave);

			// Guarda usuario en sesión
			facesContext.getExternalContext().getSessionMap().put("usuarioLogueado", usuarioLogueado);

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Usuario " + usuarioLogueado.getNombre() + " logueado correctamente"));

			facesContext.getExternalContext()
					.redirect(facesContext.getExternalContext().getRequestContextPath() + "/index.xhtml");

		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
	}

	public String logout() {
		usuarioLogueado = null;
		facesContext.getExternalContext().invalidateSession();
		return "/segundum/login.xhtml?faces-redirect=true";
	}

}
