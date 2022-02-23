package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Exercice;
import com.wipreo.beans.Module;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ExerciceDaoImpl implements ExerciceDao {

	private final String SQL_CREER_EXERCICE = "INSERT INTO " + Constants.TABLE_EXERCICE
			+ "(titre,competences,module_id) VALUES(?,?,?)";

	private final String SQL_GET_ALL_EXERCICE = "SELECT e.id AS exercice_id,e.titre,e.competences,e.module_id,e.finish FROM "
			+ Constants.TABLE_EXERCICE + " AS e INNER JOIN " + Constants.TABLE_MODULE + " AS m INNER JOIN "
			+ Constants.TABLE_FORMATION
			+ " AS f ON e.module_id=m.id AND m.formation_id=f.id WHERE f.id=? ORDER BY e.id";

	private final String SQL_EXERCICE_EXIST = "SELECT titre FROM " + Constants.TABLE_EXERCICE + " WHERE id=?";

	private final String SQL_GET_ONE_EXERCICE = "SELECT id,titre,competences,finish FROM " + Constants.TABLE_EXERCICE
			+ " WHERE id=?";

	private final String SQL_DELETE_EXERCICE = "DELETE FROM " + Constants.TABLE_EXERCICE + " WHERE id=?";

	private final String SQL_UPDATE_EXERCICE_TITLE = "UPDATE " + Constants.TABLE_EXERCICE + " SET titre=? WHERE id=?";

	private final String SQL_UPDATE_EXERCICE_COMPETENCE = "UPDATE " + Constants.TABLE_EXERCICE
			+ " SET competences=? WHERE id=?";

	private final String SQL_UPDATE_TERMINATE_EXERCICE = "UPDATE " + Constants.TABLE_EXERCICE
			+ " SET finish=true WHERE id=?";

	private DaoFactory factory = null;

	public ExerciceDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean creerExercice(final Exercice exercice) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_EXERCICE, true, exercice.getTitre(),
					exercice.getCompetence(), exercice.getModule().getId());
			prepare.executeUpdate();
			result = prepare.getGeneratedKeys();
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
	public List<Exercice> getAllExercice(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Exercice> listeExercice = new ArrayList<Exercice>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_EXERCICE, false, formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Exercice exercice = new Exercice();
				final Module module = new Module();

				module.setId(result.getLong("module_id"));

				exercice.setModule(module);
				exercice.setId(result.getLong("exercice_id"));
				exercice.setTitre(result.getString("titre"));
				exercice.setCompetence(result.getString("competences"));
				exercice.setFinish(result.getBoolean("finish"));

				listeExercice.add(exercice);

			}
			return listeExercice;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean exerciceExist(final Long id) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_EXERCICE_EXIST, false, id);
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
	public Exercice getOneExercice(final long exerciceId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		Exercice exercice = null;
		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ONE_EXERCICE, false, exerciceId);
			result = prepare.executeQuery();

			while (result.next()) {
				exercice = new Exercice();
				exercice.setId(result.getLong("id"));
				exercice.setTitre(result.getString("titre"));
				exercice.setCompetence(result.getString("competences"));
				exercice.setFinish(result.getBoolean("finish"));
			}

			return exercice;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean deleteExercice(final long exerciceId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_EXERCICE, false, exerciceId);

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
	public boolean updateExerciceTitre(final Exercice exercice) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_EXERCICE_TITLE, false, exercice.getTitre(),
					exercice.getId());

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
	public boolean updateExerciceCompetence(final Exercice exercice) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_EXERCICE_COMPETENCE, false,
					exercice.getCompetence(), exercice.getId());

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
	public boolean updateTerminateExercice(final long exerciceId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_TERMINATE_EXERCICE, false, exerciceId);

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
