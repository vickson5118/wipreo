package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/formateur/*")
public class Formateur extends HttpServlet {

	private final String GET = "/WEB-INF/components/other/formateur.jsp";
	private final String ATT_UTILISATEUR = "utilisateur";
	private final String ATT_LISTE_FORMATION = "listeFormation";

	private UtilisateurDao utilisateurDao = null;
	private FormationDao formationDao = null;

	@Override
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final String profil = request.getParameter("profil").trim();

		final Utilisateur utilisateur = this.utilisateurDao.getFormateurInfo(profil);

		if (utilisateur == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		final List<Formation> listeFormation = this.formationDao.getUserListFormationActive(utilisateur.getId());

		request.setAttribute(this.ATT_UTILISATEUR, utilisateur);
		request.setAttribute(this.ATT_LISTE_FORMATION, listeFormation);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
