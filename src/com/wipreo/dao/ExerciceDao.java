package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Exercice;

public interface ExerciceDao {

	boolean creerExercice(Exercice exercice);

	List<Exercice> getAllExercice(Long formationId);

	boolean exerciceExist(Long id);

	Exercice getOneExercice(long exerciceId);

	boolean deleteExercice(long exerciceId);

	boolean updateExerciceTitre(Exercice exercice);

	boolean updateExerciceCompetence(Exercice exercice);

	boolean updateTerminateExercice(long exerciceId);

}
