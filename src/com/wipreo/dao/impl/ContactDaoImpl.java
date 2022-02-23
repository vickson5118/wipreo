package com.wipreo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wipreo.beans.Contact;
import com.wipreo.dao.ContactDao;
import com.wipreo.factory.DaoFactory;
import com.wipreo.utils.Constants;
import com.wipreo.utils.DaoUtil;

public class ContactDaoImpl implements ContactDao {

	private DaoFactory factory = null;

	private final String SQL_CREER_CONTACT = "INSERT INTO " + Constants.TABLE_CONTACT + "(nom,prenoms,email,telephone,"
			+ "objet,message,date_envoi) VALUES(?,?,?,?,?,?,?)";

	public ContactDaoImpl(final DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean creerContact(final Contact contact) {
		Connection connection = null;
		PreparedStatement prepare = null;

		try {
			connection = this.factory.getConnection();
			prepare = DaoUtil.queryInit(connection, this.SQL_CREER_CONTACT, false, contact.getNom(),
					contact.getPrenoms(), contact.getEmail(), contact.getTelephone(), contact.getObjet(),
					contact.getMessage(), contact.getDateEnvoi());

			return (prepare.executeUpdate() != 0);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.stateConnectClose(prepare, connection);
		}
		return false;
	}

}
