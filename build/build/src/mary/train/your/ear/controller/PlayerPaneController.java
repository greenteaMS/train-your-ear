/*
 *
 */
package mary.train.your.ear.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;
import mary.train.your.ear.observers.UserChangeObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerPaneController.
 */
public class PlayerPaneController extends AbstractController
		implements SettingsChangedObserver, TaskChangeObserver, UserChangeObserver {

	/** The player pane. */
	@FXML
	private VBox playerPane;

	/** The play button. */
	@FXML
	private Button playButton;

	/** The repeat button. */
	@FXML
	private Button repeatButton;

	/** The show answer button. */
	@FXML
	private Button showAnswerButton;

	/**
	 * The Enum PlayAction.
	 */
	private enum PlayAction {

		/** The Play. */
		Play, /** The Repeat. */
		Repeat, /** The Show. */
		Show
	}

	/** The response. */
	private TaskResponse response;

	/** The tuner task. */
	private boolean tunerTask = false;

	/**
	 * Instantiates a new player pane controller.
	 */
	public PlayerPaneController() {
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

		playButton.setOnAction((action) -> {
			response = TaskPlayer.getInstance().play();
			if (response == null)
				return;
			if (!tunerTask) {
				repeatButton.setDisable(false);
				showAnswerButton.setDisable(false);
			}
			notify(PlayAction.Play);
		});

		repeatButton.setOnAction((action) -> {
			TaskPlayer.getInstance().repeat();
			notify(PlayAction.Repeat);
		});

		showAnswerButton.setOnAction((action) -> notify(PlayAction.Show));

		logInit(getClass());
	}

	/**
	 * Notify.
	 *
	 * @param action
	 *            the action
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.observers.SettingsChangedObserver#onSettingsChanged()
	 */
	@Override
	public void onSettingsChanged() {
		repeatButton.setDisable(true);
		showAnswerButton.setDisable(true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.observers.TaskChangeObserver#onTaskChange(boolean)
	 */
	@Override
	public void onTaskChange(boolean tunerTask) {
		repeatButton.setDisable(true);
		showAnswerButton.setDisable(true);
		this.tunerTask = tunerTask;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.observers.UserChangeObserver#onUserChange()
	 */
	@Override
	public void onUserChange() {
		repeatButton.setDisable(true);
		showAnswerButton.setDisable(true);
	}

}
