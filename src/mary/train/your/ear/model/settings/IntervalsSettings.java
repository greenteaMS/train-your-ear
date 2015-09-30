/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.settings;

import java.util.LinkedHashMap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.Interval;

// TODO: Auto-generated Javadoc
/**
 * The Class IntervalsSettings.
 */
@Root
public class IntervalsSettings {

	/** The settings. */
	private static IntervalsSettings settings;

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public static IntervalsSettings getSettings() {
		if (settings == null)
			settings = new IntervalsSettings();
		return settings;
	}

	/**
	 * Sets the settings.
	 *
	 * @param s the new settings
	 */
	public static void setSettings(IntervalsSettings s) {
		settings = s;
	}

	/** The intervals availability. */
	@ElementMap
	private LinkedHashMap<Interval, Boolean> intervalsAvailability;
	
	/** The simple. */
	@Element
	private boolean simple;
	
	/** The compound. */
	@Element
	private boolean compound;

	/**
	 * Instantiates a new intervals settings.
	 */
	public IntervalsSettings() {
		intervalsAvailability = new LinkedHashMap<>();
		for (Interval interval : Interval.values())
			intervalsAvailability.put(interval, true);

		setSimple(true);
		setCompound(false);
	}

	/**
	 * Gets the intervals availability.
	 *
	 * @return the intervals availability
	 */
	public LinkedHashMap<Interval, Boolean> getIntervalsAvailability() {
		return intervalsAvailability;
	}

	/** The any available. */
	private boolean anyAvailable = false;

	/**
	 * Check availability.
	 *
	 * @return true, if successful
	 */
	public boolean checkAvailability() {
		anyAvailable = false;
		if (simple)
			intervalsAvailability.keySet().stream().filter(p -> p.getJfugueRep() < 13).forEach(i -> {
				if (intervalsAvailability.get(i))
					anyAvailable = true;
			});
		if (compound)
			intervalsAvailability.keySet().stream().filter(p -> p.getJfugueRep() > 12).forEach(i -> {
				if (intervalsAvailability.get(i))
					anyAvailable = true;
			});
		return anyAvailable;

	}

	/**
	 * Sets the intervals availability.
	 *
	 * @param intervalsAvailability the intervals availability
	 */
	public void setIntervalsAvailability(LinkedHashMap<Interval, Boolean> intervalsAvailability) {
		this.intervalsAvailability = intervalsAvailability;
	}

	/**
	 * Checks if is simple.
	 *
	 * @return true, if is simple
	 */
	public boolean isSimple() {
		return simple;
	}

	/**
	 * Sets the simple.
	 *
	 * @param simple the new simple
	 */
	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	/**
	 * Checks if is compound.
	 *
	 * @return true, if is compound
	 */
	public boolean isCompound() {
		return compound;
	}

	/**
	 * Sets the compound.
	 *
	 * @param complex the new compound
	 */
	public void setCompound(boolean complex) {
		this.compound = complex;
	}

}
