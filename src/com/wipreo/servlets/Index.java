package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Formation;
import com.wipreo.beans.Temoignage;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.TemoignageDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet("")
public class Index extends HttpServlet {

	private final String ATT_LISTE_HUIT_DERNIERS_FORMATION = "listeHuitDerniersFormation";
	private final String ATT_LISTE_TROIS_DERNIERS_FORMATION = "listeTroisDerniersFormation";
	private final String ATT_LISTE_TEMOIGNAGE = "listeTemoignage";
	private final String ATT_LISTE_COUPS_COEUR = "listeCoupsCoeur";
	private static final String VUE = "/index.jsp";

	private FormationDao formationDao = null;
	private TemoignageDao temoignageDao = null;

	@Override
	public void init() throws ServletException {
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.temoignageDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getTemoignageDao();
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final List<Formation> listeTroisDerniersFormation = this.formationDao.getTroisDerniersFormation();
		final List<Formation> listeHuitDerniersFormation = this.formationDao.getHuitDerniersFormation();
		final List<Formation> listeCoupsCoeur = this.formationDao.getFormationCoupsCoeur();
		final List<Temoignage> listeTemoignage = this.temoignageDao.getAllTemoignage();

		request.setAttribute(this.ATT_LISTE_TROIS_DERNIERS_FORMATION, listeTroisDerniersFormation);
		request.setAttribute(this.ATT_LISTE_HUIT_DERNIERS_FORMATION, listeHuitDerniersFormation);
		request.setAttribute(this.ATT_LISTE_COUPS_COEUR, listeCoupsCoeur);
		request.setAttribute(this.ATT_LISTE_TEMOIGNAGE, listeTemoignage);

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
