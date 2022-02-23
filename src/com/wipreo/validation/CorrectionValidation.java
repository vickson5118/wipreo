package com.wipreo.validation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.wipreo.beans.Exercice;
import com.wipreo.beans.ExercicePassed;
import com.wipreo.beans.Reponse;
import com.wipreo.beans.ReponseCheck;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.dao.ReponseDao;
import com.wipreo.servlets.AfficheExercice;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

public class CorrectionValidation {

	private ReponseCheckDao reponseCheckDao = null;
	private ReponseDao reponseDao = null;
	private ExercicePassedDao exercicePassedDao = null;
	private String messageFinal;

	public CorrectionValidation(final ReponseCheckDao reponseCheckDao, final ReponseDao reponseDao,
			final ExercicePassedDao exercicePassedDao) {
		this.reponseCheckDao = reponseCheckDao;
		this.reponseDao = reponseDao;
		this.exercicePassedDao = exercicePassedDao;
	}

	public String getMessageFinal() {
		return this.messageFinal;
	}

	public void setMessageFinal(final String messageFinal) {
		this.messageFinal = messageFinal;
	}

	public boolean validCorrection(final HttpServletRequest request) {

		final HttpSession session = request.getSession();

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
		final Exercice exercice = (Exercice) session.getAttribute(AfficheExercice.ATT_EXERCICE);

		// Je recupere la liste des reponses que l'utilisateur a choisi
		final List<ReponseCheck> listeExerciceReponseCheck = this.reponseCheckDao
				.getExerciceReponseCheck(utilisateur.getId(), exercice.getId());

		// Je recupere la liste des reponses corrects
		final List<Reponse> listeReponseCorrect = this.reponseDao.getExerciceReponseCorrect(exercice.getId());

		byte nombreReponseCorrect = 0;

		// On verifie que les 2 ont la même taille
		if (listeExerciceReponseCheck.size() == listeReponseCorrect.size()) {

			for (int i = 0; i < listeReponseCorrect.size(); i++) {
				if ((listeExerciceReponseCheck.get(i).getQuestion().getId() == listeReponseCorrect.get(i)
						.getQuestionParent().getId())
						&& listeExerciceReponseCheck.get(i).getReponseChecked().getId() == listeReponseCorrect.get(i)
								.getId()) {

					nombreReponseCorrect += 1;
				}
			}

			final int reponseCorrectPercent = nombreReponseCorrect * 100 / listeReponseCorrect.size();

			if (reponseCorrectPercent >= Constants.VALID_EXERCICE_PERCENT) {
				this.setMessageFinal(
						"Félicitations vous avez validé cet exercice avec un score de " + reponseCorrectPercent + "%.");
			} else {
				this.setMessageFinal(
						"Dommage, vous n'avez pas validé cet exercice. Il faut avoir une note supérieure ou égale à "
								+ Constants.VALID_EXERCICE_PERCENT + "% pour valider. Votre note est de "
								+ reponseCorrectPercent + "%.");
			}

			final ExercicePassed exercicePassed = new ExercicePassed();

			exercicePassed.setExercice(exercice);
			exercicePassed.setUtilisateur(utilisateur);
			exercicePassed.setNote(reponseCorrectPercent);
			exercicePassed.setValide(reponseCorrectPercent >= Constants.VALID_EXERCICE_PERCENT ? true : false);
			exercicePassed.setDate(FunctionUtils.parseDateTimeToStringForBdd(new DateTime()));

			return this.exercicePassedDao.addExerciceFinish(exercicePassed);

		} else {
			return false;
		}
	}

}
