package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.ExercicePassed;

public interface ExercicePassedDao {

	boolean addExerciceFinish(ExercicePassed exercicePassed);

	ExercicePassed getOneExercicePassed(Long exerciceId, Long utilisateurId);

	List<ExercicePassed> getFormationListeExercicePassed(Long utilisateurId, Long formationId);

}
