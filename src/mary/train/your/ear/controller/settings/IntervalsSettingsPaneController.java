package mary.train.your.ear.controller.settings;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.observers.SettingsChangedObserver;

public class IntervalsSettingsPaneController extends AbstractController {
	@FXML
	private VBox intervalsSettingsPane;

	@FXML
	private VBox listOfButtons;

	private ObservableList<ToggleButton> intervalButtons = FXCollections.observableArrayList();

	@FXML
	private CheckBox complexIntervals;

	@FXML
	private CheckBox simpleIntervals;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(getClass() + " controller initialized");
		refreshButtons();
	}

	private void refreshButtons() {
		intervalButtons.clear();
		for (Interval interval : Interval.values())
		//IntervalsSettings.getSettings().getIntervalsAvailability().forEach((key, value) -> {
			if ((complexIntervals.isSelected() && interval.getJfugueRep() > 12
					|| (simpleIntervals.isSelected() && interval.getJfugueRep() <= 12))) {
				ToggleButton button = new ToggleButton(interval.getSymbol());
				button.setSelected(IntervalsSettings.getSettings().getIntervalsAvailability().get(interval));
				button.getStyleClass().add("intervalToggle");
				checkRedStyle(button);
				button.setOnAction((event) -> {
					LinkedHashMap<Interval, Boolean> map = IntervalsSettings.getSettings().getIntervalsAvailability();
					map.put(interval, !map.get(interval));
					checkRedStyle(button);
					notifySettingsObservers();
				});
				intervalButtons.add(button);
			}
		//});
		listOfButtons.getChildren().setAll(intervalButtons);
	}
	private void checkRedStyle(ToggleButton button) {
		if (button.isSelected())
			button.getStyleClass().remove("intervalToggleRed");
		else
			button.getStyleClass().add("intervalToggleRed");
	}

	private void notifySettingsObservers() {
		observers.stream().filter(p -> p instanceof SettingsChangedObserver)
				.forEach(obs -> ((SettingsChangedObserver) obs).onSettingsChanged());
	}

	public void setComplexSetting() {
		IntervalsSettings.getSettings().setCompound(complexIntervals.isSelected());
		refreshButtons();
		notifySettingsObservers();
	}

	public void setSimpleSetting() {
		IntervalsSettings.getSettings().setSimple(simpleIntervals.isSelected());
		refreshButtons();
		notifySettingsObservers();
	}

}
