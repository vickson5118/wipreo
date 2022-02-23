package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Favoris;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FavorisDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/espace-client/mes-favoris")
public class MesFavoris extends HttpServlet {

	public final String GET = "/WEB-INF/components/espace-client/mes-favoris.jsp";
	private final String ATT_LISTE_FAVORIS = "listeFavoris";
	private FavorisDao favorisDao = null;

	@Override
	public void init() throws ServletException {
		this.favorisDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getFavorisDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final List<Favoris> listeFavoris = this.favorisDao.getUserFormationFavoris(
				((Utilisateur) request.getSession().getAttribute(Inscription.ATT_SESSION_UTILISATEUR)).getId());

		request.setAttribute(this.ATT_LISTE_FAVORIS, listeFavoris);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
