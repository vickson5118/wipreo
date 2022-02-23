package com.wipreo.validation;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.FunctionUtils;

public class FacebookConnexionValidation {

	private final String CHAMP_ID = "id";
	private final String CHAMP_NOM = "nom";
	private final String CHAMP_PRENOMS = "prenoms";
	private final String CHAMP_EMAIL = "email";
	private final String CHAMP_PHOTO = "photo";

	private String message;

	public String getMessage() {
		return this.message;
	}

	private UtilisateurDao utilisateurDao = null;

	public FacebookConnexionValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public Utilisateur validFacebookConnexion(final HttpServletRequest request) {
		final String id = FunctionUtils.getValueChamp(request, this.CHAMP_ID);
		final String nom = FunctionUtils.getValueChamp(request, this.CHAMP_NOM);
		final String prenoms = FunctionUtils.getValueChamp(request, this.CHAMP_PRENOMS);
		final String email = FunctionUtils.getValueChamp(request, this.CHAMP_EMAIL);
		final String photo = FunctionUtils.getValueChamp(request, this.CHAMP_PHOTO);

		System.out.println("id: " + id);

		final Utilisateur utilisateur = this.utilisateurDao.getFacebookUserExit(Long.parseLong(id));

		if (utilisateur != null && !utilisateur.isBloquer() && !utilisateur.isSupprimer()) {
			System.out.println("1");
			return utilisateur;
		} else if (utilisateur != null && (utilisateur.isBloquer() || utilisateur.isSupprimer())) {
			System.out.println("2");
			this.message = "Votre compte a été bloquer. Si vous pensez qu'il s'agit d'une erreur, veuillez nous contacter avec ce compte.";
			return null;
		} else {

			if (this.utilisateurDao.userEmailExist(email)) {
				this.message = "Un compte associé à cette adresse email existe. Veuillez vous connectez";
				return null;
			}

			System.out.println("3");
			final Utilisateur facebookUtilisateur = new Utilisateur();

			facebookUtilisateur.setFbId(Long.parseLong(id));
			facebookUtilisateur.setNom(nom);
			facebookUtilisateur.setPrenoms(prenoms);
			facebookUtilisateur.setEmail(email);
			facebookUtilisateur.setPhoto(photo);
			facebookUtilisateur.setActive(true);
			facebookUtilisateur.setInitial(FunctionUtils.getUtilisateurInitial(nom, prenoms));
			facebookUtilisateur.setProfil(FunctionUtils.supprimerCaracteresSpeciaux(prenoms + " " + nom + " " + id));
			facebookUtilisateur.setDateCreation(FunctionUtils.parseDateToStringForBdd(new Date()));
			facebookUtilisateur.setConnect(true);
			facebookUtilisateur.setDerniereConnexion(FunctionUtils.parseDateTimeToStringForBdd(new DateTime()));

			final Long faceUserId = this.utilisateurDao.createFacebookUser(facebookUtilisateur);

			if (faceUserId != null) {
				facebookUtilisateur.setId(faceUserId);
				return facebookUtilisateur;
			}

		}

		return null;
	}

}
