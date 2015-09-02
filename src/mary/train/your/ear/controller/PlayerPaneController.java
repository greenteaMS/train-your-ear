package mary.train.your.ear.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import mary.train.your.ear.model.TaskPlayer;
import mary.train.your.ear.model.TaskResponse;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;

public class PlayerPaneController extends AbstractController implements SettingsChangedObserver, TaskChangeObserver {
	@FXML
	private VBox playerPane;
	@FXML
	private Button playButton;
	@FXML
	private Button repeatButton;
	@FXML
	private Button showAnswerButton;

	private enum PlayAction {
		Play, Repeat, Show
	}

	private TaskResponse response;
	@FXML
	CheckBox autoPlay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(getClass() + " controller initialized");

		playButton.setOnAction((action) -> {
			response = TaskPlayer.getInstance().play();
			repeatButton.setDisable(false);
			showAnswerButton.setDisable(false);
			notify(PlayAction.Play);
		});

		repeatButton.setOnAction((action) -> {
			TaskPlayer.getInstance().repeat();
			notify(PlayAction.Repeat);
		});

		showAnswerButton.setOnAction((action) -> {
			TaskPlayer.getInstance().show();
			notify(PlayAction.Show);
		});
	}

	private void notify(PlayAction action) {
		observers.stream().filter(ac -> ac instanceof PlayerPaneObserver).forEach(obs -> {
			switch (action) {
			case Play:
				((PlayerPaneObserver) obs).onPlay(response);
				break;
			case Repeat:
				((PlayerPaneObserver) obs).onRepeat();
				break;
			case Show:
				((PlayerPaneObserver) obs).onShowAnswer();
				break;
			}
		});
	}

	public void updateAutoPlaySetting() {
		TaskPlayer.getInstance().setAutoPlay(autoPlay.isSelected());
	}

	@Override
	public void onSettingsChanged() {
		repeatButton.setDisable(true);
		showAnswerButton.setDisable(true);
	}

	@Override
	public void onTaskChange() {
		repeatButton.setDisable(true);
		showAnswerButton.setDisable(true);
	}

}
