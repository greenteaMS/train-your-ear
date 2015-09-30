/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.view.ui;

import java.io.IOException;

import org.jfugue.player.Player;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.statistics.IntervalComparatorStatElement;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.model.structures.IntervalComparatorElement;

public class ComparatorViewElement extends VBox {

	private IntervalComparatorElement element;

	@FXML
	private Label statusLabel;

	@FXML
	private Label ratioValue;

	@FXML
	private Button firstLargerButton;

	@FXML
	private Button secondLargerButton;

	@FXML
	private Button theSameButton;

	@FXML
	private Button playButton;

	@FXML
	private Button showAnswer;

	private PseudoClass correct = PseudoClass.getPseudoClass("correctAnswerCell");
	private PseudoClass wrong = PseudoClass.getPseudoClass("wrongAnswerCell");

	public ComparatorViewElement() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ComparatorViewElement.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public ComparatorViewElement(IntervalComparatorElement element) {
		this();
		this.setElement(element);
	}

	public IntervalComparatorElement getElement() {
		return element;
	}

	public void setElement(IntervalComparatorElement element) {
		this.element = element;
	}

	public void firstLargerAnswer() {
		statusLabel.setText(StringConstants.SL_FIRST_LARGER);
		checkAnswer(1);
	}

	public void theSameAnswer() {
		statusLabel.setText(StringConstants.SL_THE_SAME);
		checkAnswer(0);

	}

	public void secondLargerAnswer() {
		statusLabel.setText(StringConstants.SL_SECOND_LARGER);
		checkAnswer(-1);
	}

	private void checkAnswer(int i) {
		if (i == element.getFirstCompareToSecond()) {
			statusLabel.setText(statusLabel.getText());
			statusLabel.pseudoClassStateChanged(correct, true);
			if (i != 0)
				ratioValue.setText(element.getRatio().getName());
			updateButtonsState(true);
			updateStatistics(true, i == 0);
			highlightCorrect(i);
		} else {
			statusLabel.setText(statusLabel.getText());
			statusLabel.pseudoClassStateChanged(wrong, true);
			updateStatistics(false, i == 0);
		}
	}

	private void highlightCorrect(int i) {
		switch (i) {
		case 1:
			firstLargerButton.getStyleClass().add("firstCorrect");
			break;
		case 0:
			theSameButton.getStyleClass().add("sameCorrect");
			break;
		case -1:
			secondLargerButton.getStyleClass().add("secondCorrect");
			break;
		}
	}

	private void updateStatistics(boolean goodAnswer, boolean sameInterval) {
		if (sameInterval)
			UserData.getUserData().getDailyUserStatistics().updateIntervalComparatorStatistics(
					new IntervalComparatorStatElement(FrequencyRatio.ZERO_RATIO, goodAnswer));
		else
			UserData.getUserData().getDailyUserStatistics().updateIntervalComparatorStatistics(
					new IntervalComparatorStatElement(element.getRatio(), goodAnswer));
	}

	private void updateButtonsState(boolean disabled) {
		firstLargerButton.setDisable(disabled);
		secondLargerButton.setDisable(disabled);
		theSameButton.setDisable(disabled);
	}

	public void showAnswer() {
		statusLabel.setText(StringConstants.EMPTY);
		statusLabel.pseudoClassStateChanged(wrong, false);
		statusLabel.pseudoClassStateChanged(correct, false);
		if (element.getFirstCompareToSecond() == 1) {
			ratioValue.setText(StringConstants.INTERVAL_DIFFERENCE + element.getRatio().getName());
			statusLabel.setText(StringConstants.SL_FIRST_LARGER);
		} else if (element.getFirstCompareToSecond() == -1) {
			ratioValue.setText(StringConstants.INTERVAL_DIFFERENCE + element.getRatio().getName());
			statusLabel.setText(StringConstants.SL_SECOND_LARGER);
		} else
			statusLabel.setText(StringConstants.SL_THE_SAME);
		highlightCorrect(element.getFirstCompareToSecond());
		updateButtonsState(true);
	}

	public void play() {
		updateButtonsState(true);
		statusLabel.setText(StringConstants.EMPTY);
		statusLabel.pseudoClassStateChanged(wrong, false);
		statusLabel.pseudoClassStateChanged(correct, false);
		Player player = new Player();
		player.play(element.getPatterns()[0]);
		player = new Player();
		player.play(element.getPatterns()[1]);
		updateButtonsState(false);
	}

}
