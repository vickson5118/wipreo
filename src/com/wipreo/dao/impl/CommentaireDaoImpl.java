package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Commentaire;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.CommentaireDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class CommentaireDaoImpl implements CommentaireDao {

	private final String SQL_CREATE_COMMENTAIRE = "INSERT INTO " + Constants.TABLE_COMMENTAIRE
			+ "(avis,formation_id,utilisateur_id,date_ajout,rating) VALUES(?,?,?,?,?)";

	private final String SQL_GET_FORMATION_RATING_MOYENNE = "SELECT AVG(rating) AS moyenne FROM "
			+ Constants.TABLE_COMMENTAIRE + " WHERE formation_id=?";

	private final String SQL_GET_FORMATION_ALL_COMMENTAIRE = "SELECT c.avis,c.rating,u.nom,u.prenoms FROM "
			+ Constants.TABLE_COMMENTAIRE + " AS c INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON c.utilisateur_id=u.id WHERE c.formation_id=? AND c.bloquer=false ORDER BY c.id DESC";

	private DaoFactory factory = null;

	public CommentaireDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean createCommentaire(final Commentaire commentaire) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREATE_COMMENTAIRE, true, commentaire.getAvis(),
					commentaire.getFormation().getId(), commentaire.getUtilisateur().getId(),
					commentaire.getDateAjout(), commentaire.getRating());

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
	public Float getFormationRatingMoyenne(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_RATING_MOYENNE, false, formationId);
			result = prepare.executeQuery();
			result.first();
			return result.getFloat("moyenne");
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Commentaire> getFormationAllCommentaire(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Commentaire> listeCommentaire = new ArrayList<Commentaire>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_ALL_COMMENTAIRE, false, formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Utilisateur utilisateur = new Utilisateur();
				final Commentaire commentaire = new Commentaire();

				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));

				commentaire.setAvis(result.getString("avis"));
				commentaire.setRating(result.getFloat("rating"));
				commentaire.setUtilisateur(utilisateur);

				listeCommentaire.add(commentaire);
			}

			return listeCommentaire;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
