package com.wipreo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Commentaire;
import com.wipreo.beans.Exercice;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Lesson;
import com.wipreo.beans.Module;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.CommentaireDao;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.LessonDao;
import com.wipreo.dao.ModuleDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

@WebServlet(urlPatterns = "/formation-dash/*")
public class FormationDashboard extends HttpServlet {

	private final String ATT_IS_PANIER = "isPanier";
	private final String ATT_LISTE_FORMATION = "listeFormation";
	private final String ATT_IS_PAID = "isPaid";
	private final String GET = "/WEB-INF/components/formation/formation-dashboard.jsp";
	private final String ATT_FORMATION = "formation";
	private final String ATT_LISTE_MODULE = "listeModule";
	private final String ATT_LISTE_LESSON = "listeLesson";
	private final String ATT_LISTE_EXERCICE = "listeExercice";
	private final String ATT_NOTATION_CINQ_PERCENT = "notationCinqPercent";
	private final String ATT_NOTATION_QUATRE_PERCENT = "notationQuatrePercent";
	private final String ATT_NOTATION_TROIS_PERCENT = "notationTroisPercent";
	private final String ATT_NOTATION_DEUX_PERCENT = "notationDeuxPercent";
	private final String ATT_NOTATION_UN_PERCENT = "notationUnPercent";
	private final String ATT_LISTE_COMMENTAIRE = "listeCommentaire";
	private final String ATT_DOMAINE_TITRE = "domaineTitre";
	private final String ATT_FORMATION_NOMBRE_COMMENTAIRE = "formationNombreCommentaire";

	private FormationDao formationDao = null;
	private ModuleDao moduleDao = null;
	private LessonDao lessonDao = null;
	private AchatDao achatDao = null;
	private ExerciceDao exerciceDao = null;
	private CommentaireDao commentaireDao = null;

	@Override
	public void init() throws ServletException {
		this.formationDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getFormationDao();
		this.lessonDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getLessonDao();
		this.moduleDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getModuleDao();
		this.commentaireDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY))
				.getCommentaireDao();
		this.achatDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getAchatDao();
		this.exerciceDao = ((DaoFactory) this.getServletContext().getAttribute(Constants.ATT_FACTORY)).getExerciceDao();

	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8");

		final String titreUrlFormation = FunctionUtils.getValueChamp(request, "titreFormation");

		final Formation formation = this.formationDao.getOneFormationInfo(titreUrlFormation);

		if (!formation.isValidated() || formation.isBloquer() || formation.isSupprimer()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		final HttpSession session = request.getSession();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		if (utilisateur != null && utilisateur.getId() > 0) {
			boolean isPaid = false;
			boolean isPanier = false;
			final List<Long> listePanierPaidId = this.achatDao.getAllUserFormationPaidId(utilisateur.getId());
			final List<Long> listePanierNoPaidId = this.achatDao.getAllUserFormationNoPaidId(utilisateur.getId());
			if (listePanierPaidId.contains(formation.getId())) {
				isPaid = true;
			}
			if (listePanierNoPaidId.contains(formation.getId())) {
				isPanier = true;
			}
			request.setAttribute(this.ATT_IS_PAID, isPaid);
			request.setAttribute(this.ATT_IS_PANIER, isPanier);
		}

		final List<Formation> listeFormation = this.formationDao
				.getTroisDerniersFormationWithoutCurrentFormation(formation.getId());
		final List<Module> listeModule = this.moduleDao.getAllModuleByFormation(formation.getId());
		final List<Lesson> listeLesson = this.lessonDao.getAllLesson(formation.getId());
		final List<Commentaire> listeCommentaire = this.commentaireDao.getFormationAllCommentaire(formation.getId());
		final List<Exercice> listeExercice = this.exerciceDao.getAllExercice(formation.getId());

		if (!listeCommentaire.isEmpty()) {

			final Integer formationNombreCommentaire = listeCommentaire.size();

			Integer notationCinqCount = 0, notationQuatreCount = 0, notationTroisCount = 0, notationDeuxCount = 0,
					notationUnCount = 0;

			/* Obtenir le nombre de commentaire par niveau */
			for (final Commentaire commentaire : listeCommentaire) {

				if (commentaire.getRating() >= 4.0 && commentaire.getRating() <= 5.0) {
					notationCinqCount += 1;
				}

				if (commentaire.getRating() >= 3.0 && commentaire.getRating() < 4.0) {
					notationQuatreCount += 1;
				}

				if (commentaire.getRating() >= 2.0 && commentaire.getRating() < 3.0) {
					notationTroisCount += 1;
				}

				if (commentaire.getRating() >= 1.0 && commentaire.getRating() < 2.0) {
					notationDeuxCount += 1;
				}

				if (commentaire.getRating() >= 0.0 && commentaire.getRating() < 1.0) {
					notationUnCount += 1;
				}

			}

			final int notationUnPercent = Math.round((notationUnCount * 100) / formationNombreCommentaire);
			final int notationDeuxPercent = Math.round((notationDeuxCount * 100) / formationNombreCommentaire);
			final int notationTroisPercent = Math.round((notationTroisCount * 100) / formationNombreCommentaire);
			final int notationQuatrePercent = Math.round((notationQuatreCount * 100) / formationNombreCommentaire);
			final int notationCinqPercent = 100
					- (notationQuatrePercent + notationTroisPercent + notationDeuxPercent + notationUnPercent);

			request.setAttribute(this.ATT_NOTATION_CINQ_PERCENT, notationCinqPercent);
			request.setAttribute(this.ATT_NOTATION_QUATRE_PERCENT, notationQuatrePercent);
			request.setAttribute(this.ATT_NOTATION_TROIS_PERCENT, notationTroisPercent);
			request.setAttribute(this.ATT_NOTATION_DEUX_PERCENT, notationDeuxPercent);
			request.setAttribute(this.ATT_NOTATION_UN_PERCENT, notationUnPercent);
			request.setAttribute(this.ATT_FORMATION_NOMBRE_COMMENTAIRE, formationNombreCommentaire);
			request.setAttribute(this.ATT_LISTE_COMMENTAIRE, listeCommentaire);
		}

		request.setAttribute(this.ATT_FORMATION, formation);
		request.setAttribute(this.ATT_LISTE_LESSON, listeLesson);
		request.setAttribute(this.ATT_LISTE_MODULE, listeModule);
		request.setAttribute(this.ATT_LISTE_EXERCICE, listeExercice);
		request.setAttribute(this.ATT_LISTE_FORMATION, listeFormation);
		request.setAttribute(this.ATT_DOMAINE_TITRE, formation.getDomaine().getTitre().toLowerCase());

		this.getServletContext().getRequestDispatcher(this.GET).forward(request, response);
	}

}
