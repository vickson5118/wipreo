package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Objectif;
import com.wipreo.dao.ObjectifDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.InscriptionValidation;

@WebServlet(urlPatterns = "/compte/preferences")
public class Preferences extends HttpServlet {

	// private final String ATT_UTILISATEUR = "utilisateur";
	private final String GET = "/WEB-INF/components/compte/preferences.jsp";
	private final String ATT_LISTE_OBJECTIF = "listeObjectif";
	private ObjectifDao objectifDao = null;
	// private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.objectifDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getObjectifDao();
		/*
		 * this.utilisateurDao = ((DaoFactory)
		 * this.getServletContext().getAttribute(Constants.ATT_FACTORY))
		 * .getUtilisateurDao();
		 */
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final String email = (String) request.getSession().getAttribute(InscriptionValidation.ATT_UTILISATEUR_EMAIL);

		if (email == null || email.trim().length() == 0) {
			response.sendRedirect(Constants.BASE_APP_PATH);
			return;
		}

		final List<Objectif> listeObjectif = this.objectifDao.getAllObjectif();

		request.setAttribute(this.ATT_LISTE_OBJECTIF, listeObjectif);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);

	}

}
