package mary.train.your.ear.model.util;

import org.junit.Test;

public class PatternToAbcNotationParserTest {


	String pattern1 = "T120 I[Choir_Aahs] 50q 54q 58q";
	String pattern2 = "T120 I[Clarinet] 58q 62q 65q";
	String pattern3 = "T120 I[Choir_Aahs] 59q+63q+66q";
	String pattern4 = "T120 I[Flute] 49q 52q 55q";

	@Test
	public void parsingSimpleMelodyTest() {
		String abcNotation = PatternToAbcNotationParser.parse(pattern1);
		System.out.println(abcNotation);
		abcNotation = PatternToAbcNotationParser.parse(pattern2);
		System.out.println(abcNotation);
		abcNotation = PatternToAbcNotationParser.parse(pattern3);
		System.out.println(abcNotation);
		abcNotation = PatternToAbcNotationParser.parse(pattern4);
		System.out.println(abcNotation);
	}

	@Test
	public void parsingSimpleChordTest() {

	}

	@Test
	public void parsingMelodyAndChordMixTest(){

	}

	@Test
	public void parsingMelodyWithTempoAndInstrumentTest() {

	}

	@Test
	public void parsingChordWithTempoAndInstrumentTest() {

	}

}
