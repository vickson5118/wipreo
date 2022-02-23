package com.wipreo.beans;

public class Module {

	private Long id;
	private String titre;
	private String description;
	private Formation formation;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(final Formation formation) {
		this.formation = formation;
	}

}
