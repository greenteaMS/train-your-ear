package mary.train.your.ear.model.structures;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public enum Interval {

	MinorSecond("Minor second", 1, "2>"), MajorSecond("Major second", 2, "2"), MinorThird("Minor third", 3,"3>"),
	MajorThird("Major third", 4, "3"), Fourth("Perfect fourth", 5, "4"), Tritone("Tritone", 6, "4< | 5>"),
	Fifth("Perfect fifth", 7, "5"), MinorSixth("Minor sixth", 8, "6>"), MajorSixth("Major sixth", 9, "6"),
	MinorSeventh("Minor seventh", 10, "7"), MajorSeventh("Major seventh", 11, "7<"),
	Octave("Octave", 12, "8"), MinorNinth("Minor ninth",13, "9>"), MajorNinth("Major ninth", 14, "9"),
	MinorTenth("Minor tenth",15, "10>"), MajorTenth("Major tenth", 16, "10"), PerfectEleventh("Perfect eleventh", 17, "11"),
	TritoneWithOctave("Tritone with octave", 18, "11< | 12>"), PerfectTwelfth("Perfect twelfth", 19, "12"),
	MinorThirteenth("Minor thirteenth", 20, "13>"), MajorThirteenth("Major thirteenth", 21, "13"), MinorFourteenth("Minor fourteenth", 22, "14"),
	MajorFourteenth("Major fourteenth", 23, "14<"), DoubleOctave("Double octave", 24, "15");

	@Element
	private String name;
	@Element
	private int jfugueRep;
	@Element
	private String symbol;

	private Interval(String name, int jfugueRep, String shortCut) {
		this.setName(name);
		this.setJfugueRep(jfugueRep);
		this.setSymbol(shortCut);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJfugueRep() {
		return jfugueRep;
	}

	public void setJfugueRep(int jfugueRep) {
		this.jfugueRep = jfugueRep;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public static Interval find(int semis) {
		for (Interval i : Interval.values()) {
			if (i.jfugueRep == semis)
				return i;
		}
		return MajorThird;
	}

	public static Interval findBySymbol(String intervalSymbol) {
		for (Interval i : Interval.values()) {
			if (i.symbol == intervalSymbol)
				return i;
		}
		return null;
	}

	public static Interval findByRep(int acc) {
		for (Interval i : Interval.values()) {
			if (i.jfugueRep == acc)
				return i;
		}
		return null;
	}


}
