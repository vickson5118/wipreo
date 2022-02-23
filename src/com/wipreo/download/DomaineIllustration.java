package com.wipreo.download;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.utils.Constants;
import com.wipreo.utils.FileManagement;

@WebServlet(Constants.DOMAINE_ILLUSTRATION_DIRECTORY + "*")
public class DomaineIllustration extends HttpServlet {

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final String chemin = Constants.DOMAINE_ILLUSTRATION_DIRECTORY;
		final String fichierPath = request.getPathInfo();
		FileManagement.uploadFichier(request, response, fichierPath, chemin, Constants.CONTENT_DISPOSITION_INLINE);

	}

}
