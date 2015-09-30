/*
 * 
 */
package mary.train.your.ear.model.util;

import java.util.LinkedList;

import org.jfugue.pattern.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternToAbcNotationParser.
 */
public class PatternToAbcNotationParser {

	/**
	 * Instantiates a new pattern to abc notation parser.
	 */
	public PatternToAbcNotationParser() {

	}

	/**
	 * Parses the.
	 *
	 * @param patternString            - caly pattern z tempem i instrumentem - jedno wspolbrzmienie
	 *            = jeden takt
	 * @return the string
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

	/**
	 * Resolve abc notation.
	 *
	 * @param elem the elem
	 * @return the string
	 */
	private static String resolveAbcNotation(String elem) {
		// eliminacja tempa i instrumentu
		if (elem.startsWith("T") || elem.startsWith("I["))
			return "";
		// rozwiazanie wspolbrzmien - harmoniczne w calych nutach
		if (elem.contains("+"))
			return resolveChord(elem);
		return resolveSingleNote(elem);
	}

	/**
	 * Resolve single note.
	 *
	 * @param elem the elem
	 * @return the string
	 */
	private static String resolveSingleNote(String elem) {
		return AbcMapper.NOTES_MAPPER.get(Integer.parseInt(elem.replace("q", "")));
	}

	/**
	 * Resolve chord.
	 *
	 * @param elem the elem
	 * @return the string
	 */
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

	/**
	 * Parses the pattern.
	 *
	 * @param pattern the pattern
	 * @return the string
	 */
	public String parsePattern(Pattern pattern) {
		return null;
	}

	/**
	 * Parses the patterns list.
	 *
	 * @param patterns the patterns
	 * @return the string
	 */
	public static String parsePatternsList(LinkedList<Pattern> patterns) {
		StringBuilder sb = new StringBuilder();
		sb.append("X:1\nT:\nL:1/4\nK:C\n");
		patterns.forEach(p -> {
			sb.append(parse(p.toString()));
			sb.append(AbcMapper.BARLINE);
		});
		return sb.toString();
	}

	/**
	 * Parses the chord.
	 *
	 * @param chord the chord
	 * @return the string
	 */
	String parseChord(String chord) {
		return null;
	}

}
