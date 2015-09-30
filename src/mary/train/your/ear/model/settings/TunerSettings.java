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

import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.structures.FrequencyRatio;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerSettings.
 */
@Root
public class TunerSettings {

	/** The settings. */
	private static TunerSettings settings;

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public static TunerSettings getSettings() {
		if (settings == null)
			settings = new TunerSettings();
		return settings;
	}

	/**
	 * Sets the settings.
	 *
	 * @param s
	 *            the new settings
	 */
	public static void setSettings(TunerSettings s) {
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

	/**
	 * Instantiates a new tuner settings.
	 */
	public TunerSettings() {
		loadRatios();

	}

	/**
	 * Load ratios.
	 */
	private void loadRatios() {
		allRatios.clear();
		allRatios.addAll(FrequencyRatio.ALL);
		allRatios.addAll(StructuresManager.getRatios());
		FrequencyRatio.ALL.stream().filter(ratio -> !FrequencyRatio.SMALL_RATIOS.contains(ratio))
				.forEach(ratio -> availableRatios.put(ratio, true));
	}

	public void reloadAllRatios() {
		allRatios.clear();
		allRatios.addAll(FrequencyRatio.ALL);
		allRatios.addAll(StructuresManager.getRatios());
		availableRatios.keySet().removeIf(ratio -> !allRatios.contains(ratio));
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
}
