package mary.train.your.ear.controller.settings;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.observers.UserChangeObserver;

public class IntervalComparatorSettingsPaneController extends AbstractController implements UserChangeObserver {
	@FXML
	private VBox intervalComparatorSettingsPane;
	@FXML
	private Spinner<Integer> setSizeSpinner;
	@FXML
	private VBox listOfButtons;

	private ObservableList<ToggleButton> ratioButtons = FXCollections.observableArrayList();
	@FXML
	CheckBox samePitchCheckBox;

	public IntervalComparatorSettingsPaneController() {
		LOG = LogManager.getLogger(getClass());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInitializing(getClass());
		refreshButtons();

		setSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			IntervalComparatorSettings.getSettings().setSetSize(newValue);
		});
		logInit(getClass());
	}

	private void refreshButtons() {
		ratioButtons.clear();
		IntervalComparatorSettings.getSettings().getAvailableRatios().forEach((ratio, value) -> {
			ToggleButton button = new ToggleButton(ratio.getName());
			button.setSelected(value);
			button.getStyleClass().add("intervalToggle");
			checkRedStyle(button);
			button.setOnAction((event) -> {
				HashMap<FrequencyRatio, Boolean> map = IntervalComparatorSettings.getSettings().getAvailableRatios();
				map.put(ratio, !map.get(ratio));
				checkRedStyle(button);
				notifySettingsObservers();
			});
			ratioButtons.add(button);
		});
		listOfButtons.getChildren().setAll(ratioButtons);
	}

	@Override
	public void onUserChange() {
		LOG.info("onUserChange");
		refreshButtons();
		setSizeSpinner.getValueFactory().setValue(IntervalComparatorSettings.getSettings().getSetSize());
		samePitchCheckBox.selectedProperty().set(IntervalComparatorSettings.getSettings().getSamePitchValue());
		notifySettingsObservers();

	}

	@FXML
	public void setSamePitchRequest() {
		IntervalComparatorSettings.getSettings().setSamePitch(samePitchCheckBox.selectedProperty().getValue());
	}

}
