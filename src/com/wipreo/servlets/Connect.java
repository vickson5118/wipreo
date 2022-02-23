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
import com.wipreo.filters.SessionFilter;
import com.wipreo.utils.Constants;
import com.wipreo.validation.ConnectValidation;

@WebServlet(urlPatterns = "/compte/connect")
public class Connect extends HttpServlet {

	private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final ConnectValidation validation = new ConnectValidation(this.utilisateurDao);
		final Utilisateur utilisateur = validation.connectUtilisateur(request);

		if (validation.getMessage() == null) {

			final HttpSession session = request.getSession();
			session.setAttribute(Inscription.ATT_SESSION_UTILISATEUR, utilisateur);

			// on recupere l'adresse de lurl avec deconnexion dans le cookie et on mettra a
			// null ensuite
			final String redirectUri = (String) session.getAttribute(SessionFilter.ATT_LINK_CONNECT);

			if (redirectUri != null && !redirectUri.trim().isEmpty()) {
				session.removeAttribute(SessionFilter.ATT_LINK_CONNECT);
				response.getWriter().write("{\"type\" : \"success\",\"uri\":\"" + redirectUri + "\"}");
			} else {
				response.getWriter().write("{\"type\" : \"success\"}");
			}

		} else {
			response.getWriter().write("{\"msg\" : \"" + validation.getMessage() + "\"}");
		}

	}

}
