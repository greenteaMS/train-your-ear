/*
 *
 */
package mary.train.your.ear.model;

import java.util.LinkedList;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.GeneralTaskSettings;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.statistics.DailyUserStatistics;

// TODO: Auto-generated Javadoc
/**
 * The Class UserData.
 */
public class UserData {

	/** The current user. */
	private User currentUser;

	/** The list of users. */
	private LinkedList<User> listOfUsers;

	/** The user data. */
	private static UserData userData;

	private DailyUserStatistics dailyUserStatistics;

	/**
	 * Gets the user data.
	 *
	 * @return the user data
	 */
	public static UserData getUserData() {
		if (userData == null)
			userData = new UserData();
		return userData;
	}

	/**
	 * Instantiates a new user data.
	 */
	private UserData() {
		setCurrentUser(new User(StringConstants.GUEST));
		loadUsersList();
		loadSettings();
		setDailyUserStatistics(new DailyUserStatistics(currentUser));
	}

	/**
	 * Load users list.
	 */
	public void loadUsersList() {
		User guest = new User(StringConstants.GUEST);
		listOfUsers = UserManager.getUsers();
		if (currentUser == null || !listOfUsers.contains(currentUser))
			currentUser = guest;
		listOfUsers.add(guest);
	}

	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the current user.
	 *
	 * @param currentUser the new current user
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Gets the list of users.
	 *
	 * @return the list of users
	 */
	public LinkedList<User> getListOfUsers() {
		return listOfUsers;
	}

	/**
	 * Sets the list of users.
	 *
	 * @param listOfUsers the new list of users
	 */
	public void setListOfUsers(LinkedList<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	/**
	 * Sets the current user.
	 *
	 * @param source the new current user
	 */
	public void setCurrentUser(final String source) {
		for (User user : listOfUsers) {
			if (user.getName().equals(source)) {
				currentUser = user;
				break;
			}
		}
		loadSettings();
	}

	public User getUserByName(String name){
		for (User user : listOfUsers) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		GeneralTaskSettings.setInstance(currentUser.getGeneralTaskSettings());
		ChordsSettings.setSettings(currentUser.getChordsSettings());
		IntervalsSettings.setSettings(currentUser.getIntervalsSettings());
		TunerSettings.setSettings(currentUser.getTunerSettings());
		IntervalComparatorSettings.setSettings(currentUser.getIntervalComparatorSettings());
	}

	/**
	 * Gets the updated users list.
	 *
	 * @return the updated users list
	 */
	public LinkedList<User> getUpdatedUsersList() {
		LinkedList<User> list = new LinkedList<>();
		for (User user : listOfUsers) {
			if (user.getName().equals(currentUser.getName())) {
				user.setChordsSettings(ChordsSettings.getSettings());
				user.setGeneralTaskSettings(GeneralTaskSettings.getSettings());
				user.setIntervalsSettings(IntervalsSettings.getSettings());
				user.setTunerSettings(TunerSettings.getSettings());
				user.setIntervalComparatorSettings(IntervalComparatorSettings.getSettings());
			}

			if (!user.getName().equals(StringConstants.GUEST))
				list.add(user);
		}
		return list;
	}

	public DailyUserStatistics getDailyUserStatistics() {
		return dailyUserStatistics;
	}

	public void setDailyUserStatistics(DailyUserStatistics dailyUserStatistics) {
		this.dailyUserStatistics = dailyUserStatistics;
	}

}
