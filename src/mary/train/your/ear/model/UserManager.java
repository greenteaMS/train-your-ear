/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mary.train.your.ear.model.structures.dao.UserDao;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManager.
 */
public class UserManager {

	/** The Constant LOG. */
	private static final Logger LOG = LogManager.getLogger(UserManager.class);

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public static LinkedList<User> getUsers() {
		try {
			return UserDao.getUsers();
		} catch (Exception e) {
			LOG.error("getUsers:\n" + e);
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Adds the user.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean addUser(String name) {
		LOG.info("Adding new user: " + name + "...");
		try {
			UserDao.addUser(new User(name));
			UserData.getUserData().loadUsersList();
			LOG.info("Adding new user: " + name + " - SUCCESS");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Adding new user:\n" + e);
			return false;
		}
	}

	/**
	 * Save current user settings.
	 */
	public static void saveCurrentUserSettings() {
		LOG.info("Saving current user settings...");
		try {
			UserDao.updateUser(UserData.getUserData().getUpdatedUsersList());
			LOG.info("Saving current user settings - SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Save current users settings:\n" + e);
		}
	}

	public static boolean deleteUser(String userToDelete) {
		LOG.info("Saving current user settings...");
		try {
			LinkedList<User> users = UserData.getUserData().getUpdatedUsersList();
			users.remove(UserData.getUserData().getUserByName(userToDelete));
			UserDao.updateUser(users);
			UserData.getUserData().loadUsersList();
			StatisticsManager.loadCurrentUserStatistics();
			StatisticsManager.deleteAllStatisticsForUser(userToDelete);
			LOG.info("Saving current user settings - SUCCESS");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Save current users settings:\n" + e);
			return false;
		}
	}

}
