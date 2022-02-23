package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Exercice;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.utils.FunctionUtils;

public class EditExerciceCompetenceValidation {

	private final String CHAMP_EXERCICE_ID = "exerciceId";
	private final String CHAMP_EXERCICE_COMPETENCE = "competence";

	private ExerciceDao exerciceDao = null;

	public EditExerciceCompetenceValidation(final ExerciceDao exerciceDao) {
		this.exerciceDao = exerciceDao;
	}

	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validEditExerciceCompetence(final HttpServletRequest request) {
		final String exerciceId = FunctionUtils.getValueChamp(request, this.CHAMP_EXERCICE_ID);
		final String competences = FunctionUtils.getValueChamp(request, this.CHAMP_EXERCICE_COMPETENCE);

		try {
			FunctionUtils.validTexte(this.CHAMP_EXERCICE_COMPETENCE, "Compétences évaluées", 20, 500);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_EXERCICE_COMPETENCE, e.getMessage());
		}

		try {
			EditModuleValidation.validLongNumber(exerciceId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_EXERCICE_ID, "Une erreur est survenue.Veuillez réessayer ultérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_EXERCICE_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {
			final Exercice exercice = new Exercice();

			exercice.setId(Long.parseLong(exerciceId));
			exercice.setCompetence(FunctionUtils.premiereLettreEnMajuscule(competences));

			return this.exerciceDao.updateExerciceCompetence(exercice);
		}

		return false;
	}

}
