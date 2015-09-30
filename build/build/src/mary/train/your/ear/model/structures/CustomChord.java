/*
 *
 */
package mary.train.your.ear.model.structures;

import java.util.LinkedList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomChord.
 */
@Root
public class CustomChord implements Comparable<CustomChord> {

	/** The structure. */
	@ElementList
	private LinkedList<Interval> structure;

	/** The name. */
	@Element
	private String name;

	/** The inversion. */
	private Inversion inversion;

	/** The hints. */
	@Element(required = false)
	private String hints = "";

	/** The has inversions. */
	@Element
	private boolean hasInversions = true;

	/** The custom chord. */
	private boolean customChord = false;

	/**
	 * Instantiates a new custom chord.
	 */
	public CustomChord() {
		inversion = Inversion.None;
	}

	/**
	 * Instantiates a new custom chord.
	 *
	 * @param customChord the custom chord
	 */
	public CustomChord(CustomChord customChord) {
		this.name = customChord.name;
		this.structure = customChord.structure;
		this.hints = customChord.hints;
		this.hasInversions = customChord.hasInversions;
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the structure.
	 *
	 * @return the structure
	 */
	public LinkedList<Interval> getStructure() {
		return structure;
	}

	/**
	 * Sets the structure.
	 *
	 * @param structure the new structure
	 */
	public void setStructure(LinkedList<Interval> structure) {
		this.structure = structure;
	}

	/**
	 * Gets the hints.
	 *
	 * @return the hints
	 */
	public String getHints() {
		return hints;
	}

	/**
	 * Sets the hints.
	 *
	 * @param hints the new hints
	 */
	public void setHints(String hints) {
		this.hints = hints;
	}

	/**
	 * Gets the inversion.
	 *
	 * @return the inversion
	 */
	public Inversion getInversion() {
		return inversion;
	}

	/**
	 * Sets the inversion.
	 *
	 * @param inversion the new inversion
	 */
	public void setInversion(Inversion inversion) {
		this.inversion = inversion;
	}

	/**
	 * Checks for inversions.
	 *
	 * @return true, if successful
	 */
	public boolean hasInversions() {
		return hasInversions;
	}

	/**
	 * Sets the checks for inversions.
	 *
	 * @param hasInversions the new checks for inversions
	 */
	public void setHasInversions(boolean hasInversions) {
		this.hasInversions = hasInversions;
	}

	/**
	 * Sets the custom.
	 *
	 * @param b the new custom
	 */
	public void setCustom(boolean b) {
		this.customChord = b;
	}

	/**
	 * Checks if is custom.
	 *
	 * @return true, if is custom
	 */
	public boolean isCustom() {
		return customChord;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CustomChord o) {
		return name.compareToIgnoreCase(o.name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return name + " " + structure;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomChord other = (CustomChord) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (structure == null) {
			if (other.structure != null)
				return false;
		} else if (!structure.equals(other.structure))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((structure == null) ? 0 : structure.hashCode());
		return result;
	}
}
