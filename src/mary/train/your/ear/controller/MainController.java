package mary.train.your.ear.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import mary.train.your.ear.controller.settings.ChordsSettingsPaneController;
import mary.train.your.ear.controller.settings.GeneralSettingsPaneController;
import mary.train.your.ear.controller.settings.IntervalsSettingsPaneController;
import mary.train.your.ear.controller.tasks.ChordTaskPaneController;
import mary.train.your.ear.controller.tasks.IntervalsTaskPaneController;
import mary.train.your.ear.model.TaskPlayer;
import mary.train.your.ear.observers.TaskChangeObserver;

public class MainController extends AbstractController {
	@FXML
	private TabPane tasksTabPane;
	@FXML
	private Tab intervalsTask;
	@FXML
	private Tab chordsTask;
	@FXML
	private Tab scalesTask;
	@FXML
	private Tab tunerTask;

	@FXML
	private PlayerPaneController playerPaneController;
	@FXML
	private GeneralSettingsPaneController generalSettingsPaneController;
	@FXML
	private IntervalsSettingsPaneController intervalsSettingsPaneController;
	@FXML
	private ChordsSettingsPaneController chordsSettingsPaneController;
	@FXML
	private IntervalsTaskPaneController intervalsTaskPaneController;
	@FXML
	private ChordTaskPaneController chordTaskPaneController;

	@FXML
	AnchorPane intervalsSettingsWrapper;
	@FXML
	AnchorPane chordsSettingsWrapper;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(getClass() + " controller initialized");

		tasksTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				intervalsTaskPaneController.clear();
				chordTaskPaneController.clear();
				if (newValue == intervalsTask) {
					intervalsSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy("Intervals");
				} else if (newValue == chordsTask) {
					chordsSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy("Chord");
				}
				observers.stream().filter(p -> p instanceof TaskChangeObserver)
						.forEach(obs -> ((TaskChangeObserver) obs).onTaskChange());

			}
		});
		// init observers
		System.out.println("*** " + chordTaskPaneController + " ***");
		intervalsSettingsPaneController.addObserver(intervalsTaskPaneController);
		playerPaneController.addObserver(intervalsTaskPaneController);
		playerPaneController.addObserver(chordTaskPaneController);
		chordsSettingsPaneController.addObserver(chordTaskPaneController);
		chordsSettingsPaneController.addObserver(playerPaneController);
		intervalsSettingsPaneController.addObserver(playerPaneController);
		observers.add(intervalsTaskPaneController);
		observers.add(chordTaskPaneController);
		observers.add(playerPaneController);
	}

}
