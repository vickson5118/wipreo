package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.ReponseCheck;

public interface ReponseCheckDao {

	Long questionReponseExist(long questionId, long utilisateurId);

	boolean updateReponseCheck(ReponseCheck reponseCheck);

	boolean creerReponseCheck(ReponseCheck reponseCheck);

	List<ReponseCheck> getExerciceReponseCheck(Long utilisateurId, Long exerciceId);

}
