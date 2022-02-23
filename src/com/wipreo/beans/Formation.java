package com.wipreo.beans;

public class Formation {

	private Long id;
	private String titre;
	private String titreUrl;
	private Domaine domaine;
	private Utilisateur auteur;
	private String but;
	private String objectifs;
	private String pointsCles;
	private String prerequis;
	private String pourQui;
	private String description;
	private String motifBlocage;
	private String motifSuppression;
	private String dateCreation;
	private String dateBlocage;
	private int nombreModule;
	private int nombreLesson;
	private byte nombreExercice;
	private String dateSuppression;
	private boolean bloquer;
	private boolean supprimer;
	private boolean validated;
	private Integer prix;
	private Integer nombreHeures;
	private String illustration;
	private boolean redactionFinished;
	private String videoPresentation;
	private String messageBienvenue;
	private String messageFelicitations;
	private Float rating;
	private boolean coupsCoeur;
	private String dateCoupsCoeur;
	private int nombreAchat;

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

	public Domaine getDomaine() {
		return this.domaine;
	}

	public void setDomaine(final Domaine domaine) {
		this.domaine = domaine;
	}

	public Utilisateur getAuteur() {
		return this.auteur;
	}

	public void setAuteur(final Utilisateur auteur) {
		this.auteur = auteur;
	}

	public String getBut() {
		return this.but;
	}

	public void setBut(final String but) {
		this.but = but;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(final String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getObjectifs() {
		return this.objectifs;
	}

	public void setObjectifs(final String objectifs) {
		this.objectifs = objectifs;
	}

	public String getPointsCles() {
		return this.pointsCles;
	}

	public void setPointsCles(final String pointsCles) {
		this.pointsCles = pointsCles;
	}

	public String getPrerequis() {
		return this.prerequis;
	}

	public void setPrerequis(final String prerequis) {
		this.prerequis = prerequis;
	}

	public String getPourQui() {
		return this.pourQui;
	}

	public void setPourQui(final String pourQui) {
		this.pourQui = pourQui;
	}

	public String getDescription() {
		return this.description;
	}

	public byte getNombreExercice() {
		return this.nombreExercice;
	}

	public void setNombreExercice(final byte nombreExercice) {
		this.nombreExercice = nombreExercice;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getMotifBlocage() {
		return this.motifBlocage;
	}

	public void setMotifBlocage(final String motifBlocage) {
		this.motifBlocage = motifBlocage;
	}

	public String getMotifSuppression() {
		return this.motifSuppression;
	}

	public void setMotifSuppression(final String motifSuppression) {
		this.motifSuppression = motifSuppression;
	}

	public String getDateBlocage() {
		return this.dateBlocage;
	}

	public void setDateBlocage(final String dateBlocage) {
		this.dateBlocage = dateBlocage;
	}

	public String getDateSuppression() {
		return this.dateSuppression;
	}

	public boolean isBloquer() {
		return this.bloquer;
	}

	public void setBloquer(final boolean bloquer) {
		this.bloquer = bloquer;
	}

	public boolean isSupprimer() {
		return this.supprimer;
	}

	public void setSupprimer(final boolean supprimer) {
		this.supprimer = supprimer;
	}

	public void setDateSuppression(final String dateSuppression) {
		this.dateSuppression = dateSuppression;
	}

	public boolean isValidated() {
		return this.validated;
	}

	public void setValidated(final boolean validated) {
		this.validated = validated;
	}

	public Integer getPrix() {
		return this.prix;
	}

	public void setPrix(final Integer prix) {
		this.prix = prix;
	}

	public Integer getNombreHeures() {
		return this.nombreHeures;
	}

	public void setNombreHeures(final Integer nombreHeures) {
		this.nombreHeures = nombreHeures;
	}

	public String getIllustration() {
		return this.illustration;
	}

	public boolean isRedactionFinished() {
		return this.redactionFinished;
	}

	public void setRedactionFinished(final boolean redactionFinished) {
		this.redactionFinished = redactionFinished;
	}

	public void setIllustration(final String illustration) {
		this.illustration = illustration;
	}

	public int getNombreModule() {
		return this.nombreModule;
	}

	public void setNombreModule(final int nombreModule) {
		this.nombreModule = nombreModule;
	}

	public String getVideoPresentation() {
		return this.videoPresentation;
	}

	public void setVideoPresentation(final String videoPresentation) {
		this.videoPresentation = videoPresentation;
	}

	public String getMessageBienvenue() {
		return this.messageBienvenue;
	}

	public void setMessageBienvenue(final String messageBienvenue) {
		this.messageBienvenue = messageBienvenue;
	}

	public String getMessageFelicitations() {
		return this.messageFelicitations;
	}

	public void setMessageFelicitations(final String messageFelicitations) {
		this.messageFelicitations = messageFelicitations;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(final Float rating) {
		this.rating = rating;
	}

	public boolean isCoupsCoeur() {
		return this.coupsCoeur;
	}

	public void setCoupsCoeur(final boolean coupsCoeur) {
		this.coupsCoeur = coupsCoeur;
	}

	public String getDateCoupsCoeur() {
		return this.dateCoupsCoeur;
	}

	public void setDateCoupsCoeur(final String dateCoupsCoeur) {
		this.dateCoupsCoeur = dateCoupsCoeur;
	}

	public int getNombreLesson() {
		return this.nombreLesson;
	}

	public void setNombreLesson(final int nombreLesson) {
		this.nombreLesson = nombreLesson;
	}

	public int getNombreAchat() {
		return this.nombreAchat;
	}

	public void setNombreAchat(final int nombreAchat) {
		this.nombreAchat = nombreAchat;
	}

}
