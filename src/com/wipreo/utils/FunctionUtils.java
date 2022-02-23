package com.wipreo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FunctionUtils {

	/**
	 * Supprimer les accents et les caractères spéciaux
	 *
	 * @param text
	 * @return
	 */
	public static String supprimerCaracteresSpeciaux(final String text) {
		return text == null ? null
				: Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
						.replaceAll("[^A-Za-z0-9@&]", "-");
	}

	/**
	 * Mettre la 1ere lettre en majuscule
	 *
	 * @param mot
	 * @return
	 */
	public static String premiereLettreEnMajuscule(final String mot) {
		final String texte = mot.replaceFirst(".", (mot.charAt(0) + "").toUpperCase());
		return texte;
	}

	/* Mettre en majuscule apres les espaces */
	public static String mettreEnMajusculeApresEspace(final String mot) {
		if (mot == null) {
			return null;
		} else {
			// decoupe le mot envoye
			final String[] decoupe = mot.split(" ");
			String newTexte = "";

			if (decoupe.length == 0) {
				return mot;
			} else {
				// mets la 1ere lettre de chaque mot decoupé en majuscules
				for (String texte : decoupe) {
					texte = premiereLettreEnMajuscule(texte);
					newTexte = newTexte + texte + " ";
				}
				return newTexte.trim();

			}
		}
	}

	/**
	 * Supprimer le dernier caractère si c'est un tiret
	 *
	 * @param titre
	 * @return
	 */
	public static String supprimerDernierCaractereSpecial(final String titre) {

		if (titre.endsWith("-") || titre.endsWith(".")) {
			final String texte = titre.substring(0, titre.length() - 1);
			return texte;
		}

		return titre;

	}

	/**
	 * Recupère la valeur du champ dans le post
	 *
	 * @param request
	 * @param nomChamp
	 * @return
	 */
	public static String getValueChamp(final HttpServletRequest request, final String nomChamp) {
		final String valueChamp = request.getParameter(nomChamp);
		return valueChamp != null ? valueChamp.trim() : null;
	}

	/**
	 * Convertir le format de la date pour l'enregistrement en base de données
	 *
	 * @param date
	 * @return
	 */
	public static String parseDateToStringForBdd(final Date date) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		if (date != null) {
			// System.out.println(formatter.print(date));
			return formatter.print(date.getTime());
		}
		return null;
	}

	/*
	 * Mettre la date au format de la base de donnée mais en String pour pouvor
	 * l'utiliser dans les accesseurs et les mutateurs
	 */
	public static String parseDateTimeToStringForBdd(final DateTime date) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			// System.out.println(formatter.print(date));
			return formatter.print(date);
		}
		return null;
	}

	/**
	 * Mettre la date en string au format 15/03/1995 14:30
	 *
	 * @param date
	 * @return
	 */

	/*
	 * public static String parseDateTimeToStringWithSlash(final Timestamp
	 * timestamp) { final SimpleDateFormat format = new
	 * SimpleDateFormat("dd/MM/yyyy HH:mm"); String dateString = null; if (timestamp
	 * != null) { dateString = format.format(timestamp); return dateString; } return
	 * null; }
	 */

	public static String parseDateTimeToStringWithSlash(final DateTime date) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		if (date != null) {
			// System.out.println(formatter.print(date));
			return formatter.print(date);
		}
		return null;
	}

	/**
	 * Convertir le format de la date pour au format 15-03-1995
	 *
	 * @param date
	 * @return
	 */
	public static String parseDateToStringFrFormat(final Date date) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		if (date != null) {
			// System.out.println(formatter.print(date));
			return formatter.print(date.getTime());
		}
		return null;
	}

	/**
	 * Mettre la date en string au format 15/03/1995
	 *
	 * @param date
	 * @return
	 */
	public static String parseDateToStringWithSlash(final Date date) {
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = null;
		if (date != null) {
			dateString = format.format(date);
			return dateString;
		}
		return null;
	}

	/**
	 * Mettre la date en string au format 15 mar. 1995
	 *
	 * @param date
	 * @return
	 */
	public static String parseDateToStringWithMonthName(final Date date) {
		final SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		String dateString = null;
		if (date != null) {
			dateString = format.format(date);
			return dateString;
		}
		return null;
	}

	/**
	 * Mettre la date en string au format 15 mars 1995
	 *
	 * @param date
	 * @return
	 */
	public static String parseDateToStringWithAllMonthName(final Date date) {
		final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
		String dateString = null;
		if (date != null) {
			dateString = format.format(date);
			return dateString;
		}
		return null;
	}

	/**
	 * Generer un mot de passe aléatoire crypter en se basant sur l'email et la
	 * dateHeure de l'instant
	 *
	 * @param algorithme est l'algorithme pour le cryptage
	 * @param login      le login de l'user
	 * @param email      l'email de l'user
	 * @return retourne le mot de passe crypté
	 */
	public static String genererPasswordCrypter(final String algorithme, final String email) {

		/* Creation d'un mot de passe (j'utilise la date, le login et le mot de passe */
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH-mm-ss");
		final String dateTime = formatter.print(new DateTime());

		// on crée un mot de passe en se basant sur certaine info
		final String passwordGenerate = dateTime + email + dateTime;

		/* utilisation de la bibliotheque jasypt pour encoder un password en SHA-512 */
		// Ce password generer aleatoirement est crypté avec un SHA-224 (40 caracteres)
		// et envoyé à l'user
		final ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(algorithme);
		passwordEncryptor.setPlainDigest(true);
		return passwordEncryptor.encryptPassword(passwordGenerate);
	}

	/**
	 * Crypter le mot de passe en ajoutant des incredients
	 *
	 * @param algorithme algorithme de crypatge
	 * @param login      le login de l'user
	 * @param password   le mot de passe envoyé pour le cryptage
	 * @return le mot de passe crypté
	 */
	public static String crypterPass(final String algorithme, final String password) {

		// on ajout des infos au pass pour le corser avant de l'enregistrer en Bdd
		final String passwordCrypteTemp = Constants.ENCRYPT_PASSWORD_START + password + Constants.ENCRYPT_PASSWORD_END;

		final ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(algorithme);
		passwordEncryptor.setPlainDigest(true);
		return passwordEncryptor.encryptPassword(passwordCrypteTemp);
	}

	/**
	 * Mettre la date au format francais avec des tirets
	 *
	 * @param date
	 * @return
	 */
	public static Date parseStringToDate(final String date) {
		final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dateParse = null;
		if (date != null && !date.trim().isEmpty()) {
			try {
				dateParse = format.parse(date);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
			return dateParse;

		}
		return null;
	}

	/**
	 * Valider le texte quon lui donne
	 *
	 * @param nom
	 * @param nomChamp
	 * @param lenghtMin
	 * @param lengthMax
	 * @throws Exception
	 */
	public static void validTexte(final String nom, final String nomChamp, final int lenghtMin, final int lengthMax)
			throws Exception {
		if (nom.isEmpty() || nom == null) {
			throw new Exception("Le champ " + nomChamp + " ne peut être vide.");
		} else if (nom.length() < lenghtMin) {
			throw new Exception("Le champ " + nomChamp + " ne peut être inférieur à " + lenghtMin + " caractères.");
		} else if (nom.length() > lengthMax) {
			throw new Exception("Le champ " + nomChamp + " ne peut être supèrieur à " + lengthMax + " caractères.");
		}
	}

	/**
	 * Supprimer les tags de liste
	 *
	 * @param texte
	 * @return
	 */
	public static String supprimerHtmlListeCaractères(final String texte) {
		return texte.replaceAll("<ul>|<li>|</li>|</ul>", "");
	}

	/**
	 * Mettre le texte en formation de liste
	 *
	 * @param texte
	 * @return
	 */
	public static String formatTexte(final String texte) {
		if (texte != null) {
			final String newTexte = texte.replaceAll("<ul>|</ul>|<li>", "").replace("</li>", ";");
			return newTexte;
		}
		return null;
	}

	/**
	 * Supprimer les caractères de TINY
	 *
	 * @param texte
	 * @return
	 */
	public static String supprimerTinyMceCaractères(final String texte) {
		return texte.replaceAll("<p>|<li>|<strong>|<em>|<span style=\"text-decoration: underline;\">|"
				+ "<ul>|<li>|</p>|</li>|</strong>|</em>|</span>|</ul>|</li>|&nbsp;", "").replace("\\n", "");
	}

	public static String genererFactureHash() throws NoSuchAlgorithmException {
		final Long dateTimeLong = System.currentTimeMillis();
		return dateTimeLong.toString();
	}

	public static String genererToken(final String texte) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(texte.toString().getBytes());

			final byte byteData[] = md.digest();

			final StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return stringBuffer.toString();
		} catch (final NoSuchAlgorithmException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		return null;
		// convertir le tableau de bits en une format hexadécimal - méthode 1

	}

	/**
	 * Ajouter un cookie dans la reponse
	 *
	 * @param response
	 * @param nom      le nom du cookie
	 * @param value    la valeur du cookie
	 * @param maxAge   La date d'expiration du cookie
	 */
	public static void setCookie(final HttpServletResponse response, final String nom, final String valeur,
			final int maxAge) {
		final Cookie cookie = new Cookie(nom, valeur);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * Recuperer la valeur d'un cookie spécifique
	 *
	 * @param request
	 * @param nom
	 * @return
	 */
	public static String getCookieValue(final HttpServletRequest request, final String nom) {
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if (cookie != null && nom.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}

	public static String getUtilisateurInitial(final String nom, final String prenoms) {
		return (prenoms.substring(0, 1) + nom.substring(0, 1)).toUpperCase();
	}

	public static void validLongId(final String id) throws Exception {
		if (id == null || id.isEmpty()) {
			throw new Exception("Une erreur est survenue.Veuillez reessayer ulttérieurement.");
		} else if (Long.parseLong(id) == 0) {
			throw new Exception("Une erreur est survenue.Veuillez reessayer ulttérieurement.");
		}
	}

	/**
	 * Envoyer une video sur les serveurs vimeo
	 *
	 * @param part
	 * @param lesson
	 * @return
	 * @throws IOException
	 * @throws VimeoException
	 */
	/*
	 * public static String telechargerLessonVimeo(final Part part, final Lesson
	 * lesson) throws IOException, VimeoException {
	 *
	 * final Vimeo vimeo = new Vimeo(Constants.VIMEO_ACCES_TOKEN); final boolean
	 * upgradeTo1080 = true; final String videoEndPoint =
	 * vimeo.addVideo(part.getInputStream(), upgradeTo1080); final VimeoResponse
	 * vimeoResponse = vimeo.getVideoInfo(videoEndPoint); //edit video final String
	 * titreVimeo = lesson.getTitre(); final String description = lesson.getTitre();
	 * final String license = ""; //see Vimeo API Documentation final String
	 * privacyView = "disable"; //see Vimeo API Documentation final String
	 * privacyEmbed = "public"; //see Vimeo API Documentation final boolean
	 * reviewLink = false; vimeo.updateVideoMetadata(videoEndPoint, titreVimeo,
	 * description, license, privacyView, privacyEmbed, reviewLink); //add video
	 * privacy domain //vimeo.addVideoPrivacyDomain(videoEndPoint, "clickntap.com");
	 * return vimeoResponse.getJson().getString("uri");
	 *
	 * }
	 */

}
