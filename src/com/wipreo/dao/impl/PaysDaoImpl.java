package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Pays;
import com.wipreo.dao.PaysDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class PaysDaoImpl implements PaysDao {

	private DaoFactory factory = null;

	private final String SQL_GET_ALL_PAYS = "SELECT id,nom FROM " + Constants.TABLE_PAYS + " ORDER BY nom";

	private final String SQL_IS_EXIST = "SELECT nom FROM " + Constants.TABLE_PAYS + " WHERE id=?";

	public PaysDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Pays> getAllPays() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Pays> listePays = new ArrayList<Pays>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_PAYS, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Pays pays = new Pays();

				pays.setId(result.getShort("id"));
				pays.setNom(result.getString("nom"));

				listePays.add(pays);
			}

			return listePays;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean isExist(final long paysId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_IS_EXIST, false, paysId);
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
