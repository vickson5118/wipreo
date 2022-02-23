package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.Constants;
import com.wipreo.utils.EnvoyerMail;
import com.wipreo.utils.FunctionUtils;

public class ResetPasswordValidation {

	private final String CHAMP_EMAIL = "email";
	private final String ATT_OBJECT = "Mot de passe oublié";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	private UtilisateurDao utilisateurDao = null;

	public ResetPasswordValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validResetPassword(final HttpServletRequest request) {
		final String email = FunctionUtils.getValueChamp(request, this.CHAMP_EMAIL);

		if (email == null || email.isEmpty()) {
			this.setErreur(this.CHAMP_EMAIL, "L'adresse email n'existe pas.");
			return false;
		}

		final String prenoms = this.utilisateurDao.getUserPrenomIfEmailExist(email);

		if (prenoms != null) {

			final String passwordGenerated = FunctionUtils.genererPasswordCrypter(Constants.PASSWORD_GENERATE_ALGORITHM,
					email);
			final String passwordCrypted = FunctionUtils.crypterPass(Constants.PASSWORD_ALGORITHM, passwordGenerated);

			final String texteEmail = "<h3 style='text-align:center;font-size: 25px;font-weight:bold;'>Bonjour "
					+ prenoms + "</h3>"
					+ "<p style='text-align:justify'>Votre nouveau mot de passe a bien été généré.<br />Ci-dessous vos nouveaux identifiants de connexion</p>"
					+ "<p>Email : " + email + "<br />" + "Mot de passe : " + passwordGenerated + "</p>"
					+ "<p style='text-align:justify'><a style='text-decoration:none;padding:15px;color:#fff;background:#f07b16' href='"
					+ Constants.HTTP_BASE_APP
					+ "/compte/login'>Connectez-vous </a>       et apprenez en toute simplicité.</p> "
					+ "<p>A bientôt,<br />L'équipe Wipreo</p>";

			try {
				EnvoyerMail.envoyer(Constants.NOM_EMETTEUR, email, texteEmail, this.ATT_OBJECT);
				return this.utilisateurDao.updatePassword(passwordCrypted, email);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			this.setErreur(this.CHAMP_EMAIL, "L'adresse email n'existe pas.");
		}

		return false;

	}

}
