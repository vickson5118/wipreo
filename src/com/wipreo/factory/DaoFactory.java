package com.wipreo.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.wipreo.dao.AchatDao;
import com.wipreo.dao.CommentaireDao;
import com.wipreo.dao.ContactDao;
import com.wipreo.dao.DomaineDao;
import com.wipreo.dao.ExerciceDao;
import com.wipreo.dao.ExercicePassedDao;
import com.wipreo.dao.FactureDao;
import com.wipreo.dao.FavorisDao;
import com.wipreo.dao.FormationDao;
import com.wipreo.dao.LessonDao;
import com.wipreo.dao.LessonViewDao;
import com.wipreo.dao.ModuleDao;
import com.wipreo.dao.ObjectifDao;
import com.wipreo.dao.PaysDao;
import com.wipreo.dao.QuestionDao;
import com.wipreo.dao.ReponseCheckDao;
import com.wipreo.dao.ReponseDao;
import com.wipreo.dao.SexeDao;
import com.wipreo.dao.TemoignageDao;
import com.wipreo.dao.UtilisateurDao;
import com.wipreo.dao.impl.AchatDaoImpl;
import com.wipreo.dao.impl.CommentaireDaoImpl;
import com.wipreo.dao.impl.ContactDaoImpl;
import com.wipreo.dao.impl.DomaineDaoImpl;
import com.wipreo.dao.impl.ExerciceDaoImpl;
import com.wipreo.dao.impl.ExercicePassedDaoImpl;
import com.wipreo.dao.impl.FactureDaoImpl;
import com.wipreo.dao.impl.FavorisDaoImpl;
import com.wipreo.dao.impl.FormationDaoImpl;
import com.wipreo.dao.impl.LessonDaoImpl;
import com.wipreo.dao.impl.LessonViewDaoImpl;
import com.wipreo.dao.impl.ModuleDaoImpl;
import com.wipreo.dao.impl.ObjectifDaoImpl;
import com.wipreo.dao.impl.PaysDaoImpl;
import com.wipreo.dao.impl.QuestionDaoImpl;
import com.wipreo.dao.impl.ReponseCheckDaoImpl;
import com.wipreo.dao.impl.ReponseDaoImpl;
import com.wipreo.dao.impl.SexeDaoImpl;
import com.wipreo.dao.impl.TemoignageDaoImpl;
import com.wipreo.dao.impl.UtilisateurDaoImpl;
import com.wipreo.utils.Constants;

public class DaoFactory {

	private static final String FICHIER_PROPERTIES = "/com/wipreo/properties/dao.properties";
	private static final String PROPERTIES_URL = "url";
	private static final String PROPERTIES_USERNAME = "username";
	private static final String PROPERTIES_PASSWORD = "password";
	private static final String PROPERTIES_DRIVER = "driver";

	private static BoneCP connexionPool = null;

	public DaoFactory(final BoneCP pool) {
		connexionPool = pool;
	}

	public static DaoFactory getInstance() throws Exception {
		final Properties properties = new Properties();
		final String username;
		final String password;
		final String driver;
		final String url;

		/**
		 * Chargement du fichier properties
		 */
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream fichierProperties = loader.getResourceAsStream(FICHIER_PROPERTIES);

		if (fichierProperties == null) {
			throw new Exception("Le fichier de configuration n'a pas été chargé correctement.");
		}

		/**
		 * Chargement du contenu du fichier properties
		 */
		try {
			properties.load(fichierProperties);
			username = properties.getProperty(PROPERTIES_USERNAME);
			password = properties.getProperty(PROPERTIES_PASSWORD);
			url = properties.getProperty(PROPERTIES_URL);
			driver = properties.getProperty(PROPERTIES_DRIVER);
		} catch (final IOException e) {
			throw new Exception("Le fichierde configuration n'existe pas ou a été supprimé.");
		}

		try {
			Class.forName(driver);
			final BoneCPConfig config = new BoneCPConfig();

			config.setJdbcUrl(url);
			config.setUser(username);
			config.setPassword(password);

			config.setPartitionCount(Constants.PARTITION_COUNT);
			config.setMinConnectionsPerPartition(Constants.MIN_CONNEXION_PER_PARTITION);
			config.setMaxConnectionsPerPartition(Constants.MAX_CONNEXION_PER_PARTITION);

			connexionPool = new BoneCP(config);
		} catch (final ClassNotFoundException e) {
			throw new Exception("Le driver n'existe pas ou a été supprimé.");
		}

		final DaoFactory factory = new DaoFactory(connexionPool);
		return factory;
	}

	/**
	 * Creer une connexion
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return connexionPool.getConnection();
	}

	public UtilisateurDao getUtilisateurDao() {
		return new UtilisateurDaoImpl(this);
	}

	public ContactDao getContactDao() {
		return new ContactDaoImpl(this);
	}

	public DomaineDao getDomaineDao() {
		return new DomaineDaoImpl(this);
	}

	public FormationDao getFormationDao() {
		return new FormationDaoImpl(this);
	}

	public ModuleDao getModuleDao() {
		return new ModuleDaoImpl(this);
	}

	public LessonDao getLessonDao() {
		return new LessonDaoImpl(this);
	}

	public FavorisDao getFavorisDao() {
		return new FavorisDaoImpl(this);
	}

	public ExerciceDao getExerciceDao() {
		return new ExerciceDaoImpl(this);
	}

	public QuestionDao getQuestionDao() {
		return new QuestionDaoImpl(this);
	}

	public ReponseDao getReponseDao() {
		return new ReponseDaoImpl(this);
	}

	public AchatDao getAchatDao() {
		return new AchatDaoImpl(this);
	}

	public FactureDao getFactureDao() {
		return new FactureDaoImpl(this);
	}

	public SexeDao getSexeDao() {
		return new SexeDaoImpl(this);
	}

	public PaysDao getPaysDao() {
		return new PaysDaoImpl(this);
	}

	public CommentaireDao getCommentaireDao() {
		return new CommentaireDaoImpl(this);
	}

	public ObjectifDao getObjectifDao() {
		return new ObjectifDaoImpl(this);
	}

	public LessonViewDao getLessonViewDao() {
		return new LessonViewDaoImpl(this);
	}

	public ReponseCheckDao getReponseCheckDao() {
		return new ReponseCheckDaoImpl(this);
	}

	public ExercicePassedDao getExercicePassedDao() {
		return new ExercicePassedDaoImpl(this);
	}

	public TemoignageDao getTemoignageDao() {
		return new TemoignageDaoImpl(this);
	}

}
