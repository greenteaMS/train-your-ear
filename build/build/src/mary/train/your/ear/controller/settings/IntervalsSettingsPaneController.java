/*
 *
 */
package mary.train.your.ear.controller.settings;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.observers.UserChangeObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class IntervalsSettingsPaneController.
 */
public class IntervalsSettingsPaneController extends AbstractController implements UserChangeObserver {

	/** The intervals settings pane. */
	@FXML
	private VBox intervalsSettingsPane;

	/** The list of buttons. */
	@FXML
	private VBox listOfButtons;

	/** The interval buttons. */
	private ObservableList<HBox> intervalButtons = FXCollections.observableArrayList();

	/** The complex intervals. */
	@FXML
	private CheckBox complexIntervals;

	/** The simple intervals. */
	@FXML
	private CheckBox simpleIntervals;


	/**
	 * Instantiates a new intervals settings pane controller.
	 */
	public IntervalsSettingsPaneController() {
		LOG = LogManager.getLogger(getClass());
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.controller.AbstractController#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInitializing(getClass());
		refreshButtons();
		logInit(getClass());
	}

	/**
	 * Refresh buttons.
	 */
	private void refreshButtons() {
		intervalButtons.clear();
		for (Interval interval : Interval.values())
			if ((complexIntervals.isSelected() && interval.getJfugueRep() > 12
					|| (simpleIntervals.isSelected() && interval.getJfugueRep() <= 12))) {
				ToggleButton button = new ToggleButton(interval.getName());
				button.setSelected(IntervalsSettings.getSettings().getIntervalsAvailability().get(interval));
				button.getStyleClass().add("intervalToggle");
				checkRedStyle(button);
				button.setOnAction((event) -> {
					LinkedHashMap<Interval, Boolean> map = IntervalsSettings.getSettings().getIntervalsAvailability();
					map.put(interval, !map.get(interval));
					checkRedStyle(button);
					notifySettingsObservers();
				});
				LinkedList<Interval> tempList = new LinkedList<>();
				tempList.add(interval);
				Button play = createPlayButtonForChord(tempList);
				HBox panel = new HBox(5, button, play);
				panel.setAlignment(Pos.CENTER);
				intervalButtons.add(panel);
			}
		listOfButtons.getChildren().setAll(intervalButtons);
	}

	private Button createPlayButtonForChord(List<Interval> list) {
		Button b = new Button();
		b.getStyleClass().add("speakerButton");
		b.setOnAction(action -> TaskPlayer.getInstance().tryNewChord(list));
		return b;
	}

	/**
	 * Sets the complex setting.
	 */
	public void setComplexSetting() {
		IntervalsSettings.getSettings().setCompound(complexIntervals.isSelected());
		refreshButtons();
		notifySettingsObservers();
	}

	/**
	 * Sets the simple setting.
	 */
	public void setSimpleSetting() {
		IntervalsSettings.getSettings().setSimple(simpleIntervals.isSelected());
		refreshButtons();
		notifySettingsObservers();
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.observers.UserChangeObserver#onUserChange()
	 */
	@Override
	public void onUserChange() {
		LOG.info("onUserChange");
		IntervalsSettings settings = IntervalsSettings.getSettings();
		complexIntervals.setSelected(settings.isCompound());
		simpleIntervals.setSelected(settings.isSimple());
		refreshButtons();
		notifySettingsObservers();
	}

}
