package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Exercice;
import com.wipreo.beans.ExercicePassed;
import com.wipreo.beans.Question;
import com.wipreo.beans.Reponse;
import com.wipreo.beans.ReponseCheck;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.dao.QuestionDao;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.dao.ReponseDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet("/exercice-learn/*")
public class AfficheExercice extends HttpServlet {

	private final String GET = "/WEB-INF/components/formation/affiche-exercice.jsp";
	public static final String ATT_EXERCICE = "exercice";
	private final String ATT_SESSION_TITRE_URL_DOMAINE = "titreUrlDomaine";
	private final String ATT_SESSION_TITRE_URL_FORMATION = "titreUrlFormation";
	private final String ATT_LISTE_QUESTION = "listeQuestion";
	private final String ATT_LISTE_REPONSE = "listeReponse";
	private final String ATT_LISTE_REPONSE_CHECK = "listeReponseCheck";
	private final String ATT_EXERCICE_PASSED = "exercicePassed";

	private ExerciceDao exerciceDao = null;
	private QuestionDao questionDao = null;
	private ReponseDao reponseDao = null;
	private ReponseCheckDao reponseCheckDao = null;
	private ExercicePassedDao exercicePassedDao = null;

	@Override
	public void init() throws ServletException {
		this.exerciceDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getExerciceDao();
		this.questionDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getQuestionDao();
		this.reponseDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getReponseDao();
		this.reponseCheckDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getReponseCheckDao();
		this.exercicePassedDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getExercicePassedDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");
		response.setHeader("Content-type", "application/json");

		final HttpSession session = request.getSession();
		if ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR) == null) {
			response.sendRedirect(Constants.PAGE_LOGIN);
			return;
		}

		final Long exerciceId = Long.parseLong(FunctionUtils.getValueChamp(request, "exerciceId"));
		final String titreUrlDomaine = FunctionUtils.getValueChamp(request, "titreDomaine");
		final String titreUrlFormation = FunctionUtils.getValueChamp(request, "titreFormation");

		final Exercice exercice = this.exerciceDao.getOneExercice(exerciceId);
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		if (exercice == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		final List<Question> listeQuestion = this.questionDao.getAllQuestionByExercice(exerciceId);
		final List<Reponse> listeReponse = this.reponseDao.getAllExerciceReponse(exerciceId);
		final List<ReponseCheck> listeReponseCheck = this.reponseCheckDao.getExerciceReponseCheck(utilisateur.getId(),
				exerciceId);
		final ExercicePassed exercicePassed = this.exercicePassedDao.getOneExercicePassed(exerciceId,
				utilisateur.getId());

		session.setAttribute(ATT_EXERCICE, exercice);
		session.setAttribute(this.ATT_SESSION_TITRE_URL_DOMAINE, titreUrlDomaine);
		session.setAttribute(this.ATT_SESSION_TITRE_URL_FORMATION, titreUrlFormation);
		request.setAttribute(this.ATT_LISTE_QUESTION, listeQuestion);
		request.setAttribute(this.ATT_LISTE_REPONSE, listeReponse);
		request.setAttribute(this.ATT_LISTE_REPONSE_CHECK, listeReponseCheck);
		request.setAttribute(this.ATT_EXERCICE_PASSED, exercicePassed);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);

	}

}
