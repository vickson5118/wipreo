package com.wipreo.beans;

public class Favoris {

	private Long id;
	private Formation formation;
	private Utilisateur utilisateur;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(final Formation formation) {
		this.formation = formation;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
