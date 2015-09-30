/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.tasks;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.statistics.ChordStatElement;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;

// TODO: Auto-generated Javadoc
/**
 * The Class ChordTaskPaneController.
 */
public class ChordTaskPaneController extends AbstractTaskPaneController {

	/**
	 * Instantiates a new chord task pane controller.
	 */
	public ChordTaskPaneController() {
		LOG = LogManager.getLogger(getClass());
		infoText = StringConstants.TI_CHORDS;
	}

	private LinkedList<CustomChord> playedChords;

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.controller.tasks.AbstractTaskPaneController#
	 * refreshPanel()
	 */
	protected void refreshPanel() {
		itemListPane.getChildren().clear();
		itemButtons.clear();
		ChordsSettings.getSettings().getAvailableChordsList().forEach(s -> {
			createButton(s.getName(), s.getHints(), s.hasInversions());
		});
		itemListPane.getChildren().addAll(itemButtons);
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		clear();
	}

	/**
	 * Creates the button for chord.
	 *
	 * @param name
	 *            the chord name
	 * @param hints
	 *            the chord hints
	 * @param hasInversion
	 *            the chordHasInversion flag
	 */
	private void createButton(String name, String hints, boolean hasInversion) {
		Button chordButton = new Button(name);
		itemButtons.add(chordButton);
		chordButton.getStyleClass().add("chordButton");
		chordButton.setOnAction(action -> {
			String text = hasInversion ? (" " + ChordsSettings.getSettings().getInversions().get(0).getText()) : "";
			userAnswer.add(name + text);
			itemAnswerList.setItems(userAnswer);
			if (userAnswer.size() == correctAnswer.size())
				checkAnswer(true);
		});
		if (hasInversion) {
			createContextMenu(name, chordButton);
		}
		if (hints != null && !hints.isEmpty())
			chordButton.setTooltip(new Tooltip(hints));
		chordButton.setDisable(true);
	}

	private void createContextMenu(String name, Button b) {
		ContextMenu menu = new ContextMenu();
		LinkedList<Inversion> inversions = ChordsSettings.getSettings().getInversions();
		for (int i = 0; i < inversions.size(); i++) {
			MenuItem item = new MenuItem(inversions.get(i).getText());
			item.setOnAction(action -> {
				userAnswer.add(name + " " + item.getText());
				itemAnswerList.setItems(userAnswer);
				if (userAnswer.size() == correctAnswer.size())
					checkAnswer(true);
			});
			menu.getItems().add(item);
		}
		if (menu.getItems().size() > 1) {
			b.setContextMenu(menu);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.observers.PlayerPaneObserver#onPlay(mary.train.your.
	 * ear.model.player.TaskResponse)
	 */
	@Override
	public void onPlay(TaskResponse response) {
		clear();
		if (response == null || response.getType() != TaskResponse.Type.ChordResponse)
			return;
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
		correctAnswer = response.getAnswer();
		playedChords = response.getChords();
	}

	@Override
	protected void updateStatistics(LinkedList<String> checkedUserAnswer) {
		LinkedList<ChordStatElement> statisticsElements = new LinkedList<>();
		for (int i = 0; i < checkedUserAnswer.size(); i++) {
			statisticsElements.add(new ChordStatElement(playedChords.get(i), checkedUserAnswer.get(i)));
		}
		UserData.getUserData().getDailyUserStatistics().updateChordStatistics(statisticsElements);
		// TODO ewentualne odœwie¿enie wyswietlania statystyk przy tasku
	}

}
