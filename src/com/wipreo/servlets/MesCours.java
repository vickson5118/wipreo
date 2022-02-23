package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Achat;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/espace-client/mes-cours")
public class MesCours extends HttpServlet {

	private final String GET = "/WEB-INF/components/espace-client/mes-cours.jsp";
	private final String ATT_LISTE_PAID_FORMATION = "listePaidFormation";
	private AchatDao achatDao = null;

	@Override
	public void init() throws ServletException {
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final Long utilisateurId = ((Utilisateur) request.getSession()
				.getAttribute(Inscription.ATT_SESSION_UTILISATEUR)).getId();

		final List<Achat> listePaidFormation = this.achatDao.getAllUSerFormationPaidNotFInish(utilisateurId);

		request.setAttribute(this.ATT_LISTE_PAID_FORMATION, listePaidFormation);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);

	}

}
