/*
 *
 */
package mary.train.your.ear.model.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import org.jfugue.theory.Note;

import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.GeneralTaskSettings;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.structures.IntervalComparatorElement;
import mary.train.your.ear.model.structures.Inversion;
import mary.train.your.ear.model.structures.TunerElement;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Structures objects.
 */
public class StructuresFactory {

	/** The random. */
	private static Random random = new Random();

	/**
	 * Creates a new Structures object.
	 *
	 * @return list of Interval arrays
	 */
	public static LinkedList<Interval> createIntervals() {
		LinkedList<Interval> list = new LinkedList<>();
		for (int i = 1; i <= GeneralTaskSettings.getSettings().getNrOfChords(); ++i)
			list.add(randomInterval());
		return list;
	}

	/**
	 * Random interval.
	 *
	 * @return the interval[]
	 */
	private static Interval randomInterval() {
		int lower = 1;
		int upper = 13;
		if (IntervalsSettings.getSettings().isCompound())
			upper += 12;
		if (!IntervalsSettings.getSettings().isSimple())
			lower += 12;
		int nrOfSemitones = random.ints(lower, upper).findAny().getAsInt();
		Interval ivl = Interval.find(nrOfSemitones);
		if (!IntervalsSettings.getSettings().getIntervalsAvailability().get(ivl))
			return randomInterval();
		return ivl;
	}

	/**
	 * Creates a new Structures object.
	 *
	 * @return list of CustomChord
	 */
	public static LinkedList<CustomChord> createChords() {
		LinkedList<CustomChord> list = new LinkedList<>();
		for (int i = 1; i <= GeneralTaskSettings.getSettings().getNrOfChords(); ++i)
			list.add(randomChord());
		return list;
	}

	/**
	 * Random chord.
	 *
	 * @return CustomChord random chord
	 */
	private static CustomChord randomChord() {
		LinkedList<CustomChord> chords = ChordsSettings.getSettings().getAvailableChordsList();
		int count = chords.size();
		CustomChord chord = new CustomChord(chords.get(random.ints(0, count).findAny().getAsInt()));
		if (chord.hasInversions())
			chord.setInversion(randomInversion());
		return chord;
	}

	/**
	 * Random inversion.
	 *
	 * @return the inversion
	 */
	private static Inversion randomInversion() {
		int value = random.ints(0, ChordsSettings.getSettings().getNoOfPitches()).findAny().getAsInt();
		Inversion inv = Inversion.getByIndex(value);
		if (inv.getIndex() >= ChordsSettings.getSettings().getNoOfPitches()
				|| !ChordsSettings.getSettings().getEnabledInvertions().get(inv))
			inv = randomInversion();
		return inv;
	}

	/**
	 * Generate tuner elements.
	 *
	 * @return the linked list
	 */
	public static LinkedList<TunerElement> generateTunerElements() {
		LinkedList<TunerElement> list = new LinkedList<>();
		for (int i = 0; i < TunerSettings.getSettings().getSetSize(); i++) {
			FrequencyRatio ratio = getRatio(TunerSettings.getSettings().getAvailableRatios());
			double base = Note.getFrequencyForNote(getLowest());
			TunerElement element = new TunerElement(ratio, base);
			double correct = calculateCorrectFreq(ratio, base);
			element.setCorrectFreq(correct);
			if (ratio.getRatio() < 1.1)
				element.setStartingFreq(correct + random.doubles(-19, 19).findAny().getAsDouble());
			else
				element.setStartingFreq(correct + random.doubles(-39, 39).findAny().getAsDouble());
			list.add(element);
		}
		return list;
	}

	/**
	 * Calculate correct freq.
	 *
	 * @param ratio
	 *            the ratio
	 * @param base
	 *            the base
	 * @return the double
	 */
	private static double calculateCorrectFreq(FrequencyRatio ratio, double base) {
		double pom = base * ratio.getRatio();
		return Math.round(pom * 1000000) / 1000000;
	}

	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	private static FrequencyRatio getRatio(LinkedHashMap<FrequencyRatio, Boolean> map) {
		int index = random.ints(0, map.size()).findAny().getAsInt();
		FrequencyRatio[] array = new FrequencyRatio[map.size()];
		FrequencyRatio ratio = map.keySet().toArray(array)[index];
		if (!map.get(ratio))
			return getRatio(map);
		return ratio;
	}

	/**
	 * Gets the lowest.
	 *
	 * @return the lowest
	 */
	private static int getLowest() {
		GeneralTaskSettings ps = GeneralTaskSettings.getSettings();
		return random.ints(ps.getLowestPitchRange()[0], ps.getLowestPitchRange()[1]).findAny().getAsInt();
	}

	// 1 - first larger than second
	// 0 - the same
	// -1 - first smaller than second
	public static LinkedList<IntervalComparatorElement> generateIntervalComparatorElements() {
		LinkedList<IntervalComparatorElement> list = new LinkedList<>();
		for (int i = 0; i < IntervalComparatorSettings.getSettings().getSetSize(); i++) {
			FrequencyRatio ratio = getRatio(IntervalComparatorSettings.getSettings().getAvailableRatios());
			Interval randomInterval = randomSimpleInterval();
			int firstCompareToSecond = random.ints(-1, 2).findAny().getAsInt();
			IntervalComparatorElement element = new IntervalComparatorElement(randomInterval, ratio,
					firstCompareToSecond);
			list.add(element);
		}
		return list;
	}



	private static Interval randomSimpleInterval() {
		int nrOfSemitones = random.ints(1, 13).findAny().getAsInt();
		return Interval.find(nrOfSemitones);
	}

}
