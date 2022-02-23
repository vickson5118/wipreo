package com.wipreo.utils;

public class Constants {

	/**
	 * VIMEO
	 */
	public static final String VIMEO_ACCES_TOKEN = "0d7e30f0f2b0c8ebe3e093ba6dfd0242";
	public static final String VIMEO_CLIENT_ID = "84cada56464d88bf1166625fa39e61ebc9ffc963";

	/**
	 * Reseaux sociaux
	 */
	public static final String LIEN_URL_FACEBOOK = "https://www.facebook.com/";
	public static final String LIEN_URL_TWITTER = "https://www.twitter.com/";
	public static final String LIEN_URL_YOUTUBE = "https://www.youtube.com/";
	public static final String LIEN_URL_LINKEDIN = "https://www.linkedin.com/";

	/**
	 * CINET PAY
	 */
	public static final String CINET_API_KEY = "10573704960a01e9fb67c90.06630299";
	public static final String CINET_SITE_ID = "213335";

	/**
	 * index
	 */
	public static final String HTTP_BASE_APP = "http://wipreo.com";
	public static final String HTTP_BASE_APP_LOCAL = "http://wipreo.local:8080";
	public static final String BASE_APP_PATH = "/";
	public static final String PAGE_LOGIN = "/compte/login";
	/**
	 * Factory attribut
	 */
	public static final String ATT_FACTORY = "factory";

	/**
	 * Mail
	 */

	public static final String NOM_EMETTEUR = "L'équipe Wipreo";

	/**
	 * Password
	 */
	public static final String ENCRYPT_PASSWORD_START = "wipreo950315";
	public static final String ENCRYPT_PASSWORD_END = "51181995";
	public static final String PASSWORD_GENERATE_ALGORITHM = "SHA-224";
	public static final String PASSWORD_ALGORITHM = "SHA-512";

	/**
	 * Constantes de connexion
	 */
	public static final Integer PARTITION_COUNT = 1;
	public static final Integer MIN_CONNEXION_PER_PARTITION = 1;
	public static final Integer MAX_CONNEXION_PER_PARTITION = 1;

	/**
	 * Taille du tampon pour l'ecriture des fichiers
	 */
	public static final Integer TAILLE_TAMPON = 10240;
	public static final long FILE_SIZE = 100000000;
	public static final byte VALID_EXERCICE_PERCENT = 60;
	public static final String BASE_DIRECTORY = "/home/debian/w_uploads/";
	public static final String TEMP_DIRECTORY = BASE_DIRECTORY + "temp/";
	public static final String DOMAINE_ILLUSTRATION_DIRECTORY = BASE_DIRECTORY + "domaines/";
	public static final String FORMATION_ILLUSTRATIONS_DIRECTORY = BASE_DIRECTORY + "formations/";
	public static final String PDF_DIRECTORY = BASE_DIRECTORY + "pdf/";
	public static final String CERTIFICAT_PDF_DIRECTORY = PDF_DIRECTORY + "certs/";
	public static final String PROFIL_PICTURE_DIRECTORY = BASE_DIRECTORY + "profil/";

	public static final String CONTENT_DISPOSITION_INLINE = "inline";
	public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment";

	/**
	 * nom des bases de données
	 */
	public static final String TABLE_FORMATION = "formations";
	public static final String TABLE_MODULE = "modules";
	public static final String TABLE_LESSON = "lessons";
	public static final String TABLE_EXERCICE = "exercices";
	public static final String TABLE_QUESTION = "questions";
	public static final String TABLE_REPONSE = "reponses";
	public static final String TABLE_DOMAINE = "domaines";
	public static final String TABLE_UTILISATEUR = "utilisateurs";
	public static final String TABLE_CONTACT = "contactez_nous";
	public static final String TABLE_FAVORIS = "favoris";
	public static final String TABLE_ACHAT = "achats";
	public static final String TABLE_FACTURE = "factures";
	public static final String TABLE_SEXE = "sexes";
	public static final String TABLE_PAYS = "pays";
	public static final String TABLE_COMMENTAIRE = "commentaires";
	public static final String TABLE_OBJECTIF = "objectifs";
	public static final String TABLE_LESSON_VIEW = "views";
	public static final String TABLE_REPONSE_CHECK = "reponse_check";
	public static final String TABLE_EXERCICE_PASSED = "exercices_passed";
	public static final String TABLE_FORMATION_FINISH = "formations_finish";
	public static final String TABLE_TEMOIGNAGE = "temoignages";
	/*
	 * Other
	 */
	public static final Byte NOMBRE_FORMATION_PAR_PAGE = 8;

}
