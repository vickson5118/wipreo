package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Achat;

public interface AchatDao {

	boolean ajouterAuPanier(Achat panier);

	List<Achat> getUserPanierFormation(Long utilisateurId);

	boolean deletePanier(Long panierId);

	int getPanierCount(Long utilisateurId);

	boolean buyPanier(Achat panier);

	List<Achat> getUserPanierBuyWithFormation(Long utilisateurId, String factureDesignation);

	List<Achat> getAllUSerFormationPaidNotFInish(Long utilisateurId);

	List<Achat> getAllUserFormationPaidFinish(Long utilisateurId);

	List<Long> getAllUserFormationPaidId(Long utilisateurId);

	List<Long> getAllUserFormationNoPaidId(Long id);

	List<Achat> getPanierBuyFormationForValidation(Long id);

	boolean updatePlusNombreLessonViewAndPourcentageAndFinish(Long formationId, Long utilisateurId);

	boolean formationIsBuy(Long utilisateurId, Long formationId);

	boolean formationIsFinish(Long utilisateurId, Long formationId);

	boolean addCertificatAndFormationTerminated(Achat achat);

	Achat getOneAchat(Long utilisateurId, Long formationId);

}
