package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.wipreo.beans.Contact;
import com.wipreo.dao.ContactDao;
import com.wipreo.utils.FunctionUtils;

public class ContactValidation {

	private final String CHAMP_NOM = "nom";
	private final String CHAMP_PRENOMS = "prenoms";
	private final String CHAMP_TELEPHONE = "telephone";
	private final String CHAMP_EMAIL = "email";
	private final String CHAMP_OBJET = "objet";
	private final String CHAMP_MESSAGE = "message";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	private ContactDao contactDao = null;

	public ContactValidation(final ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validContact(final HttpServletRequest request) {
		final String nom = FunctionUtils.getValueChamp(request, this.CHAMP_NOM);
		final String prenoms = FunctionUtils.getValueChamp(request, this.CHAMP_PRENOMS);
		final String telephone = FunctionUtils.getValueChamp(request, this.CHAMP_TELEPHONE);
		final String email = FunctionUtils.getValueChamp(request, this.CHAMP_EMAIL);
		final String objet = FunctionUtils.getValueChamp(request, this.CHAMP_OBJET);
		final String message = FunctionUtils.getValueChamp(request, this.CHAMP_MESSAGE);

		try {
			this.validTexte(nom, this.CHAMP_NOM, 2, 100, true, true);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_NOM, e.getMessage());
		}

		try {
			this.validTexte(prenoms, this.CHAMP_PRENOMS, 2, 250, false, true);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_PRENOMS, e.getMessage());
		}

		try {
			this.validEmail(email);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_EMAIL, e.getMessage());
		}

		try {
			this.validTelephone(telephone);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_TELEPHONE, e.getMessage());
		}

		try {
			this.validTexte(objet, this.CHAMP_OBJET, 10, 500, true, true);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_OBJET, e.getMessage());
		}

		try {
			this.validTexte(message, this.CHAMP_MESSAGE, 30, 1500, true, true);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_MESSAGE, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {
			final Contact contact = new Contact();

			contact.setNom(FunctionUtils.premiereLettreEnMajuscule(nom));
			contact.setPrenoms(FunctionUtils.mettreEnMajusculeApresEspace(prenoms));
			contact.setTelephone(telephone);
			contact.setEmail(email);
			contact.setObjet(FunctionUtils.premiereLettreEnMajuscule(objet));
			contact.setMessage(FunctionUtils.premiereLettreEnMajuscule(message));
			contact.setDateEnvoi(FunctionUtils.parseDateTimeToStringForBdd(new DateTime()));

			return this.contactDao.creerContact(contact);

		}

		return false;
	}

	private void validTexte(final String nom, final String nomChamp, final int lenghtMin, final int lengthMax,
			final boolean required, final boolean limited) throws Exception {
		if ((nom.isEmpty() || nom == null) && required) {
			throw new Exception("Le champ " + nomChamp + " ne peut être vide.");
		} else if (nom.length() < lenghtMin && required) {
			throw new Exception("Le champ " + nomChamp + " ne peut être inférieur à " + lenghtMin + " caractères.");
		} else if (nom.length() > lengthMax && limited) {
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
		}
	}

	private void validTelephone(final String telephone) throws Exception {
		if (!telephone.matches("^([0-9]{2}[. -]?){4}[0-9]{2}$")) {
			throw new Exception("Le format du numéro téléphonique n'est pas correct.");
		}
	}

}
