package com.wipreo.beans;

public class Lesson {

	private Long id;
	private String titre;
	private String titreUrl;
	private String source;
	private Module module;

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

	public String getTitreUrl() {
		return this.titreUrl;
	}

	public void setTitreUrl(final String titreUrl) {
		this.titreUrl = titreUrl;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(final Module module) {
		this.module = module;
	}

}
