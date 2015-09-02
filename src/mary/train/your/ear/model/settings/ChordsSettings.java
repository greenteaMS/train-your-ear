package mary.train.your.ear.model.settings;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import mary.train.your.ear.model.DataManager;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;
import mary.train.your.ear.model.structures.dao.CustomChordDao;

public class ChordsSettings {

	private static ChordsSettings settings;

	public static ChordsSettings getSettings() {
		if (settings == null)
			settings = new ChordsSettings();
		return settings;
	}

	private int noOfPitches;
	private HashMap<Integer, LinkedList<CustomChord>> listsOfChords;
	private LinkedHashMap<String, Boolean> chordsAvailability;
	private HashMap<Inversion, Boolean> enabledInversions;

	private ChordsSettings() {
		setNoOfPitches(3);
		setListsOfChords(new HashMap<>());

		initInversions();
		chordsAvailability = new LinkedHashMap<>();
		loadBuildInChords();
	}

	private void initInversions() {
		enabledInversions = new HashMap<>();
		for (Inversion inv : Inversion.values()) {
			if (inv == Inversion.None)
				enabledInversions.put(inv, true);
			else
				enabledInversions.put(inv, false);
		}

	}

	private void loadBuildInChords() {
		listsOfChords = DataManager.getChords();
		listsOfChords.values().forEach(list -> {
			list.forEach(chord -> {
				chordsAvailability.put(chord.getName(), true);

			});
		});
	}

	public LinkedList<Inversion> getInversions() {
		LinkedList<Inversion> inversion = new LinkedList<>();
		enabledInversions.entrySet().stream()
				.filter(entry -> entry.getValue() == true && entry.getKey().getIndex() < noOfPitches)
				.forEach(elem -> inversion.add(elem.getKey()));
		Collections.sort(inversion);
		return inversion;
	}

	public int getNoOfPitches() {
		return noOfPitches;
	}

	public void setNoOfPitches(int noOfPitches) {
		this.noOfPitches = noOfPitches;
	}

	public HashMap<Integer, LinkedList<CustomChord>> getListsOfChords() {
		return listsOfChords;
	}

	public void setListsOfChords(HashMap<Integer, LinkedList<CustomChord>> listsOfChords) {
		this.listsOfChords = listsOfChords;
	}

	public HashMap<Inversion, Boolean> getEnabledInvertions() {
		return enabledInversions;
	}

	public void setEnabledInvertions(HashMap<Inversion, Boolean> enabledInvertions) {
		this.enabledInversions = enabledInvertions;
	}

	public LinkedHashMap<String, Boolean> getChordsAvailability() {
		return chordsAvailability;
	}

	public void setChordsAvailability(LinkedHashMap<String, Boolean> chordsAvailability) {
		this.chordsAvailability = chordsAvailability;
	}

	public void changeInvertionEnabled(Inversion inv) {
		enabledInversions.put(inv, !enabledInversions.get(inv));
	}

	public LinkedList<CustomChord> getChords() {
		return listsOfChords.get(noOfPitches);
	}

	public LinkedList<CustomChord> getAvailableChordsList() {
		LinkedList<CustomChord> chords = new LinkedList<>();
		getChords().stream().filter(p -> chordsAvailability.get(p.getName()) == true).forEach(s -> chords.add(s));
		return chords;
	}

	public LinkedList<CustomChord> getChords(int i) {
		listsOfChords.get(i).forEach(l -> {
			System.out.println(i + " " + l.getName());
		});
		return listsOfChords.get(i);
	}

	public void refresh() {
		loadBuildInChords();
	}
}
