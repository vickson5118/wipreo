package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Utilisateur;

public interface UtilisateurDao {

	boolean creerUtilisateur(Utilisateur utilisateur);

	boolean userEmailExist(String email);

	boolean updateRedefinirPassword(Utilisateur utilisateur);

	Utilisateur connectUtilisateur(String email, String password);

	void updateConnect(Long id, String derniereConnexion);

	void deconnexion(Long utilisateurId, String derniereConnexion);

	String getUserPrenomIfEmailExist(String email);

	boolean updatePassword(String password, String email);

	Utilisateur getUserWithToken(String token);

	boolean activeCompte(Utilisateur utilisateur);

	boolean updateUserInfo(Utilisateur utilisateur);

	boolean userNewEmailExist(String email, Long utilisateurId);

	boolean updateEmail(Utilisateur utilisateur);

	boolean updateProfilInstructeur(Utilisateur utilisateur);

	boolean updateAuteurRating(Float moyenneAuteur, Long auteurId);

	List<Utilisateur> getHuitDerniersAuteur();

	Utilisateur getFormateurInfo(String profil);

	boolean addObjectif(byte objectifId, String utilisateurEmail);

	boolean addDomaineFavoris(short domaineId, String utilisateurEmail);

	boolean updateProfilPicture(String profilSource, Long utilisateurId);

	Utilisateur getFacebookUserExit(long fbId);

	Long createFacebookUser(Utilisateur facebookUtilisateur);
}
