package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wipreo.beans.Module;
import com.wipreo.dao.ModuleDao;
import com.wipreo.utils.FunctionUtils;

public class EditModuleValidation {

	private final String CHAMP_MODULE_ID = "id";
	private final String CHAMP_MODULE_NOM = "moduleName";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	private ModuleDao moduleDao = null;

	public EditModuleValidation(final ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validEditModule(final HttpServletRequest request) {

		final String moduleId = FunctionUtils.getValueChamp(request, this.CHAMP_MODULE_ID);
		final String moduleName = FunctionUtils.getValueChamp(request, this.CHAMP_MODULE_NOM);

		try {
			FunctionUtils.validTexte(moduleName, "Nom du module", 10, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_MODULE_NOM, e.getMessage());
		}

		try {
			validLongNumber(moduleId);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_MODULE_ID, "Une erreur est survenue, veuillez réessayer ultérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_MODULE_ID, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {
			final Module module = new Module();

			module.setId(Long.parseLong(moduleId));
			module.setTitre(FunctionUtils.premiereLettreEnMajuscule(moduleName));

			return this.moduleDao.updateModuleName(module);
		}

		return false;
	}

	public static void validLongNumber(final String moduleId) throws Exception {
		if (moduleId == null || moduleId.equals("")) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		} else if (Long.parseLong(moduleId) <= 0) {
			throw new Exception("Une erreur est survenue, veuillez réessauyer ultérieurement.");
		}
	}

}
