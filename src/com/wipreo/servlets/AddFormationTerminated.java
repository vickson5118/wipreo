package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.AddFormationTerminatedValidation;

@WebServlet("/formation-terminated")
public class AddFormationTerminated extends HttpServlet {

	private AchatDao achatDao = null;
	private ExercicePassedDao exercicePassedDao = null;

	@Override
	public void init() throws ServletException {
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
		this.exercicePassedDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getExercicePassedDao();
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) == null) {
			response.getWriter().write("{\"type\" : \"session\"}");
			return;
		}

		final AddFormationTerminatedValidation validation = new AddFormationTerminatedValidation(this.achatDao,
				this.exercicePassedDao);
		final boolean reponse = validation.validAddFormationTerminated(request);

		if (reponse) {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			response.getWriter().println(gson.toJsonTree(validation.getErreurs()));
		} else {
			response.getWriter().write("{\"msg\" : \"" + validation.getMessage() + "\"}");
		}

	}

}
