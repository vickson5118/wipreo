package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Achat;
import com.wipreo.beans.Domaine;
import com.wipreo.beans.Facture;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;
import com.wipreo.utils.FunctionUtils;

public class AchatDaoImpl implements AchatDao {

	private DaoFactory factory = null;

	private final String SQL_AJOUTER_PANIER = "INSERT INTO " + Constants.TABLE_ACHAT
			+ "(utilisateur_id,formation_id) VALUES(?,?)";

	private final String SQL_GET_USER_PANIER_FORMATION = "SELECT f.id,f.titre,f.titre_url AS formation_titre_url,"
			+ "f.illustration,f.nombre_heures,f.prix,f.rating,f.date_creation,p.id AS panier_id,u.nom,u.prenoms,d.titre_url FROM "
			+ Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_ACHAT + " AS p INNER JOIN "
			+ Constants.TABLE_UTILISATEUR + " AS u INNER JOIN " + Constants.TABLE_DOMAINE
			+ " AS d ON p.formation_id=f.id AND f.domaine_id=d.id AND p.utilisateur_id=u.id WHERE p.utilisateur_id=? "
			+ "AND p.is_paid=false";

	private final String SQL_DELETE_PANIER = "DELETE FROM " + Constants.TABLE_ACHAT + " WHERE id=?";

	private final String SQL_GET_PANIER_COUNT = "SELECT COUNT(*) AS nombre_formation_panier FROM "
			+ Constants.TABLE_ACHAT + " WHERE utilisateur_id=? AND is_paid=false";

	private final String SQL_BUY_PANIER = "UPDATE " + Constants.TABLE_ACHAT
			+ " SET is_paid=?,facture_id=? WHERE utilisateur_id=? AND is_paid=false";

	private final String SQL_GET_USER_PANIER_BUY_WITH_FORMATION = " SELECT f.designation,f.date_creation,fo.titre,fo.prix,"
			+ "u.nom,u.prenoms FROM " + Constants.TABLE_ACHAT + " AS p INNER JOIN " + Constants.TABLE_FACTURE
			+ " as f INNER JOIN " + Constants.TABLE_FORMATION + " AS fo INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON p.formation_id=fo.id AND p.facture_id=f.id AND fo.auteur_id=u.id WHERE p.utilisateur_id=? "
			+ "AND f.designation=? AND p.is_paid=true";

	private final String SQL_GET_ALL_USER_FORMATION_PAID_NOT_FINISH = "SELECT p.pourcentage_lesson_view,p.nombre_lesson_view,f.id,f.titre,f.titre_url,"
			+ "f.illustration,f.rating,d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM " + Constants.TABLE_ACHAT
			+ " AS p INNER JOIN " + Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE
			+ " AS d INNER JOIN " + Constants.TABLE_UTILISATEUR
			+ " AS u ON p.formation_id = f.id AND f.domaine_id=d.id AND f.auteur_id = u.id "
			+ "WHERE p.utilisateur_id=? AND p.finish=false AND p.is_paid=true AND f.redaction_finished = true AND f.validated = true "
			+ "AND f.bloquer = false AND f.supprimer = false ORDER BY p.id DESC";

	private final String SQL_GET_ALL_USER_FORMATION_PAID_FINISH = "SELECT f.id,f.titre,f.titre_url,f.illustration,f.rating,"
			+ "d.titre_url AS domaine_titre_url,u.nom,u.prenoms FROM " + Constants.TABLE_ACHAT + " AS p INNER JOIN "
			+ Constants.TABLE_FORMATION + " AS f INNER JOIN " + Constants.TABLE_DOMAINE + " AS d INNER JOIN "
			+ Constants.TABLE_UTILISATEUR
			+ " AS u ON p.formation_id = f.id AND f.domaine_id=d.id AND f.auteur_id = u.id "
			+ "WHERE p.utilisateur_id=? AND p.is_paid=true AND p.finish=true AND f.redaction_finished = true AND "
			+ "f.validated = true AND f.bloquer = false AND f.supprimer = false ORDER BY p.id DESC";

	private final String SQL_GET_ALL_USER_FORMATION_PAID_ID = "SELECT f.id AS formation_id FROM "
			+ Constants.TABLE_ACHAT + " AS p INNER JOIN " + Constants.TABLE_FORMATION
			+ " AS f ON p.formation_id=f.id WHERE utilisateur_id=? AND is_paid=true";

