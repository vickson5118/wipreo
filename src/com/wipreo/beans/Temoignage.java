package com.wipreo.beans;

public class Temoignage {

	private Short id;
	private String nom;
	private String prenoms;
	private String fonction;
	private String texte;
	private String dateAjout;

	public Short getId() {
		return this.id;
	}

	public void setId(final Short id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return this.prenoms;
	}

	public void setPrenoms(final String prenoms) {
		this.prenoms = prenoms;
	}

	public String getFonction() {
		return this.fonction;
	}

	public void setFonction(final String fonction) {
		this.fonction = fonction;
	}

	public String getTexte() {
		return this.texte;
	}

	public void setTexte(final String texte) {
		this.texte = texte;
	}

	public String getDateAjout() {
		return this.dateAjout;
	}

	public void setDateAjout(final String dateAjout) {
		this.dateAjout = dateAjout;
	}

}
