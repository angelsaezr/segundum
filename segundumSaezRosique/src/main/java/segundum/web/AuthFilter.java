package segundum.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		// URLs públicas que no requieren login
		String loginURI = req.getContextPath() + "/segundum/login.xhtml";
		String indexURI = req.getContextPath() + "/index.xhtml";

		boolean loggedIn = (session != null) && (session.getAttribute("usuarioLogueado") != null);
		boolean loginRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(indexURI);

		if (loggedIn || loginRequest) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(indexURI);
		}
	}
}
