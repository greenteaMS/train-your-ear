package mary.train.your.ear.controller.tasks;

import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import mary.train.your.ear.model.TaskResponse;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.Inversion;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;

public class ChordTaskPaneController extends AbstractTaskPaneController
		implements PlayerPaneObserver, SettingsChangedObserver, TaskChangeObserver {

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

	private void createButton(String name, String hints, boolean hasInversion) {
		Button b = new Button(name);
		itemButtons.add(b);
		b.getStyleClass().add("intervalButton");
		b.setOnAction(action -> {
			String text = hasInversion ? (" " + ChordsSettings.getSettings().getInversions().get(0).getText()) : "";
			userAnswer.add(name + text);
			itemAnswerList.setItems(userAnswer);
			if (userAnswer.size() == correctAnswer.size())
				checkAnswer();
		});
		if (hasInversion) {
			// System.out.println("hasInversion");
			ContextMenu menu = new ContextMenu();
			LinkedList<Inversion> inversions = ChordsSettings.getSettings().getInversions();
			for (int i = 0; i < inversions.size(); i++) {
				MenuItem item = new MenuItem(inversions.get(i).getText());
				item.setOnAction(action -> {
					userAnswer.add(name + " " + item.getText());
					itemAnswerList.setItems(userAnswer);
					if (userAnswer.size() == correctAnswer.size())
						checkAnswer();
				});
				menu.getItems().add(item);
			}
			if (menu.getItems().size() > 1) {
				System.out.println("menuAdded");
				b.setContextMenu(menu);
			}
		}
		if (hints != null)
			b.setTooltip(new Tooltip(hints));
		b.setDisable(true);
	}

	@Override
	public void onPlay(TaskResponse response) {
		clear();
		if (response == null || response.getType() != TaskResponse.Type.ChordResponse)
			return;
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
		correctAnswer = response.getAnswer();

	}
}
