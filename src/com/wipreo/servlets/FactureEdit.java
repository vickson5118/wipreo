package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Achat;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet(urlPatterns = "/facture/*")
public class FactureEdit extends HttpServlet {

	private final String GET = "/WEB-INF/components/compte/facture-edit.jsp";
	private final String ATT_LISTE_PANIER_PAID = "listePanierPaid";
	private AchatDao achatDao = null;

	@Override
	public void init() throws ServletException {
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final HttpSession session = request.getSession();

		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) == null) {
			response.sendRedirect(Constants.PAGE_LOGIN);
			return;
		}

		final Long utilisateurId = ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR)).getId();
		final String factureDesignation = FunctionUtils.getValueChamp(request, "numFacture");

		final List<Achat> listePanierPaid = this.achatDao.getUserPanierBuyWithFormation(utilisateurId,
				factureDesignation);

		if (listePanierPaid.size() == 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		request.setAttribute(this.ATT_LISTE_PANIER_PAID, listePanierPaid);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