	private final String SQL_GET_ALL_USER_FORMATION_NO_PAID_ID = "SELECT f.id AS formation_id FROM "
			+ Constants.TABLE_ACHAT + " AS p INNER JOIN " + Constants.TABLE_FORMATION
			+ " AS f ON p.formation_id=f.id WHERE utilisateur_id=? AND is_paid=false";

	private final String SQL_GET_PANIER_BUY_FORMATION_FOR_VALIDATION = " SELECT fo.id,fo.titre,fo.message_bienvenue,fo.prix FROM "
			+ Constants.TABLE_ACHAT + " AS p INNER JOIN " + Constants.TABLE_FORMATION
			+ " AS fo ON p.formation_id=fo.id  WHERE p.utilisateur_id=? AND p.is_paid=false";

	private final String SQL_UPDATE_PLUS_NOMBRE_LESSON_VIEW_AND_POURCENTAGE_AND_FINISH = "UPDATE "
			+ Constants.TABLE_ACHAT + " AS p SET nombre_lesson_view=nombre_lesson_view+1,pourcentage_lesson_view="
			+ "CEIL(((p.nombre_lesson_view/(SELECT nombre_lesson FROM " + Constants.TABLE_FORMATION
			+ " AS f WHERE f.id=p.formation_id))*100)),finish=CASE WHEN p.pourcentage_lesson_view < 100 "
			+ "THEN false ELSE true END WHERE p.formation_id=? AND p.utilisateur_id=?";

	private final String SQL_FORMATION_IS_BUY = "SELECT id FROM " + Constants.TABLE_ACHAT + " WHERE is_paid=true "
			+ "AND utilisateur_id=? AND formation_id=?";

	private final String SQL_FORMATION_IS_FINISH = "SELECT id FROM " + Constants.TABLE_ACHAT + " WHERE finish=true "
			+ "AND utilisateur_id=? AND formation_id=?";

	private final String SQL_ADD_CERTIFICAT_AND_FORMATION_TERMINATED = "UPDATE " + Constants.TABLE_ACHAT
			+ " SET certificat=?,formation_terminated=true WHERE utilisateur_id=? AND formation_id=?";

	private final String SQL_GET_ONE_ACHAT = "SELECT formation_terminated,certificat FROM " + Constants.TABLE_ACHAT
			+ " WHERE utilisateur_id=? AND formation_id=?";

