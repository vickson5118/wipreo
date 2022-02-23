package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Facture;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FactureDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/membre/historique-achats")
public class HistoriqueAchats extends HttpServlet {

	private final String ATT_LISTE_FACTURE = "listeFacture";
	private final String GET = "/WEB-INF/components/compte/historique-achats.jsp";
	private FactureDao factureDao = null;

	@Override
	public void init() throws ServletException {
		this.factureDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getFactureDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final HttpSession session = request.getSession();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		final List<Facture> listeFacture = this.factureDao.getUserAllFacture(utilisateur.getId());

		request.setAttribute(this.ATT_LISTE_FACTURE, listeFacture);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
