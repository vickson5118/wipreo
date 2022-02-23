package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Question;
import com.wipreo.beans.Reponse;
import com.wipreo.beans.ReponseCheck;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ReponseCheckDaoImpl implements ReponseCheckDao {

	private DaoFactory factory = null;

	private final String SQL_QUESTION_REPONSE_EXIST = "SELECT id FROM " + Constants.TABLE_REPONSE_CHECK
			+ " WHERE question_id=? AND utilisateur_id=?";

	private final String SQL_UPDATE_REPONSE_CHECK = "UPDATE " + Constants.TABLE_REPONSE_CHECK
			+ " SET reponse_id=? WHERE id=? AND utilisateur_id=?";

	private final String SQL_CREER_REPONSE_CHECK = "INSERT INTO " + Constants.TABLE_REPONSE_CHECK
			+ "(utilisateur_id,exercice_id,question_id,reponse_id) VALUES(?,?,?,?)";

	private final String SQL_GET_EXERCICE_REPONSE_CHECK = "SELECT question_id,reponse_id FROM "
			+ Constants.TABLE_REPONSE_CHECK + " WHERE utilisateur_id=? AND exercice_id=? ORDER BY question_id";

	public ReponseCheckDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Long questionReponseExist(final long questionId, final long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_QUESTION_REPONSE_EXIST, false, questionId, utilisateurId);
			result = prepare.executeQuery();

			if (result.first()) {
				return result.getLong("id");
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean updateReponseCheck(final ReponseCheck reponseCheck) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_REPONSE_CHECK, false,
					reponseCheck.getReponseChecked().getId(), reponseCheck.getId(),
					reponseCheck.getUtilisateur().getId());

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
	public boolean creerReponseCheck(final ReponseCheck reponseCheck) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_REPONSE_CHECK, false,
					reponseCheck.getUtilisateur().getId(), reponseCheck.getExercice().getId(),
					reponseCheck.getQuestion().getId(), reponseCheck.getReponseChecked().getId());

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
	public List<ReponseCheck> getExerciceReponseCheck(final Long utilisateurId, final Long exerciceId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<ReponseCheck> listeReponseCheck = new ArrayList<ReponseCheck>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_EXERCICE_REPONSE_CHECK, false, utilisateurId,
					exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				final ReponseCheck reponseCheck = new ReponseCheck();
				final Question question = new Question();
				final Reponse reponse = new Reponse();

				question.setId(result.getLong("question_id"));

				reponse.setId(result.getLong("reponse_id"));

				reponseCheck.setQuestion(question);
				reponseCheck.setReponseChecked(reponse);

				listeReponseCheck.add(reponseCheck);

			}

			return listeReponseCheck;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
