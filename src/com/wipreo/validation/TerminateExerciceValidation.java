package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.dao.ExerciceDao;
import com.wipreo.utils.FunctionUtils;

public class TerminateExerciceValidation {

	private final String CHAMP_EXERCICE_ID = "id";

	private ExerciceDao exerciceDao = null;

	public TerminateExerciceValidation(final ExerciceDao exerciceDao) {
		this.exerciceDao = exerciceDao;
	}

	public boolean addTerminateExercice(final HttpServletRequest request) {
		final String exerciceId = FunctionUtils.getValueChamp(request, this.CHAMP_EXERCICE_ID);

		try {
			EditModuleValidation.validLongNumber(exerciceId);
		} catch (final NumberFormatException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}

		return this.exerciceDao.updateTerminateExercice(Long.parseLong(exerciceId));

	}

}
