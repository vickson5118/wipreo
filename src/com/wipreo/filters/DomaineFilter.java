package com.wipreo.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Domaine;
import com.wipreo.beans.Favoris;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.DomaineDao;
import com.wipreo.dao.FavorisDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;

public class DomaineFilter implements Filter {

	public static final String ATT_SESSION_LISTE_DOMAINE = "listeDomaine";
	public static final String ATT_SESSION_LISTE_FAVORIS = "listeFavoris";
	// public static final String ATT_SESSION_LISTE_PANIER = "listePanier";
	public static final String ATT_SESSION_PANIER_COUNT = "panierCount";
	private DomaineDao domaineDao = null;
	private FavorisDao favorisDao = null;
	private AchatDao achatDao = null;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		this.domaineDao = ((DaoFactory) filterConfig.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getDomaineDao();
		this.favorisDao = ((DaoFactory) filterConfig.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFavorisDao();
		this.achatDao = ((DaoFactory) filterConfig.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getAchatDao();
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpSession session = request.getSession();
		final List<Domaine> listeDomaine = this.domaineDao.getListDomaineWithoutLockedAndDelete();

		// Recupère la liste des formations favoris si la sessin est initialisée
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
		if (utilisateur != null) {

			final List<Favoris> listeFavoris = this.favorisDao.getUserFormationFavorisId(utilisateur.getId());
			// final List<Panier> listePanier =
			// this.panierDao.getUserPanierFormation(utilisateur.getId());
			final int panierCount = this.achatDao.getPanierCount(utilisateur.getId());
			session.setAttribute(ATT_SESSION_LISTE_FAVORIS, listeFavoris);
			// session.setAttribute(ATT_SESSION_LISTE_PANIER, listePanier);
			session.setAttribute(ATT_SESSION_PANIER_COUNT, panierCount);
		}

		session.setAttribute(ATT_SESSION_LISTE_DOMAINE, listeDomaine);
		chain.doFilter(request, response);
	}

}
