package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Domaine;
import com.wipreo.beans.Objectif;
import com.wipreo.beans.Pays;
import com.wipreo.beans.Sexe;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;
import com.wipreo.utils.FunctionUtils;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private DaoFactory factory = null;

	/**
	 * Créer un utilisateur
	 */
	private final String SQL_CREER_UTILISATEUR = "INSERT INTO " + Constants.TABLE_UTILISATEUR
			+ "(nom,prenoms,initial,email,password,date_creation,token) VALUES(?,?,?,?,?,?,?)";
	/**
	 * Verifie si l'email existe deja
	 */
	private final String SQL_USER_EMAIL_EXIST = "SELECT id FROM " + Constants.TABLE_UTILISATEUR + " WHERE email=?";
	// Mettre le mot de passe a jour
	private final String SQL_UPDATE_REDEFINIR_PASSWORD = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET password=? WHERE id=?";
	// se connecter
	private final String SQL_CONNECT_UTILISATEUR = "SELECT id,nom,prenoms,initial,email,profil,telephone,date_naissance,"
			+ "sexe_id,pays_id,fonction,site,facebook,twitter,linkedin,youtube,biographie,photo,objectif_id,domaine_favoris,bloquer,"
			+ "supprimer FROM " + Constants.TABLE_UTILISATEUR + " WHERE email=? AND password=? AND "
			+ "active=TRUE AND admin=false";
	// mise a jour de connect
	private final String SQL_UPDATE_CONNECT = "UPDATE " + Constants.TABLE_UTILISATEUR + " SET connect=true,"
			+ "derniere_connexion=? WHERE id=?";

	private final String SQL_DECONNEXION = "UPDATE " + Constants.TABLE_UTILISATEUR + " SET connect = false,"
			+ "derniere_connexion=? WHERE id=?";

	private final String SQL_GET_USER_PRENOM_IF_EMAIL_EXIST = "SELECT prenoms FROM " + Constants.TABLE_UTILISATEUR
			+ " WHERE email=?";

	private final String SQL_UPDATE_PASSWORD = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET password=? WHERE email=?";

	private final String SQL_GET_USER_WITH_TOKEN = "SELECT id,nom,prenoms,initial,email,profil,telephone,date_naissance,sexe_id,pays_id,"
			+ "fonction,site,facebook,twitter,linkedin,youtube,biographie,photo,objectif_id,domaine_favoris,bloquer,supprimer FROM "
			+ Constants.TABLE_UTILISATEUR + " WHERE token=?";

	private final String SQL_ACTIVATE_COMPTE = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET active=true,connect=true,token=null,profil=? WHERE id=?";

	private final String SQL_UPDATE_USER_INFOS = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET nom=?,prenoms=?,profil=?,date_naissance=?,telephone=?,sexe_id=?,pays_id=? WHERE id=?";

	private final String SQL_USER_NEW_EMAIL_EXIST = "SELECT id FROM " + Constants.TABLE_UTILISATEUR
			+ " WHERE email = ? AND id != ?";

	private final String SQL_UPDATE_EMAIL = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET email=?,password=?,token=?,active=? WHERE id=?";

	private final String SQL_UPDATE_PROFIL_INSTRUCTEUR = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET fonction=?,site=?,facebook=?,twitter=?,linkedin=?,youtube=?,biographie=? WHERE id=?";

	private final String SQL_UPDATE_AUTEUR_RATING = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET rating=? WHERE id=?";

	private final String SQL_GET_HUIT_DERNIERS_AUTEUR = "SELECT nom,prenoms,fonction,rating,nombre_formation,profil,photo FROM "
			+ Constants.TABLE_UTILISATEUR + " WHERE nombre_formation>0 AND active=true ORDER BY rating DESC LIMIT 0,"
			+ Constants.NOMBRE_FORMATION_PAR_PAGE;

	private final String SQL_GET_FORMATEUR_INFO = "SELECT id,nom,prenoms,initial,fonction,facebook,twitter,linkedin,youtube,site,biographie,"
			+ "rating,nombre_formation,photo,supprimer,bloquer FROM " + Constants.TABLE_UTILISATEUR + " WHERE profil=?";

	private final String SQL_ADD_OBJECTIF = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET objectif_id=? WHERE email=?";

	private final String SQL_ADD_DOMAINE_FAVORIS = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET domaine_favoris=? WHERE email=?";

	private final String SQL_UPDATE_PROFIL_PICTURE = "UPDATE " + Constants.TABLE_UTILISATEUR
			+ " SET photo=? WHERE id=?";

	private final String SQL_GET_FACEBOOK_USER = "SELECT id,nom,prenoms,initial,email,profil,telephone,date_naissance,sexe_id,pays_id,"
			+ "fonction,site,facebook,twitter,linkedin,youtube,biographie,photo,objectif_id,domaine_favoris,bloquer,supprimer FROM "
			+ Constants.TABLE_UTILISATEUR + " WHERE fb_id=?";

	private final String SQL_CREATE_FACEBOOK_USER = "INSERT INTO " + Constants.TABLE_UTILISATEUR
			+ "(fb_id,nom,prenoms,initial,email,date_creation,active,connect,profil,photo,derniere_connexion) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

	public UtilisateurDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean creerUtilisateur(final Utilisateur utilisateur) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_UTILISATEUR, true, utilisateur.getNom(),
					utilisateur.getPrenoms(), utilisateur.getInitial(), utilisateur.getEmail(),
					utilisateur.getPassword(), utilisateur.getDateCreation(), utilisateur.getToken());
			prepare.executeUpdate();
			result = prepare.getGeneratedKeys();
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

	@Override
	public boolean userEmailExist(final String email) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_USER_EMAIL_EXIST, false, email);
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

	@Override
	public boolean updateRedefinirPassword(final Utilisateur utilisateur) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_REDEFINIR_PASSWORD, false,
					utilisateur.getPassword(), utilisateur.getId());
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
	public Utilisateur connectUtilisateur(final String email, final String password) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		Utilisateur utilisateur = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CONNECT_UTILISATEUR, false, email, password);
			result = prepare.executeQuery();

			while (result.next()) {
				utilisateur = new Utilisateur();
				final Sexe sexe = new Sexe();
				final Pays pays = new Pays();
				final Objectif objectif = new Objectif();
				final Domaine domaineFavoris = new Domaine();

				sexe.setId(result.getByte("sexe_id"));

				pays.setId(result.getShort("pays_id"));

				objectif.setId(result.getByte("objectif_id"));

				domaineFavoris.setId(result.getShort("domaine_favoris"));

				utilisateur.setId(result.getLong("id"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setInitial(result.getString("initial"));
				utilisateur.setEmail(result.getString("email"));
				utilisateur.setProfil(result.getString("profil"));
				utilisateur.setTelephone(result.getString("telephone"));
				utilisateur.setDateNaissance(FunctionUtils.parseDateToStringFrFormat(result.getDate("date_naissance")));
				utilisateur.setSexe(sexe);
				utilisateur.setPays(pays);
				utilisateur.setFonction(result.getString("fonction"));
				utilisateur.setSite(result.getString("site"));
				utilisateur.setFacebook(result.getString("facebook"));
				utilisateur.setTwitter(result.getString("twitter"));
				utilisateur.setLinkedin(result.getString("linkedin"));
				utilisateur.setYoutube(result.getString("youtube"));
				utilisateur.setBiographie(result.getString("biographie"));
				utilisateur.setPhoto(result.getString("photo"));
				utilisateur.setBloquer(result.getBoolean("bloquer"));
				utilisateur.setSupprimer(result.getBoolean("supprimer"));
				utilisateur.setObjectif(objectif);
				utilisateur.setDomaineFavoris(domaineFavoris);

			}

			return utilisateur;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public void updateConnect(final Long id, final String derniereConnexion) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_CONNECT, false, derniereConnexion, id);
			prepare.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}

	}

	@Override
	public void deconnexion(final Long utilisateurId, final String derniereConnexion) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DECONNEXION, false, derniereConnexion, utilisateurId);
			prepare.executeUpdate();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}

	}

	@Override
	public String getUserPrenomIfEmailExist(final String email) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_PRENOM_IF_EMAIL_EXIST, false, email);
			result = prepare.executeQuery();
			if (result.first()) {
				return result.getString("prenoms");
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean updatePassword(final String password, final String email) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_PASSWORD, false, password, email);
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
	public Utilisateur getUserWithToken(final String token) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final Utilisateur utilisateur = new Utilisateur();
		final Pays pays = new Pays();
		final Sexe sexe = new Sexe();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_WITH_TOKEN, false, token);
			result = prepare.executeQuery();
			while (result.next()) {
				sexe.setId(result.getByte("sexe_id"));
				pays.setId(result.getShort("pays_id"));
				utilisateur.setId(result.getLong("id"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setEmail(result.getString("email"));
				utilisateur.setProfil(result.getString("profil"));
				utilisateur.setInitial(result.getString("initial"));
				utilisateur.setTelephone(result.getString("telephone"));
				utilisateur.setDateNaissance(FunctionUtils.parseDateToStringFrFormat(result.getDate("date_naissance")));
				utilisateur.setSexe(sexe);
				utilisateur.setPays(pays);
				utilisateur.setFonction(result.getString("fonction"));
				utilisateur.setSite(result.getString("site"));
				utilisateur.setFacebook(result.getString("facebook"));
				utilisateur.setTwitter(result.getString("twitter"));
				utilisateur.setLinkedin(result.getString("linkedin"));
				utilisateur.setYoutube(result.getString("youtube"));
				utilisateur.setBiographie(result.getString("biographie"));
			}

			return utilisateur;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean activeCompte(final Utilisateur utilisateur) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ACTIVATE_COMPTE, false, utilisateur.getProfil(),
					utilisateur.getId());
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
	public boolean updateUserInfo(final Utilisateur utilisateur) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_USER_INFOS, false, utilisateur.getNom(),
					utilisateur.getPrenoms(), utilisateur.getProfil(),
					FunctionUtils.parseStringToDate(utilisateur.getDateNaissance()), utilisateur.getTelephone(),
					utilisateur.getSexe().getId(), utilisateur.getPays().getId(), utilisateur.getId());
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
	public boolean userNewEmailExist(final String email, final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_USER_NEW_EMAIL_EXIST, false, email, utilisateurId);
			result = prepare.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return false;
	}

	@Override
	public boolean updateEmail(final Utilisateur utilisateur) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_EMAIL, false, utilisateur.getEmail(),
					utilisateur.getPassword(), utilisateur.getToken(), utilisateur.isActive(), utilisateur.getId());
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
	public boolean updateProfilInstructeur(final Utilisateur utilisateur) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_PROFIL_INSTRUCTEUR, false,
					utilisateur.getFonction(), utilisateur.getSite(), utilisateur.getFacebook(),
					utilisateur.getTwitter(), utilisateur.getLinkedin(), utilisateur.getYoutube(),
					utilisateur.getBiographie(), utilisateur.getId());

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
	public boolean updateAuteurRating(final Float moyenneAuteur, final Long auteurId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_AUTEUR_RATING, false, moyenneAuteur, auteurId);
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
	public List<Utilisateur> getHuitDerniersAuteur() {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Utilisateur> listeHuitDerniersAuteur = new ArrayList<Utilisateur>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_HUIT_DERNIERS_AUTEUR, false);
			result = prepare.executeQuery();

			while (result.next()) {
				final Utilisateur auteur = new Utilisateur();

				auteur.setNom(result.getString("nom"));
				auteur.setPrenoms(result.getString("prenoms"));
				auteur.setFonction(result.getString("fonction"));
				auteur.setRating(result.getFloat("rating"));
				auteur.setNombreFormation(result.getShort("nombre_formation"));
				auteur.setProfil(result.getString("profil"));
				auteur.setPhoto(result.getString("photo"));

				listeHuitDerniersAuteur.add(auteur);
			}

			return listeHuitDerniersAuteur;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public Utilisateur getFormateurInfo(final String profil) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		Utilisateur utilisateur = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATEUR_INFO, false, profil);
			result = prepare.executeQuery();

			while (result.next()) {

				utilisateur = new Utilisateur();

				utilisateur.setId(result.getLong("id"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setInitial(result.getString("initial"));
				utilisateur.setFonction(result.getString("fonction"));
				utilisateur.setFacebook(result.getString("facebook"));
				utilisateur.setTwitter(result.getString("twitter"));
				utilisateur.setLinkedin(result.getString("linkedin"));
				utilisateur.setYoutube(result.getString("youtube"));
				utilisateur.setSite(result.getString("site"));
				utilisateur.setBiographie(result.getString("biographie"));
				utilisateur.setRating(result.getFloat("rating"));
				utilisateur.setNombreFormation(result.getShort("nombre_formation"));
				utilisateur.setPhoto(result.getString("photo"));
				utilisateur.setSupprimer(result.getBoolean("supprimer"));
				utilisateur.setBloquer(result.getBoolean("bloquer"));
			}
			return utilisateur;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean addObjectif(final byte objectifId, final String utilisateurEmail) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_OBJECTIF, false, objectifId, utilisateurEmail);

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
	public boolean addDomaineFavoris(final short domaineId, final String utilisateurEmail) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_DOMAINE_FAVORIS, false, domaineId, utilisateurEmail);

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
	public boolean updateProfilPicture(final String profilSource, final Long utilisateurId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_PROFIL_PICTURE, false, profilSource, utilisateurId);

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
	public Utilisateur getFacebookUserExit(final long fbId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		Utilisateur utilisateur = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FACEBOOK_USER, false, fbId);
			result = prepare.executeQuery();

			while (result.next()) {
				utilisateur = new Utilisateur();
				final Sexe sexe = new Sexe();
				final Pays pays = new Pays();
				final Objectif objectif = new Objectif();
				final Domaine domaineFavoris = new Domaine();

				sexe.setId(result.getByte("sexe_id"));

				pays.setId(result.getShort("pays_id"));

				objectif.setId(result.getByte("objectif_id"));

				domaineFavoris.setId(result.getShort("domaine_favoris"));

				utilisateur.setId(result.getLong("id"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));
				utilisateur.setInitial(result.getString("initial"));
				utilisateur.setEmail(result.getString("email"));
				utilisateur.setProfil(result.getString("profil"));
				utilisateur.setTelephone(result.getString("telephone"));
				utilisateur.setDateNaissance(FunctionUtils.parseDateToStringFrFormat(result.getDate("date_naissance")));
				utilisateur.setSexe(sexe);
				utilisateur.setPays(pays);
				utilisateur.setFonction(result.getString("fonction"));
				utilisateur.setSite(result.getString("site"));
				utilisateur.setFacebook(result.getString("facebook"));
				utilisateur.setTwitter(result.getString("twitter"));
				utilisateur.setLinkedin(result.getString("linkedin"));
				utilisateur.setYoutube(result.getString("youtube"));
				utilisateur.setBiographie(result.getString("biographie"));
				utilisateur.setPhoto(result.getString("photo"));
				utilisateur.setBloquer(result.getBoolean("bloquer"));
				utilisateur.setSupprimer(result.getBoolean("supprimer"));
				utilisateur.setObjectif(objectif);
				utilisateur.setDomaineFavoris(domaineFavoris);

			}

			return utilisateur;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public Long createFacebookUser(final Utilisateur utilisateur) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREATE_FACEBOOK_USER, true, utilisateur.getFbId(),
					utilisateur.getNom(), utilisateur.getPrenoms(), utilisateur.getInitial(), utilisateur.getEmail(),
					utilisateur.getDateCreation(), utilisateur.isActive(), utilisateur.isConnect(),
					utilisateur.getProfil(), utilisateur.getPhoto(), utilisateur.getDerniereConnexion());
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

}
