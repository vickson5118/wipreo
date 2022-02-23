package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Domaine;
import com.wipreo.dao.DomaineDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class DomaineDaoImpl implements DomaineDao {

	private DaoFactory factory = null;

	private final String SQL_GET_LIST_DOMAINE_WITHOUT_LOCKED_AND_DELETE = "SELECT id,titre,titre_url,illustration FROM "
			+ Constants.TABLE_DOMAINE + " WHERE bloquer=false AND supprimer=false";

	private final String SQL_DOMAINE_EXIST = "SELECT titre_url FROM " + Constants.TABLE_DOMAINE + " WHERE id=?";

	private final String SQL_GET_DOMAINE_INFO = "SELECT id,titre,titre_url,description,supprimer,bloquer,illustration FROM "
			+ Constants.TABLE_DOMAINE + " WHERE titre_url=?";

	private final String SQL_GET_HUIT_DERNIERS_DOMAINE = "SELECT titre,titre_url FROM " + Constants.TABLE_DOMAINE
			+ " WHERE id != ? ORDER BY id DESC LIMIT 0," + Constants.NOMBRE_FORMATION_PAR_PAGE;

	private final String SQL_AJOUTER_UNE_FORMATION_AU_DOMAINE = "UPDATE " + Constants.TABLE_DOMAINE
			+ " SET nombre_formation_total=nombre_formation_total+1,nombre_formation_redaction=nombre_formation_redaction+1 "
			+ "WHERE id=?";

	private final String SQL_UPDATE_NOMBRE_FORMATION_REDACTION_AND_VALIDATED = "UPDATE " + Constants.TABLE_DOMAINE
			+ " SET nombre_formation_redaction=nombre_formation_redaction-1,"
			+ "nombre_formation_validation=nombre_formation_validation+1 WHERE id=?";

	public DomaineDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Domaine> getListDomaineWithoutLockedAndDelete() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Domaine> listeDomaine = new ArrayList<Domaine>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_LIST_DOMAINE_WITHOUT_LOCKED_AND_DELETE, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Domaine domaine = new Domaine();

				domaine.setId(result.getShort("id"));
				domaine.setTitre(result.getString("titre"));
				domaine.setTitreUrl(result.getString("titre_url"));
				domaine.setIllustration(result.getString("illustration"));

				listeDomaine.add(domaine);
			}

			return listeDomaine;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public String domaineExist(final Long domaineId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DOMAINE_EXIST, false, domaineId);
			result = prepare.executeQuery();
			result.first();
			return result.getString("titre_url");
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public Domaine getDomaineInfo(final String titreUrlDomaine) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_DOMAINE_INFO, false, titreUrlDomaine);
			result = prepare.executeQuery();

			final Domaine domaine = new Domaine();

			while (result.next()) {
				domaine.setId(result.getShort("id"));
				domaine.setTitre(result.getString("titre"));
				domaine.setDescription(result.getString("description"));
				domaine.setTitreUrl(result.getString("titre_url"));
				domaine.setBloquer(result.getBoolean("bloquer"));
				domaine.setSupprimer(result.getBoolean("supprimer"));
				domaine.setIllustration(result.getString("illustration"));
			}

			return domaine;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Domaine> getHuitDerniersDomaine(final Short domaineId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Domaine> listeDomaine = new ArrayList<Domaine>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_HUIT_DERNIERS_DOMAINE, false, domaineId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Domaine domaine = new Domaine();
				domaine.setTitre(result.getString("titre"));
				domaine.setTitreUrl(result.getString("titre_url"));

				listeDomaine.add(domaine);
			}
			return listeDomaine;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public void AjouterUneFormationAuDomaine(final Short domaineId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_AJOUTER_UNE_FORMATION_AU_DOMAINE, false, domaineId);
			prepare.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}

	}

	@Override
	public boolean updateNombreFormationRedactionAndValidated(final Short domaineId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_NOMBRE_FORMATION_REDACTION_AND_VALIDATED, false,
					domaineId);

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
