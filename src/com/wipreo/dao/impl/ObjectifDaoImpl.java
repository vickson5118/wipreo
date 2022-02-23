package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Objectif;
import com.wipreo.dao.ObjectifDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ObjectifDaoImpl implements ObjectifDao {

	private DaoFactory factory = null;

	private final String SQL_GET_ALL_OBJECTIF = "SELECT id,type FROM " + Constants.TABLE_OBJECTIF;

	public ObjectifDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Objectif> getAllObjectif() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Objectif> listeObjectif = new ArrayList<Objectif>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_OBJECTIF, false);
			result = prepare.executeQuery();

			while (result.next()) {

				final Objectif objectif = new Objectif();

				objectif.setId(result.getByte("id"));
				objectif.setType(result.getString("type"));

				listeObjectif.add(objectif);

			}

			return listeObjectif;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
