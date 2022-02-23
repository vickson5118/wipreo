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
import com.wipreo.dao.CommentaireDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.AddCommentaireValidation;

@WebServlet(urlPatterns = "/add-commentaire")
public class AddCommentaire extends HttpServlet {

	private CommentaireDao commentaireDao = null;
	private FormationDao formationDao = null;
	private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.commentaireDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getCommentaireDao();
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) == null) {
			response.getWriter().write("{\"type\" : \"session\"}");
			return;
		}

		final AddCommentaireValidation validation = new AddCommentaireValidation(this.commentaireDao, this.formationDao,
				this.utilisateurDao);
		final boolean reponse = validation.validCommnetaire(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			response.getWriter().println(gson.toJsonTree(validation.getErreurs()));
		}

	}

}
