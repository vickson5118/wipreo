package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Temoignage;
import com.wipreo.dao.TemoignageDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class TemoignageDaoImpl implements TemoignageDao {

	private final String SQL_GET_ALL_TEMOIGNAGE = "SELECT nom,prenoms,fonction,texte FROM "
			+ Constants.TABLE_TEMOIGNAGE;

	private DaoFactory factory = null;

	public TemoignageDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Temoignage> getAllTemoignage() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Temoignage> listeTemoignage = new ArrayList<Temoignage>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_TEMOIGNAGE, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Temoignage temoignage = new Temoignage();

				temoignage.setNom(result.getString("nom"));
				temoignage.setPrenoms(result.getString("prenoms"));
				temoignage.setFonction(result.getString("fonction"));
				temoignage.setTexte(result.getString("texte"));

				listeTemoignage.add(temoignage);
			}

			return listeTemoignage;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
