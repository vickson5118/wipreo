package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Achat;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.FunctionUtils;

public class AddToBasketValidation {

	private final String CHAMP_FORMATION_ID = "id";

	private AchatDao achatDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public AddToBasketValidation(final AchatDao achatDao) {
		this.achatDao = achatDao;
	}

	public boolean validAddToBasket(final HttpServletRequest request) {
		final String formationId = FunctionUtils.getValueChamp(request, this.CHAMP_FORMATION_ID);

		final HttpSession session = request.getSession();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

		try {
			FunctionUtils.validLongId(formationId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_FORMATION_ID, "Une erreur est survenue.Veuillez reessayer ulttérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_FORMATION_ID, e.getMessage());
		}

		if (utilisateur != null && this.erreurs.isEmpty()) {

			final Achat panier = new Achat();
			final Formation formation = new Formation();

			formation.setId(Long.parseLong(formationId));

			panier.setUtilisateur(utilisateur);
			panier.setFormation(formation);

			return this.achatDao.ajouterAuPanier(panier);
		}

		return false;
	}

}
