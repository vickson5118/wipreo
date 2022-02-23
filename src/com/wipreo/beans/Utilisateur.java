package com.wipreo.beans;

public class Utilisateur {

	private Long id;
	private Long fbId;
	private String nom;
	private String prenoms;
	private String initial;
	private String email;
	private String password;
	private boolean admin;
	private String dateCreation;
	private boolean active;
	private boolean connect;
	private String profil;
	private String token;
	private Sexe sexe;
	private Pays pays;
	private String telephone;
	private String dateNaissance;
	private String fonction;
	private String site;
	private String facebook;
	private String twitter;
	private String linkedin;
	private String youtube;
	private String biographie;
	private Float rating;
	private Short nombreFormation;
	private String photo;
	private boolean supprimer;
	private boolean bloquer;
	private Objectif objectif;
	private Domaine domaineFavoris;
	private String derniereConnexion;

	public Sexe getSexe() {
		return this.sexe;
	}

	public void setSexe(final Sexe sexe) {
		this.sexe = sexe;
	}

	public Pays getPays() {
		return this.pays;
	}

	public void setPays(final Pays pays) {
		this.pays = pays;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(final String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getFbId() {
		return this.fbId;
	}

	public void setFbId(final Long fbId) {
		this.fbId = fbId;
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

	public String getInitial() {
		return this.initial;
	}

	public void setInitial(final String initial) {
		this.initial = initial;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(final boolean admin) {
		this.admin = admin;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(final String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public boolean isConnect() {
		return this.connect;
	}

	public void setConnect(final boolean connect) {
		this.connect = connect;
	}

	public String getProfil() {
		return this.profil;
	}

	public void setProfil(final String profil) {
		this.profil = profil;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public String getFonction() {
		return this.fonction;
	}

	public void setFonction(final String fonction) {
		this.fonction = fonction;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(final String site) {
		this.site = site;
	}

	public String getFacebook() {
		return this.facebook;
	}

	public void setFacebook(final String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(final String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return this.linkedin;
	}

	public void setLinkedin(final String linkedin) {
		this.linkedin = linkedin;
	}

	public String getYoutube() {
		return this.youtube;
	}

	public void setYoutube(final String youtube) {
		this.youtube = youtube;
	}

	public String getBiographie() {
		return this.biographie;
	}

	public void setBiographie(final String biographie) {
		this.biographie = biographie;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(final Float rating) {
		this.rating = rating;
	}

	public Short getNombreFormation() {
		return this.nombreFormation;
	}

	public void setNombreFormation(final Short nombreFormation) {
		this.nombreFormation = nombreFormation;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public boolean isSupprimer() {
		return this.supprimer;
	}

	public void setSupprimer(final boolean supprimer) {
		this.supprimer = supprimer;
	}

	public boolean isBloquer() {
		return this.bloquer;
	}

	public void setBloquer(final boolean bloquer) {
		this.bloquer = bloquer;
	}

	public Objectif getObjectif() {
		return this.objectif;
	}

	public void setObjectif(final Objectif objectif) {
		this.objectif = objectif;
	}

	public Domaine getDomaineFavoris() {
		return this.domaineFavoris;
	}

	public void setDomaineFavoris(final Domaine domaineFavoris) {
		this.domaineFavoris = domaineFavoris;
	}

	public String getDerniereConnexion() {
		return this.derniereConnexion;
	}

	public void setDerniereConnexion(final String derniereConnexion) {
		this.derniereConnexion = derniereConnexion;
	}

}
