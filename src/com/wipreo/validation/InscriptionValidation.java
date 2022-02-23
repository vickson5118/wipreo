package com.wipreo.validation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.Constants;
import com.wipreo.utils.EnvoyerMail;
import com.wipreo.utils.FunctionUtils;

public class InscriptionValidation {

	public static final String ATT_UTILISATEUR_EMAIL = "email";
	private final String OBJET_EMAIL = "Création d'un compte Wipreo";
	private final String CHAMP_NOM = "nom";
	private final String CHAMP_PRENOMS = "prenoms";
	private final String CHAMP_EMAIL = "email";

	private final Map<String, String> erreurs = new HashMap<String, String>();
	private UtilisateurDao utilisateurDao = null;

	public InscriptionValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validInscription(final HttpServletRequest request) {
		final String nom = FunctionUtils.getValueChamp(request, this.CHAMP_NOM);
		final String prenom = FunctionUtils.getValueChamp(request, this.CHAMP_PRENOMS);
		final String email = FunctionUtils.getValueChamp(request, this.CHAMP_EMAIL);

		try {
			validNom(nom, this.CHAMP_NOM, 2, 100);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_NOM, e.getMessage());
		}

		try {
			validNom(prenom, this.CHAMP_PRENOMS, 2, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_PRENOMS, e.getMessage());
		}

		try {
			this.validEmail(email);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_EMAIL, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final Utilisateur utilisateur = new Utilisateur();
			final Date today = new Date();
			final String tokenScheme = prenom + " " + nom;

			utilisateur.setNom(FunctionUtils.premiereLettreEnMajuscule(nom));
			utilisateur.setPrenoms(FunctionUtils.mettreEnMajusculeApresEspace(prenom));
			utilisateur.setEmail(email);
			utilisateur.setInitial(FunctionUtils.getUtilisateurInitial(nom, prenom));
			utilisateur.setToken(FunctionUtils.genererToken(tokenScheme + email));
			utilisateur.setDateCreation(FunctionUtils.parseDateToStringForBdd(today));

			// Mot de passe generer a envoyer à l'utilisateur
			final String passwordGenerated = FunctionUtils.genererPasswordCrypter(Constants.PASSWORD_GENERATE_ALGORITHM,
					utilisateur.getEmail());

			// Mot de passe crypté a enregistrer en base de données
			final String passwordCrypted = FunctionUtils.crypterPass(Constants.PASSWORD_ALGORITHM, passwordGenerated);

			// Creation du texte du mail qu'on envera à l'utilisateur
			final String texteEmail = "<h3 style='text-align:center;font-size: 25px;font-weight:bold;'>Bonjour "
					+ utilisateur.getPrenoms() + "</h3>"
					+ "<p style='text-align:justify;'>Vous y êtes. <br />Plus qu'une dernière étape et vous pourrez bénéficier de vos formations<br />"
					+ "Vous trouverez ci-dessous vos identifiants de connexion</p>"
					+ "<p style='margin-bottom:30px;'>Email : " + utilisateur.getEmail() + "<br />" + "Mot de passe : "
					+ passwordGenerated + "</p>"
					+ "<p style='text-align:center;margin-bottom:30px;'><a style='text-decoration:none;padding:15px;color:#fff;background:#f07b16' href='"
					+ Constants.HTTP_BASE_APP + "/activation?token=" + utilisateur.getToken()
					+ "'>Activez votre compte maintenant</a></p> <p>Si vous ne parvenez pas à activer en cliquant sur le bouton, "
					+ "copiez-collez le lien suivant dans votre navigateur.</p>" + "<p>" + Constants.HTTP_BASE_APP
					+ "/activation?token=" + utilisateur.getToken() + "</p>"
					+ "<p style='text-align:justify'>Activez votre compte et apprenez en toute simplicité.</p> "
					+ "<p>A bientôt,<br />L'équipe Wipreo</p>";

			utilisateur.setPassword(passwordCrypted);

			try {
				if (EnvoyerMail.envoyer(Constants.NOM_EMETTEUR, utilisateur.getEmail(), texteEmail, this.OBJET_EMAIL)
						&& this.utilisateurDao.creerUtilisateur(utilisateur)) {

					// Mette l'email en session pour pouvoir enregistrer les préferences des
					// utilisateurs;
					request.getSession().setAttribute(ATT_UTILISATEUR_EMAIL, utilisateur.getEmail());

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

	public static void validNom(final String nom, final String nomChamp, final int lenghtMin, final int lengthMax)
			throws Exception {
		if (nom.isEmpty() || nom == null) {
			throw new Exception("Le champ " + nomChamp + " ne peut être vide.");
		} else if (nom.length() < lenghtMin) {
			throw new Exception("Le champ " + nomChamp + " ne peut être inférieur à " + lenghtMin + " caractères.");
		} else if (nom.length() > lengthMax) {
			throw new Exception("Le champ " + nomChamp + " ne peut être supèrieur à " + lengthMax + " caractères.");
		}
	}

	private void validEmail(final String email) throws Exception {
		if (email.isEmpty() || email == null) {
			throw new Exception("Le champ adresse email ne peut être vide.");
		} else if (email.length() < 10) {
			throw new Exception("Le champ adresse email ne peut être inférieur à 10 caractères.");
		} else if (email.length() > 250) {
			throw new Exception("Le champ adresse email ne peut être supèrieur à 250 caractères.");
		} else if (!email.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$")) {
			throw new Exception("Le format de l'adresse email n'est pas correct.");
		} else if (this.utilisateurDao.userEmailExist(email)) {
			throw new Exception("L'adresse email existe déja.Veuillez choisir une autre titre.");
		}
	}

}
