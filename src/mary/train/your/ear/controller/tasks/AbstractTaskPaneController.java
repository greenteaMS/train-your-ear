/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.tasks;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractTaskPaneController.
 */
public abstract class AbstractTaskPaneController extends AbstractController
		implements PlayerPaneObserver, SettingsChangedObserver, TaskChangeObserver {

	/** The item list pane. */
	@FXML
	protected TilePane itemListPane;

	/** The item answer listView. */
	@FXML
	protected ListView<String> itemAnswerList;

	@FXML
	protected Label taskInfoLabel;

	/** The correct answer listView. */
	@FXML
	protected ListView<String> correctAnswerList;

	/** The item buttons. */
	protected ObservableList<Button> itemButtons = FXCollections.observableArrayList();

	/** The user answer. */
	protected ObservableList<String> userAnswer = FXCollections.observableArrayList();

	/** The correct answer. */
	protected LinkedList<String> correctAnswer;

	/** The counter. */
	protected int counter = 0;

	/** The help. */
	protected LinkedList<String> checkedUserAnswer;

	/** The correct answer flag. */
	protected boolean okAnswer = true;

	protected String infoText;

	private PseudoClass correct = PseudoClass.getPseudoClass("correctAnswerCell");
	private PseudoClass wrong = PseudoClass.getPseudoClass("wrongAnswerCell");

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
		refreshPanel();
		taskInfoLabel.setText(infoText);
		logInit(getClass());
	}

	/**
	 * Refresh panel.
	 */
	protected abstract void refreshPanel();

	/**
	 * Check answer.
	 */
	protected void checkAnswer(boolean chordTask) {
		itemButtons.forEach(b -> b.setDisable(true));
		counter = 0;
		checkedUserAnswer = new LinkedList<>();
		okAnswer = true;
		correctAnswer.forEach(ans -> {
			String s = !ans.equals(userAnswer.get(counter)) ? StringConstants.SPACE_ERROR : StringConstants.OK;
			if (s.contains(StringConstants.ERROR))
				okAnswer = false;
			checkedUserAnswer.add(userAnswer.get(counter) + s);
			counter++;
		});
		userAnswer.setAll(checkedUserAnswer);
		itemAnswerList.setItems(userAnswer);
		itemAnswerList.setCellFactory(lv -> {
            return new ListCell<String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        pseudoClassStateChanged(correct, false);
                        pseudoClassStateChanged(wrong, false);
                    } else {
                        setText(item.replace(StringConstants.SPACE_ERROR, "").replace(StringConstants.OK, "").trim());
                        pseudoClassStateChanged(correct, item.contains(StringConstants.OK));
                        pseudoClassStateChanged(wrong, item.contains(StringConstants.ERROR));
                    }
                }
            };
        });
		updateStatistics(checkedUserAnswer);
		TaskPlayer.getInstance().autoPlay(okAnswer);
	}

	protected abstract void updateStatistics(LinkedList<String> checkedUserAnswer);

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onRepeat()
	 */
	@Override
	public void onRepeat() {
		correctAnswerList.setItems(FXCollections.observableArrayList());
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onShowAnswer()
	 */
	@Override
	public void onShowAnswer() {
		if (correctAnswer != null) {
			ObservableList<String> list = FXCollections.observableArrayList();
			correctAnswer.forEach(ans -> {
				list.add(ans);
			});
			correctAnswerList.setItems(list);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.observers.SettingsChangedObserver#onSettingsChanged()
	 */
	@Override
	public void onSettingsChanged() {
		refreshPanel();
	}

	/**
	 * Clear view.
	 */
	public void clear() {
		correctAnswer = new LinkedList<>();
		correctAnswerList.setItems(FXCollections.observableArrayList());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.observers.TaskChangeObserver#onTaskChange(boolean)
	 */
	@Override
	public void onTaskChange(boolean changeText) {
		itemButtons.forEach(b -> b.setDisable(true));
	}

	public void showTaskInfo() {
		taskInfoLabel.setVisible(!taskInfoLabel.isVisible());
	}

}
