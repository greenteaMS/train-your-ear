package mary.train.your.ear.model.util;

import javax.swing.JFrame;

import org.junit.Test;

import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;

public class PatternToAbcNotationParserTest {


	String pattern1 = "T120 I[Choir_Aahs] 50q 54q 58q";
	String pattern2 = "T120 I[Clarinet] 58q 62q 65q";
	String pattern3 = "T120 I[Choir_Aahs] 59q+63q+66q";
	String pattern4 = "T120 I[Flute] 49q 52q 55q";

	@Test
	public void parsingSimpleMelodyTest() {
		PatternToAbcNotationParser parser = new PatternToAbcNotationParser();
		String abcNotation = parser.parse(pattern1);
		System.out.println(abcNotation);
		abcNotation = parser.parse(pattern2);
		System.out.println(abcNotation);
		abcNotation = parser.parse(pattern3);
		System.out.println(abcNotation);
		abcNotation = parser.parse(pattern4);
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
