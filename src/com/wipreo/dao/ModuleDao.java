package com.wipreo.dao;

import java.util.List;

import com.wipreo.beans.Module;

public interface ModuleDao {

	Long creerModule(Module module);

	List<Module> getAllModuleByFormation(Long formationId);

	/*List<Module> getAllModuleByFormation(String formationTitreUrl);*/

	int getFormationNombreModule(Long formationId);

	boolean deleteModule(Long moduleId);

	boolean updateModuleName(Module module);

	boolean moduleExist(Long moduleId);

}
