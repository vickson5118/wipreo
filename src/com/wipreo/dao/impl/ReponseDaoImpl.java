package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Question;
import com.wipreo.beans.Reponse;
import com.wipreo.dao.ReponseDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ReponseDaoImpl implements ReponseDao {

	private DaoFactory factory = null;

	private final String SQL_GET_ALL_EXERCICE_REPONSE = "SELECT r.id,r.libelle,r.question_id,r.correct FROM "
			+ Constants.TABLE_REPONSE + " AS r INNER JOIN " + Constants.TABLE_QUESTION + " AS q INNER JOIN "
			+ Constants.TABLE_EXERCICE + " AS e " + " ON r.question_id=q.id AND q.exercice_id=e.id WHERE e.id=?";

	private final String SQL_UPDATE_REPONSE = "UPDATE " + Constants.TABLE_REPONSE
			+ " SET libelle=?,correct=? WHERE id=?";

	private final String SQL_GET_EXERCICE_REPONSE_CORRECT = "SELECT r.id,question_id FROM " + Constants.TABLE_REPONSE
			+ " AS r INNER JOIN " + Constants.TABLE_QUESTION
			+ " AS q ON r.question_id=q.id WHERE q.exercice_id=? AND r.correct=true ORDER BY question_id";

	public ReponseDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Reponse> getAllExerciceReponse(final Long exerciceId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Reponse> listeReponse = new ArrayList<Reponse>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_EXERCICE_REPONSE, false, exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Reponse reponse = new Reponse();
				final Question question = new Question();

				question.setId(result.getLong("question_id"));

				reponse.setId(result.getLong("id"));
				reponse.setLibelle(result.getString("libelle"));
				reponse.setCorrect(result.getBoolean("correct"));
				reponse.setQuestionParent(question);

				listeReponse.add(reponse);
			}

			return listeReponse;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean insertMultipleReponse(final List<Reponse> listeReponse) {

		String SQL_INSERT_MULTIPLE_REPONSE = "INSERT INTO " + Constants.TABLE_REPONSE
				+ "(libelle,question_id,correct) VALUES";
		for (int i = 0; i < listeReponse.size(); i++) {
			SQL_INSERT_MULTIPLE_REPONSE += "(?,?,?),";
		}
		SQL_INSERT_MULTIPLE_REPONSE = SQL_INSERT_MULTIPLE_REPONSE.substring(0,
				SQL_INSERT_MULTIPLE_REPONSE.length() - 1);

		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.specialReponseQueryInit(connection, SQL_INSERT_MULTIPLE_REPONSE, listeReponse);
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
	public boolean updateReponse(final Reponse reponse) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_REPONSE, false, reponse.getLibelle(),
					reponse.isCorrect(), reponse.getId());

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
	public List<Reponse> getExerciceReponseCorrect(final Long exerciceId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Reponse> listeReponseCorrect = new ArrayList<Reponse>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_EXERCICE_REPONSE_CORRECT, false, exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Reponse reponseCorrect = new Reponse();
				final Question question = new Question();

				question.setId(result.getLong("question_id"));

				reponseCorrect.setId(result.getLong("id"));
				reponseCorrect.setQuestionParent(question);

				listeReponseCorrect.add(reponseCorrect);
			}
			return listeReponseCorrect;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
