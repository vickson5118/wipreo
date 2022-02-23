package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.AddToBasketValidation;

@WebServlet(urlPatterns = "/add-to-basket")
public class AddToBasket extends HttpServlet {

	private AchatDao achatDao = null;

	@Override
	public void init() throws ServletException {
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
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

		final AddToBasketValidation validation = new AddToBasketValidation(this.achatDao);
		final boolean reponse = validation.validAddToBasket(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			response.getWriter().write("{\"msg\" : \"Une erreur est survenue. euillez réessayer ultérieurement.\"}");
		}

	}

}
