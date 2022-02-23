package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.FunctionUtils;

public class AddObjectifPreferenceValidation {

	private static final String ATT_SESSION_OBJECTIF_ID = "objectifId";

	private final String CHAMP_ID = "id";

	private UtilisateurDao utilisateurDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public AddObjectifPreferenceValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public boolean validAddObjectif(final HttpServletRequest request) {
		final String objectifId = FunctionUtils.getValueChamp(request, this.CHAMP_ID);

		try {
			this.validByteNumber(objectifId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_ID, "Une erreur est survenue, veuillez réessayer ultérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();
			final String utilisateurEmail = (String) session.getAttribute(InscriptionValidation.ATT_UTILISATEUR_EMAIL);
			if (this.utilisateurDao.addObjectif(Byte.parseByte(objectifId), utilisateurEmail)) {
				// pour permettre à l'objectif d'être en surbrillance
				session.setAttribute(ATT_SESSION_OBJECTIF_ID, Byte.parseByte(objectifId));
				return true;
			}
		}

		return false;
	}

	private void validByteNumber(final String id) throws Exception {
		if (id == null || id.equals("")) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		} else if (Byte.parseByte(id) <= 0) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		}
	}

}
