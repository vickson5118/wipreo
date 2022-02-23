package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

public class RedefinirPasswordValidation {

	private UtilisateurDao utilisateurDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	private final String CHAMP_PASSWORD = "password";
	private final String CHAMP_REPEAT_PASSWORD = "repeatPassword";

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public RedefinirPasswordValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public boolean validRedefinirPassword(final HttpServletRequest request) {

		final String password = FunctionUtils.getValueChamp(request, this.CHAMP_PASSWORD);
		final String repeatPassword = FunctionUtils.getValueChamp(request, this.CHAMP_REPEAT_PASSWORD);

		try {
			this.validPassword(password, repeatPassword);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_PASSWORD, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();
			final Utilisateur utilisateur = ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR));

			utilisateur.setPassword(FunctionUtils.crypterPass(Constants.PASSWORD_ALGORITHM, password));

			return this.utilisateurDao.updateRedefinirPassword(utilisateur);
		}

		return false;
	}

	private void validPassword(final String password, final String repeatPassword) throws Exception {
		if (password == null || password.trim().isEmpty()) {
			throw new Exception("Le champ mot de passe ne peut être vide.");
		} else if (password.trim().length() < 8) {
			throw new Exception("Le champ mot de passe ne doit pas être inférieur à 8 caractères.");
		} else if (!password.equals(repeatPassword)) {
			throw new Exception("Les mots de passe ne sont pas identiques.");
		} else if (password.matches("[^a-z]+")) {
			throw new Exception("Le mot de passe doit conténir au moins un caractère miniscule.");
		} else if (password.matches("[^A-Z]+")) {
			throw new Exception("Le mot de passe doit conténir au moins un caractère majuscule.");
		} else if (password.matches("[^0-9]+")) {
			throw new Exception("Le mot de passe doit conténir au moins un chiffre.");
		} else if (password.matches("[^@~&*+°|#^ù%µ$<>!:;=-]+")) {
			throw new Exception(
					"Le mot de passe doit conténir au moins un caractère spécial suivant : @~&*+°|#^ù%µ$<>!:;=-");
		}
	}

}
