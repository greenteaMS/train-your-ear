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

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.structures.CustomChord;

@Root
public class CustomChordStatistics {

	@ElementMap
	HashMap<CustomChord, CustomChordItemStatistics> customChordStatisticsMap;

	@ElementMap
	HashMap<Integer, ItemSetStatistics> correctSetStatisticsMap;

	public CustomChordStatistics() {
		customChordStatisticsMap = new HashMap<>();
		correctSetStatisticsMap = new HashMap<>();
	}

	public HashMap<CustomChord, CustomChordItemStatistics> getChordsStatisticsMap() {
		return customChordStatisticsMap;
	}

	public void setIntervalStatisticsMap(HashMap<CustomChord, CustomChordItemStatistics> intervalStatisticsMap) {
		this.customChordStatisticsMap = intervalStatisticsMap;
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
		result = prime * result + ((customChordStatisticsMap == null) ? 0 : customChordStatisticsMap.hashCode());
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
		CustomChordStatistics other = (CustomChordStatistics) obj;
		if (customChordStatisticsMap == null) {
			if (other.customChordStatisticsMap != null)
				return false;
		} else if (!customChordStatisticsMap.equals(other.customChordStatisticsMap))
			return false;
		if (correctSetStatisticsMap == null) {
			if (other.correctSetStatisticsMap != null)
				return false;
		} else if (!correctSetStatisticsMap.equals(other.correctSetStatisticsMap))
			return false;
		return true;
	}

	public void clear() {
		customChordStatisticsMap.clear();
		correctSetStatisticsMap.clear();
	}

	private boolean isSetCorrect;

	public void update(LinkedList<ChordStatElement> listOfChordStatElements) {
		isSetCorrect = true;
		listOfChordStatElements.forEach(element -> {
			updateChordStat(element.getChord(), element.getUserAnswer());
			if (element.getUserAnswer().contains(StringConstants.ERROR))
				isSetCorrect = false;
		});
		updateCorrectSetStat(listOfChordStatElements.size(), isSetCorrect);

	}

	private void updateChordStat(CustomChord chord, String userAnswer) {
		if (customChordStatisticsMap.containsKey(chord)) {
			customChordStatisticsMap.get(chord).updateStats(userAnswer, chord.getInversion());
		} else {
			CustomChordItemStatistics newChordStatistics = new CustomChordItemStatistics(chord);
			newChordStatistics.updateStats(userAnswer, chord.getInversion());
			customChordStatisticsMap.put(chord, newChordStatistics);
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
