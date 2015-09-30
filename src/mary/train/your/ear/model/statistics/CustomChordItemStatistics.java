/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import java.util.HashMap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;

@Root
public class CustomChordItemStatistics extends AbstractItemStatistics {

	@Element
	private CustomChord chord;

	@ElementMap
	private HashMap<Inversion, InversionItemStatistics> inversionStatisticsMap = new HashMap<>();

	public CustomChordItemStatistics() {
		super();
	}

	public CustomChordItemStatistics(CustomChord chord) {
		super(chord.getName());
		this.setChord(chord);
	}

	public CustomChord getChord() {
		return chord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((chord == null) ? 0 : chord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomChordItemStatistics other = (CustomChordItemStatistics) obj;
		if (chord == null) {
			if (other.chord != null)
				return false;
		} else if (!chord.equals(other.chord))
			return false;
		return true;
	}

	public void setChord(CustomChord chord) {
		this.chord = chord;
	}

	public void updateStats(String userAnswer, Inversion playedInversion) {
		updateCounters(userAnswer.contains(chord.getName()));
		String answeredInversion = userAnswer.replaceAll(chord.getName(), StringConstants.EMPTY)
				.replaceAll(StringConstants.OK, StringConstants.EMPTY)
				.replaceAll(StringConstants.ERROR, StringConstants.EMPTY).trim();
		if (playedInversion != null) {
			if (inversionStatisticsMap.containsKey(playedInversion))
				inversionStatisticsMap.get(playedInversion)
						.updateCounters(playedInversion.getText().equals(answeredInversion));
			else {
				InversionItemStatistics newInversionItemStatistics = new InversionItemStatistics(playedInversion);
				newInversionItemStatistics.updateCounters(playedInversion.getText().equals(answeredInversion));
				inversionStatisticsMap.put(playedInversion, newInversionItemStatistics);
			}
		}
	}

	public HashMap<Inversion, InversionItemStatistics> getInversionStatisticsMap() {
		return inversionStatisticsMap;
	}

	public void setInversionStatisticsMap(HashMap<Inversion, InversionItemStatistics> inversionStatisticsMap) {
		this.inversionStatisticsMap = inversionStatisticsMap;
	}

}
