package mary.train.your.ear.model.structures;

import java.util.LinkedList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class CustomChord implements Comparable<CustomChord> {

	@ElementList
	private LinkedList<Interval> structure;
	@Element
	private String name;

	private String symbol;
	private Inversion inversion;

	@Element(required = false)
	private String hints = "";

	@Element
	private boolean hasInversions = true;
	private boolean customChord = false;

	public CustomChord() {
		inversion = Inversion.None;
	}

	public CustomChord(CustomChord customChord) {
		this.name = customChord.name;
		this.structure = customChord.structure;
		this.hints = customChord.hints;
		this.hasInversions = customChord.hasInversions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Interval> getStructure() {
		return structure;
	}

	public void setStructure(LinkedList<Interval> structure) {
		this.structure = structure;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	public Inversion getInversion() {
		return inversion;
	}

	public void setInversion(Inversion inversion) {
		this.inversion = inversion;
	}

	public boolean hasInversions() {
		return hasInversions;
	}

	public void setHasInversions(boolean hasInversions) {
		this.hasInversions = hasInversions;
	}

	public void setCustom(boolean b) {
		this.customChord = b;
	}

	public boolean isCustom() {
		return customChord;
	}

	@Override
	public int compareTo(CustomChord o) {
		return name.compareToIgnoreCase(o.name);
	}

	public boolean equals(CustomChord o) {
		return name.equals(o.name);
	}

	@Override
	public String toString(){
		return name + " " + structure;
	}
}
