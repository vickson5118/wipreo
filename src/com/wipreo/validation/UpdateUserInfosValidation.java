package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wipreo.beans.Pays;
import com.wipreo.beans.Sexe;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.PaysDao;
import com.wipreo.dao.SexeDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.FunctionUtils;

public class UpdateUserInfosValidation {

	private final String CHAMP_NOM = "nom";
	private final String CHAMP_PRENOMS = "prenoms";
	private final String CHAMP_TELEPHONE = "telephone";
	private final String CHAMP_DATE_NAISSANCE = "dateNaissance";
	private final String CHAMP_SEXE = "sexe";
	private final String CHAMP_PAYS = "pays";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	private UtilisateurDao utilisateurDao = null;
	private SexeDao sexeDao = null;
	private PaysDao paysDao = null;

	public UpdateUserInfosValidation(final UtilisateurDao utilisateurDao, final SexeDao sexeDao,
			final PaysDao paysDao) {
		this.utilisateurDao = utilisateurDao;
		this.sexeDao = sexeDao;
		this.paysDao = paysDao;
	}

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public boolean validUpdateUserInfo(final HttpServletRequest request) {
		final String nom = FunctionUtils.getValueChamp(request, this.CHAMP_NOM);
		final String prenoms = FunctionUtils.getValueChamp(request, this.CHAMP_PRENOMS);
		final String telephone = FunctionUtils.getValueChamp(request, this.CHAMP_TELEPHONE);
		final String dateNaissance = FunctionUtils.getValueChamp(request, this.CHAMP_DATE_NAISSANCE);
		final String sexeId = FunctionUtils.getValueChamp(request, this.CHAMP_SEXE);
		final String paysId = FunctionUtils.getValueChamp(request, this.CHAMP_PAYS);

		try {
			InscriptionValidation.validNom(nom, this.CHAMP_NOM, 2, 100);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_NOM, e.getMessage());
		}

		try {
			InscriptionValidation.validNom(prenoms, this.CHAMP_PRENOMS, 2, 250);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_PRENOMS, e.getMessage());
		}

		try {
			this.validTelephone(telephone);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_TELEPHONE, e.getMessage());
		}

		try {
			this.validDateNaissance(dateNaissance);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_DATE_NAISSANCE, e.getMessage());
		}

		try {
			this.validSexe(sexeId);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_SEXE, e.getMessage());
		}

		try {
			this.validPays(paysId);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_PAYS, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();

			final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
			final Sexe sexe = new Sexe();
			final Pays pays = new Pays();

			sexe.setId(Byte.parseByte(sexeId));

			pays.setId(Short.parseShort(paysId));

			utilisateur.setNom(FunctionUtils.premiereLettreEnMajuscule(nom));
			utilisateur.setPrenoms(FunctionUtils.mettreEnMajusculeApresEspace(prenoms));
			utilisateur.setProfil(FunctionUtils.supprimerCaracteresSpeciaux(nom + " " + prenoms));
			utilisateur.setTelephone(telephone);
			utilisateur.setDateNaissance(dateNaissance);
			utilisateur.setSexe(sexe);
			utilisateur.setPays(pays);

			return this.utilisateurDao.updateUserInfo(utilisateur);

		}

		return false;
	}

	private void validSexe(final String sexeId) throws Exception {
		if (sexeId != null && !sexeId.trim().isEmpty() && !this.sexeDao.isExist(Long.parseLong(sexeId))) {
			throw new Exception("Le sexe que vous avez choisi n'existe pas.");
		}
	}

	private void validPays(final String paysId) throws Exception {
		if (paysId != null && !paysId.trim().isEmpty() && !this.paysDao.isExist(Long.parseLong(paysId))) {
			throw new Exception("Le pays que vous avez choisi n'existe pas.");
		}
	}

	private void validDateNaissance(final String dateNaissance) throws Exception {
		if ((dateNaissance != null && !dateNaissance.isEmpty())
				&& !dateNaissance.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {
			throw new Exception("Le format de la date de naissance n'est pas correct.");
		}
	}

	private void validTelephone(final String telephone) throws Exception {
		if ((telephone != null && !telephone.trim().isEmpty()) && !telephone.matches("[0-9]{2}([. -]{1}[0-9]{2}){4}")) {
			throw new Exception("Le numero de telephone n'est pas correct.");
		}
	}

}
