package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.utils.Constants;

@WebServlet(urlPatterns = "/compte/login")
public class Login extends HttpServlet {

	private final String GET = "/WEB-INF/components/compte/login.jsp";

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) != null) {
			response.sendRedirect(Constants.BASE_APP_PATH);
			return;
		}

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
