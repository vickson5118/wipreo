package com.wipreo.beans;

public class Facture {

	private Long id;
	private String designation;
	private String pdf;
	private int prixTotal;
	private String dateCreation;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(final String designation) {
		this.designation = designation;
	}

	public String getPdf() {
		return this.pdf;
	}

	public void setPdf(final String pdf) {
		this.pdf = pdf;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(final String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public int getPrixTotal() {
		return this.prixTotal;
	}

	public void setPrixTotal(final int prixTotal) {
		this.prixTotal = prixTotal;
	}

}
