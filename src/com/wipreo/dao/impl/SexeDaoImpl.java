package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Sexe;
import com.wipreo.dao.SexeDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class SexeDaoImpl implements SexeDao {

	private DaoFactory factory = null;

	private final String SQL_GET_ALL_SEXE = "SELECT id,nom FROM " + Constants.TABLE_SEXE;

	private final String SQL_IS_EXIST = "SELECT nom FROM " + Constants.TABLE_SEXE + " WHERE id=?";

	public SexeDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Sexe> getAllSexe() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Sexe> listeSexe = new ArrayList<Sexe>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_SEXE, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Sexe sexe = new Sexe();

				sexe.setId(result.getByte("id"));
				sexe.setNom(result.getString("nom"));

				listeSexe.add(sexe);
			}
			return listeSexe;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean isExist(final long sexeId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_IS_EXIST, false, sexeId);
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
