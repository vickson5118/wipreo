package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wipreo.beans.Module;
import com.wipreo.dao.ModuleDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ModuleDaoImpl implements ModuleDao {

	// creation d'un module
	private final String SQL_CREER_MODULE = "INSERT INTO " + Constants.TABLE_MODULE
			+ "(titre, formation_id) VALUES(?,?)";

	// Obtenir tous les modules
	private final String SQL_GET_ALL_MODULE_BY_FORMATION = "SELECT id,titre,description FROM "
			+ Constants.TABLE_MODULE+ " WHERE formation_id=?";

	private final String SQL_DELETE_MODULE = "DELETE FROM " + Constants.TABLE_MODULE + " WHERE id=?";

	private final String SQL_GET_FORMATION_NOMBRE_MODULE = "SELECT COUNT(*) AS nombre_module FROM "
			+ Constants.TABLE_MODULE + " WHERE formation_id=?";

	private final String SQL_UPDATE_MODULE_NAME = "UPDATE " + Constants.TABLE_MODULE + " SET titre=? WHERE id=?";

	private final String SQL_MODULE_EXIST = "SELECT titre FROM " + Constants.TABLE_MODULE + " WHERE id=?";

	private DaoFactory factory = null;

	public ModuleDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public Long creerModule(final Module module) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_MODULE, true, module.getTitre(),
					module.getFormation().getId());
			prepare.executeUpdate();
			result = prepare.getGeneratedKeys();
			result.first();
			return result.getLong(1);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public List<Module> getAllModuleByFormation(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;
		final List<Module> listeModule = new ArrayList<Module>();

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_ALL_MODULE_BY_FORMATION, false, formationId);
			result = prepare.executeQuery();

			while (result.next()) {
				final Module module = new Module();

				module.setId(result.getLong("id"));
				module.setTitre(result.getString("titre"));
				module.setDescription(result.getString("description"));

				listeModule.add(module);

			}

			return listeModule;
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return null;
	}

	@Override
	public boolean deleteModule(final Long moduleId) {
		final ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_DELETE_MODULE, false, moduleId);
			if (prepare.executeUpdate() != 0) {
				return true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return false;
	}

	@Override
	public int getFormationNombreModule(final Long formationId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_GET_FORMATION_NOMBRE_MODULE, false, formationId);
			result = prepare.executeQuery();
			result.first();
			return result.getInt("nombre_module");
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return 0;
	}

	@Override
	public boolean updateModuleName(final Module module) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_UPDATE_MODULE_NAME, false, module.getTitre(),
					module.getId());

			if (prepare.executeUpdate() != 0) {
				return true;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}
		return false;
	}

	@Override
	public boolean moduleExist(final Long moduleId) {
		ResultSet result = null;
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_MODULE_EXIST, false, moduleId);
			result = prepare.executeQuery();

			if (result.first()) {
				return true;
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.allElementClose(prepare, connection, result);
		}
		return false;
	}
}
