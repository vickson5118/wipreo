package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Domaine;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.DomaineDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/domaines/*")
public class ListeFormationDomaine extends HttpServlet {

	// public static final String ATT_SESSION_DOMAINE_ID = "sessionDomaineId";
	private final String GET = "/WEB-INF/components/inc-domaine/domaines.jsp";
	private final String ATT_LISTE_FORMATION = "listeFormation";
	private final String ATT_NOMBRE_PAGE = "nombrePage";
	private final String ATT_DOMAINE = "domaine";
	private final String ATT_LISTE_HUIT_DERNIERES_FORMATION = "listeHuitDernieresFormation";
	private final String ATT_LISTE_TROIS_DERNIERES_FORMATION = "listeTroisDernieresFormation";
	private final String ATT_LISTE_HUIT_DERNIERS_DOMAINES = "listeHuitDerniersDomaine";
	private final String ATT_LISTE_HUIT_DERNIERS_AUTEUR = "listeHuitDerniersAuteur";
	private FormationDao formationDao = null;
	private DomaineDao domaineDao = null;
	private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.domaineDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getDomaineDao();
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final String titreUrlDomaine = request.getParameter("titreDomaine").trim();

		// On s'assure que l'url de domaine existe bien
		// Sinon on retourne une page d'erreur
		if (titreUrlDomaine == null || titreUrlDomaine.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		final Domaine domaine = this.domaineDao.getDomaineInfo(titreUrlDomaine);

		// Si le domaine est null ou il est bloquer ou supprimer on retourne une page
		// d'erreur
		if (domaine == null || domaine.isBloquer() || domaine.isSupprimer()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		/*
		 * final HttpSession session = request.getSession();
		 * session.setAttribute(ATT_SESSION_DOMAINE_ID, domaine.getId());
		 */

		final int nombreArticle = this.formationDao.getCountListeFormation(titreUrlDomaine);
		final int nombrePage = (int) Math.ceil((double) nombreArticle / Constants.NOMBRE_FORMATION_PAR_PAGE);

		final List<Formation> listeFormation = this.formationDao.getPageFormation(domaine.getTitreUrl(), (short) 0);
		final List<Formation> listeHuitDernieresFormation = this.formationDao.getHuitDerniersFormation(domaine.getId());
		final List<Domaine> listeHuitDerniersDomaine = this.domaineDao.getHuitDerniersDomaine(domaine.getId());
		final List<Utilisateur> listeHuitDerniersAuteur = this.utilisateurDao.getHuitDerniersAuteur();

		final List<Formation> listeTroisDernieresFormation = this.formationDao
				.getTroisDernieresFormation(domaine.getId());

		// Si le domaine n'a pas au moins 4 formation, alors on affiche pas la page
		/*
		 * if (listeFormation.size() < 4) { response.sendRedirect("/"); return; }
		 */

		request.setAttribute(this.ATT_LISTE_FORMATION, listeFormation);
		request.setAttribute(this.ATT_NOMBRE_PAGE, nombrePage);
		request.setAttribute(this.ATT_DOMAINE, domaine);
		request.setAttribute(this.ATT_LISTE_HUIT_DERNIERES_FORMATION, listeHuitDernieresFormation);
		request.setAttribute(this.ATT_LISTE_TROIS_DERNIERES_FORMATION, listeTroisDernieresFormation);
		request.setAttribute(this.ATT_LISTE_HUIT_DERNIERS_DOMAINES, listeHuitDerniersDomaine);
		request.setAttribute(this.ATT_LISTE_HUIT_DERNIERS_AUTEUR, listeHuitDerniersAuteur);
		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
