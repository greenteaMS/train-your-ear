/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.settings;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.FrequencyRatio;

@Root
public class IntervalComparatorSettings {

	/** The settings. */
	private static IntervalComparatorSettings settings;

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public static IntervalComparatorSettings getSettings() {
		if (settings == null)
			settings = new IntervalComparatorSettings();
		return settings;
	}

	/**
	 * Sets the settings.
	 *
	 * @param s
	 *            the new settings
	 */
	public static void setSettings(IntervalComparatorSettings s) {
		settings = s;
	}

	/** The all ratios. */
	private LinkedList<FrequencyRatio> allRatios = new LinkedList<>();

	/** The available ratios. */
	@ElementMap
	private LinkedHashMap<FrequencyRatio, Boolean> availableRatios = new LinkedHashMap<>();

	/** The set size. */
	@Element
	private int setSize = 1;

	@Element
	private boolean samePitch = true;

	/**
	 * Instantiates a new tuner settings.
	 */
	public IntervalComparatorSettings() {
		loadRatios();
		samePitch = true;
	}

	/**
	 * Load ratios.
	 */
	private void loadRatios() {
		allRatios.clear();
		allRatios.addAll(FrequencyRatio.SMALL_RATIOS);
		FrequencyRatio.SMALL_RATIOS.forEach(ratio -> availableRatios.put(ratio, true));
	}

	/**
	 * Gets the sets the size.
	 *
	 * @return the sets the size
	 */
	public int getSetSize() {
		return setSize;
	}

	/**
	 * Sets the sets the size.
	 *
	 * @param setSize
	 *            the new sets the size
	 */
	public void setSetSize(int setSize) {
		this.setSize = setSize;
	}

	/**
	 * Gets the all ratios.
	 *
	 * @return the all ratios
	 */
	public LinkedList<FrequencyRatio> getAllRatios() {
		return allRatios;
	}

	/**
	 * Sets the all ratios.
	 *
	 * @param allRatios
	 *            the new all ratios
	 */
	public void setAllRatios(LinkedList<FrequencyRatio> allRatios) {
		this.allRatios = allRatios;
	}

	/**
	 * Gets the available ratios.
	 *
	 * @return the available ratios
	 */
	public LinkedHashMap<FrequencyRatio, Boolean> getAvailableRatios() {
		return availableRatios;
	}

	/**
	 * Sets the available ratios.
	 *
	 * @param availableRatios
	 *            the available ratios
	 */
	public void setAvailableRatios(LinkedHashMap<FrequencyRatio, Boolean> availableRatios) {
		this.availableRatios = availableRatios;
	}

	public void setSamePitch(Boolean value) {
		samePitch = value;
	}

	public boolean getSamePitchValue(){
		return samePitch;
	}
}
