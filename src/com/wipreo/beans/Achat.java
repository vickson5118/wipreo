package com.wipreo.beans;

public class Achat {
	private Long id;
	private Utilisateur utilisateur;
	private Formation formation;
	private boolean isPaid;
	private Facture facture;
	private Boolean finish;
	private int nombreLessonView;
	private int pourcentageLessonView;
	private String certificat;
	private boolean formationTerminated;

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

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(final Formation formation) {
		this.formation = formation;
	}

	public boolean isPaid() {
		return this.isPaid;
	}

	public void setPaid(final boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Facture getFacture() {
		return this.facture;
	}

	public void setFacture(final Facture facture) {
		this.facture = facture;
	}

	public Boolean getFinish() {
		return this.finish;
	}

	public void setFinish(final Boolean finish) {
		this.finish = finish;
	}

	public int getNombreLessonView() {
		return this.nombreLessonView;
	}

	public void setNombreLessonView(final int nombreLessonView) {
		this.nombreLessonView = nombreLessonView;
	}

	public int getPourcentageLessonView() {
		return this.pourcentageLessonView;
	}

	public void setPourcentageLessonView(final int pourcentageLessonView) {
		this.pourcentageLessonView = pourcentageLessonView;
	}

	public String getCertificat() {
		return this.certificat;
	}

	public void setCertificat(final String certificat) {
		this.certificat = certificat;
	}

	public boolean isFormationTerminated() {
		return this.formationTerminated;
	}

	public void setFormationTerminated(final boolean formationTerminated) {
		this.formationTerminated = formationTerminated;
	}

}
