package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Formation;

public interface FormationDao {

	Formation getOneFormationInfo(String titreUrl);

	List<Formation> getUserListFormationActive(Long auteurId);

	boolean updateRating(Long formationId, Float moyenne);

	int getCountListeFormation(String titreUrlDomaine);

	List<Formation> getPageFormation(String domaineTitreUrl, Short nombreDebutPage);

	List<Formation> getHuitDerniersFormation(Short domaineId);

	List<Formation> getTroisDernieresFormation(Short domaineId);

	Float getAuteurRatingMoyenne(Long auteurId);

	List<Formation> getHuitDerniersFormation();

	List<Formation> getTroisDerniersFormation();

	List<Formation> getFormationCoupsCoeur();

	List<Formation> getTroisDerniersFormationWithoutCurrentFormation(Long id);

	boolean updateFormationNombreAchat(Long formationId);

}
