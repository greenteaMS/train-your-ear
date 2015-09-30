/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import java.time.LocalDate;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.User;

@Element
public class DailyUserStatistics {

	private static Logger LOG = LogManager.getLogger(DailyUserStatistics.class);

	@Element
	private IntervalStatistics intervalStatistics;
	@Element
	private CustomChordStatistics customChordStatistics;
	@Element
	private IntervalComparatorStatistics intervalComparatorStatistics;

	@Element
	private String creationDateString;
	@Element
	private String userName;

	// User user;

	public DailyUserStatistics() {
		LOG.debug("init DailyUserStatistics");
		creationDateString = LocalDate.now().toString();
		intervalStatistics = new IntervalStatistics();
		customChordStatistics = new CustomChordStatistics();
		intervalComparatorStatistics = new IntervalComparatorStatistics();
	}

	public DailyUserStatistics(User currentUser) {
		this();
		userName = currentUser.getName();
		LOG.debug("init DailyUserStatistics " + userName);
	}

	public IntervalStatistics getIntervalStatistics() {
		return intervalStatistics;
	}

	public void setIntervalStatistics(IntervalStatistics intervalStatistics) {
		this.intervalStatistics = intervalStatistics;
	}

	public CustomChordStatistics getCustomChordStatistics() {
		return customChordStatistics;
	}

	public void setCustomChordStatistics(CustomChordStatistics customChordStatistics) {
		this.customChordStatistics = customChordStatistics;
	}

	public IntervalComparatorStatistics getIntervalComparatorStatistics() {
		return intervalComparatorStatistics;
	}

	public void setIntervalComparatorStatistics(IntervalComparatorStatistics intervalComparatorStatistics) {
		this.intervalComparatorStatistics = intervalComparatorStatistics;
	}

	public String getUserName() {
		return userName;
	}

	public void setUser(String userName) {
		this.userName = userName;
	}

	public String generateFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(userName);
		sb.append(StringConstants.UNDERLINE);
		sb.append(creationDateString);
		sb.append(StringConstants.XML_EXTENSION);
		return sb.toString();
	}

	public void clear() {
		intervalStatistics.clear();
		customChordStatistics.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDateString == null) ? 0 : creationDateString.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyUserStatistics other = (DailyUserStatistics) obj;
		if (creationDateString == null) {
			if (other.creationDateString != null)
				return false;
		} else if (!creationDateString.equals(other.creationDateString))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public void updateIntervalStatistics(LinkedList<IntervalStatElement> listOfIntervalStatElements) {
		intervalStatistics.update(listOfIntervalStatElements);
	}

	public void updateChordStatistics(LinkedList<ChordStatElement> listOfChordStatElements) {
		customChordStatistics.update(listOfChordStatElements);
	}

	public void updateIntervalComparatorStatistics(IntervalComparatorStatElement intervalComparatorStatElement) {
		intervalComparatorStatistics.update(intervalComparatorStatElement);
	}

	public String getCreationDateString() {
		return creationDateString;
	}

	public void setCreationDateString(String creationDateString) {
		this.creationDateString = creationDateString;
	}

}
