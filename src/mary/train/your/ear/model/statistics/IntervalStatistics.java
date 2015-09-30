/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import java.util.HashMap;
import java.util.LinkedList;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.Interval;

@Root
public class IntervalStatistics {

	@ElementMap
	HashMap<Interval, IntervalItemStatistics> intervalStatisticsMap;

	@ElementMap
	HashMap<Integer, ItemSetStatistics> correctSetStatisticsMap;

	public IntervalStatistics() {
		intervalStatisticsMap = new HashMap<>();
		correctSetStatisticsMap = new HashMap<>();
	}

	public HashMap<Interval, IntervalItemStatistics> getIntervalStatisticsMap() {
		return intervalStatisticsMap;
	}

	public void setIntervalStatisticsMap(HashMap<Interval, IntervalItemStatistics> intervalStatisticsMap) {
		this.intervalStatisticsMap = intervalStatisticsMap;
	}

	public HashMap<Integer, ItemSetStatistics> getCorrectSetStatisticsMap() {
		return correctSetStatisticsMap;
	}

	public void setCorrectSetStatisticsMap(HashMap<Integer, ItemSetStatistics> setStatisticsMap) {
		this.correctSetStatisticsMap = setStatisticsMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intervalStatisticsMap == null) ? 0 : intervalStatisticsMap.hashCode());
		result = prime * result + ((correctSetStatisticsMap == null) ? 0 : correctSetStatisticsMap.hashCode());
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
		IntervalStatistics other = (IntervalStatistics) obj;
		if (intervalStatisticsMap == null) {
			if (other.intervalStatisticsMap != null)
				return false;
		} else if (!intervalStatisticsMap.equals(other.intervalStatisticsMap))
			return false;
		if (correctSetStatisticsMap == null) {
			if (other.correctSetStatisticsMap != null)
				return false;
		} else if (!correctSetStatisticsMap.equals(other.correctSetStatisticsMap))
			return false;
		return true;
	}

	public void clear() {
		intervalStatisticsMap.clear();
		correctSetStatisticsMap.clear();
	}

	private boolean isSetCorrect = true;

	public void update(LinkedList<IntervalStatElement> listOfIntervalStatElements) {
		isSetCorrect = true;
		listOfIntervalStatElements.forEach(element -> {
			updateIntervalStat(element.getInterval(), element.getRecognized());
			if (!element.getRecognized())
				isSetCorrect = false;
		});

		updateCorrectSetStat(listOfIntervalStatElements.size(), isSetCorrect);
	}

	private void updateIntervalStat(Interval interval, Boolean recognized) {
		if (intervalStatisticsMap.containsKey(interval))
			intervalStatisticsMap.get(interval).updateCounters(recognized);
		else {
			IntervalItemStatistics newIntervalStat = new IntervalItemStatistics(interval);
			newIntervalStat.updateCounters(recognized);
			intervalStatisticsMap.put(interval, newIntervalStat);
		}
	}

	private void updateCorrectSetStat(int size, boolean isSetCorrect) {
		if (correctSetStatisticsMap.containsKey(size))
			correctSetStatisticsMap.get(size).updateCounters(isSetCorrect);
		else {
			ItemSetStatistics newSetStatistics = new ItemSetStatistics(size);
			newSetStatistics.updateCounters(isSetCorrect);
			correctSetStatisticsMap.put(size, newSetStatistics);
		}
	}

}
