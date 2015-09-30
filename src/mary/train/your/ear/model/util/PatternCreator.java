/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Note;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.settings.GeneralTaskSettings;
import mary.train.your.ear.model.settings.GeneralTaskSettings.PlayType;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.structures.IntervalComparatorElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternCreator.
 */
public class PatternCreator {

	/** The random. */
	private static Random random = new Random();

	/**
	 * Creates the pattern for new chord.
	 *
	 * @param newStructure
	 *            the new structure
	 * @return the linked list
	 */
	public static LinkedList<Pattern> createPatternForNewChord(List<Interval> newStructure) {
		LinkedList<Pattern> patterns = new LinkedList<>();
		Interval array[] = new Interval[newStructure.size()];
		String melPattern = createPatternForStructureNoAccelerator(newStructure.toArray(array),
				StringConstants.MEL_CONNECTOR, 60);
		String harmPattern = createPatternForStructureNoAccelerator(newStructure.toArray(array),
				StringConstants.HARM_CONNECTOR, 60);
		Pattern p = new Pattern(melPattern);
		p.setInstrument(1);
		patterns.add(p);
		Pattern p2 = new Pattern(harmPattern);
		p2.setInstrument(2);
		patterns.add(p2);
		return patterns;
	}

	/**
	 * Creates the patterns.
	 *
	 * @param structures
	 *            the structures
	 * @return the linked list
	 * @throws PatternCreationException
	 *             the pattern creation exception
	 */
	public static LinkedList<Pattern> createPatterns(LinkedList<Interval> structures) {
		LinkedList<Pattern> patterns = new LinkedList<>();
		String connector = GeneralTaskSettings.getSettings().getPlaytype() == PlayType.Harmonically
				? StringConstants.HARM_CONNECTOR : StringConstants.MEL_CONNECTOR;
		structures.forEach(struct -> {
			String pat = createPatternForInterval(struct, connector, getLowest());
			if (pat == null)
				throw new NullPointerException();
			Pattern p = new Pattern(pat);
			p.setInstrument(GeneralTaskSettings.getSettings().getInstrumentIndex());
			p.setTempo(GeneralTaskSettings.getSettings().getTempo());
			patterns.add(p);
		});
		return patterns;
	}

