package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Achat;
import com.wipreo.beans.Commentaire;
import com.wipreo.beans.Exercice;
import com.wipreo.beans.ExercicePassed;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Lesson;
import com.wipreo.beans.Module;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.CommentaireDao;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.LessonDao;
import com.wipreo.dao.LessonViewDao;
import com.wipreo.dao.ModuleDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet(urlPatterns = "/learn/*")
public class LireFormation extends HttpServlet {

	private final String GET = "/WEB-INF/components/formation/lire-formation.jsp";
	private final String ATT_LISTE_MODULE = "listeModule";
	private final String ATT_LISTE_LESSON = "listeLesson";
	private final String ATT_LISTE_COMMENTAIRE = "listeCommentaire";
	private final String ATT_LISTE_EXERCICE = "listeExercice";
	private final String ATT_LISTE_LESSON_VIEW_ID = "listeLessonViewId";
	public static final String ATT_SESSION_FORMATION = "formation";
	private final String ATT_ACHAT = "achat";
	private final String ATT_LISTE_EXERCICE_PASSED = "listeExercicePassed";

	private ModuleDao moduleDao = null;
	private LessonDao lessonDao = null;
	private FormationDao formationDao = null;
	private CommentaireDao commentaireDao = null;
	private LessonViewDao lessonViewDao = null;
	private ExerciceDao exerciceDao = null;
	private ExercicePassedDao exercicePassedDao = null;
	private AchatDao achatDao = null;

	@Override
	public void init() throws ServletException {
		this.moduleDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getModuleDao();
		this.lessonDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getLessonDao();
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.commentaireDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getCommentaireDao();
		this.lessonViewDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getLessonViewDao();
		this.exerciceDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getExerciceDao();
		this.exercicePassedDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getExercicePassedDao();
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final HttpSession session = request.getSession();
		final String titreFormationUrl = FunctionUtils.getValueChamp(request, "titreFormation").trim();

		final Formation formation = this.formationDao.getOneFormationInfo(titreFormationUrl);

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
		if (utilisateur == null || formation == null) {
			response.sendRedirect(Constants.PAGE_LOGIN);
			return;
		}


		if (!this.achatDao.formationIsBuy(utilisateur.getId(), formation.getId())) {
			response.sendRedirect( "/formations/" + formation.getDomaine().getTitreUrl()+ "/" + formation.getTitreUrl());
			return;
		}


		final List<Module> listeModule = this.moduleDao.getAllModuleByFormation(formation.getId());
		final List<Lesson> listeLesson = this.lessonDao.getAllLesson(formation.getId());
		final List<Commentaire> listeCommentaire = this.commentaireDao.getFormationAllCommentaire(formation.getId());
		final List<Long> listeLessonViewId = this.lessonViewDao.getFormationLessonView(formation.getId(), utilisateur.getId());
		final List<Exercice> listeExercice = this.exerciceDao.getAllExercice(formation.getId());
		final List<ExercicePassed> listeExercicePassed = this.exercicePassedDao.getFormationListeExercicePassed(utilisateur.getId(), formation.getId());
		final Achat achat = this.achatDao.getOneAchat(utilisateur.getId(), formation.getId());

		request.setAttribute(this.ATT_LISTE_MODULE, listeModule);
		request.setAttribute(this.ATT_LISTE_LESSON, listeLesson);
		request.setAttribute(this.ATT_LISTE_LESSON_VIEW_ID, listeLessonViewId);
		request.setAttribute(this.ATT_LISTE_COMMENTAIRE, listeCommentaire);
		request.setAttribute(this.ATT_LISTE_EXERCICE, listeExercice);
		request.setAttribute(this.ATT_LISTE_EXERCICE_PASSED, listeExercicePassed);
		request.setAttribute(this.ATT_ACHAT, achat);
		session.setAttribute(ATT_SESSION_FORMATION, formation);

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
