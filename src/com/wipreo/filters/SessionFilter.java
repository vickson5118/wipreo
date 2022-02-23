package com.wipreo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;

public class SessionFilter implements Filter {

	public static String ATT_LINK_CONNECT = "linkConnect";

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) == null) {
			session.setAttribute(ATT_LINK_CONNECT, request.getRequestURI());
			response.sendRedirect(Constants.PAGE_LOGIN);
		} else {
			chain.doFilter(request, response);
		}

	}

}
