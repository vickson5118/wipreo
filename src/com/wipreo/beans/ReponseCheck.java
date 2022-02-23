package com.wipreo.beans;

public class ReponseCheck {
	private Long id;
	private Utilisateur utilisateur;
	private Exercice exercice;
	private Question question;
	private Reponse reponseChecked;

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

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}

	public Reponse getReponseChecked() {
		return this.reponseChecked;
	}

	public void setReponseChecked(final Reponse reponseChecked) {
		this.reponseChecked = reponseChecked;
	}

}
