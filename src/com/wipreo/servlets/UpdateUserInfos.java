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
import com.wipreo.dao.PaysDao;
import com.wipreo.dao.SexeDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.UpdateUserInfosValidation;

@WebServlet(urlPatterns = "/update-user-infos")
public class UpdateUserInfos extends HttpServlet {

	private UtilisateurDao utilisateurDao = null;
	private SexeDao sexeDao = null;
	private PaysDao paysDao = null;

	@Override
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
		this.sexeDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getSexeDao();
		this.paysDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getPaysDao();
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

		final UpdateUserInfosValidation validation = new UpdateUserInfosValidation(this.utilisateurDao, this.sexeDao,
				this.paysDao);
		final boolean reponse = validation.validUpdateUserInfo(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			response.getWriter().println(gson.toJsonTree(validation.getErreurs()));
		}

	}

}
