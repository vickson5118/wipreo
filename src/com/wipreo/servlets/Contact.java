package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipreo.dao.ContactDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.ContactValidation;

@WebServlet(urlPatterns = "/contactez-nous")
public class Contact extends HttpServlet {

	public final String GET = "/WEB-INF/components/other/contactez-nous.jsp";

	private ContactDao contactDao = null;

	@Override
	public void init() throws ServletException {
		this.contactDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getContactDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);

	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final ContactValidation validation = new ContactValidation(this.contactDao);
		final boolean reponse = validation.validContact(request);

		if (reponse) {
			response.getWriter().write("{\"type\" : \"success\"}");
		} else {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			response.getWriter().println(gson.toJsonTree(validation.getErreurs()));
		}

	}
}
