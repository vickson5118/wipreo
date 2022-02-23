package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.beans.Pays;
import com.wipreo.beans.Sexe;
import com.wipreo.dao.PaysDao;
import com.wipreo.dao.SexeDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/membre/profil/informations-generales")
public class profil extends HttpServlet {

	private final String GET = "/WEB-INF/components/profil/profil.jsp";
	private final String ATT_LISTE_SEXE = "listeSexe";
	private final String ATT_LISTE_PAYS = "listePays";

	private SexeDao sexeDao = null;
	private PaysDao paysDao = null;

	@Override
	public void init() throws ServletException {
		this.sexeDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getSexeDao();
		this.paysDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getPaysDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		final List<Sexe> listeSexe = this.sexeDao.getAllSexe();
		final List<Pays> listePays = this.paysDao.getAllPays();

		request.setAttribute(this.ATT_LISTE_SEXE, listeSexe);
		request.setAttribute(this.ATT_LISTE_PAYS, listePays);
		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);

	}

}
