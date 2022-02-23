package com.wipreo.beans;

public class Contact {

	private Long id;
	private String nom;
	private String prenoms;
	private String telephone;
	private String email;
	private String objet;
	private String message;
	private String dateEnvoi;
	private boolean view;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
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

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getObjet() {
		return this.objet;
	}

	public void setObjet(final String objet) {
		this.objet = objet;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getDateEnvoi() {
		return this.dateEnvoi;
	}

	public void setDateEnvoi(final String dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public boolean isView() {
		return this.view;
	}

	public void setView(final boolean view) {
		this.view = view;
	}

}
