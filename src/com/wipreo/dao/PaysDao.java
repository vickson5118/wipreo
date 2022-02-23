package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Pays;

public interface PaysDao {

	List<Pays> getAllPays();

	boolean isExist(long paysId);

}
