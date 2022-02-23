package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Domaine;

public interface DomaineDao {

	List<Domaine> getListDomaineWithoutLockedAndDelete();

	String domaineExist(Long domaineId);

	Domaine getDomaineInfo(String titreUrlDomaine);

	List<Domaine> getHuitDerniersDomaine(Short domaineId);

	void AjouterUneFormationAuDomaine(Short id);

	boolean updateNombreFormationRedactionAndValidated(Short domaineId);
}
