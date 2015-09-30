package mary.train.your.ear.model.statistics;

import java.util.HashMap;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.FrequencyRatio;

@Root
public class IntervalComparatorStatistics {

	@ElementMap
	HashMap<FrequencyRatio, IntervalComparatorItemStatistics> intervalComparatorStatisticsMap;

	public IntervalComparatorStatistics() {
		intervalComparatorStatisticsMap = new HashMap<>();
	}

	public HashMap<FrequencyRatio, IntervalComparatorItemStatistics> getIntervalStatisticsMap() {
		return intervalComparatorStatisticsMap;
	}

	public void setIntervalStatisticsMap(
			HashMap<FrequencyRatio, IntervalComparatorItemStatistics> intervalStatisticsMap) {
		this.intervalComparatorStatisticsMap = intervalStatisticsMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((intervalComparatorStatisticsMap == null) ? 0 : intervalComparatorStatisticsMap.hashCode());
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
		IntervalComparatorStatistics other = (IntervalComparatorStatistics) obj;
		if (intervalComparatorStatisticsMap == null) {
			if (other.intervalComparatorStatisticsMap != null)
				return false;
		} else if (!intervalComparatorStatisticsMap.equals(other.intervalComparatorStatisticsMap))
			return false;
		return true;
	}

	public void clear() {
		intervalComparatorStatisticsMap.clear();
	}

	public void update(IntervalComparatorStatElement intervalComparatorStatElement) {
		// intervalComparatorStatElement.forEach(element -> {
		updateIntervalComparatorStat(intervalComparatorStatElement.getRatio(),
				intervalComparatorStatElement.getRecognized());
		// });
	}

	private void updateIntervalComparatorStat(FrequencyRatio ratio, Boolean recognized) {
		if (intervalComparatorStatisticsMap.containsKey(ratio))
			intervalComparatorStatisticsMap.get(ratio).updateCounters(recognized);
		else {
			IntervalComparatorItemStatistics newIntervalStat = new IntervalComparatorItemStatistics(ratio);
			newIntervalStat.updateCounters(recognized);
			intervalComparatorStatisticsMap.put(ratio, newIntervalStat);
		}
	}

}
