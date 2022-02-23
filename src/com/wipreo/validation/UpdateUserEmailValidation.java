package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;
import com.wipreo.utils.EnvoyerMail;
import com.wipreo.utils.FunctionUtils;

public class UpdateUserEmailValidation {

	private final String CHAMP_OLD_EMAIL = "email";
	private final String CHAMP_NEW_EMAIL = "newEmail";
	private final String OBJET_EMAIL = "Modification de l'adresse email Wipreo";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	private UtilisateurDao utilisateurDao = null;

	public UpdateUserEmailValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validUpdateEmail(final HttpServletRequest request) {
		final String oldEmail = FunctionUtils.getValueChamp(request, this.CHAMP_OLD_EMAIL);
		final String newEmail = FunctionUtils.getValueChamp(request, this.CHAMP_NEW_EMAIL);

		final HttpSession session = request.getSession();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		try {
			this.validEmail(oldEmail, this.utilisateurDao);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_OLD_EMAIL, e.getMessage());
		}

		try {
			this.validEmail(newEmail, utilisateur.getId());
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_NEW_EMAIL, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final String token = FunctionUtils.genererToken(utilisateur.getProfil() + utilisateur.getEmail());

			// Mot de passe generer a envoyer à l'utilisateur
			final String passwordGenerated = FunctionUtils.genererPasswordCrypter(Constants.PASSWORD_GENERATE_ALGORITHM,
					utilisateur.getEmail());

			// Mot de passe crypté a enregistrer en base de données
			final String passwordCrypted = FunctionUtils.crypterPass(Constants.PASSWORD_ALGORITHM, passwordGenerated);

			// Creation du texte du mail qu'on envera à l'utilisateur
			final String texteEmail = "<h3 style='text-align:center'>Bonjour " + utilisateur.getPrenoms() + "</h3>"
					+ "<p style='text-align:justify;'>Vous avez souhaité changer d'adresse mail, "
					+ "vous trouverez donc ci-dessous vos nouveaux identifiants de connexion</p>"
					+ "<p style='margin-bottom:30px;'>Email : " + newEmail + "<br />" + "Mot de passe : "
					+ passwordGenerated + "</p>"
					+ "<p style='text-align:center;margin-bottom:30px;'><a style='text-decoration:none;padding:15px;color:#fff;background:#f07b16' href='"
					+ Constants.HTTP_BASE_APP + "/activation?token=" + token
					+ "'>Confirmer la modification de votre adresse mail</a></p> <p>Si vous ne parvenez pas à activer en cliquant sur le bouton, "
					+ "copiez-collez le lien suivant dans votre navigateur.</p>" + "<p>" + Constants.HTTP_BASE_APP
					+ "/activation?token=" + token + "</p>"
					+ "<p style='text-align:justify'>Confirmer votre nouvelle adresse mail et apprenez en toute simplicité.</p> "
					+ "<p>A bientôt,<br />L'équipe Gehant</p>";

			utilisateur.setEmail(newEmail);
			utilisateur.setPassword(passwordCrypted);
			utilisateur.setToken(token);
			utilisateur.setActive(false);

			try {
				if (EnvoyerMail.envoyer(Constants.NOM_EMETTEUR, utilisateur.getEmail(), texteEmail, this.OBJET_EMAIL)) {
					this.utilisateurDao.updateEmail(utilisateur);
					session.invalidate();
					return true;
				}
			} catch (final Exception e) {
				e.printStackTrace();
				this.setErreur("emailSend", "Une erreur est survenue lors de la creation de l'utilisateur."
						+ "Vérifier votre connexion internet et réessayer ulterieurement.");
				return false;
			}

		}

		return false;
	}

	private void validEmail(final String email, final UtilisateurDao utilisateurDao) throws Exception {
		if (!utilisateurDao.userEmailExist(email)) {
			throw new Exception("L'adresse email n'est pas correcte.");
		}
	}

	private void validEmail(final String email, final Long utilisateurId) throws Exception {
		if (email.isEmpty() || email == null) {
			throw new Exception("Le champ nouvelle adresse email ne peut être vide.");
		} else if (email.length() < 10) {
			throw new Exception("Le champ nouvelle adresse email ne peut être inférieur à 10 caractères.");
		} else if (email.length() > 250) {
			throw new Exception("Le champ nouvelle adresse email ne peut être supèrieur à 250 caractères.");
		} else if (!email.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$")) {
			throw new Exception("Le format de la nouvelle adresse email n'est pas correct.");
		} else if (this.utilisateurDao.userNewEmailExist(email, utilisateurId)) {
			throw new Exception("La nouvelle adresse email existe déja.Veuillez choisir une autre titre.");
		}
	}
}
