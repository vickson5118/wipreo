package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Lesson;
import com.wipreo.beans.Module;
import com.wipreo.dao.LessonDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class LessonDaoImpl implements LessonDao {

	private DaoFactory factory = null;

	private final String SQL_CREER_LESSON = "INSERT INTO " + Constants.TABLE_LESSON
			+ "(titre,titre_url,source,module_id) VALUES(?,?,?,?)";

	private final String SQL_GET_ALL_LESSON = "SELECT l.id AS lesson_id,l.titre,l.titre_url,l.source,l.module_id FROM "
			+ Constants.TABLE_LESSON + " AS l INNER JOIN " + Constants.TABLE_MODULE + " AS m INNER JOIN "
			+ Constants.TABLE_FORMATION
			+ " AS f ON l.module_id=m.id AND m.formation_id=f.id WHERE f.id=? ORDER BY l.id";

	// supprimer les leçons d'un module
	private final String SQL_DELETE_LESSON = "DELETE FROM " + Constants.TABLE_LESSON + " WHERE id=?";

	private final String SQL_UPDATE_TITRE = "UPDATE " + Constants.TABLE_LESSON + " SET titre=?,titre_url=? WHERE id=?";

	private final String SQL_UPDATE_LESSON = "UPDATE " + Constants.TABLE_LESSON
			+ " SET titre=?,titre_url=?,source=? WHERE id=?";

	private final String SQL_GET_LESSON_SOURCE = "SELECT source FROM " + Constants.TABLE_LESSON + " WHERE id=?";

	private final String SQL_GET_LESSON_ID = "SELECT id FROM " + Constants.TABLE_LESSON + " WHERE source=?";

	public LessonDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean creerLesson(final Lesson lesson) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_LESSON, false, lesson.getTitre(),
					lesson.getTitreUrl(), lesson.getSource(), lesson.getModule().getId());

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
	public List<Lesson> getAllLesson(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Lesson> listeLessonByModule = new ArrayList<Lesson>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_LESSON, false, formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Lesson lesson = new Lesson();
				final Module module = new Module();

				module.setId(result.getLong("module_id"));

				lesson.setId(result.getLong("lesson_id"));
				lesson.setTitre(result.getString("titre"));
				lesson.setTitreUrl(result.getString("titre_url"));
				lesson.setSource(result.getString("source"));
				lesson.setModule(module);

				listeLessonByModule.add(lesson);
			}

			return listeLessonByModule;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean deleteLesson(final Long lessonId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_LESSON, false, lessonId);
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
	public boolean updateTitre(final Lesson lesson) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_TITRE, false, lesson.getTitre(),
					lesson.getTitreUrl(), lesson.getId());

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
	public boolean updateLesson(final Lesson lesson) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_LESSON, false, lesson.getTitre(),
					lesson.getTitreUrl(), lesson.getSource(), lesson.getId());

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
	public String getLessonSource(final long lessonId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_LESSON_SOURCE, false, lessonId);
			result = prepare.executeQuery();

			if (result.first()) {
				return result.getString("source");
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public Long getLessonId(final String sourceUrl) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_LESSON_ID, false, sourceUrl);
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

}
