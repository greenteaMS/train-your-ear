package mary.train.your.ear.controller.tasks;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import mary.train.your.ear.model.TaskResponse;
import mary.train.your.ear.model.TaskResponse.Type;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;

public class IntervalsTaskPaneController extends AbstractTaskPaneController
		implements PlayerPaneObserver, SettingsChangedObserver, TaskChangeObserver {

	protected void refreshPanel() {
		itemListPane.getChildren().clear();
		itemButtons.clear();
		IntervalsSettings.getSettings().getIntervalsAvailability().entrySet().stream().filter(p -> p.getValue())
				.forEach(s -> {
					if ((IntervalsSettings.getSettings().isCompound() && s.getKey().getJfugueRep() > 12)
							|| (IntervalsSettings.getSettings().isSimple() && s.getKey().getJfugueRep() <= 12))
						createButton(s.getKey());
				});
		itemListPane.getChildren().addAll(itemButtons);
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		clear();
	}

	private void createButton(Interval i) {
		Button b = new Button(i.getSymbol());
		itemButtons.add(b);
		b.getStyleClass().add("intervalButton");
		b.setOnAction(action -> {
			userAnswer.add(i.getName());
			itemAnswerList.setItems(userAnswer);
			if (userAnswer.size() == correctAnswer.size())
				checkAnswer();
		});
		b.setDisable(true);
	}

	@Override
	public void onPlay(TaskResponse response) {
		clear();
		if (response == null || response.getType() != Type.IntervalResponse)
			return;
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
		correctAnswer = response.getAnswer();
	}

}
