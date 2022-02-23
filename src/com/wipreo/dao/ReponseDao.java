package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Reponse;

public interface ReponseDao {

	List<Reponse> getAllExerciceReponse(Long exerciceId);

	boolean insertMultipleReponse(List<Reponse> listeReponse);

	boolean updateReponse(Reponse reponse);

	List<Reponse> getExerciceReponseCorrect(Long exerciceId);

}
