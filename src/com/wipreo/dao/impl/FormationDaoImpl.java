package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Domaine;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FormationDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;
import com.wipreo.utils.FunctionUtils;

public class FormationDaoImpl implements FormationDao {

	private final String SQL_GET_ONE_FORMATION_INFO = "SELECT f.id,f.titre,f.titre_url, f.but,f.objectifs,f.points_cles,"
			+ "f.prerequis,f.pour_qui,f.description,f.prix,f.nombre_heures,f.illustration,f.video_presentation,f.date_creation,f.redaction_finished,"
			+ "f.validated,f.bloquer,f.message_bienvenue,f.message_felicitations,f.supprimer,f.rating,f.nombre_exercice,"
			+ "u.id AS auteurId,u.prenoms,u.nom,u.initial,u.fonction,u.biographie,u.profil,u.photo,u.site,u.facebook,u.twitter,u.linkedin,u.youtube,"
			+ "d.id AS domaine_id,d.titre AS domaine_titre,d.titre_url AS domaine_titre_url FROM "+ Constants.TABLE_FORMATION
			+ " AS f INNER JOIN " + Constants.TABLE_UTILISATEUR + " AS u INNER JOIN "+ Constants.TABLE_DOMAINE
			+ " AS d ON f.auteur_id=u.id AND f.domaine_id = d.id WHERE f.titre_url=?";

	private final String SQL_GET_USER_LIST_FORMATION_ACTIVE = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,f.rating,"
			+ "d.titre AS domaine_titre,d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM "
			+ Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN "
			+ Constants.TABLE_UTILISATEUR + " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.auteur_id=? "
			+ "AND f.redaction_finished=true AND f.validated=true AND f.bloquer=false AND f.supprimer=false";

	private final String SQL_UPDATE_RATING = "UPDATE " + Constants.TABLE_FORMATION + " SET rating = ? WHERE id=?";

	private final String SQL_GET_COUNT_LISTE_FORMATION = "SELECT COUNT(f.id) AS nombre_formation FROM "
			+ Constants.TABLE_FORMATION
			+ " AS f WHERE f.redaction_finished=true AND f.validated=true AND f.bloquer=false AND"
			+ " f.supprimer = false AND f.domaine_id=(SELECT id FROM " + Constants.TABLE_DOMAINE
			+ " WHERE titre_url=?)";

	private final String SQL_GET_HUIT_DERNIERS_FORMATION_BY_DOMAINE = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,f.rating,"
			+ "d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM " + Constants.TABLE_FORMATION + " AS f INNER JOIN "
			+ Constants.TABLE_DOMAINE + " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.domaine_id=? AND "
			+ "f.redaction_finished = true AND f.validated = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY f.date_creation DESC LIMIT 3,"
			+ Constants.NOMBRE_FORMATION_PAR_PAGE;

	private final String SQL_GET_TROIS_DERNIERES_FORMATION = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,"
			+ "f.date_creation,f.nombre_heures,f.nombre_module,f.rating,d.titre_url AS domaine_titre_url,"
			+ "u.nom,u.prenoms FROM " + Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE
			+ " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.domaine_id=? AND "
			+ "f.redaction_finished = true AND f.validated = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY f.date_creation DESC LIMIT 0,3";

	private final String SQL_GET_AUTEUR_RATING_MOYENNE = "SELECT AVG(rating) AS auteur_moyenne FROM "
			+ Constants.TABLE_FORMATION + " WHERE auteur_id=? AND redaction_finished=true AND "
			+ "validated=true AND bloquer=false AND supprimer=false";

	private final String SQL_GET_HUIT_DERNIERS_FORMATION_INDEX = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,"
			+ "f.rating,d.titre_url AS domaine_titre_url,u.nom,u.prenoms,u.fonction FROM " + Constants.TABLE_FORMATION
			+ " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.redaction_finished = true AND f.validated = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY f.date_creation DESC LIMIT 3,"
			+ Constants.NOMBRE_FORMATION_PAR_PAGE;

	private final String SQL_GET_TROIS_DERNIERS_FORMATION = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,"
			+ "f.rating,d.titre_url AS domaine_titre_url,u.nom,u.prenoms,u.fonction FROM " + Constants.TABLE_FORMATION
			+ " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.redaction_finished = true AND f.validated = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY f.date_creation DESC LIMIT 0,3";

	private final String SQL_GET_FORMATION_COUPS_COEUR = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,"
			+ "f.rating,d.titre_url AS domaine_titre_url,u.nom,u.prenoms,u.fonction FROM " + Constants.TABLE_FORMATION
			+ " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.coups_coeur = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY f.date_coups_coeur DESC LIMIT 0,10";

	private final String SQL_GET_TROIS_DERNIERS_FORMATION_WITHOUT_CURRENT_FORMATION = "SELECT f.id,f.titre,f.titre_url,"
			+ "f.prix,f.but,f.rating,d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM " + Constants.TABLE_FORMATION
			+ " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.id!=? "
			+ "AND f.redaction_finished = true AND f.validated = true AND f.bloquer = false AND f.supprimer = false "
			+ "ORDER BY f.date_creation DESC LIMIT 0,3";

	private final String SQL_UPDATE_FORMATION_NOMBRE_ACHAT = "UPDATE " + Constants.TABLE_FORMATION
			+ " SET nombre_achat=nombre_achat+1 WHERE id=?";

	private final DaoFactory factory;

