package com.wipreo.beans;

public class Reponse {

	private Long id;
	private String libelle;
	private boolean correct;
	private Question questionParent;

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

	public boolean isCorrect() {
		return this.correct;
	}

	public void setCorrect(final boolean correct) {
		this.correct = correct;
	}

	public Question getQuestionParent() {
		return this.questionParent;
	}

	public void setQuestionParent(final Question questionParent) {
		this.questionParent = questionParent;
	}

}
