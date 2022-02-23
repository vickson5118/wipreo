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
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/panier")
public class ListeAchat extends HttpServlet {

	private final String ATT_LISTE_FORMATION = "listeFormation";
	private final String ATT_LISTE_PANIER = "listePanier";
	private final String GET = "/WEB-INF/components/compte/panier.jsp";
	private FormationDao formationDao = null;
	private AchatDao achatDao = null;

	@Override
	public void init() throws ServletException {
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final HttpSession session = request.getSession();

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		if (utilisateur == null) {
			response.sendRedirect(Constants.PAGE_LOGIN);
			return;
		}

		final List<Formation> listeFormation = this.formationDao.getHuitDerniersFormation(
				utilisateur.getDomaineFavoris().getId() == null ? 1 : utilisateur.getDomaineFavoris().getId());
		final List<Achat> listePanier = this.achatDao.getUserPanierFormation(utilisateur.getId());

		request.setAttribute(this.ATT_LISTE_FORMATION, listeFormation);
		request.setAttribute(this.ATT_LISTE_PANIER, listePanier);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
