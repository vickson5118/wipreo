package com.wipreo.validation;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.wipreo.beans.Achat;
import com.wipreo.beans.Facture;
import com.wipreo.beans.Utilisateur;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.FactureDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.servlets.Inscription;
import com.wipreo.utils.Constants;
import com.wipreo.utils.EnvoyerMail;
import com.wipreo.utils.FunctionUtils;

public class BuyNowValidation {

	private final String CHAMP_PANIER_PRIX_TOTAL = "prix";

	private FactureDao factureDao = null;
	private AchatDao achatDao = null;
	private FormationDao formationDao = null;

	public BuyNowValidation(final FactureDao factureDao, final AchatDao achatDao, final FormationDao formationDao) {
		this.factureDao = factureDao;
		this.achatDao = achatDao;
		this.formationDao = formationDao;
	}

	public boolean validBuyNow(final HttpServletRequest request) {

		final HttpSession session = request.getSession();

		final Utilisateur utilisateur = ((Utilisateur) session.getAttribute(Inscription.ATT_SESSION_UTILISATEUR));

		final String panierPrixTotal = FunctionUtils.getValueChamp(request, this.CHAMP_PANIER_PRIX_TOTAL);

		String factureDesignation = null;
		try {
			factureDesignation = "WP-" + FunctionUtils.genererFactureHash();
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (factureDesignation != null && utilisateur != null && utilisateur.getId() != 0) {

			final List<Achat> listePanierFormation = this.achatDao
					.getPanierBuyFormationForValidation(utilisateur.getId());
			int prixTotalBdd = 0;

			for (final Achat panier : listePanierFormation) {
				prixTotalBdd += panier.getFormation().getPrix();
			}

			if (prixTotalBdd == Integer.parseInt(panierPrixTotal)) {

				final Facture facture = new Facture();
				final DateTime dateTime = new DateTime();

				facture.setDesignation(factureDesignation);
				facture.setPdf(factureDesignation + ".pdf");
				facture.setPrixTotal(prixTotalBdd);
				facture.setDateCreation(FunctionUtils.parseDateTimeToStringForBdd(dateTime));

				final Long factureId = this.factureDao.creerFacture(facture);

				if (factureId != null) {
					facture.setId(factureId);

					final Achat panier = new Achat();

					panier.setFacture(facture);
					panier.setPaid(true);
					panier.setUtilisateur(utilisateur);

					if (this.achatDao.buyPanier(panier)) {

						for (final Achat panierBdd : listePanierFormation) {
							this.formationDao.updateFormationNombreAchat(panierBdd.getFormation().getId());

							if (panierBdd.getFormation().getMessageBienvenue() != null) {
								final String objet = "Achat de la formation " + panierBdd.getFormation().getTitre();
								try {
									EnvoyerMail.envoyer(Constants.NOM_EMETTEUR, utilisateur.getEmail(),
											panierBdd.getFormation().getMessageBienvenue(), objet);
								} catch (final Exception e) {
									e.printStackTrace();
								}
							}
						}

						String html = "<div style=\"padding: 10px;\">" + "<img src=\"" + Constants.HTTP_BASE_APP
								+ "/inc/images/logo.png\" alt=\"Logo Wipreo\" width=\"830\" height=\"260\" style=\"position:absolute;"
								+ "margin-left: -65px;margin-top: 400px; opacity: 0.1;-ms-transform: rotate(90deg);"
								+ "transform: rotate(90deg);moz-transform: rotate(90deg);-webkit-transform: rotate(90deg);\"/>"
								+ "<p style=\"text-align: center;font-size: 40px;font-weight: bold;\">Reçu</p>"
								+ "<div><div><div>" + "<h1>Wipreo, Inc</h1>"
								+ "<div style=\"font-size:13px\">Boulevard Valery Giscard d'Estaing (Face ORCA DECO) <br />Marcory, Immeuble Kalimba, 3eme etage</div>"
								+ "<p><a href=\"https://wipreo.com\" style=\"text-decoration: none;color: #f15a24\">Wipreo</a></p>"
								+ "<p style=\"margin-top: 30px;font-size:13px\">Vendu à: <b>" + utilisateur.getPrenoms()
								+ " " + utilisateur.getNom() + "</b></p></div>"

								+ "<div>" + "<div style=\"font-size:13px\"><b>Date: </b>"
								+ FunctionUtils.parseDateTimeToStringWithSlash(dateTime) + "</div>"
								+ "<p style=\"font-size:13px\"><b>N° de facture :</b> " + facture.getDesignation()
								+ "</p>" + "</div></div>" + "<div style=\"margin-top: 30px;\">"
								+ "<table style=\"width: 100%;margin-bottom: 30px;padding: 15px;\">"
								+ "<thead style=\"margin-bottom: 20px;\">"
								+ "<tr style=\"text-align: center;background-color: #f07b16;color: white;\">"
								+ "<th style=\"padding: 10px;\">N°</th>" + "<th>Titre de la formation</th>"
								+ "<th>Auteur</th>" + "<th>Prix</th>" + "</tr>" + "</thead>" + "<tbody>";

						final List<Achat> listePanierPaid = this.achatDao
								.getUserPanierBuyWithFormation(utilisateur.getId(), facture.getDesignation());

						int prixTotal = 0;
						for (int i = 0; i < listePanierPaid.size(); i++) {
							html += "<tr style=\"text-align: center; background-color: #f0f0f0;\">"
									+ "<th style=\"padding: 5px;\">" + (i + 1) + "</th>" + "	<td>"
									+ listePanierPaid.get(i).getFormation().getTitre() + "</td>" + "<td>"
									+ listePanierPaid.get(i).getFormation().getAuteur().getPrenoms() + " "
									+ listePanierPaid.get(i).getFormation().getAuteur().getNom() + "</td>" + "<td>"
									+ listePanierPaid.get(i).getFormation().getPrix() + " FCFA</td>" + "</tr>";

							prixTotal += listePanierPaid.get(i).getFormation().getPrix();
						}

						html += "</tbody>" + "</table>"
								+ "<div style=\"width: 98%;text-align: right;font-size: 16px;\">" + "Total réglé : <b>"
								+ prixTotal + " FCFA</b>" + "</div>" + "</div>" + "</div> </div>"
								+ "<div style=\"position:absolute;bottom:0\">Facture générée par <b>Wipreo</b></div>";

						try {
							final PdfWriter writer = new PdfWriter(new BufferedOutputStream(
									new FileOutputStream(Constants.PDF_DIRECTORY + factureDesignation + ".pdf")));
							final PdfDocument pdf = new PdfDocument(writer);
							pdf.setDefaultPageSize(PageSize.A4);

							final ConverterProperties properties = new ConverterProperties();
							properties.setBaseUri(Constants.BASE_DIRECTORY);

							HtmlConverter.convertToPdf(html, pdf, properties);
							pdf.close();
							return true;

						} catch (final FileNotFoundException e) {
							e.printStackTrace();
						}

					}

				}
			} else {
				return false;
			}

		}

		return false;

	}

}
