package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Exercice;
import com.wipreo.beans.Question;
import com.wipreo.beans.Reponse;
import com.wipreo.beans.ReponseCheck;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.servlets.AfficheExercice;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.FunctionUtils;

public class ReponseCheckValidation {

	private final String CHAMP_QUESTION_ID = "questionId";
	private final String CHAMP_REPONSE_ID = "reponseId";

	private ReponseCheckDao reponseCheckDao = null;

	public ReponseCheckValidation(final ReponseCheckDao reponseCheckDao) {
		this.reponseCheckDao = reponseCheckDao;
	}

	public boolean validReponseCheck(final HttpServletRequest request) {
		final String questionId = FunctionUtils.getValueChamp(request, this.CHAMP_QUESTION_ID);
		final String reponseId = FunctionUtils.getValueChamp(request, this.CHAMP_REPONSE_ID);

		try {
			FunctionUtils.validLongId(questionId);
		} catch (final NumberFormatException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}

		try {
			FunctionUtils.validLongId(reponseId);
		} catch (final NumberFormatException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}

		final HttpSession session = request.getSession();

		final Long exerciceId = ((Exercice) session.getAttribute(AfficheExercice.ATT_EXERCICE)).getId();

		if (exerciceId != null && exerciceId > 0) {
			final ReponseCheck reponseCheck = new ReponseCheck();
			final Question question = new Question();
			final Exercice exercice = new Exercice();
			final Reponse reponse = new Reponse();
			final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

			question.setId(Long.parseLong(questionId));

			reponse.setId(Long.parseLong(reponseId));

			exercice.setId(exerciceId);

			reponseCheck.setUtilisateur(utilisateur);
			reponseCheck.setQuestion(question);
			reponseCheck.setReponseChecked(reponse);
			reponseCheck.setExercice(exercice);

			// on verifie que la question n'existe pas deja
			final Long reponseChekId = this.reponseCheckDao.questionReponseExist(Long.parseLong(questionId),
					utilisateur.getId());
			if (reponseChekId != null) {
				reponseCheck.setId(reponseChekId);
				return this.reponseCheckDao.updateReponseCheck(reponseCheck);
			} else {
				return this.reponseCheckDao.creerReponseCheck(reponseCheck);
			}

		}

		return false;
	}

}
