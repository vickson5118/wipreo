package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.CompteActivationValidation;

@WebServlet(urlPatterns = "/activation/*")
public class CompteActivation extends HttpServlet {

	private final String GET = "/WEB-INF/components/compte/activate.jsp";

	private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final CompteActivationValidation validation = new CompteActivationValidation(this.utilisateurDao);
		final Utilisateur utilisateur = validation.validCompteActivation(request);

		if (utilisateur == null) {
			response.sendRedirect("/");;
			return;
		} else {

			final HttpSession session = request.getSession();
			session.setAttribute(Inscription.ATT_SESSION_UTILISATEUR, utilisateur);

			this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
		}

	}

}
