package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipreo.beans.Formation;
import com.wipreo.dao.FormationDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet(urlPatterns = "/get-page")
public class GetPage extends HttpServlet {

	private FormationDao formationDao = null;

	@Override
	public void init() throws ServletException {
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		final String pageNumber = FunctionUtils.getValueChamp(request, "page") == null ? "1"
				: FunctionUtils.getValueChamp(request, "page");
		final String domaineTitreUrl = FunctionUtils.getValueChamp(request, "domaineTitreUrl");

		Short nombreDebutPage = null;

		try {
			nombreDebutPage = (short) ((Byte.parseByte(pageNumber) - 1) * Constants.NOMBRE_FORMATION_PAR_PAGE);
		} catch (final NumberFormatException e) {
			nombreDebutPage = 0;
		}

		final List<Formation> listeFormation = this.formationDao.getPageFormation(domaineTitreUrl, nombreDebutPage);

		response.getWriter().println(gson.toJsonTree(listeFormation));
	}

}
