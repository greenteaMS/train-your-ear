/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import mary.train.your.ear.model.structures.CustomChord;

public class ChordStatElement {

	private CustomChord chord;
	private String userAnswer;

	public ChordStatElement(CustomChord chord, String userAnswer){
		this.setChord(chord);
		this.setUserAnswer(userAnswer);
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public CustomChord getChord() {
		return chord;
	}

	public void setChord(CustomChord chord) {
		this.chord = chord;
	}

}
