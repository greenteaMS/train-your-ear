package mary.train.your.ear.controller.settings;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.RangeSlider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.settings.GeneralSettings;

public class GeneralSettingsPaneController extends AbstractController {


	@FXML
	private VBox rangeSliderPane;

	private RangeSlider rangeSlider;
	private ObservableList<String> instruments;

	@FXML
	private RadioButton rbMelodically;

	@FXML
	private RadioButton rbHarmonically;

	@FXML
	private Spinner<?> noOfItems;

	@FXML
	private ListView<String> listView;

	private final ToggleGroup playTypeGroup = new ToggleGroup();

	@FXML CheckBox randomInstrument;

	@FXML Slider tempoSlider;

	@FXML Slider pauseSlider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(getClass() + " controller initialized");

		rbMelodically.setToggleGroup(playTypeGroup);
		rbHarmonically.setToggleGroup(playTypeGroup);

		rangeSlider = new RangeSlider(0, 127, 48, 84);

		rangeSlider.setOrientation(Orientation.HORIZONTAL);
		rangeSliderPane.getChildren().add(rangeSlider);
		rangeSlider.lowValueProperty().addListener((obc, oldValue, newValue) -> {
			GeneralSettings.getInstance().getLowestPitchRange()[0] = (int) ((double) newValue);
			System.out.println("newLowValue " + newValue);
		});
		rangeSlider.highValueProperty().addListener((obc, oldValue, newValue) -> {
			GeneralSettings.getInstance().getLowestPitchRange()[1] = (int) ((double) newValue);
			System.out.println("newHighValue " + newValue);
		});

		instruments = FXCollections.observableArrayList(GeneralSettings.getInstance().getLoadedInstrumentsNames());

		noOfItems.valueProperty().addListener((obs, oldValue, newValue) -> {
			GeneralSettings.getInstance().setNrOfChords((int) newValue);
			System.out.println("setNewValue " + newValue);
		});

		listView.setItems(instruments);
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			System.out.println(newValue);
			GeneralSettings.getInstance().setInstrument(newValue);

		});
		tempoSlider.valueProperty().addListener(change -> setTempo());
		pauseSlider.valueProperty().addListener(change -> setPauseLength());
		setRandomInstrument();


	}

	public void setHPlayType() {
		System.out.println("setHorizontallyPlayType");
		GeneralSettings.getInstance().setPlaytype(GeneralSettings.PlayType.Harmonically);
	}

	public void setMPlayType() {
		System.out.println("setMelodicallyPlayType");
		GeneralSettings.getInstance().setPlaytype(GeneralSettings.PlayType.Melodically);
	}

	public void setRandomInstrument() {
		GeneralSettings.getInstance().setRandomInstrument(randomInstrument.isSelected());
		listView.setDisable(randomInstrument.isSelected());
	}

	public void setPauseLength(){
		GeneralSettings.getInstance().setPauseLength((long) pauseSlider.getValue() * 1000);
	}

	public void setTempo(){
		GeneralSettings.getInstance().setTempo((int)tempoSlider.getValue());
	}

}
