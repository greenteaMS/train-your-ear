package mary.train.your.ear.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mary.train.your.ear.model.statistics.DailyUserStatistics;
import mary.train.your.ear.model.structures.dao.DailyUserStatisticsDao;

public class StatisticsManager {

	private static Logger LOG = LogManager.getLogger(StatisticsManager.class);

	public static void saveCurrentUserStatistics() {
		LOG.info("saveCurrentStats");
		try {
			DailyUserStatisticsDao.saveStatistics(UserData.getUserData().getDailyUserStatistics());
		} catch (Exception e) {
			LOG.error("Saving dailyUserStatistics failed", e);
		}

	}

	public static void loadCurrentUserStatistics() {
		String userName = UserData.getUserData().getCurrentUser().getName();
		try {
			UserData.getUserData().setDailyUserStatistics(DailyUserStatisticsDao.getUserStatisticsOfToday(userName));
		} catch (Exception e) {
			LOG.info("User " + userName + " does not have any saved statistics for " + LocalDate.now().toString());
			UserData.getUserData()
					.setDailyUserStatistics(new DailyUserStatistics(UserData.getUserData().getCurrentUser()));
		}
	}

	public static void deleteAllStatisticsForUser(String userName) {
		try {
			DailyUserStatisticsDao.deleteAllUserStatistics(userName);
		} catch (IOException e) {
			LOG.error("removing all files for " + userName + " failed");
		}
	}

	public static LinkedList<DailyUserStatistics> getAllCurrentUserStatistics() {
		LOG.info("getAllCurrentUserStatistics...");
		try {
			return DailyUserStatisticsDao.getAllStatisticsByUser(UserData.getUserData().getCurrentUser());
		} catch (IOException e) {
			LOG.error("Geting all user statistics failed");
			return new LinkedList<>();
		}

	}

	public static boolean deleteStatistics(String generatedFileName) {
		LOG.info("deleteStatistics...");
		try {
			DailyUserStatisticsDao.deleteSelectedStatistics(generatedFileName);
			return true;
		} catch (IOException e) {
			LOG.error("DeleteStatistics all user statistics failed");
			return false;
		}
	}

}
