package com.wipreo.validation;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.wipreo.beans.Achat;
import com.wipreo.beans.ExercicePassed;
import com.wipreo.beans.Formation;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.servlets.LireFormation;
import com.wipreo.utils.Constants;
import com.wipreo.utils.EnvoyerMail;
import com.wipreo.utils.FunctionUtils;

public class AddFormationTerminatedValidation {

	private String message;
	private AchatDao achatDao = null;
	private ExercicePassedDao exercicePassedDao = null;

	private final String CHAMP_PRENOMS = "prenoms";
	private final String CHAMP_NOM = "nom";
	private final String CHAMP_DATE_NAISSANCE = "date";
	private final Map<String, String> erreurs = new HashMap<String, String>();

	public void setErreur(final String nomErreur, final String messageErreur) {
		this.erreurs.put(nomErreur, messageErreur);
	}

	public Map<String, String> getErreurs() {
		return this.erreurs;
	}

	public String getMessage() {
		return this.message;
	}

	public AddFormationTerminatedValidation(final AchatDao achatDao, final ExercicePassedDao exercicePassedDao) {
		this.achatDao = achatDao;
		this.exercicePassedDao = exercicePassedDao;
	}

	public boolean validAddFormationTerminated(final HttpServletRequest request) {
		final String prenoms = FunctionUtils.getValueChamp(request, this.CHAMP_PRENOMS);
		final String nom = FunctionUtils.getValueChamp(request, this.CHAMP_NOM);
		final String dateNaissance = FunctionUtils.getValueChamp(request, this.CHAMP_DATE_NAISSANCE);

		if (prenoms == null || prenoms.isEmpty() || nom == null || nom.isEmpty() || dateNaissance == null
				|| dateNaissance.isEmpty()) {
			this.message = "Assurez d'avoir rempli tous les champs pour pouvoir générer le certificat.";
			return false;
		}

		final HttpSession session = request.getSession();

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR);
		final Formation formation = (Formation) session.getAttribute(LireFormation.ATT_SESSION_FORMATION);

		if (utilisateur == null || formation == null) {
			this.message = "Une erreur est survenue, veuillez réessayer ultérieurement.";
			return false;
		}

		final List<ExercicePassed> listeFormationExercicePassed = this.exercicePassedDao
				.getFormationListeExercicePassed(utilisateur.getId(), formation.getId());

		if (this.achatDao.formationIsFinish(utilisateur.getId(), formation.getId())
				&& listeFormationExercicePassed.size() == formation.getNombreExercice()) {

			int sommeNoteExercice = 0;

			for (final ExercicePassed exercicePassed : listeFormationExercicePassed) {
				sommeNoteExercice += exercicePassed.getNote();
			}

			final int sommeNoteExercicePercent = sommeNoteExercice / formation.getNombreExercice();

			final Achat achat = new Achat();

			achat.setUtilisateur(utilisateur);
			achat.setFormation(formation);
			achat.setCertificat(null);

			if (sommeNoteExercicePercent < Constants.VALID_EXERCICE_PERCENT) {
				this.setErreur("formationEchec", "Dommage vous n'avez pas validé cette formation car le cumul de vos notes n'atteint pas les "
						+ Constants.VALID_EXERCICE_PERCENT + "%  requis pour valider. Votre note total est de "
						+sommeNoteExercicePercent+"%.");

				return this.achatDao.addCertificatAndFormationTerminated(achat);
			} else {

				String certificatDesignation = null;
				try {
					certificatDesignation = "WP-CERT-" + FunctionUtils.genererFactureHash();
				} catch (final NoSuchAlgorithmException e) {
					e.printStackTrace();
				}

				achat.setCertificat(Constants.CERTIFICAT_PDF_DIRECTORY + certificatDesignation + ".pdf");

				if (this.achatDao.addCertificatAndFormationTerminated(achat)) {

					this.setErreur("formationSuccess", "Félicitations vous avez suivi et validé avec succès la formation "
							+ formation.getTitre() + " avec une note total de "+sommeNoteExercicePercent+"%.");

					if (formation.getMessageFelicitations() != null && !formation.getMessageFelicitations().isEmpty()) {
						final String objet = "Félicitations vous avez suivi et validé avec succès la formation "
								+ formation.getTitre() + " avec une note total de "+sommeNoteExercicePercent+"%.";
						try {
							EnvoyerMail.envoyer(Constants.NOM_EMETTEUR, utilisateur.getEmail(),
									formation.getMessageFelicitations(), objet);
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}

					final String html = "<div style=\"text-align: center;" + "background-image: url("
							+ Constants.HTTP_BASE_APP
							+ "/inc/images/certificat.jpeg);background-repeat: no-repeat;position:absolute;left:-30px;right:-30px;top:-50px;bottom:0;"
							+ "background-position:center;background-size:100% 100%\">" + "<div>" + "<img src=\""
							+ Constants.HTTP_BASE_APP
							+ "/inc/images/logo.png\" alt=\"Logo Wipreo\" width=\"265\" height=\"80\" style=\"display: inline-block;text-align: center;"
							+ "margin-top: 100px;\"/>" + "<h1 style=\"font-size: 50px\">CERTIFICAT DE REUSSITE</h1>"
							+ "<div style=\"font-size: 50px;font-family: Montserrat;margin-bottom: 15px;\">"
							+ FunctionUtils.mettreEnMajusculeApresEspace(prenoms) + " "
							+ FunctionUtils.premiereLettreEnMajuscule(nom) + "</div>" + "<div>Né(e) le " + dateNaissance
							+ "</div><p style=\"font-size: 18px;\">a suivi et réussi avec "
							+ "succès la formation <br />" + "<b>" + formation.getTitre() + "</b>" + "</p>"
							+ "<div style=\"text-align: right;margin-right: 100px;margin-top:20px;font-size: 12px;\">"
							+ "<div>Le " + FunctionUtils.parseDateToStringWithSlash(new Date()) + "</div>"
							+ "<div>Patrice Blehouet, fondateur de Wipreo</div>" + ""
							+ "<div style=\"position:absolute;right:-30px;bottom:5px;\"><img src=\""
							+ Constants.HTTP_BASE_APP
							+ "/inc/images/signature.png\" alt=\"Signature\" width=\"350\" height=\"170\" /></div></div></div></div>";

					try {
						final PdfWriter writer = new PdfWriter(new BufferedOutputStream(new FileOutputStream(
								Constants.CERTIFICAT_PDF_DIRECTORY + certificatDesignation + ".pdf")));
						final PdfDocument pdf = new PdfDocument(writer);
						pdf.setDefaultPageSize(PageSize.A4.rotate());

						final ConverterProperties properties = new ConverterProperties();
						properties.setBaseUri(Constants.BASE_DIRECTORY);

						HtmlConverter.convertToPdf(html, pdf, properties);
						pdf.close();
						return true;

					} catch (final FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				return false;
			}

		} else {
			this.message = "Verifiez que vous avez bien validé regardé toutes les leçons et passé tous les quiz de la formation";
			return false;
		}

	}

}
