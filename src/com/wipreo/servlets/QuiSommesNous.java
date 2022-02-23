package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qui-sommes-nous")
public class QuiSommesNous extends HttpServlet {

	private final String GET = "/WEB-INF/components/other/qui-sommes-nous.jsp";

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
