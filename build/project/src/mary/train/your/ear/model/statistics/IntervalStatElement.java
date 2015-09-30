package mary.train.your.ear.model.statistics;

import mary.train.your.ear.model.structures.Interval;

public class IntervalStatElement {

	private Interval interval;
	private Boolean recognized;

	public IntervalStatElement(Interval interval, Boolean recognized){
		this.setInterval(interval);
		this.setRecognized(recognized);
	}

	public Boolean getRecognized() {
		return recognized;
	}

	public void setRecognized(Boolean recognized) {
		this.recognized = recognized;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}
}
