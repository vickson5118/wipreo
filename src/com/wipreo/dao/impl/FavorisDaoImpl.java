package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Domaine;
import com.wipreo.beans.Favoris;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FavorisDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class FavorisDaoImpl implements FavorisDao {

	private final String SQL_ADD_FAVORIS = "INSERT INTO " + Constants.TABLE_FAVORIS
			+ "(formation_id,utilisateur_id) VALUES(?,?)";

	private final String SQL_GET_USER_FORMATION_FAVORIS_ID = "SELECT f.id,fa.id AS favoris_id FROM "
			+ Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_FAVORIS + " AS fa"
			+ " ON fa.formation_id=f.id WHERE fa.utilisateur_id=?";

	private final String SQL_GET_USER_FORMATION_FAVORIS = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,f.rating,"
			+ "fa.id AS favoris_id,d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM " + Constants.TABLE_FAVORIS
			+ " AS fa INNER JOIN " + Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE
			+ " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON fa.formation_id = f.id AND f.domaine_id=d.id AND f.auteur_id = u.id "
			+ "WHERE fa.utilisateur_id=? AND f.redaction_finished = true AND f.validated = true AND f.bloquer = false AND "
			+ "f.supprimer = false ORDER BY fa.id";

	private final String SQL_DELETE_FAVORIS = "DELETE FROM " + Constants.TABLE_FAVORIS + " WHERE id=?";

	private DaoFactory factory = null;

	public FavorisDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Long addFavoris(final Favoris favoris) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_FAVORIS, true, favoris.getFormation().getId(),
					favoris.getUtilisateur().getId());
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
	public List<Favoris> getUserFormationFavorisId(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Favoris> listeFavoris = new ArrayList<Favoris>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_FORMATION_FAVORIS_ID, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Favoris favoris = new Favoris();

				formation.setId(result.getLong("id"));

				favoris.setId(result.getLong("favoris_id"));
				favoris.setFormation(formation);

				listeFavoris.add(favoris);
			}

			return listeFavoris;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean deleteFavoris(final Long favorisId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_FAVORIS, false, favorisId);
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
	public List<Favoris> getUserFormationFavoris(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Favoris> listeFormationFavoris = new ArrayList<Favoris>();
		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_FORMATION_FAVORIS, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Favoris favoris = new Favoris();
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setNom(result.getString("nom"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setIllustration(result.getString("illustration"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				favoris.setId(result.getLong("favoris_id"));
				favoris.setFormation(formation);

				listeFormationFavoris.add(favoris);
			}

			return listeFormationFavoris;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
