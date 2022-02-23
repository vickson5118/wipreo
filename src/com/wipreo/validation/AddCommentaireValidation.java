package com.wipreo.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.wipreo.beans.Commentaire;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.CommentaireDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.servlets.LireFormation;
import com.wipreo.utils.FunctionUtils;

public class AddCommentaireValidation {

	private final String CHAMP_RATING = "rating";
	private final String CHAMP_AVIS = "avis";

	private CommentaireDao commentaireDao = null;
	private FormationDao formationDao = null;
	private UtilisateurDao utilisateurDao = null;
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public AddCommentaireValidation(final CommentaireDao commentaireDao, final FormationDao formationDao,
			final UtilisateurDao utilisateurDao) {
		this.commentaireDao = commentaireDao;
		this.formationDao = formationDao;
		this.utilisateurDao = utilisateurDao;
	}

	public boolean validCommnetaire(final HttpServletRequest request) {
		final String avis = FunctionUtils.getValueChamp(request, this.CHAMP_AVIS);
		final String rating = FunctionUtils.getValueChamp(request, this.CHAMP_RATING);

		try {
			FunctionUtils.validTexte(avis, "avis", 10, 1000);
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_AVIS, e.getMessage());
		}

		try {
			this.validRating(rating);
		} catch (final NumberFormatException e) {
			this.setErreur(this.CHAMP_RATING, "Une erreur est survenue.Veuillez reessayer ulttérieurement.");
		} catch (final Exception e) {
			this.setErreur(this.CHAMP_RATING, e.getMessage());
		}

		if (this.erreurs.isEmpty()) {

			final HttpSession session = request.getSession();
			final Commentaire commentaire = new Commentaire();
			final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
			final Formation formation = (Formation) session.getAttribute(LireFormation.ATT_SESSION_FORMATION);

			commentaire.setAvis(avis);
			commentaire.setRating(Float.parseFloat(rating));
			commentaire.setFormation(formation);
			commentaire.setUtilisateur(utilisateur);
			commentaire.setDateAjout(FunctionUtils.parseDateTimeToStringForBdd(new DateTime()));

			if (this.commentaireDao.createCommentaire(commentaire)) {
				final Float moyenneFormation = this.commentaireDao.getFormationRatingMoyenne(formation.getId());
				this.formationDao.updateRating(formation.getId(), moyenneFormation);
				final Float moyenneAuteur = this.formationDao.getAuteurRatingMoyenne(formation.getAuteur().getId());
				this.utilisateurDao.updateAuteurRating(moyenneAuteur, formation.getAuteur().getId());
				return true;
			}

		}

		return false;
	}

	private void validRating(final String rating) throws Exception {
		if (rating == null || rating.isEmpty()) {
			throw new Exception("Vous devez donner une note à la formation.");
		} else if (Float.parseFloat(rating) == 0.0) {
			throw new Exception("Vous devez donner une note comprise entre 1 et 5.");
		}
	}

}
