package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Facture;
import com.wipreo.dao.FactureDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;
import com.wipreo.utils.FunctionUtils;

public class FactureDaoImpl implements FactureDao {

	private DaoFactory factory = null;

	private final String SQL_CREER_FACTURE = "INSERT INTO " + Constants.TABLE_FACTURE
			+ "(designation,date_creation,pdf,prix_total) VALUES(?,?,?,?)";

	private final String SQL_GET_USER_ALL_FACTURE = " SELECT f.designation,f.date_creation,f.pdf,f.prix_total FROM "
			+ Constants.TABLE_FACTURE + " AS f INNER JOIN " + Constants.TABLE_ACHAT + " as p ON "
			+ "p.facture_id=f.id WHERE p.utilisateur_id=? ORDER BY f.id";

	public FactureDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Long creerFacture(final Facture facture) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_FACTURE, true, facture.getDesignation(),
					facture.getDateCreation(), facture.getPdf(), facture.getPrixTotal());
			prepare.executeUpdate();
			result = prepare.getGeneratedKeys();
			if (result.first()) {
				return result.getLong(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Facture> getUserAllFacture(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Facture> listeFacture = new ArrayList<Facture>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_ALL_FACTURE, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Facture facture = new Facture();

				facture.setDesignation(result.getString("designation"));
				facture.setPdf(result.getString("pdf"));
				facture.setPrixTotal(result.getInt("prix_total"));
				facture.setDateCreation(FunctionUtils.parseDateToStringWithMonthName(result.getDate("date_creation")));

				listeFacture.add(facture);

			}
			return listeFacture;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
