package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.dao.AchatDao;
import com.wipreo.utils.FunctionUtils;

public class DeletePanierValidation {

	private final String CHAMP_ID = "id";

	private AchatDao achatDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public DeletePanierValidation(final AchatDao achatDao) {
		this.achatDao = achatDao;
	}

	public boolean validDeletePanier(final HttpServletRequest request) {
		final String panierId = FunctionUtils.getValueChamp(request, this.CHAMP_ID);

		try {
			FunctionUtils.validLongId(panierId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_ID, "Une erreur est survenue.Veuillez reessayer ultérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {
			return this.achatDao.deletePanier(Long.parseLong(panierId));
		}

		return false;
	}

}
