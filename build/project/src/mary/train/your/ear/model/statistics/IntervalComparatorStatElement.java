package mary.train.your.ear.model.statistics;

import mary.train.your.ear.model.structures.FrequencyRatio;

public class IntervalComparatorStatElement {

	private FrequencyRatio ratio;
	private Boolean recognized;

	public IntervalComparatorStatElement(FrequencyRatio ratio, Boolean recognized){
		this.setRatio(ratio);
		this.setRecognized(recognized);
	}

	public Boolean getRecognized() {
		return recognized;
	}

	public void setRecognized(Boolean recognized) {
		this.recognized = recognized;
	}

	public FrequencyRatio getRatio() {
		return ratio;
	}

	public void setRatio(FrequencyRatio interval) {
		this.ratio = interval;
	}
}
