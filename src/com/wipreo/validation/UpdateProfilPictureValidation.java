package com.wipreo.validation;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;
import com.wipreo.utils.FileManagement;

public class UpdateProfilPictureValidation {

	private UtilisateurDao utilisateurDao = null;
	private final String FILE_PROFIL_PICTURE = "profilFile";

	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public UpdateProfilPictureValidation(final UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public boolean validUpdateProfilPicture(final HttpServletRequest request) throws IOException, ServletException {

		/* Recuperation de l'image d'illustation */
		final Part partProfilPicture = request.getPart(this.FILE_PROFIL_PICTURE);
		final String profilPicture = FileManagement.getFichierNom(partProfilPicture);

		if (!profilPicture.trim().isEmpty() && profilPicture.trim() != null) {

			final String profilPictureExtension = profilPicture.substring(profilPicture.lastIndexOf(".")).toLowerCase();
			final List<String> imageFormatAutorise = Arrays.asList(".jpg", ".png", ".jpeg");

			/**
			 * On s'assure que les extensions de fichiers sont correctes
			 */
			if (!imageFormatAutorise.contains(profilPictureExtension)) {
				this.setErreur(this.FILE_PROFIL_PICTURE,
						"Le fichier d'image sélectionné n'est pas correct. Les formats autorisés sont : jpg, png et jpeg");
				return false;
			}

			final HttpSession session = request.getSession();
			final Utilisateur utilisateur = ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR));

			/* Chemin pour l'ecriture de l'image d'illustration */
			final String profilSource = Constants.PROFIL_PICTURE_DIRECTORY + utilisateur.getId()
					+ profilPictureExtension;
			FileManagement.ecrireFichier(partProfilPicture, profilSource);

			if (this.utilisateurDao.updateProfilPicture(profilSource, utilisateur.getId())) {
				utilisateur.setPhoto(profilSource);
				session.setAttribute(Inscription.ATT_SESSION_UTILISATEUR, utilisateur);
				return true;
			}

		} else {
			this.setErreur(this.FILE_PROFIL_PICTURE, "Une erreur est survenue. Veuillez réessayer ultérieurement.");
		}

		return false;
	}

}
