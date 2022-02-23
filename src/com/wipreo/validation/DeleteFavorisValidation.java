package com.wipreo.validation;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.dao.FavorisDao;
import com.wipreo.utils.FunctionUtils;

public class DeleteFavorisValidation {

	private final String CHAMP_FAVORIS_ID = "id";

	private FavorisDao favorisDao = null;

	public DeleteFavorisValidation(final FavorisDao favorisDao) {
		this.favorisDao = favorisDao;
	}

	public boolean validDeleteFavoris(final HttpServletRequest request) {
		final String favorisId = FunctionUtils.getValueChamp(request, this.CHAMP_FAVORIS_ID);

		try {

			if (favorisId != null && !favorisId.trim().isEmpty() && Long.parseLong(favorisId) != 0) {

				return this.favorisDao.deleteFavoris(Long.parseLong(favorisId));
			}

		} catch (final NumberFormatException e) {
			return false;
		}

		return false;
	}

}
