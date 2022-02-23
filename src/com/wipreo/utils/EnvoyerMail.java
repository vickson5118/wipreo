package com.wipreo.utils;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvoyerMail {

	private final static String FICHIER_EMAIL_PROPERTIES = "/com/wipreo/properties/email.properties";
	private final static String PROPERTIES_EMAIL = "fromEmail";
	private final static String PROPERTIES_PASS = "fromPassword";
	private final static String SMTP = "smtp.gmail.com";
	private final static String PORT = "587";

	public static boolean envoyer(final String nomEmetteur, final String toEmail, final String emailTexte,
			final String emailObjet) throws Exception {

		// Element indispensable pour la connexion au serveur smtp de gmail
		final Properties props = System.getProperties();
		props.put("mail.smtp.host", SMTP);
		props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", PORT);

		// chargement du fichier contenant les identifications pour l'authentification
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream fichier = loader.getResourceAsStream(FICHIER_EMAIL_PROPERTIES);

		if (fichier == null) {
			throw new Exception("Le fichier n'a pas pu être chargé correctement");
		}

		final Properties connexionProps = new Properties();
		connexionProps.load(fichier);
		final String emailAuth = connexionProps.getProperty(PROPERTIES_EMAIL);
		final String passAuth = connexionProps.getProperty(PROPERTIES_PASS);

		// Creation d'une session de mail avec les informations qui se trouve dans le
		// fichier properties email
		final Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAuth, passAuth);
			}
		});

		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailAuth, nomEmetteur));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(emailObjet);
			message.setContent(emailTexte, "text/html; charset = UTF-8");

			// envoie du mail
			Transport.send(message);

			return true;
		} catch (final Exception e) {
			e.printStackTrace();

		}
		return false;
	}

}
