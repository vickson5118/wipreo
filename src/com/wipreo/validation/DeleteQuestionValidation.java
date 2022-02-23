package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.dao.QuestionDao;
import com.wipreo.utils.FunctionUtils;

public class DeleteQuestionValidation {

	private final String CHAMP_QUESTION_ID = "id";

	private QuestionDao questionDao = null;

	public DeleteQuestionValidation(final QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validDeleteQuestion(final HttpServletRequest request) {
		final String questionId = FunctionUtils.getValueChamp(request, this.CHAMP_QUESTION_ID);

		try {
			this.validQuestionId(questionId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_QUESTION_ID, "La question selectionnée n'est pas correct.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_QUESTION_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {
			return this.questionDao.deleteOneQuestion(Long.parseLong(questionId));
		}

		return false;
	}

	private void validQuestionId(final String questionId) throws Exception {
		if (questionId == null || questionId.isEmpty()) {
			throw new Exception("La question selectionnée n'est pas correct.");
		} else if (Long.parseLong(questionId) == 0) {
			throw new Exception("La question selectionnée n'est pas correct..");
		}
	}

}
