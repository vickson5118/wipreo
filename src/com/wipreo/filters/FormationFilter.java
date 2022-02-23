package com.wipreo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormationFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String chemin = request.getPathInfo().substring(1);

		if (chemin == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else if (chemin.split("/").length == 2) {
			response.sendRedirect("/");
		}

		System.out.println(chemin.split("/").length);

		chain.doFilter(request, response);
	}

}
