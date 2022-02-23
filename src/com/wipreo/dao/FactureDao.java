package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Facture;

public interface FactureDao {

	Long creerFacture(Facture facture);

	List<Facture> getUserAllFacture(Long utilisateurId);
}
