package com.wipreo.beans;

public class ExercicePassed {
	private Long id;
	private Utilisateur utilisateur;
	private Exercice exercice;
	private int note;
	private boolean valide;
	private String date;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Exercice getExercice() {
		return this.exercice;
	}

	public void setExercice(final Exercice exercice) {
		this.exercice = exercice;
	}

	public int getNote() {
		return this.note;
	}

	public void setNote(final int note) {
		this.note = note;
	}

	public boolean isValide() {
		return this.valide;
	}

	public void setValide(final boolean valide) {
		this.valide = valide;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

}
