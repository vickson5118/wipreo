package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.InscriptionValidation;

@WebServlet("/compte/inscription")
public class Inscription extends HttpServlet {

	private final String GET = "/WEB-INF/components/compte/inscription.jsp";
	public final static String ATT_SESSION_UTILISATEUR = "utilisateur";

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

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) != null) {
			response.sendRedirect(Constants.BASE_APP_PATH);
			return;
		}

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final InscriptionValidation validation = new InscriptionValidation(this.utilisateurDao);
		final boolean reponse = validation.validInscription(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			response.getWriter().println(gson.toJsonTree(validation.getErreurs()));
		}
	}

}
