package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.FunctionUtils;

public class ProfilInstructeurValidation {

	private final String CHAMP_FONCTION = "fonction";
	private final String CHAMP_SITE = "site";
	private final String CHAMP_FACEBOOK = "facebook";
	private final String CHAMP_TWITTER = "twitter";
	private final String CHAMP_LINKEDIN = "linkedin";
	private final String CHAMP_YOUTUBE = "youtube";
	private final String CHAMP_BIOGRAPHIE = "biographie";

	private final Map<String, String> erreurs = new HashMap<String, String>();
	private UtilisateurDao utilisateurDao = null;

	public ProfilInstructeurValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validProfilUtilisateur(final HttpServletRequest request) {
		final String fonction = FunctionUtils.getValueChamp(request, this.CHAMP_FONCTION);
		final String site = FunctionUtils.getValueChamp(request, this.CHAMP_SITE);
		final String facebook = FunctionUtils.getValueChamp(request, this.CHAMP_FACEBOOK);
		final String twitter = FunctionUtils.getValueChamp(request, this.CHAMP_TWITTER);
		final String linkedin = FunctionUtils.getValueChamp(request, this.CHAMP_LINKEDIN);
		final String youtube = FunctionUtils.getValueChamp(request, this.CHAMP_YOUTUBE);
		final String biographie = FunctionUtils.getValueChamp(request, this.CHAMP_BIOGRAPHIE);

		try {
			this.validTexte(fonction, this.CHAMP_FONCTION, 5, 50);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_FONCTION, e.getMessage());
		}

		try {
			this.validTexte(site, this.CHAMP_SITE, 5, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_SITE, e.getMessage());
		}

		try {
			this.validTexte(facebook, this.CHAMP_FACEBOOK, 5, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_FACEBOOK, e.getMessage());
		}

		try {
			this.validTexte(twitter, this.CHAMP_TWITTER, 5, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_TWITTER, e.getMessage());
		}

		try {
			this.validTexte(linkedin, this.CHAMP_LINKEDIN, 5, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_LINKEDIN, e.getMessage());
		}

		try {
			this.validTexte(youtube, this.CHAMP_YOUTUBE, 5, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_YOUTUBE, e.getMessage());
		}

		try {
			this.validTexte(biographie, this.CHAMP_BIOGRAPHIE, 10, 1000);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_BIOGRAPHIE, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();
			final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

			utilisateur.setFonction(fonction != null ? FunctionUtils.premiereLettreEnMajuscule(fonction) : fonction);
			utilisateur.setSite(site);
			utilisateur.setFacebook(facebook);
			utilisateur.setTwitter(twitter);
			utilisateur.setLinkedin(linkedin);
			utilisateur.setYoutube(youtube);
			utilisateur.setBiographie(biographie);

			this.utilisateurDao.updateProfilInstructeur(utilisateur);
			session.removeAttribute(Inscription.ATT_SESSION_UTILISATEUR);
			session.setAttribute(Inscription.ATT_SESSION_UTILISATEUR, utilisateur);
			return true;

		}

		return false;
	}

	private void validTexte(final String texte, final String nomChamp, final int lenghtMin, final int lengthMax)
			throws Exception {
		if (texte != null && !texte.isEmpty() && texte.length() < lenghtMin) {
			throw new Exception("Le champ " + nomChamp + " ne peut être inférieur à " + lenghtMin + " caractères.");
		} else if (texte != null && !texte.isEmpty() && texte.length() > lengthMax) {
			throw new Exception("Le champ " + nomChamp + " ne peut être supèrieur à " + lengthMax + " caractères.");
		}
	}

}
