package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.LessonView;
import com.wipreo.dao.LessonViewDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class LessonViewDaoImpl implements LessonViewDao {

	private DaoFactory factory = null;

	private final String SQL_ADD_VIEW_LESSON = "INSERT INTO " + Constants.TABLE_LESSON_VIEW
			+ "(utilisateur_id,lesson_id) VALUES(?,?)";

	private final String SQL_GET_FORMATION_LESSON_VIEW_ID = "SELECT v.lesson_id FROM " + Constants.TABLE_LESSON_VIEW
			+ " AS v INNER JOIN " + Constants.TABLE_LESSON + " AS l INNER JOIN " + Constants.TABLE_MODULE
			+ " AS m INNER JOIN " + Constants.TABLE_FORMATION
			+ " AS f ON v.lesson_id=l.id  AND l.module_id=m.id AND m.formation_id=f.id WHERE v.utilisateur_id=? AND f.id=?";

	private final String SQL_LESSON_IS_VIEW = "SELECT id FROM "+Constants.TABLE_LESSON_VIEW
			+" WHERE utilisateur_id=? AND lesson_id=?";

	public LessonViewDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean addViewLesson(final LessonView lessonView) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_VIEW_LESSON, false,
					lessonView.getUtilisateur().getId(), lessonView.getLesson().getId());

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
	public List<Long> getFormationLessonView(final Long formationId, final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Long> listeLessonViewId = new ArrayList<Long>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_LESSON_VIEW_ID, false, utilisateurId,
					formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Long id = result.getLong("lesson_id");

				listeLessonViewId.add(id);
			}

			return listeLessonViewId;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean lessonIsView(final Long utilisateurId, final Long lessonId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_LESSON_IS_VIEW,false,utilisateurId,lessonId);
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

}
