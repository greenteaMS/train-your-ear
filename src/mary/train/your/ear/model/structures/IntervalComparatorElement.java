/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures;

import org.jfugue.pattern.Pattern;

public class IntervalComparatorElement {

	private Interval interval;
	private FrequencyRatio ratio;
	private int firstCompareToSecond;

	private double firstBaseFreq;
	private double secondBaseFreq;

	private Pattern[] patterns;

	public IntervalComparatorElement(Interval randomInterval, FrequencyRatio ratio, int firstCompareToSecond) {
		this.setRatio(ratio);
		this.setInterval(randomInterval);
		this.setFirstCompareToSecond(firstCompareToSecond);
	}

	public int getFirstCompareToSecond() {
		return firstCompareToSecond;
	}

	public void setFirstCompareToSecond(int firstCompareToSecond) {
		this.firstCompareToSecond = firstCompareToSecond;
	}

	public double getSecondBaseFreq() {
		return secondBaseFreq;
	}

	public void setSecondBaseFreq(double secondBaseFreq) {
		this.secondBaseFreq = secondBaseFreq;
	}

	public double getFirstBaseFreq() {
		return firstBaseFreq;
	}

	public void setFirstBaseFreq(double firstBaseFreq) {
		this.firstBaseFreq = firstBaseFreq;
	}

	public FrequencyRatio getRatio() {
		return ratio;
	}

	public void setRatio(FrequencyRatio ratio) {
		this.ratio = ratio;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Pattern[] getPatterns() {
		return patterns;
	}

	public void setPatterns(Pattern[] patterns) {
		this.patterns = patterns;
	}


}
