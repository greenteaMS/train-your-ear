/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;

// TODO: Auto-generated Javadoc
/**
 * The Enum Interval.
 */
@Root
public enum Interval {

	/** The Minor second. */
	MinorSecond(StringConstants.I_MINOR_SECOND_NAME, 1,
			"2>"), /** The Major second. */
	MajorSecond(StringConstants.I_MAJOR_SECOND_NAME, 2,
			"2"), /** The Minor third. */
	MinorThird(StringConstants.I_MINOR_THIRD_NAME, 3, "3>"),

	/** The Major third. */
	MajorThird(StringConstants.I_MAJOR_THIRD_NAME, 4, "3"), /** The Fourth. */
	Fourth(StringConstants.I_PERFECT_FOURTH_NAME, 5, "4"), /** The Tritone. */
	Tritone(StringConstants.I_TRITONE_NAME, 6, "4< | 5>"),

	/** The Fifth. */
	Fifth(StringConstants.I_PERFECT_FIFTH_NAME, 7, "5"), /** The Minor sixth. */
	MinorSixth(StringConstants.I_MINOR_SIXTH_NAME, 8,
			"6>"), /** The Major sixth. */
	MajorSixth(StringConstants.I_MAJOR_SIXTH_NAME, 9, "6"),

	/** The Minor seventh. */
	MinorSeventh(StringConstants.I_MINOR_SEVENTH_NAME, 10,
			"7"), /** The Major seventh. */
	MajorSeventh(StringConstants.I_MAJOR_SEVENTH_NAME, 11, "7<"),

	/** The Octave. */
	Octave(StringConstants.I_OCTAVE_NAME, 12, "8"), /** The Minor ninth. */
	MinorNinth(StringConstants.I_MINOR_NINTH_NAME, 13, "9>"), /** The Major ninth. */
	MajorNinth(StringConstants.I_MAJOR_NINTH_NAME, 14, "9"),

	/** The Minor tenth. */
	MinorTenth(StringConstants.I_MINOR_TENTH_NAME, 15,
			"10>"), /** The Major tenth. */
	MajorTenth(StringConstants.I_MAJOR_TENTH_NAME, 16,
			"10"), /** The Perfect eleventh. */
	PerfectEleventh(StringConstants.I_PERFECT_ELEVENTH_NAME, 17, "11"),

	/** The Tritone with octave. */
	TritoneWithOctave(StringConstants.I_TRITONE_WITH_OCTAVE_NAME, 18,
			"11< | 12>"), /** The Perfect twelfth. */
	PerfectTwelfth(StringConstants.I_PERFECT_TWELFTH_NAME, 19, "12"),

	/** The Minor thirteenth. */
	MinorThirteenth(StringConstants.I_MINOR_THIRTEENTH_NAME, 20,
			"13>"), /** The Major thirteenth. */
	MajorThirteenth(StringConstants.I_MAJOR_THIRTEENTH_NAME, 21,
			"13"), /** The Minor fourteenth. */
	MinorFourteenth(StringConstants.I_MINOR_FOURTEENTH_NAME, 22, "14"),

	/** The Major fourteenth. */
	MajorFourteenth(StringConstants.I_MAJOR_FOURTEENTH_NAME, 23,
			"14<"), /** The Double octave. */
	DoubleOctave(StringConstants.I_DOUBLE_OCTAVE_NAME, 24, "15");

	/** The name. */
	@Element
	private String name;

	/** The jfugue rep. */
	@Element
	private int jfugueRep;

	/** The symbol. */
	@Element
	private String symbol;

	/**
	 * Instantiates a new interval.
	 *
	 * @param name
	 *            the name
	 * @param jfugueRep
	 *            the jfugue rep
	 * @param shortCut
	 *            the short cut
	 */
	private Interval(String name, int jfugueRep, String shortCut) {
		this.setName(name);
		this.setJfugueRep(jfugueRep);
		this.setSymbol(shortCut);
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
	 * Gets the jfugue rep.
	 *
	 * @return the jfugue rep
	 */
	public int getJfugueRep() {
		return jfugueRep;
	}

	/**
	 * Sets the jfugue rep.
	 *
	 * @param jfugueRep
	 *            the new jfugue rep
	 */
	public void setJfugueRep(int jfugueRep) {
		this.jfugueRep = jfugueRep;
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol
	 *            the new symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Find.
	 *
	 * @param semis
	 *            the semis
	 * @return the interval
	 */
	public static Interval find(int semis) {
		for (Interval i : Interval.values()) {
			if (i.jfugueRep == semis)
				return i;
		}
		return MajorThird;
	}

	/**
	 * Find by symbol.
	 *
	 * @param intervalSymbol
	 *            the interval symbol
	 * @return the interval
	 */
	public static Interval findBySymbol(String intervalSymbol) {
		for (Interval i : Interval.values()) {
			if (i.symbol == intervalSymbol)
				return i;
		}
		return null;
	}

	/**
	 * Find by rep.
	 *
	 * @param acc
	 *            the acc
	 * @return the interval
	 */
	public static Interval findByRep(int acc) {
		for (Interval i : Interval.values()) {
			if (i.jfugueRep == acc)
				return i;
		}
		return null;
	}

}
