package com.wipreo.beans;

public class Exercice {
	private Long id;
	private String titre;
	private String competence;
	private Module module;
	private boolean finish;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(final String titre) {
		this.titre = titre;
	}

	public String getCompetence() {
		return this.competence;
	}

	public void setCompetence(final String competence) {
		this.competence = competence;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(final Module module) {
		this.module = module;
	}

	public boolean isFinish() {
		return this.finish;
	}

	public void setFinish(final boolean finish) {
		this.finish = finish;
	}

}
