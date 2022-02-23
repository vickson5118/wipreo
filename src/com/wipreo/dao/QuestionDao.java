package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Question;

public interface QuestionDao {

	Long creerQuestion(Question question);

	List<Question> getAllQuestionByExercice(Long exerciceId);

	boolean questionExist(Long questionId);

	boolean updateQuestion(Question question);

	boolean deleteOneQuestion(long questionId);

}
