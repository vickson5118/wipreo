package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Formation;
import com.wipreo.beans.Lesson;
import com.wipreo.beans.LessonView;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.LessonDao;
import com.wipreo.dao.LessonViewDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.servlets.LireFormation;
import com.wipreo.utils.FunctionUtils;

public class AddLessonViewValidation {

	private final String CHAMP_LESSON_SOURCE = "id";
	//private final String CHAMP_FORMATION_ID = "formationId";

	private AchatDao achatDao = null;
	private LessonViewDao lessonViewDao = null;
	private LessonDao lessonDao = null;

	public AddLessonViewValidation(final AchatDao achatDao, final LessonViewDao lessonViewDao,
			final LessonDao lessonDao) {
		this.achatDao = achatDao;
		this.lessonViewDao = lessonViewDao;
		this.lessonDao = lessonDao;
	}

	public boolean validAddLessonView(final HttpServletRequest request) {
		final String lessonSource = FunctionUtils.getValueChamp(request, this.CHAMP_LESSON_SOURCE);
		//final String formationId = FunctionUtils.getValueChamp(request, this.CHAMP_FORMATION_ID);

		/*try {
			EditModuleValidation.validLongNumber(formationId);
		} catch (final Exception e) {
			return false;
		}*/

		if (lessonSource != null && !lessonSource.isEmpty()) {

			final Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
			final Formation formation = (Formation)request.getSession().getAttribute(LireFormation.ATT_SESSION_FORMATION);
			final Lesson lesson = new Lesson();

			lesson.setId(this.lessonDao.getLessonId(lessonSource));

			final LessonView lessonView = new LessonView();

			lessonView.setUtilisateur(utilisateur);
			lessonView.setLesson(lesson);

			if (!this.lessonViewDao.lessonIsView(utilisateur.getId(),lesson.getId())) {
				return this.lessonViewDao.addViewLesson(lessonView) &&
						this.achatDao.updatePlusNombreLessonViewAndPourcentageAndFinish(formation.getId(),utilisateur.getId());
			}
		} else {
			return false;
		}
		return false;
	}

}