	public AchatDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean ajouterAuPanier(final Achat panier) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_AJOUTER_PANIER, true, panier.getUtilisateur().getId(),
					panier.getFormation().getId());
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
	public List<Achat> getUserPanierFormation(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Achat> listePanier = new ArrayList<Achat>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_PANIER_FORMATION, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Formation formation = new Formation();
				final Utilisateur utilisateur = new Utilisateur();
				final Domaine domaine = new Domaine();
				final Achat panier = new Achat();

				domaine.setTitreUrl(result.getString("titre_url"));

				formation.setId(result.getLong("id"));
				formation.setTitre(result.getString("titre"));
				formation.setIllustration(result.getString("Illustration"));
				formation.setNombreHeures(result.getInt("nombre_heures"));
				formation.setPrix(result.getInt("prix"));
				formation.setRating(result.getFloat("rating"));
				formation.setTitreUrl(result.getString("formation_titre_url"));
				formation.setDomaine(domaine);
				formation.setDateCreation(FunctionUtils.parseDateToStringWithSlash(result.getDate("date_creation")));

				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenoms(result.getString("prenoms"));

				panier.setId(result.getLong("panier_id"));
				panier.setFormation(formation);
				panier.setUtilisateur(utilisateur);

				listePanier.add(panier);
			}

			return listePanier;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean deletePanier(final Long panierId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_PANIER, false, panierId);
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
	public int getPanierCount(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_PANIER_COUNT, false, utilisateurId);
			result = prepare.executeQuery();
			result.first();
			return result.getInt("nombre_formation_panier");
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return 0;
	}

	@Override
	public boolean buyPanier(final Achat panier) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_BUY_PANIER, false, panier.isPaid(),
					panier.getFacture().getId(), panier.getUtilisateur().getId());

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
	public List<Achat> getUserPanierBuyWithFormation(final Long utilisateurId, final String factureDesignation) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Achat> listePanier = new ArrayList<Achat>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_USER_PANIER_BUY_WITH_FORMATION, false, utilisateurId,
					factureDesignation);
			result = prepare.executeQuery();

			while (result.next()) {
				final Achat panier = new Achat();
				final Facture facture = new Facture();
				final Formation formation = new Formation();
				final Utilisateur auteur = new Utilisateur();

				facture.setDesignation(result.getString("designation"));
				facture.setDateCreation(
						FunctionUtils.parseDateToStringWithAllMonthName(result.getDate("date_creation")));

				auteur.setNom(result.getString("nom"));
				auteur.setPrenoms(result.getString("prenoms"));

				formation.setTitre(result.getString("titre"));
				formation.setPrix(result.getInt("prix"));
				formation.setAuteur(auteur);

				panier.setFacture(facture);
				panier.setFormation(formation);

				listePanier.add(panier);

			}
			return listePanier;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Achat> getAllUSerFormationPaidNotFInish(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Achat> listeFormationPaid = new ArrayList<Achat>();
		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_USER_FORMATION_PAID_NOT_FINISH, false,
					utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Achat paid = new Achat();
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
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				paid.setFormation(formation);
				paid.setNombreLessonView(result.getInt("nombre_lesson_view"));
				paid.setPourcentageLessonView(result.getInt("pourcentage_lesson_view"));

				listeFormationPaid.add(paid);
			}

			return listeFormationPaid;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Achat> getAllUserFormationPaidFinish(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Achat> listeFormationPaid = new ArrayList<Achat>();
		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_USER_FORMATION_PAID_FINISH, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Achat paid = new Achat();
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
				formation.setRating(result.getFloat("rating"));
				formation.setDomaine(domaine);
				formation.setAuteur(utilisateur);

				paid.setFormation(formation);

				listeFormationPaid.add(paid);
			}

			return listeFormationPaid;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Long> getAllUserFormationPaidId(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Long> listePaidId = new ArrayList<Long>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_USER_FORMATION_PAID_ID, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {

				final Long id = result.getLong("formation_id");

				listePaidId.add(id);

			}

			return listePaidId;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Long> getAllUserFormationNoPaidId(final Long utilisateurId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Long> listeNoPaidId = new ArrayList<Long>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_USER_FORMATION_NO_PAID_ID, false, utilisateurId);
			result = prepare.executeQuery();

			while (result.next()) {

				final Long id = result.getLong("formation_id");

				listeNoPaidId.add(id);

			}

			return listeNoPaidId;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Achat> getPanierBuyFormationForValidation(final Long id) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Achat> listePanierFormation = new ArrayList<Achat>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_PANIER_BUY_FORMATION_FOR_VALIDATION, false, id);
			result = prepare.executeQuery();

			while (result.next()) {
				final Achat panier = new Achat();
				final Formation formation = new Formation();

				formation.setId(result.getLong("id"));
				formation.setPrix(result.getInt("prix"));
				formation.setTitre(result.getString("titre"));
				formation.setMessageBienvenue(result.getString("message_bienvenue"));

				panier.setFormation(formation);

				listePanierFormation.add(panier);

			}

			return listePanierFormation;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean updatePlusNombreLessonViewAndPourcentageAndFinish(final Long formationId, final Long utilisateurId) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_PLUS_NOMBRE_LESSON_VIEW_AND_POURCENTAGE_AND_FINISH,
					false, formationId, utilisateurId);

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
	public boolean formationIsBuy(final Long utilisateurId, final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_FORMATION_IS_BUY, false, utilisateurId, formationId);
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
	public boolean formationIsFinish(final Long utilisateurId, final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_FORMATION_IS_FINISH, false, utilisateurId, formationId);
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
	public boolean addCertificatAndFormationTerminated(final Achat achat) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_ADD_CERTIFICAT_AND_FORMATION_TERMINATED, false,
					achat.getCertificat(), achat.getUtilisateur().getId(), achat.getFormation().getId());

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
	public Achat getOneAchat(final Long utilisateurId, final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ONE_ACHAT, false, utilisateurId, formationId);
			result = prepare.executeQuery();
			Achat achat = null;

			if (result.first()) {
				achat = new Achat();

				achat.setCertificat(result.getString("certificat"));
				achat.setFormationTerminated(result.getBoolean("formation_terminated"));
			}

			return achat;

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

}
