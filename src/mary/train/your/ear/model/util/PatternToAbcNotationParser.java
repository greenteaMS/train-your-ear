package mary.train.your.ear.model.util;

import java.util.LinkedList;

import org.jfugue.pattern.Pattern;

public class PatternToAbcNotationParser {

	public PatternToAbcNotationParser() {

	}

	/**
	 *
	 * @param patternString
	 *            - caly pattern z tempem i instrumentem - jedno wspolbrzmienie
	 *            = jeden takt
	 * @return
	 */
	static String parse(String patternString) {
		StringBuilder sb = new StringBuilder();
		String[] elements = patternString.split(" ");
		//sb.append(AbcMapper.QUARTER);
		//sb.append("\n");
		for (String elem : elements) {
			sb.append(resolveAbcNotation(elem));
		}
		return sb.toString();
	}

	private static String resolveAbcNotation(String elem) {
		// eliminacja tempa i instrumentu
		if (elem.startsWith("T") || elem.startsWith("I["))
			return "";
		// rozwiazanie wspolbrzmien - harmoniczne w calych nutach
		if (elem.contains("+"))
			return resolveChord(elem);
		return resolveSingleNote(elem);
	}

	private static String resolveSingleNote(String elem) {
		return AbcMapper.NOTES_MAPPER.get(Integer.parseInt(elem.replace("q", "")));
	}

	private static String resolveChord(String elem) {
		System.out.println(elem);
		String[] notes = elem.split("\\+");
		StringBuilder sb = new StringBuilder();
		sb.append(AbcMapper.OPEN_BRACKET);
		for (String note : notes) {
			sb.append(resolveSingleNote(note));
		}
		sb.append(AbcMapper.CLOSE_BRACKET);
		sb.append("4");
		return sb.toString();
	}

	public String parsePattern(Pattern pattern) {
		return null;
	}

	public static String parsePatternsList(LinkedList<Pattern> patterns) {
		StringBuilder sb = new StringBuilder();
		sb.append("X:1\nT:\nL:1/4\nK:C\n");
		patterns.forEach(p -> {
			sb.append(parse(p.toString()));
			sb.append(AbcMapper.BARLINE);
		});
		return sb.toString();
	}

	String parseChord(String chord) {
		return null;
	}

}
