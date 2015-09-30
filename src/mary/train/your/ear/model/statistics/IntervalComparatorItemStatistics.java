/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.FrequencyRatio;

@Root
public class IntervalComparatorItemStatistics extends AbstractItemStatistics {

	@Element
	private FrequencyRatio ratio;

	public IntervalComparatorItemStatistics() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
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
		IntervalComparatorItemStatistics other = (IntervalComparatorItemStatistics) obj;
		if (ratio != other.ratio)
			return false;
		return true;
	}

	public IntervalComparatorItemStatistics(FrequencyRatio ratio) {
		super(ratio.getName());
		this.ratio = ratio;
	}

	public void setFrequencyRatio(FrequencyRatio ratio) {
		this.ratio = ratio;
	}

	public FrequencyRatio getFrequencyRatio() {
		return ratio;
	}

}
