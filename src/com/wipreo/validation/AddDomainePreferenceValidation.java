package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.dao.UtilisateurDao;
import com.wipreo.utils.FunctionUtils;

public class AddDomainePreferenceValidation {

	private static final String ATT_SESSION_DOMAINE_FAVORIS = "domaineFavoris";
	private final String CHAMP_ID = "id";
	private UtilisateurDao utilisateurDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public AddDomainePreferenceValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validAddDomaine(final HttpServletRequest request) {
		final String domaineId = FunctionUtils.getValueChamp(request, this.CHAMP_ID);

		try {
			this.validShortNumber(domaineId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_ID, "Une erreur est survenue, veuillez réessayer ultérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();

			final String utilisateurEmail = (String) session.getAttribute(InscriptionValidation.ATT_UTILISATEUR_EMAIL);
			if (this.utilisateurDao.addDomaineFavoris(Short.parseShort(domaineId), utilisateurEmail)) {
				session.setAttribute(ATT_SESSION_DOMAINE_FAVORIS, Short.parseShort(domaineId));
				return true;
			}

		}

		return false;
	}

	private void validShortNumber(final String id) throws Exception {
		if (id == null || id.equals("")) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		} else if (Short.parseShort(id) <= 0) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		}
	}

}
