package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Exercice;
import com.wipreo.beans.ExercicePassed;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ExercicePassedDaoImpl implements ExercicePassedDao {

	private DaoFactory factory = null;

	private final String SQL_ADD_EXERCICE_FINISH = "INSERT INTO " + Constants.TABLE_EXERCICE_PASSED
			+ "(utilisateur_id,exercice_id,note,valide,date) VALUES(?,?,?,?,?)";

	private final String SQL_GET_ONE_EXERCICE_PASSED = "SELECT valide FROM " + Constants.TABLE_EXERCICE_PASSED
			+ " WHERE utilisateur_id=? AND exercice_id=?";

	private final String SQL_GET_FORMATION_LISTE_EXERCICE_PASSED = "SELECT valide,ep.exercice_id,note FROM "
			+ Constants.TABLE_EXERCICE_PASSED + " AS ep INNER JOIN " + Constants.TABLE_EXERCICE + " AS e INNER JOIN "
			+ Constants.TABLE_MODULE + " AS m INNER JOIN " + Constants.TABLE_FORMATION
			+ " AS f ON ep.exercice_id=e.id AND "
			+ "e.module_id=m.id AND m.formation_id=f.id WHERE ep.utilisateur_id=? AND f.id=?";

	public ExercicePassedDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean addExerciceFinish(final ExercicePassed exercicePassed) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_EXERCICE_FINISH, false,
					exercicePassed.getUtilisateur().getId(), exercicePassed.getExercice().getId(),
					exercicePassed.getNote(), exercicePassed.isValide(), exercicePassed.getDate());

			if (prepare.executeUpdate() != 0) {
				return true;
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}
		return false;
	}

	@Override
	public ExercicePassed getOneExercicePassed(final Long exerciceId, final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		ExercicePassed exercicePassed = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ONE_EXERCICE_PASSED, false, utilisateurId, exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				exercicePassed = new ExercicePassed();

				exercicePassed.setValide(result.getBoolean("valide"));
			}

			return exercicePassed;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<ExercicePassed> getFormationListeExercicePassed(final Long utilisateurId, final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<ExercicePassed> listeExercicePassed = new ArrayList<ExercicePassed>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_LISTE_EXERCICE_PASSED, false, utilisateurId,
					formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final ExercicePassed exercicePassed = new ExercicePassed();
				final Exercice exercice = new Exercice();

				exercice.setId(result.getLong("exercice_id"));

				exercicePassed.setValide(result.getBoolean("valide"));
				exercicePassed.setNote(result.getInt("note"));
				exercicePassed.setExercice(exercice);

				listeExercicePassed.add(exercicePassed);

			}

			return listeExercicePassed;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
