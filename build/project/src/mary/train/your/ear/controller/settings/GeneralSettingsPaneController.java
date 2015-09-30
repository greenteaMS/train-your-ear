/*
 *
 */
package mary.train.your.ear.controller.settings;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
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
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.settings.GeneralTaskSettings;
import mary.train.your.ear.model.settings.GeneralTaskSettings.PlayType;
import mary.train.your.ear.observers.UserChangeObserver;
import mary.train.your.ear.view.ui.PersistentButtonToggleGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneralSettingsPaneController.
 */
public class GeneralSettingsPaneController extends AbstractController implements UserChangeObserver {

	/** The range slider pane. */
	@FXML
	private VBox rangeSliderPane;

	/** The range slider. */
	private RangeSlider rangeSlider;

	/** The instruments. */
	private ObservableList<String> instruments;

	/** The rb melodically. */
	@FXML
	private RadioButton rbMelodically;

	/** The rb harmonically. */
	@FXML
	private RadioButton rbHarmonically;

	/** The no of items. */
	@FXML
	private Spinner<Integer> noOfItems;

	/** The play type group. */
	private final ToggleGroup playTypeGroup = new PersistentButtonToggleGroup();

	/** The random instrument flag. */
	@FXML
	private CheckBox randomInstrument;

	/** The tempo slider. */
	@FXML
	private Slider tempoSlider;

	/** The pause slider. */
	@FXML
	private Slider pauseSlider;

	@FXML
	private ListView<String> instrumentsListView;


	@FXML
	private RadioButton melUp;

	@FXML
	private RadioButton melDown;

	@FXML
	private RadioButton melRandom;

	private final ToggleGroup melTypeGroup = new PersistentButtonToggleGroup();

	/**
	 * Instantiates a new general settings pane controller.
	 */
	public GeneralSettingsPaneController() {
		LOG = LogManager.getLogger(getClass());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.controller.AbstractController#initialize(java.net.
	 * URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInitializing(getClass());

		rbMelodically.setToggleGroup(playTypeGroup);
		rbHarmonically.setToggleGroup(playTypeGroup);
		melDown.setToggleGroup(melTypeGroup);
		melUp.setToggleGroup(melTypeGroup);
		melRandom.setToggleGroup(melTypeGroup);

		rangeSlider = new RangeSlider(0, 127, 48, 84);

		rangeSlider.setOrientation(Orientation.HORIZONTAL);
		rangeSliderPane.getChildren().add(rangeSlider);
		rangeSlider.lowValueProperty().addListener((obc, oldValue, newValue) -> {
			GeneralTaskSettings.getSettings().getLowestPitchRange()[0] = (int) ((double) newValue);
		});
		rangeSlider.highValueProperty().addListener((obc, oldValue, newValue) -> {
			GeneralTaskSettings.getSettings().getLowestPitchRange()[1] = (int) ((double) newValue);
		});

		instruments = FXCollections.observableArrayList(GeneralTaskSettings.getLoadedInstrumentsNames());
		noOfItems.valueProperty().addListener((obs, oldValue, newValue) -> {
			GeneralTaskSettings.getSettings().setNrOfChords(newValue);
		});

		instrumentsListView.setItems(instruments);
		instrumentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			System.out.println(newValue);
			GeneralTaskSettings.getSettings().setInstrument(newValue);

		});
		instrumentsListView.getSelectionModel().select(StringConstants.IN_PIANO_I);
		tempoSlider.valueProperty().addListener(change -> setTempo());
		pauseSlider.valueProperty().addListener(change -> setPauseLength());
		setRandomInstrument();
		logInit(getClass());

	}

	/**
	 * Sets the harmonicPlayType.
	 */
	public void setHPlayType() {
		GeneralTaskSettings.getSettings().setPlaytype(GeneralTaskSettings.PlayType.Harmonically);
		melUp.setVisible(false);
		melDown.setVisible(false);
		melRandom.setVisible(false);
	}

	/**
	 * Sets the melodicPlayType.
	 */
	public void setMPlayType() {
		GeneralTaskSettings.getSettings().setPlaytype(GeneralTaskSettings.PlayType.Melodically);
		melUp.setVisible(true);
		melDown.setVisible(true);
		melRandom.setVisible(true);
	}

	/**
	 * Sets the random instrument flag.
	 */
	public void setRandomInstrument() {
		GeneralTaskSettings.getSettings().setRandomInstrument(randomInstrument.isSelected());
		instrumentsListView.setDisable(randomInstrument.isSelected());
	}

	/**
	 * Sets the length of pause between chords.
	 */
	public void setPauseLength() {
		GeneralTaskSettings.getSettings().setPauseLength((int) (pauseSlider.getValue() * 1000));
	}

	/**
	 * Sets the tempo.
	 */
	public void setTempo() {
		GeneralTaskSettings.getSettings().setTempo((int) tempoSlider.getValue());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.observers.UserChangeObserver#onUserChange()
	 */
	@Override
	public void onUserChange() {
		LOG.info("onUserChange");
		GeneralTaskSettings settings = GeneralTaskSettings.getSettings();
		restoreToggles(settings);
		rangeSlider.setLowValue(settings.getLowestPitchRange()[0]);
		rangeSlider.setHighValue(settings.getLowestPitchRange()[1]);
		noOfItems.getValueFactory().setValue(settings.getNrOfChords());
		randomInstrument.setSelected(settings.isRandomInstrument());
		instrumentsListView.setDisable(randomInstrument.isSelected());
		tempoSlider.setValue(settings.getTempo());
		pauseSlider.setValue((double) settings.getPauseLength() / 1000);
		notifySettingsObservers();

	}

	private void restoreToggles(GeneralTaskSettings settings){
		if (settings.getPlaytype() == PlayType.Harmonically){
			playTypeGroup.selectToggle(rbHarmonically);
			melUp.setVisible(false);
			melDown.setVisible(false);
			melRandom.setVisible(false);
		}
		else{
			playTypeGroup.selectToggle(rbMelodically);
			melUp.setVisible(true);
			melDown.setVisible(true);
			melRandom.setVisible(true);
		}

		if (settings.getMelPlayType() == PlayType.MelDown)
			melTypeGroup.selectToggle(melDown);
		else if (settings.getMelPlayType() == PlayType.MelUp)
			melTypeGroup.selectToggle(melUp);
		else
			melTypeGroup.selectToggle(melRandom);
	}

	@FXML
	public void setUpType() {
		GeneralTaskSettings.getSettings().setMelPlaytype(GeneralTaskSettings.PlayType.MelUp);
	}

	@FXML
	public void setDwonType() {
		GeneralTaskSettings.getSettings().setMelPlaytype(GeneralTaskSettings.PlayType.MelDown);
	}

	@FXML
	public void setRandomType() {
		GeneralTaskSettings.getSettings().setMelPlaytype(GeneralTaskSettings.PlayType.MelRandom);
	}

}
