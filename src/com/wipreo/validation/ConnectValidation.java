package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FunctionUtils;

public class ConnectValidation {

	private final String CHAMP_EMAIL = "email";
	private final String CHAMP_PASSWORD = "password";
	private String message;

	public String getMessage() {
		return this.message;
	}

	private UtilisateurDao utilisateurDao = null;

	public ConnectValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur connectUtilisateur(final HttpServletRequest request) {

		final String email = FunctionUtils.getValueChamp(request, this.CHAMP_EMAIL);
		final String password = FunctionUtils.getValueChamp(request, this.CHAMP_PASSWORD);

		final String passwordCrypted = FunctionUtils.crypterPass(Constants.PASSWORD_ALGORITHM, password);
		final Utilisateur utilisateur = this.utilisateurDao.connectUtilisateur(email, passwordCrypted);

		if (utilisateur == null) {
			this.message = "L'adresse mail et/ou le mot de passe est incorrect.";
		} else if (utilisateur != null && (utilisateur.isBloquer() || utilisateur.isSupprimer())) {
			this.message = "Votre compte a été bloquer. Si vous pensez qu'il s'agit d'une erreur, veuillez nous contacter.";
		} else {
			final String derniereConnexion = FunctionUtils.parseDateTimeToStringForBdd(new DateTime());
			this.utilisateurDao.updateConnect(utilisateur.getId(), derniereConnexion);
			return utilisateur;
		}

		return null;
	}

}
