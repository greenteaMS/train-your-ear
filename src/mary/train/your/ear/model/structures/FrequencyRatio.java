/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures;

import java.util.LinkedList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class FrequencyRatio.
 */
@Root
public class FrequencyRatio {

	/** The Constant MINOR_SECOND. */
	public static final FrequencyRatio MINOR_SECOND = new FrequencyRatio(StringConstants.I_MINOR_SECOND_NAME, 1.059463);

	/** The Constant MAJOR_SECOND. */
	public static final FrequencyRatio MAJOR_SECOND = new FrequencyRatio(StringConstants.I_MAJOR_SECOND_NAME, 1.122462);

	/** The Constant MINOR_THIRD. */
	public static final FrequencyRatio MINOR_THIRD = new FrequencyRatio(StringConstants.I_MINOR_THIRD_NAME, 1.189207);

	/** The Constant MAJOR_THIRD. */
	public static final FrequencyRatio MAJOR_THIRD = new FrequencyRatio(StringConstants.I_MAJOR_THIRD_NAME, 1.259921);

	/** The Constant PERFECT_FOURTH. */
	public static final FrequencyRatio PERFECT_FOURTH = new FrequencyRatio(StringConstants.I_PERFECT_FOURTH_NAME,
			1.334840);

	/** The Constant TRITONE. */
	public static final FrequencyRatio TRITONE = new FrequencyRatio(StringConstants.I_TRITONE_NAME, 1.414214);

	/** The Constant PERFECT_FIFTH. */
	public static final FrequencyRatio PERFECT_FIFTH = new FrequencyRatio(StringConstants.I_PERFECT_FIFTH_NAME, 1.498307);

	/** The Constant MINOR_SIXTH. */
	public static final FrequencyRatio MINOR_SIXTH = new FrequencyRatio(StringConstants.I_MINOR_SIXTH_NAME, 1.587401);

	/** The Constant MAJOR_SIXTH. */
	public static final FrequencyRatio MAJOR_SIXTH = new FrequencyRatio(StringConstants.I_MAJOR_SIXTH_NAME, 1.681793);

	/** The Constant MINOR_SEVENTH. */
	public static final FrequencyRatio MINOR_SEVENTH = new FrequencyRatio(StringConstants.I_MINOR_SEVENTH_NAME, 1.781797);

	/** The Constant MAJOR_SEVENTH. */
	public static final FrequencyRatio MAJOR_SEVENTH = new FrequencyRatio(StringConstants.I_MAJOR_SEVENTH_NAME, 1.887749);

	/** The Constant OCTAVE. */
	public static final FrequencyRatio OCTAVE = new FrequencyRatio(StringConstants.I_OCTAVE_NAME, 2.0);

	/** The Constant PYTHAGOREAN_COMMA. */
	public static final FrequencyRatio PYTHAGOREAN_COMMA = new FrequencyRatio(StringConstants.R_PYTHAGOREAN_COMMA_NAME,
			1.013643264);

	/** The Constant SYNTONIC_COMMA. */
	public static final FrequencyRatio SYNTONIC_COMMA = new FrequencyRatio(StringConstants.R_SYNTONIC_COMMA_NAME, 1.0125);

	/** The Constant SEPTIMAL_COMMA. */
	public static final FrequencyRatio SEPTIMAL_COMMA = new FrequencyRatio(StringConstants.R_SEPTIMAL_COMMA_NAME,
			1.015873015);

	/** The Constant QUARTER_TONE. */
	public static final FrequencyRatio QUARTER_TONE = new FrequencyRatio(StringConstants.R_QUARTER_TONE_NAME, 1.0293);

	public static final FrequencyRatio SIXTH_TONE = new FrequencyRatio(StringConstants.R_SIXTH_TONE_NAME, 1.01944);

	public static final FrequencyRatio EIGHTH_TONE = new FrequencyRatio(StringConstants.R_EIGHTH_TONE_NAME, 1.01454);

	/** The Constant ALL. */
	public static final LinkedList<FrequencyRatio> ALL = new LinkedList<>();

	public static final LinkedList<FrequencyRatio> SMALL_RATIOS = new LinkedList<>();

	public static final FrequencyRatio ZERO_RATIO = new FrequencyRatio(StringConstants.NO_CHANGE_RATIO, 0);

	static {

		ALL.add(MINOR_SECOND);
		ALL.add(MAJOR_SECOND);
		ALL.add(MINOR_THIRD);
		ALL.add(MAJOR_THIRD);
		ALL.add(PERFECT_FOURTH);
		ALL.add(TRITONE);
		ALL.add(PERFECT_FIFTH);
		ALL.add(MINOR_SIXTH);
		ALL.add(MAJOR_SIXTH);
		ALL.add(MINOR_SEVENTH);
		ALL.add(MAJOR_SEVENTH);
		ALL.add(OCTAVE);
		ALL.add(PYTHAGOREAN_COMMA);
		ALL.add(SYNTONIC_COMMA);
		ALL.add(SEPTIMAL_COMMA);
		ALL.add(QUARTER_TONE);
		ALL.add(EIGHTH_TONE);
		ALL.add(SIXTH_TONE);

		SMALL_RATIOS.add(QUARTER_TONE);
		SMALL_RATIOS.add(SIXTH_TONE);
		SMALL_RATIOS.add(EIGHTH_TONE);
		SMALL_RATIOS.add(PYTHAGOREAN_COMMA);
		SMALL_RATIOS.add(SYNTONIC_COMMA);
		SMALL_RATIOS.add(SEPTIMAL_COMMA);

	}

	/** The name. */
	@Element
	private String name;

	/** The ratio. */
	@Element
	private double ratio;

	/** The custom. */
	@Element
	private boolean custom;

	/**
	 * Instantiates a new frequency ratio.
	 */
	public FrequencyRatio() {

	}

	/**
	 * Instantiates a new frequency ratio.
	 *
	 * @param name
	 *            the name
	 * @param ratio
	 *            the ratio
	 */
	public FrequencyRatio(String name, double ratio) {
		this.setName(name);
		this.setRatio(ratio);
		setCustom(false);
	}

	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}

	/**
	 * Sets the ratio.
	 *
	 * @param ratio
	 *            the new ratio
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is custom.
	 *
	 * @return true, if is custom
	 */
	public boolean isCustom() {
		return custom;
	}

	/**
	 * Sets the custom.
	 *
	 * @param custom
	 *            the new custom
	 */
	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ratio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FrequencyRatio other = (FrequencyRatio) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(ratio) != Double.doubleToLongBits(other.ratio))
			return false;
		return true;
	}

}