	/**
	 * Creates the pattern for structure.
	 *
	 * @param structure
	 *            the structure
	 * @param connector
	 *            the connector
	 * @param lowestPitch
	 *            the lowest pitch
	 * @return the string
	 */
	private static String createPatternForInterval(Interval structure, String connector, double lowestPitch) {
		if (structure == null)
			return null;
		LinkedList<Double> list = new LinkedList<>();
		list.add(lowestPitch);
		list.add(lowestPitch + structure.getJfugueRep());
		reorderList(list);

		StringBuilder sb = new StringBuilder();
		sb.append("Re ");
		sb.append((int) (double) list.get(0));
		sb.append(StringConstants.QUARTER);
		sb.append(connector);

		sb.append((int) (double) list.get(1));
		sb.append(StringConstants.QUARTER);
		sb.append(connector);

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Creates the pattern for structure no acc.
	 *
	 * @param structure
	 *            the structure
	 * @param connector
	 *            the connector
	 * @param lowestPitch
	 *            the lowest pitch
	 * @return the string
	 */
	private static String createPatternForStructureNoAccelerator(Interval[] structure, String connector,
			int lowestPitch) {
		if (structure == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append(lowestPitch + StringConstants.QUARTER);
		sb.append(connector);
		for (Interval interval : structure) {
			sb.append(lowestPitch + interval.getJfugueRep());
			sb.append(StringConstants.QUARTER);
			sb.append(connector);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
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

	/**
	 * Creates the patterns for chords.
	 *
	 * @param structures
	 *            the structures
	 * @return the linked list
	 */
	public static LinkedList<Pattern> createPatternsForChords(LinkedList<CustomChord> structures) {
		LinkedList<Pattern> patterns = new LinkedList<>();
		String connector = GeneralTaskSettings.getSettings().getPlaytype() == PlayType.Harmonically
				? StringConstants.HARM_CONNECTOR : StringConstants.MEL_CONNECTOR;
		structures.forEach(struct -> {
			String pat = createChordPattern(generateMidis(struct), connector);
			if (pat == null)
				throw new NullPointerException();
			Pattern p = new Pattern(pat);
			p.setInstrument(GeneralTaskSettings.getSettings().getInstrumentIndex());
			p.setTempo(GeneralTaskSettings.getSettings().getTempo());
			patterns.add(p);
		});
		return patterns;
	}

	/**
	 * Creates the chord pattern.
	 *
	 * @param generatedMidis
	 *            the generate midis
	 * @param connector
	 *            the connector
	 * @return the string
	 */
	private static String createChordPattern(LinkedList<Double> generatedMidis, String connector) {
		if (generatedMidis == null)
			return null;
		reorderList(generatedMidis);
		StringBuilder sb = new StringBuilder();
		sb.append("Re ");
		generatedMidis.forEach(midi -> {
			sb.append((int)(double)midi);
			sb.append(StringConstants.QUARTER);
			sb.append(connector);
		});
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private static void reorderList(LinkedList<Double> list) {
		if (GeneralTaskSettings.getSettings().getPlaytype() == PlayType.Harmonically
				|| GeneralTaskSettings.getSettings().getMelPlayType() == PlayType.MelUp)
			return;
		if (GeneralTaskSettings.getSettings().getMelPlayType() == PlayType.MelDown)
			Collections.reverse(list);
		else
			Collections.shuffle(list, random);
	}

	/**
	 * Generate midis.
	 *
	 * @param struct
	 *            the struct
	 * @return the linked list
	 */
	private static LinkedList<Double> generateMidis(CustomChord struct) {
		LinkedList<Double> values = new LinkedList<>();
		int prime = getLowest();
		values.add((double) prime);
		for (Interval in : struct.getStructure()) {
			values.add((double) (prime + in.getJfugueRep()));
		}
		if (struct.hasInversions()) {
			for (int i = 0; i < struct.getInversion().getIndex(); i++) {
				values.set(i, values.get(i) + 12);
			}
		}
		Collections.sort(values);
		double shift = prime - values.get(0);
		for (int i = 0; i < values.size(); i++)
			values.set(i, values.get(i) + shift);
		return values;
	}

	public static void createPatternsForComparator(LinkedList<IntervalComparatorElement> listOfElements) {
		listOfElements.forEach(element -> {
			createPattern(element);
		});

	}

	private static void createPattern(IntervalComparatorElement element) {
		String connector = GeneralTaskSettings.getSettings().getPlaytype() == PlayType.Harmonically
				? StringConstants.HARM_CONNECTOR : StringConstants.MEL_CONNECTOR;
		double baseOfFirst = getLowest();
		double baseOfSecond;
		if (IntervalComparatorSettings.getSettings().getSamePitchValue())
			baseOfSecond = baseOfFirst;
		else
			baseOfSecond = getLowest();
		Pattern first = createFirstPattern(element, connector, baseOfFirst);
		Pattern second;
		if (element.getFirstCompareToSecond() == 0)
			second = createFirstPattern(element, connector, baseOfSecond);
		else {
			second = createSecondPattern(element, connector, baseOfSecond);
		}
		if (element.getFirstCompareToSecond() > 0)
			element.setPatterns(new Pattern[] { second, first });
		else
			element.setPatterns(new Pattern[] { first, second });
	}

	private static Pattern createFirstPattern(IntervalComparatorElement element, String connector, double baseFreq) {
		String pat = createPatternForInterval(element.getInterval(), connector, baseFreq);
		Pattern p = new Pattern("Rq " + pat);
		p.setInstrument(GeneralTaskSettings.getSettings().getInstrumentIndex());
		p.setTempo(GeneralTaskSettings.getSettings().getTempo());
		return p;
	}

	private static Pattern createSecondPattern(IntervalComparatorElement element, String connector,
			double baseOfSecond) {

		double higherFreq = calculateHigherFreq(baseOfSecond, element.getInterval().getJfugueRep(), element.getRatio());
		String pat = buildPattern(Note.getFrequencyForNote((int) baseOfSecond), higherFreq, connector);
		Pattern p = new Pattern("Rq " + pat);
		p.setInstrument(GeneralTaskSettings.getSettings().getInstrumentIndex());
		p.setTempo(GeneralTaskSettings.getSettings().getTempo());
		return p;
	}

	private static double calculateHigherFreq(double baseOfSecond, int semitones, FrequencyRatio ratio) {
		double higherFreq = Note.getFrequencyForNote((int) (baseOfSecond + semitones));
		higherFreq = ratio.getRatio() * higherFreq;
		return Math.round(higherFreq * 1000) / 1000;
	}

	private static String buildPattern(double base, double higher, String connector) {
		LinkedList<Double> list = new LinkedList<>();
		list.add(base);
		list.add(higher);
		reorderList(list);
		return (connector.equals(StringConstants.MEL_CONNECTOR) ? buildMelodicPattern(list)
				: buildHarmonicPattern(list));

	}

	private static String buildMelodicPattern(LinkedList<Double> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringConstants.M);
		sb.append(list.get(0));
		sb.append(StringConstants.QUARTER);
		sb.append(StringConstants.SPACE);
		sb.append(StringConstants.M);
		sb.append(list.get(1));
		sb.append(StringConstants.QUARTER);
		return sb.toString();
	}

	private static String buildHarmonicPattern(LinkedList<Double> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringConstants.VOICE_0);
		sb.append(StringConstants.M);
		sb.append(list.get(0));
		sb.append(StringConstants.QUARTER);
		sb.append(StringConstants.VOICE_1);
		sb.append(StringConstants.M);
		sb.append(list.get(1));
		sb.append(StringConstants.QUARTER);
		return sb.toString();
	}

}
