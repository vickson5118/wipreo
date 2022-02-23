package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.ReponseCheckValidation;

@WebServlet("/reponse-check")
public class ReponseCheck extends HttpServlet {

	private ReponseCheckDao reponseCheckDao = null;

	@Override
	public void init() throws ServletException {
		this.reponseCheckDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getReponseCheckDao();
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

		final ReponseCheckValidation validation = new ReponseCheckValidation(this.reponseCheckDao);
		final boolean reponse = validation.validReponseCheck(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			response.getWriter().write("{\"msg\" : \"Une erreur est survenue. Veuillez réessayer ultérieurement.\"}");
		}

	}

}
