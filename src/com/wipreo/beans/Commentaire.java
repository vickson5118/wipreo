package com.wipreo.beans;

public class Commentaire {

	private Long id;
	private String avis;
	private Utilisateur utilisateur;
	private Formation formation;
	private Float rating;
	private String dateAjout;
	private boolean bloquer;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getAvis() {
		return this.avis;
	}

	public void setAvis(final String avis) {
		this.avis = avis;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(final Formation formation) {
		this.formation = formation;
	}

	public String getDateAjout() {
		return this.dateAjout;
	}

	public void setDateAjout(final String dateAjout) {
		this.dateAjout = dateAjout;
	}

	public boolean isBloquer() {
		return this.bloquer;
	}

	public void setBloquer(final boolean bloquer) {
		this.bloquer = bloquer;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(final Float rating) {
		this.rating = rating;
	}

}
