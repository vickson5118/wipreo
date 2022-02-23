package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Sexe;

public interface SexeDao {

	List<Sexe> getAllSexe();

	boolean isExist(long sexeId);

}
