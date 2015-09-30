package mary.train.your.ear.model.statistics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.Interval;

@Root
public class IntervalItemStatistics extends AbstractItemStatistics {

	@Element
	private Interval interval;

	public IntervalItemStatistics() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((interval == null) ? 0 : interval.hashCode());
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
		IntervalItemStatistics other = (IntervalItemStatistics) obj;
		if (interval != other.interval)
			return false;
		return true;
	}

	public IntervalItemStatistics(Interval interval) {
		super(interval.getName());
		this.interval = interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

}
