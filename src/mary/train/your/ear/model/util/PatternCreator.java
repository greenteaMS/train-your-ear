package mary.train.your.ear.model.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.jfugue.pattern.Pattern;

import mary.train.your.ear.exceptions.PatternCreationException;
import mary.train.your.ear.model.settings.GeneralSettings;
import mary.train.your.ear.model.settings.GeneralSettings.PlayType;
import mary.train.your.ear.model.strategies.ChordStrategy;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Interval;

public class PatternCreator {

	private static Random random = new Random();

	public static LinkedList<Pattern> createPatternForNewChord(LinkedList<Interval> newStructure){
		LinkedList<Pattern> patterns = new LinkedList<>();
		Interval array[] = new Interval[newStructure.size()];
		String melPattern = createPatternForStructureNoAcc(newStructure.toArray(array), " ", 60);
		String harmPattern = createPatternForStructureNoAcc(newStructure.toArray(array), "+", 60);
		Pattern p = new Pattern(melPattern);
		p.setInstrument(1);
		patterns.add(p);
		Pattern p2 = new Pattern(harmPattern);
		p2.setInstrument(2);
		patterns.add(p2);
		return patterns;
	}

	public static LinkedList<Pattern> createPatterns(LinkedList<Interval[]> structures)
			throws PatternCreationException {
		LinkedList<Pattern> patterns = new LinkedList<>();
		String connector = GeneralSettings.getInstance().getPlaytype() == PlayType.Harmonically ? "+" : " ";
		structures.forEach(struct -> {
			String pat = createPatternForStructure(struct, connector, getLowest());
			if (pat == null)
				throw new PatternCreationException("Method createPatternForStructure failed");
			Pattern p = new Pattern(pat);
			p.setInstrument(GeneralSettings.getInstance().getInstrumentIndex());
			p.setTempo(GeneralSettings.getInstance().getTempo());
			patterns.add(p);
			System.out.println(struct + " " + p.toString());
		});
		return patterns;
	}

	private static String createPatternForStructure(Interval[] structure, String connector, int lowestPitch) {
		if (structure == null)
			return null;
		int acc = lowestPitch;
		StringBuilder sb = new StringBuilder();
		sb.append(acc + "q");
		sb.append(connector);
		for (Interval interval : structure) {
			acc += interval.getJfugueRep();
			sb.append(acc);
			sb.append("q");
			sb.append(connector);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private static String createPatternForStructureNoAcc(Interval[] structure, String connector, int lowestPitch) {
		if (structure == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append(lowestPitch + "q");
		sb.append(connector);
		for (Interval interval : structure) {
			sb.append(lowestPitch + interval.getJfugueRep());
			sb.append("q");
			sb.append(connector);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private static int getLowest() {
		GeneralSettings ps = GeneralSettings.getInstance();

		return random.ints(ps.getLowestPitchRange()[0], ps.getLowestPitchRange()[1]).findAny().getAsInt();
	}

	public static LinkedList<Pattern> createPatternsForChords(LinkedList<CustomChord> structures) {
		LinkedList<Pattern> patterns = new LinkedList<>();
		String connector = GeneralSettings.getInstance().getPlaytype() == PlayType.Harmonically ? "+" : " ";
		structures.forEach(struct -> {
			String pat = createChordPattern(generateMidis(struct), connector);
			if (pat == null)
				throw new PatternCreationException("Method createPatternForStructure failed");
			Pattern p = new Pattern(pat);
			p.setInstrument(GeneralSettings.getInstance().getInstrumentIndex());
			p.setTempo(GeneralSettings.getInstance().getTempo());
			patterns.add(p);
			System.out.println(struct + " " + p.toString());
		});
		return patterns;
	}

	private static String createChordPattern(LinkedList<Integer> generateMidis, String connector) {
		if (generateMidis == null)
			return null;
		StringBuilder sb = new StringBuilder();
		generateMidis.forEach(midi -> {
			sb.append(midi);
			sb.append("q");
			sb.append(connector);
		});
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private static LinkedList<Integer> generateMidis(CustomChord struct) {
		System.out.println(struct.getName() + " " + struct.getStructure());
		LinkedList<Integer> values = new LinkedList<>();
		int prime = getLowest();
		values.add(prime);
		for (Interval in : struct.getStructure()) {
			values.add(prime + in.getJfugueRep());
		}
		if (struct.hasInversions()) {
			System.out.println("Inversion: " + struct.getInversion().getIndex());
			for (int i = 0; i < struct.getInversion().getIndex(); i++) {
				values.set(i, values.get(i) + 12);
			}
		}
		Collections.sort(values);
		int shift = prime - values.get(0);
		for (int i = 0; i < values.size(); i++)
			values.set(i, values.get(i) + shift);
		System.out.println(values);
		return values;
	}

	/*public static void main(String[] args) {
		ChordStrategy chordPlayer = new ChordStrategy();
		ChordsResponse response = (ChordsResponse) chordPlayer.play();
		System.out.println(response.getAnswer() + "\n" + response.getReadyToPlay());
	}*/

}
