package com.wipreo.beans;

public class Question {
	private Long id;
	private String libelle;
	private Exercice exercice;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	public Exercice getExercice() {
		return this.exercice;
	}

	public void setExercice(final Exercice exercice) {
		this.exercice = exercice;
	}
}
