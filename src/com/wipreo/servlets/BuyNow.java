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
import com.wipreo.dao.FactureDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.BuyNowValidation;

@WebServlet(urlPatterns = "/buy-now")
public class BuyNow extends HttpServlet {

	private AchatDao achatDao = null;
	private FactureDao factureDao = null;
	private FormationDao formationDao = null;

	@Override
	public void init() throws ServletException {
		this.factureDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getFactureDao();
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
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

		final BuyNowValidation validation = new BuyNowValidation(this.factureDao, this.achatDao, this.formationDao);
		final boolean reponse = validation.validBuyNow(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			response.getWriter().write("{\"msg\" : \"Une erreur est survenue. Veuillez réessayer ultérieurement.\"}");
		}

	}

}
