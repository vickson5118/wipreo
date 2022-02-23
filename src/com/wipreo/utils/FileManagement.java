package com.wipreo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileManagement {

	private FileManagement() {
	}

	/**
	 * Creer un nouveau dossier
	 *
	 * @param chemin
	 * @throws IOException
	 */
	public static void createDossier(final Path chemin) throws IOException {
		Files.createDirectory(chemin);
	}

	/**
	 * Obtenir le nom du fichier dans le contentDisposition
	 *
	 * @param part
	 * @return
	 */
	public static String getFichierNom(final Part part) {
		final String contentDisposition = part.getHeader("Content-Disposition");
		for (final String content : contentDisposition.split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	/**
	 * Recuperer le fichier dans le Part de la requete et l'ecrire dans un endroit
	 * spécifique de notre systeme de fichier
	 *
	 * @param part
	 * @param chemin
	 * @param nomFichier
	 */
	public static boolean ecrireFichier(final Part part, final String chemin) {
		BufferedInputStream lecture = null;
		BufferedOutputStream ecriture = null;

		try {
			lecture = new BufferedInputStream(part.getInputStream(), Constants.TAILLE_TAMPON);
			ecriture = new BufferedOutputStream(new FileOutputStream(new File(chemin)), Constants.TAILLE_TAMPON);

			final byte tampon[] = new byte[Constants.TAILLE_TAMPON];
			int longueur = 0;

			while ((longueur = lecture.read(tampon)) > 0) {
				ecriture.write(tampon, 0, longueur);
			}
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (lecture != null) {
				try {
					lecture.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (ecriture != null) {
				try {
					ecriture.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

		}
		return false;
	}

	/**
	 * Ecrire une image dans un endroit specifique de notre systeme de fichier
	 *
	 * @param image
	 * @param chemin
	 * @param nomFichier
	 */
	/*
	 * public static void ecrireBufferedImage(final BufferedImage image, final
	 * String chemin, final String nomFichier) {
	 *
	 * try { ImageIO.write(image, "png", new File(chemin, nomFichier)); } catch
	 * (final IOException e) { e.printStackTrace(); }
	 *
	 * }
	 */

	/**
	 * cette fonction permet de telecharger un fichier enregistrer sur le disque dur
	 * dans la page de reponse
	 *
	 * @param request
	 * @param response
	 * @param fichierUploaderPath
	 * @param chemin
	 * @throws IOException
	 */
	public static void uploadFichier(final HttpServletRequest request, final HttpServletResponse response,
			String fichierUploaderPath, final String chemin, final String contentDisposition) throws IOException {
		// verifie si le nom de l'image dans l'url n'est pas vide si cest le cas
		// renvoyer une erreur
		if (fichierUploaderPath == null || fichierUploaderPath.equals("/")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// convertir le nom en un url
		fichierUploaderPath = URLDecoder.decode(fichierUploaderPath, "UTF-8");
		final File fichier = new File(chemin, fichierUploaderPath);

		// verifier que le fichier specifier dans la requete existe. Si ce nest pas le
		// cas rammené une erreur
		if (!fichier.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// le type du fichier. Sil n'y a pas de type particulier alors lui deifnir le
		// type application/octet-stream
		String type = request.getServletContext().getMimeType(fichier.getName());
		if (type == null) {
			type = "application/octet-stream";
		}

		// reinitialiser les parametres de la reponse et definir certains headers
		response.reset();
		response.setBufferSize(10240);
		response.setContentType(type);
		response.setHeader("Content-Length", String.valueOf(fichier.length()));
		response.setHeader("Content-Disposition", contentDisposition + "; filename=\"" + fichier.getName() + "\"");
		com.google.common.io.Files.copy(fichier, response.getOutputStream());

	}

	/**
	 * Supprimer un dossier et son contenu
	 *
	 * @param startPath le chemin de base
	 * @throws IOException
	 */
	public static void supprimerDossiers(final Path startPath) throws IOException {
		Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				if (exc == null) {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				} else {
					// directory iteration failed
					throw exc;
				}
			}
		});
	}

}
