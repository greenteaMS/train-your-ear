/*
 *
 */
package mary.train.your.ear.controller.tasks;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.response.TaskResponse.Type;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.statistics.IntervalStatElement;
import mary.train.your.ear.model.structures.Interval;

// TODO: Auto-generated Javadoc
/**
 * The Class IntervalsTaskPaneController.
 */
public class IntervalsTaskPaneController extends AbstractTaskPaneController {

	/**
	 * Instantiates a new intervals task pane controller.
	 */
	public IntervalsTaskPaneController() {
		LOG = LogManager.getLogger(getClass());
		infoText = StringConstants.TI_INTERVALS;
	}

	private LinkedList<Interval> playedIntervals;

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.controller.tasks.AbstractTaskPaneController#
	 * refreshPanel()
	 */
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

	/**
	 * Creates the button.
	 *
	 * @param i
	 *            the i
	 */
	private void createButton(Interval i) {
		Button b = new Button(i.getName());
		itemButtons.add(b);
		b.getStyleClass().add("intervalButton");
		b.setOnAction(action -> {
			userAnswer.add(i.getName());
			itemAnswerList.setItems(userAnswer);
			if (userAnswer.size() == correctAnswer.size())
				checkAnswer(false);
		});
		b.setDisable(true);
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
		if (response == null || response.getType() != Type.IntervalResponse)
			return;
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
		correctAnswer = response.getAnswer();
		playedIntervals = response.getIntervals();
	}

	@Override
	protected void updateStatistics(LinkedList<String> checkedUserAnswer) {
		LinkedList<IntervalStatElement> statisticsElements = new LinkedList<>();
		for (int i = 0; i < checkedUserAnswer.size(); i++) {
			statisticsElements.add(new IntervalStatElement(playedIntervals.get(i),
					checkedUserAnswer.get(i).contains(StringConstants.OK)));
		}
		UserData.getUserData().getDailyUserStatistics().updateIntervalStatistics(statisticsElements);
		// TODO ewentualne odœwie¿enie wyswietlania statystyk przy tasku
	}

}