	public FormationDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Formation getOneFormationInfo(final String titreUrl) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final Formation formation = new Formation();
		final Domaine domaine = new Domaine();
		final Utilisateur utilisateur = new Utilisateur();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ONE_FORMATION_INFO, false, titreUrl);
			result = prepare.executeQuery();

			while (result.next()) {

				domaine.setId(result.getShort("domaine_id"));
				domaine.setTitre(result.getString("domaine_titre"));
				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setId(result.getLong("auteurId"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setInitial(result.getString("initial"));
				utilisateur.setProfil(result.getString("profil"));
				utilisateur.setFonction(result.getString("fonction"));
				utilisateur.setBiographie(result.getString("biographie"));
				utilisateur.setPhoto(result.getString("photo"));
				utilisateur.setFacebook(result.getString("facebook"));
				utilisateur.setTwitter(result.getString("twitter"));
				utilisateur.setLinkedin(result.getString("linkedin"));
				utilisateur.setYoutube(result.getString("youtube"));
				utilisateur.setSite(result.getString("site"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setDescription(result.getString("description"));
				formation.setObjectifs(result.getString("objectifs"));
				formation.setPourQui(result.getString("pour_qui"));
				formation.setBut(result.getString("but"));
				formation.setPointsCles(result.getString("points_cles"));
				formation.setPrix(result.getInt("prix"));
				formation.setNombreHeures(result.getInt("nombre_heures"));
				formation.setRedactionFinished(result.getBoolean("redaction_finished"));
				formation.setPrerequis(result.getString("prerequis"));
				formation.setIllustration(result.getString("illustration"));
				formation.setVideoPresentation(result.getString("video_presentation"));
				formation.setNombreExercice(result.getByte("nombre_exercice"));
				formation.setMessageBienvenue(result.getString("message_bienvenue"));
				formation.setMessageFelicitations(result.getString("message_felicitations"));
				formation.setRating(result.getFloat("rating"));
				formation.setValidated(result.getBoolean("validated"));
				formation.setBloquer(result.getBoolean("bloquer"));
				formation.setSupprimer(result.getBoolean("supprimer"));
				formation.setDateCreation(FunctionUtils.parseDateToStringWithSlash(result.getDate("date_creation")));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);
			}

			return formation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getUserListFormationActive(final Long auteurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormationActive = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_LIST_FORMATION_ACTIVE, false, auteurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitre(result.getString("domaine_titre"));
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

				listeFormationActive.add(formation);
			}

			return listeFormationActive;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean updateRating(final Long formationId, final Float moyenne) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_RATING, false, moyenne, formationId);
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
	public int getCountListeFormation(final String titreUrlDomaine) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_COUNT_LISTE_FORMATION, false, titreUrlDomaine);
			result = prepare.executeQuery();
			result.first();
			return result.getInt(1);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return 0;
	}

	@Override
	public List<Formation> getPageFormation(final String domaineTitreUrl, final Short nombreDebutPage) {

		final String SQL_GET_PAGE_FORMATION = "SELECT f.id,f.titre,f.titre_url,f.prix,f.illustration,f.rating,d.titre_url AS domaine_titre_url,"
				+ "u.nom,u.prenoms FROM " + Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE
				+ " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
				+ " AS u ON f.domaine_id=d.id AND f.auteur_id = u.id WHERE f.domaine_id=(SELECT id FROM "
				+ Constants.TABLE_DOMAINE + " WHERE titre_url=?) AND "
				+ "f.redaction_finished=true AND f.validated=true " + "AND f.bloquer=false AND f.supprimer=false LIMIT "
				+ nombreDebutPage + "," + Constants.NOMBRE_FORMATION_PAR_PAGE;

		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, SQL_GET_PAGE_FORMATION, false, domaineTitreUrl);
			result = prepare.executeQuery();

			while (result.next()) {
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

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getHuitDerniersFormation(final Short domaineId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_HUIT_DERNIERS_FORMATION_BY_DOMAINE, false, domaineId);
			result = prepare.executeQuery();

			while (result.next()) {
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

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getTroisDernieresFormation(final Short domaineId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeTroisDernieresFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_TROIS_DERNIERES_FORMATION, false, domaineId);
			result = prepare.executeQuery();

			while (result.next()) {
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
				formation.setDateCreation(FunctionUtils.parseDateToStringWithSlash(result.getDate("date_creation")));
				formation.setNombreHeures(result.getInt("nombre_heures"));
				formation.setNombreModule(result.getInt("nombre_module"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				listeTroisDernieresFormation.add(formation);

			}

			return listeTroisDernieresFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public Float getAuteurRatingMoyenne(final Long auteurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_AUTEUR_RATING_MOYENNE, false, auteurId);
			result = prepare.executeQuery();
			result.first();
			return result.getFloat("auteur_moyenne");
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getHuitDerniersFormation() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_HUIT_DERNIERS_FORMATION_INDEX, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setFonction(result.getString("fonction"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setIllustration(result.getString("illustration"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getTroisDerniersFormation() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_TROIS_DERNIERS_FORMATION, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setFonction(result.getString("fonction"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setIllustration(result.getString("illustration"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getFormationCoupsCoeur() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_COUPS_COEUR, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setFonction(result.getString("fonction"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setIllustration(result.getString("illustration"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Formation> getTroisDerniersFormationWithoutCurrentFormation(final Long id) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Formation> listeFormation = new ArrayList<Formation>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_TROIS_DERNIERS_FORMATION_WITHOUT_CURRENT_FORMATION,
					false, id);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Domaine domaine = new Domaine();
				final Utilisateur utilisateur = new Utilisateur();

				domaine.setTitreUrl(result.getString("domaine_titre_url"));

				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setNom(result.getString("nom"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setTitreUrl(result.getString("titre_url"));
				formation.setBut(result.getString("but"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				listeFormation.add(formation);
			}

			return listeFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean updateFormationNombreAchat(final Long formationId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_FORMATION_NOMBRE_ACHAT, false, formationId);

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
