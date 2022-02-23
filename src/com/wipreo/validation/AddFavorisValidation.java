package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Favoris;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.FavorisDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.FunctionUtils;

public class AddFavorisValidation {

	private final String CHAMP_FORMATION_ID = "formationId";

	private FavorisDao favorisDao = null;

	public AddFavorisValidation(final FavorisDao favorisDao) {
		this.favorisDao = favorisDao;
	}

	public Long validFavoris(final HttpServletRequest request) {
		final String formationId = FunctionUtils.getValueChamp(request, this.CHAMP_FORMATION_ID);

		try {
			if (formationId != null && !formationId.trim().isEmpty() && Long.parseLong(formationId) != 0) {

				final Favoris favoris = new Favoris();
				final Formation formation = new Formation();
				final Utilisateur utilisateur = (Utilisateur) request.getSession()
						.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);

				formation.setId(Long.parseLong(formationId));

				favoris.setFormation(formation);
				favoris.setUtilisateur(utilisateur);

				return this.favorisDao.addFavoris(favoris);

			}
		} catch (final NumberFormatException e) {
			return null;
		}

		return null;
	}

}
