package mary.train.your.ear.view.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class TunerWithSlider extends VBox{

	@FXML
	private Label itemInfo;
	@FXML
	private Slider frequencySlider;
	@FXML
	private Label sliderValue;
	@FXML
	private Button playButton;
	@FXML
	private Button checkButton;
	@FXML
	private Label result;

	private double[] frequencies;


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

	public TunerWithSlider(double[] freqs){
		this();
		frequencies = freqs;
	}

	public Label getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(Label itemInfo) {
		this.itemInfo = itemInfo;
	}

	public Slider getFrequencySlider() {
		return frequencySlider;
	}

	public void setFrequencySlider(Slider frequencySlider) {
		this.frequencySlider = frequencySlider;
	}

	public Label getSliderValue() {
		return sliderValue;
	}

	public void setSliderValue(Label sliderValue) {
		this.sliderValue = sliderValue;
	}

	public Button getPlayButton() {
		return playButton;
	}

	public void setPlayButton(Button playButton) {
		this.playButton = playButton;
	}

	public Button getCheckButton() {
		return checkButton;
	}

	public void setCheckButton(Button checkButton) {
		this.checkButton = checkButton;
	}

	public Label getResult() {
		return result;
	}

	public void setResult(Label result) {
		this.result = result;
	}

}
