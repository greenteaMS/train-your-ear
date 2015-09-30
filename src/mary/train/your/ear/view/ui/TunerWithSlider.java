/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.view.ui;

import java.io.IOException;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.structures.TunerElement;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerWithSlider.
 */
public class TunerWithSlider extends VBox {

	/** The item info. */
	@FXML
	private Label itemInfo;

	/** The frequency slider. */
	@FXML
	private Slider frequencySlider;

	/** The play button. */
	@FXML
	private Button playButton;

	/** The check button. */
	@FXML
	private Button checkButton;

	/** The result. */
	@FXML
	private Label result;

	/** The slider value. */
	@FXML
	private Label sliderValue;

	/** The element. */
	private TunerElement element;

	/**
	 * Instantiates a new tuner with slider.
	 */
	public TunerWithSlider() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TunerWithSlider.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Instantiates a new tuner with slider.
	 *
	 * @param element
	 *            the element
	 */
	public TunerWithSlider(TunerElement element) {
		this();
		this.element = element;
		itemInfo.setText(element.getRatio().getName());
		if (element.getRatio().getRatio() < 1.1) {
			frequencySlider.setMin(-20);
			frequencySlider.setMax(20);
		}
		sliderValue.setText(StringConstants.HZ_0);
		frequencySlider.valueProperty().addListener(change -> {
			sliderValue.setText(
					((double) (Math.round(frequencySlider.getValue() * 1000)) / 1000) + StringConstants.SPACE_HZ);
		});
	}

	/**
	 * Play.
	 */
	// TODO instrument z general settings
	public void play() {
		double newFrequency = getCurrentFrequency();
		Pattern pattern = new Pattern(StringConstants.TEMPO_INSTR);
		pattern.add(buildMelodicPattern(newFrequency));
		Player player = new Player();
		player.play(pattern);
		pattern = new Pattern(StringConstants.TEMPO_INSTR);
		pattern.add(buildHarmonicPattern(newFrequency));
		player.play(pattern);

	}

	private String buildMelodicPattern(double newFrequency) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringConstants.M);
		sb.append(element.getBaseFreq());
		sb.append(StringConstants.QUARTER);
		sb.append(StringConstants.SPACE);
		sb.append(StringConstants.M);
		sb.append(newFrequency);
		sb.append(StringConstants.QUARTER);
		return sb.toString();
	}

	private String buildHarmonicPattern(double newFrequency) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringConstants.VOICE_0);
		sb.append(StringConstants.M);
		sb.append(element.getBaseFreq());
		sb.append(StringConstants.WHOLE);
		sb.append(StringConstants.VOICE_1);
		sb.append(StringConstants.M);
		sb.append(newFrequency);
		sb.append(StringConstants.WHOLE);
		return sb.toString();
	}

	/**
	 * Check answer.
	 */
	public void checkAnswer() {
		double newFrequency = getCurrentFrequency();
		if (Math.abs(newFrequency - element.getCorrectFreq()) < 1)
			result.setText(StringConstants.OK);
		else
			result.setText(StringConstants.TRY_AGAIN);
	}

	/**
	 * Gets the current frequency.
	 *
	 * @return the current frequency
	 */
	private double getCurrentFrequency() {
		return Math.round((element.getStartingFreq() + frequencySlider.getValue()) * 1000) / 1000;
	}

	/**
	 * Show answer.
	 */
	public void showAnswer() {
		double newFrequency = getCurrentFrequency();
		result.setText(
				StringConstants.ERROR_COLON + (element.getCorrectFreq() - newFrequency) + StringConstants.SPACE_HZ);
	}

}
