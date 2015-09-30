/*
 *
 */
package mary.train.your.ear.model.settings;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;

// TODO: Auto-generated Javadoc
/**
 * The Class ChordsSettings.
 */
@Root
public class ChordsSettings {

	private static final Logger LOG = LogManager.getLogger(ChordsSettings.class);

	/** The settings. */
	private static ChordsSettings settings;

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public static ChordsSettings getSettings() {
		if (settings == null)
			settings = new ChordsSettings();
		return settings;
	}

	/**
	 * Sets the settings.
	 *
	 * @param s
	 *            the new settings
	 */
	public static void setSettings(ChordsSettings s) {
		settings = s;
	}

	/** The no of pitches. */
	@Element
	private int noOfPitches;

	/** The lists of chords. */
	private HashMap<Integer, LinkedList<CustomChord>> listsOfChords;

	/** The chords availability. */
	@ElementMap
	private LinkedHashMap<String, Boolean> chordsAvailability;

	/** The enabled inversions. */
	@ElementMap
	private HashMap<Inversion, Boolean> enabledInversions;

	/**
	 * Instantiates a new chords settings.
	 */
	public ChordsSettings() {
		setNoOfPitches(3);
		setListsOfChords(new HashMap<>());

		initInversions();
		chordsAvailability = new LinkedHashMap<>();
		loadBuildInChords();
	}

	/**
	 * Inits the inversions.
	 */
	private void initInversions() {
		enabledInversions = new HashMap<>();
		for (Inversion inv : Inversion.values()) {
			if (inv == Inversion.None)
				enabledInversions.put(inv, true);
			else
				enabledInversions.put(inv, false);
		}

	}

	/**
	 * Load build in chords.
	 */
	private void loadBuildInChords() {
		listsOfChords = StructuresManager.getChords();
		//LOG.debug(listsOfChords);
		listsOfChords.values().forEach(list -> {
			list.forEach(chord -> {
				chordsAvailability.put(chord.getName(), true);

			});
		});
	}

	/**
	 * Gets the inversions.
	 *
	 * @return the inversions
	 */
	public LinkedList<Inversion> getInversions() {
		LinkedList<Inversion> inversion = new LinkedList<>();
		enabledInversions.entrySet().stream()
				.filter(entry -> entry.getValue() == true && entry.getKey().getIndex() < noOfPitches)
				.forEach(elem -> inversion.add(elem.getKey()));
		Collections.sort(inversion);
		return inversion;
	}

	/**
	 * Gets the no of pitches.
	 *
	 * @return the no of pitches
	 */
	public int getNoOfPitches() {
		return noOfPitches;
	}

	/**
	 * Sets the no of pitches.
	 *
	 * @param noOfPitches
	 *            the new no of pitches
	 */
	public void setNoOfPitches(int noOfPitches) {
		this.noOfPitches = noOfPitches;
	}

	/**
	 * Gets the lists of chords.
	 *
	 * @return the lists of chords
	 */
	public HashMap<Integer, LinkedList<CustomChord>> getListsOfChords() {
		return listsOfChords;
	}

	/**
	 * Sets the lists of chords.
	 *
	 * @param listsOfChords
	 *            the lists of chords
	 */
	public void setListsOfChords(HashMap<Integer, LinkedList<CustomChord>> listsOfChords) {
		this.listsOfChords = listsOfChords;
	}

	/**
	 * Gets the enabled invertions map.
	 *
	 * @return the enabled invertions map
	 */
	public HashMap<Inversion, Boolean> getEnabledInvertions() {
		return enabledInversions;
	}

	/**
	 * Sets the enabled invertions map.
	 *
	 * @param enabledInvertions
	 *            the enabled invertions map
	 */
	public void setEnabledInvertions(HashMap<Inversion, Boolean> enabledInvertions) {
		this.enabledInversions = enabledInvertions;
	}

	/**
	 * Gets the chords availability map.
	 *
	 * @return the chords availability
	 */
	public LinkedHashMap<String, Boolean> getChordsAvailability() {
		return chordsAvailability;
	}

	/**
	 * Sets the chords availability map.
	 *
	 * @param chordsAvailability
	 *            the chords availability
	 */
	public void setChordsAvailability(LinkedHashMap<String, Boolean> chordsAvailability) {
		this.chordsAvailability = chordsAvailability;
	}

	/**
	 * Sets inv chord inversion enabled value to opposite.
	 *
	 * @param inv
	 *            the inversion of chord
	 */
	public void changeInvertionEnabled(Inversion inv) {
		enabledInversions.put(inv, !enabledInversions.get(inv));
	}

	/**
	 * Gets the chords.
	 *
	 * @return the chords
	 */
	public LinkedList<CustomChord> getChords() {
		return listsOfChords.get(noOfPitches);
	}

	/**
	 * Gets the available chords list.
	 *
	 * @return the available chords list
	 */
	public LinkedList<CustomChord> getAvailableChordsList() {
		LinkedList<CustomChord> chords = new LinkedList<>();
		getChords().stream().filter(p -> chordsAvailability.get(p.getName()) == true).forEach(s -> chords.add(s));
		return chords;
	}

	/**
	 * Gets the chords.
	 *
	 * @param numberOfPitches
	 *            the number of pitches in chord
	 * @return the chords
	 */
	public LinkedList<CustomChord> getChords(int numberOfPitches) {
		return listsOfChords.get(numberOfPitches);
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		loadBuildInChords();
	}
}
