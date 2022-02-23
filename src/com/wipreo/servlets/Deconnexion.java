package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet(urlPatterns = "/deconnexion")
public class Deconnexion extends HttpServlet {

	private UtilisateurDao utilisateurDao = null;

	@Override
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getUtilisateurDao();
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
		final String derniereConnexion = FunctionUtils.parseDateTimeToStringForBdd(new DateTime());

		this.utilisateurDao.deconnexion(utilisateurId, derniereConnexion);
		session.invalidate();
		response.sendRedirect("/");

	}

}
