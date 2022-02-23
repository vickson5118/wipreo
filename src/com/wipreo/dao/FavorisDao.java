package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Favoris;

public interface FavorisDao {

	Long addFavoris(Favoris favoris);

	List<Favoris> getUserFormationFavorisId(Long utilisateurId);

	List<Favoris> getUserFormationFavoris(Long utilisateurId);

	boolean deleteFavoris(Long favorisId);

}
