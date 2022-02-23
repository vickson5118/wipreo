package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FavorisDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.AddFavorisValidation;

@WebServlet(urlPatterns = "/add-favoris")
public class AddFavoris extends HttpServlet {

	private FavorisDao favorisDao = null;

	@Override
	public void init() throws ServletException {
		this.favorisDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getFavorisDao();
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

		final AddFavorisValidation validation = new AddFavorisValidation(this.favorisDao);
		final Long reponse = validation.validFavoris(request);

		if (reponse != null) {
			response.getWriter().write("{\"type\" : \"success\",\"favorisId\" : \"" + reponse + "\"}");
		}

	}

}
