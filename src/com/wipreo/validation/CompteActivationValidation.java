package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.FunctionUtils;

public class CompteActivationValidation {

	private final String CHAMP_TOKEN = "token";

	private final UtilisateurDao utilisateurDao;

	public CompteActivationValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur validCompteActivation(final HttpServletRequest request) {

		final String token = FunctionUtils.getValueChamp(request, this.CHAMP_TOKEN);

		final Utilisateur utilisateur = this.utilisateurDao.getUserWithToken(token);
		final String profil = utilisateur.getId() + " " + utilisateur.getPrenoms() + " " + utilisateur.getNom();

		utilisateur.setProfil(FunctionUtils.supprimerCaracteresSpeciaux(profil.toLowerCase()));

		if (utilisateur != null && this.utilisateurDao.activeCompte(utilisateur)) {
			return utilisateur;
		}

		return null;

	}

}
