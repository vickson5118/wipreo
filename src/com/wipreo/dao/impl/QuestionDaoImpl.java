package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Question;
import com.wipreo.dao.QuestionDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class QuestionDaoImpl implements QuestionDao {

	private DaoFactory factory = null;

	private final String SQL_CREER_QUESTION = "INSERT INTO " + Constants.TABLE_QUESTION
			+ "(libelle,exercice_id) VALUES(?,?)";

	private final String SQL_GET_ALL_QUESTION_BY_EXERCICE = "SELECT id,libelle FROM " + Constants.TABLE_QUESTION
			+ " WHERE exercice_id=?";

	private final String SQL_QUESTION_EXIST = "SELECT libelle FROM " + Constants.TABLE_QUESTION + " WHERE id=?";

	private final String SQL_UPDATE_QUESTION = "UPDATE " + Constants.TABLE_QUESTION + " SET libelle=? WHERE id=?";

	private final String SQL_DELETE_ONE_QUESTION = "DELETE FROM " + Constants.TABLE_QUESTION + " WHERE id=?";

	public QuestionDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Long creerQuestion(final Question question) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_QUESTION, true, question.getLibelle(),
					question.getExercice().getId());
			prepare.executeUpdate();
			result = prepare.getGeneratedKeys();
			result.first();
			return result.getLong(1);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Question> getAllQuestionByExercice(final Long exerciceId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Question> listeQuestion = new ArrayList<Question>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_QUESTION_BY_EXERCICE, false, exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Question question = new Question();

				question.setId(result.getLong("id"));
				question.setLibelle(result.getString("libelle"));

				listeQuestion.add(question);

			}
			return listeQuestion;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean questionExist(final Long questionId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_QUESTION_EXIST, false, questionId);
			result = prepare.executeQuery();
			if (result.first()) {
				return true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return false;
	}

	@Override
	public boolean updateQuestion(final Question question) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_QUESTION, false, question.getLibelle(),
					question.getId());
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
	public boolean deleteOneQuestion(final long questionId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_ONE_QUESTION, false, questionId);

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

}
