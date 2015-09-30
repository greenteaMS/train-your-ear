/*
 *
 */
package mary.train.your.ear.model.structures;

import mary.train.your.ear.StringConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerElement.
 */
public class TunerElement {

	/** The ratio. */
	private FrequencyRatio ratio;

	/** The base freq. */
	private double baseFreq;

	/** The correct freq. */
	private double correctFreq;

	/** The starting freq. */
	private double startingFreq;

	/**
	 * Instantiates a new tuner element.
	 *
	 * @param ratio
	 *            the ratio
	 * @param base
	 *            the base
	 */
	public TunerElement(FrequencyRatio ratio, double base) {
		this.ratio = ratio;
		this.baseFreq = base;
	}

	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	public FrequencyRatio getRatio() {
		return ratio;
	}

	/**
	 * Sets the ratio.
	 *
	 * @param ratio
	 *            the new ratio
	 */
	public void setRatio(FrequencyRatio ratio) {
		this.ratio = ratio;
	}

	/**
	 * Gets the base freq.
	 *
	 * @return the base freq
	 */
	public double getBaseFreq() {
		return baseFreq;
	}

	/**
	 * Sets the base freq.
	 *
	 * @param baseFreq
	 *            the new base freq
	 */
	public void setBaseFreq(double baseFreq) {
		this.baseFreq = baseFreq;
	}

	/**
	 * Gets the correct freq.
	 *
	 * @return the correct freq
	 */
	public double getCorrectFreq() {
		return correctFreq;
	}

	/**
	 * Sets the correct freq.
	 *
	 * @param correctFreq
	 *            the new correct freq
	 */
	public void setCorrectFreq(double correctFreq) {
		this.correctFreq = correctFreq;
	}

	/**
	 * Gets the starting freq.
	 *
	 * @return the starting freq
	 */
	public double getStartingFreq() {
		return startingFreq;
	}

	/**
	 * Sets the starting freq.
	 *
	 * @param startingFreq
	 *            the new starting freq
	 */
	public void setStartingFreq(double startingFreq) {
		this.startingFreq = startingFreq;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ratio.getName() + StringConstants.SPACE + ratio.getRatio() + StringConstants.SPACE + baseFreq;
	}

}
