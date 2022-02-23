package com.wipreo.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.wipreo.beans.Reponse;

public class DaoUtil {

	private DaoUtil() {
	}

	public static PreparedStatement queryInit(final Connection connexion, final String query, final boolean returnId,
			final Object... objects) throws SQLException {
		PreparedStatement prepare = null;
		prepare = connexion.prepareStatement(query,
				returnId ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objects.length; i++) {
			prepare.setObject((i + 1), objects[i]);
		}
		return prepare;
	}

	public static PreparedStatement specialReponseQueryInit(final Connection connexion, final String query,
			final List<Reponse> listeReponse) throws SQLException {
		PreparedStatement prepare = null;
		prepare = connexion.prepareStatement(query);

		int j = 0;
		for (int i = 0; i < (listeReponse.size() * 3); i = i + 3) {
			prepare.setObject((i + 1), listeReponse.get(j).getLibelle());
			prepare.setObject((i + 2), listeReponse.get(j).getQuestionParent().getId());
			prepare.setObject((i + 3), listeReponse.get(j).isCorrect());
			j++;
		}
		return prepare;
	}

	private static void statementClose(final Statement prepare) {
		try {
			if (prepare != null) {
				prepare.close();
			}
		} catch (final SQLException e) {
		}

	}

	private static void connexionClose(final Connection connexion) {
		try {
			if (connexion != null) {
				connexion.close();
			}
		} catch (final SQLException e) {
		}

	}

	private static void resultClose(final ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (final SQLException e) {
		}

	}

	public static void stateConnectClose(final Statement prepare, final Connection connexion) {
		statementClose(prepare);
		connexionClose(connexion);
	}

	public static void allElementClose(final Statement prepare, final Connection connexion, final ResultSet result) {
		statementClose(prepare);
		connexionClose(connexion);
		resultClose(result);
	}

}
