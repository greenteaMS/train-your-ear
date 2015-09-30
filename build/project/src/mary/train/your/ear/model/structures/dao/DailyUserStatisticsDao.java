package mary.train.your.ear.model.structures.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.User;
import mary.train.your.ear.model.statistics.DailyUserStatistics;

public class DailyUserStatisticsDao {

	private static String STATISTICS_PATH = "./files/statistics/";
	private static final Logger LOG = LogManager.getLogger(DailyUserStatisticsDao.class);

	public static DailyUserStatistics getUserStatisticsOfToday(String userName) throws Exception {
		LOG.debug("getUserStatisticsOfToday " + userName + " " + LocalDate.now().toString());
		String fileName = STATISTICS_PATH + userName + StringConstants.UNDERLINE + LocalDate.now().toString()
				+ StringConstants.XML_EXTENSION;
		Serializer reader = new Persister();
		InputStream source = new FileInputStream(fileName);
		DailyUserStatistics dus = reader.read(DailyUserStatistics.class, source);
		source.close();
		return dus;
	}

	public static LinkedList<DailyUserStatistics> getAllStatisticsByUser(User user) throws IOException {
		LinkedList<DailyUserStatistics> listOfUsersStatistics = new LinkedList<>();
		Files.walk(Paths.get(STATISTICS_PATH)).forEach(filePath -> {
			String fileName = filePath.getFileName().toString();
			if (fileName.startsWith(user.getName() + StringConstants.UNDERLINE)
					&& fileName.endsWith(StringConstants.XML_EXTENSION)) {
				try {
					DailyUserStatistics dus = loadStatFromFile(filePath.toString());
					listOfUsersStatistics.add(dus);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error("loading filePath " + filePath + " failed", e.getMessage());
				}
			}
		});
		return listOfUsersStatistics;
	}

	private static DailyUserStatistics loadStatFromFile(String filePath) throws Exception {
		Serializer reader = new Persister();
		InputStream source = new FileInputStream(filePath);
		DailyUserStatistics dus = reader.read(DailyUserStatistics.class, source);
		source.close();
		return dus;
	}

	public static LinkedList<DailyUserStatistics> getAllStatisticsByDay(LocalDate date) throws IOException {
		LinkedList<DailyUserStatistics> listOfStatisticsByDate = new LinkedList<>();
		String endOfFileName = StringConstants.UNDERLINE + date.toString() + StringConstants.XML_EXTENSION;
		Files.walk(Paths.get(STATISTICS_PATH)).filter(path -> path.getFileName().toString().endsWith(endOfFileName))
				.forEach(filePath -> {
					try {
						listOfStatisticsByDate.add(loadStatFromFile(filePath.toString()));
					} catch (Exception e) {
						LOG.error("loading filePath " + filePath + " failed", e.getMessage());
					}

				});
		return listOfStatisticsByDate;
	}

	public static void deleteAllUserStatistics(String userName) throws IOException {
		Files.walk(Paths.get(STATISTICS_PATH)).forEach(filePath -> {
			String fileName = filePath.getFileName().toString();
			System.out.println(fileName + " " + userName + StringConstants.UNDERLINE);
			if (fileName.startsWith(userName + StringConstants.UNDERLINE)
					&& fileName.endsWith(StringConstants.XML_EXTENSION)) {
				System.out.println(fileName + " toDelete");
				try {
					boolean value = Files.deleteIfExists(filePath);
					System.out.println(value);
				} catch (Exception e) {
					LOG.error("Deleting file " + filePath + " failed");
					e.printStackTrace();
				}
			}
		});
	}

	public static void deleteSelectedStatistics(String fileName) throws IOException {

		Files.walk(Paths.get(STATISTICS_PATH)).forEach(filePath -> {
			System.out.println("filePath " + filePath + " " + fileName);
			if (fileName.equals(filePath.getFileName().toString())) {
				System.out.println("filePath to Delete " + filePath);
				try {
					Files.deleteIfExists(filePath);
				} catch (Exception e) {
					LOG.error("Deleting file " + filePath + " failed");
					e.printStackTrace();
				}
			}
		});

	}

	public static void saveStatistics(DailyUserStatistics statistics) throws Exception {
		System.out.println("******* save " + STATISTICS_PATH + statistics.generateFileName());
		Serializer serializer = new Persister();
		OutputStream file = new FileOutputStream(STATISTICS_PATH + statistics.generateFileName());
		serializer.write(statistics, file);
		file.close();
	}

	public static void main(String[] args) throws Exception {
		User mary = new User("mary");
		DailyUserStatistics stat = new DailyUserStatistics(mary);
		saveStatistics(stat);
		LinkedList<DailyUserStatistics> list = getAllStatisticsByUser(mary);
		System.out.println(list.size());
		list = getAllStatisticsByDay(LocalDate.now());
		System.out.println("today " + list.size());
		list = getAllStatisticsByDay(LocalDate.now().minusDays(1));
		System.out.println("yesterday" + list.size());
		list = getAllStatisticsByDay(LocalDate.now().minusDays(2));
		System.out.println("the day before yesterday " + list.size());
		// deleteSelectedStatistics(stat.generateFileName());
		list = getAllStatisticsByUser(mary);
		list.forEach(statistic -> {
			System.out.println(statistic.getUserName());
		});
	}

}
