package mary.train.your.ear.model.util;

import java.util.LinkedList;
import java.util.Random;

import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.GeneralSettings;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.structures.Inversion;

public class StructuresFactory {

	static Random random = new Random();

	public static LinkedList<Interval[]> createIntervals() {
		LinkedList<Interval[]> list = new LinkedList<>();
		for (int i = 1; i <= GeneralSettings.getInstance().getNrOfChords(); ++i)
			list.add(randomInterval());
		return list;
	}

	private static Interval[] randomInterval() {
		int lower = 1;
		int upper = 13;
		if (IntervalsSettings.getSettings().isCompound()) upper+= 12;
		if (!IntervalsSettings.getSettings().isSimple()) lower+= 12;
		int nrOfSemitones = random.ints(lower, upper).findAny().getAsInt();
		Interval ivl = Interval.find(nrOfSemitones);
		if (!IntervalsSettings.getSettings().getIntervalsAvailability().get(ivl))
			return randomInterval();
		return new Interval[] { ivl };
	}

	public static LinkedList<CustomChord> createChords() {
		LinkedList<CustomChord> list = new LinkedList<>();
		for (int i = 1; i <= GeneralSettings.getInstance().getNrOfChords(); ++i)
			list.add(randomChord());
		return list;
	}

	private static CustomChord randomChord() {
		LinkedList<CustomChord> chords = ChordsSettings.getSettings().getAvailableChordsList();
		int count = chords.size();
		CustomChord chord = new CustomChord(chords.get(random.ints(0, count).findAny().getAsInt()));
		if (chord.hasInversions())
			chord.setInversion(randomInversion());
		System.out.println("Random chord: " + chord.getName() + " " + chord.getInversion() + " " + chord.hasInversions());
		return chord;
	}

	private static Inversion randomInversion() {
		int value = random.ints(0, ChordsSettings.getSettings().getNoOfPitches()).findAny().getAsInt();
		System.out.println("*** " + value);
		Inversion inv = Inversion.getByIndex(value);
		if (inv.getIndex() >= ChordsSettings.getSettings().getNoOfPitches()
				|| !ChordsSettings.getSettings().getEnabledInvertions().get(inv))
			inv = randomInversion();
		return inv;
	}

}
