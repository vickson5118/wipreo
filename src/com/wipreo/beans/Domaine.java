package com.wipreo.beans;

public class Domaine {

	private Short id;
	private String titre;
	private String titreUrl;
	private String description;
	private boolean bloquer;
	private boolean supprimer;
	private String dateCreation;
	private String dateBlocage;
	private String dateSuppression;
	private String motifBlocage;
	private String motifSuppresion;
	private String illustration;
	private Float rating;
	private Short nombreFormationTotal;
	private Short nombreFormationActive;
	private Short nombreFormationRedaction;
	private Short nombreFormationValidation;
	private Short nombreFormationBloquer;
	private Short nombreFormationSupprimer;

	public Short getId() {
		return this.id;
	}

	public void setId(final Short id) {
		this.id = id;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(final String titre) {
		this.titre = titre;
	}

	public String getTitreUrl() {
		return this.titreUrl;
	}

	public void setTitreUrl(final String titreUrl) {
		this.titreUrl = titreUrl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public boolean isBloquer() {
		return this.bloquer;
	}

	public void setBloquer(final boolean bloquer) {
		this.bloquer = bloquer;
	}

	public boolean isSupprimer() {
		return this.supprimer;
	}

	public void setSupprimer(final boolean supprimer) {
		this.supprimer = supprimer;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(final String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateBlocage() {
		return this.dateBlocage;
	}

	public void setDateBlocage(final String dateBlocage) {
		this.dateBlocage = dateBlocage;
	}

	public String getDateSuppression() {
		return this.dateSuppression;
	}

	public void setDateSuppression(final String dateSuppression) {
		this.dateSuppression = dateSuppression;
	}

	public String getMotifBlocage() {
		return this.motifBlocage;
	}

	public void setMotifBlocage(final String motifBlocage) {
		this.motifBlocage = motifBlocage;
	}

	public String getMotifSuppresion() {
		return this.motifSuppresion;
	}

	public void setMotifSuppresion(final String motifSuppresion) {
		this.motifSuppresion = motifSuppresion;
	}

	public String getIllustration() {
		return this.illustration;
	}

	public void setIllustration(final String illustration) {
		this.illustration = illustration;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(final Float rating) {
		this.rating = rating;
	}

	public Short getNombreFormationTotal() {
		return this.nombreFormationTotal;
	}

	public void setNombreFormationTotal(final Short nombreFormationTotal) {
		this.nombreFormationTotal = nombreFormationTotal;
	}

	public Short getNombreFormationActive() {
		return this.nombreFormationActive;
	}

	public void setNombreFormationActive(final Short nombreFormationActive) {
		this.nombreFormationActive = nombreFormationActive;
	}

	public Short getNombreFormationRedaction() {
		return this.nombreFormationRedaction;
	}

	public void setNombreFormationRedaction(final Short nombreFormationRedaction) {
		this.nombreFormationRedaction = nombreFormationRedaction;
	}

	public Short getNombreFormationValidation() {
		return this.nombreFormationValidation;
	}

	public void setNombreFormationValidation(final Short nombreFormationValidation) {
		this.nombreFormationValidation = nombreFormationValidation;
	}

	public Short getNombreFormationBloquer() {
		return this.nombreFormationBloquer;
	}

	public void setNombreFormationBloquer(final Short nombreFormationBloquer) {
		this.nombreFormationBloquer = nombreFormationBloquer;
	}

	public Short getNombreFormationSupprimer() {
		return this.nombreFormationSupprimer;
	}

	public void setNombreFormationSupprimer(final Short nombreFormationSupprimer) {
		this.nombreFormationSupprimer = nombreFormationSupprimer;
	}

}
