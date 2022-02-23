package com.wipreo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.dao.ReponseDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.validation.CorrectionValidation;

@WebServlet("/exercice/correction")
public class Correction extends HttpServlet {

	private ReponseCheckDao reponseCheckDao = null;
	private ReponseDao reponseDao = null;
	private ExercicePassedDao exercicePassedDao = null;

	@Override
	public void init() throws ServletException {
		this.reponseCheckDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getReponseCheckDao();
		this.reponseDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getReponseDao();
		this.exercicePassedDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getExercicePassedDao();
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final CorrectionValidation validation = new CorrectionValidation(this.reponseCheckDao, this.reponseDao,
				this.exercicePassedDao);
		final boolean reponse = validation.validCorrection(request);

		if (reponse) {
			response.getWriter().write("{\"msgFinal\" : \"" + validation.getMessageFinal() + "\"}");
		} else {
			response.getWriter().write(
					"{\"msgFinal\" : \"Vous n'avez pas repondu à toutes les questions. Merci de repondre à toutes les questions avant de continuer.\"}");
		}

	}

}
