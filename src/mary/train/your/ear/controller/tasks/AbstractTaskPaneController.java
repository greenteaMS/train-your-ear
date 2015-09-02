package mary.train.your.ear.controller.tasks;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.TaskPlayer;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;
import mary.train.your.ear.observers.TaskChangeObserver;

public abstract class AbstractTaskPaneController extends AbstractController
		implements PlayerPaneObserver, SettingsChangedObserver, TaskChangeObserver {

	@FXML
	protected TilePane itemListPane;

	@FXML
	protected ListView<String> itemAnswerList;
	@FXML
	protected ListView<String> correctAnswerList;

	protected ObservableList<Button> itemButtons = FXCollections.observableArrayList();

	protected ObservableList<String> userAnswer = FXCollections.observableArrayList();

	protected LinkedList<String> correctAnswer;

	protected int counter = 0;
	protected LinkedList<String> help;
	protected boolean okAnswer = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(getClass() + " controller initialized");
		refreshPanel();
	}

	protected abstract void refreshPanel();

	protected void checkAnswer() {
		itemButtons.forEach(b -> b.setDisable(true));
		counter = 0;
		help = new LinkedList<>();
		okAnswer = true;
		correctAnswer.forEach(ans -> {
			String s = !ans.equals(userAnswer.get(counter)) ? " ERROR" : " OK";
			if (s.contains("ERROR"))
				okAnswer = false;
			help.add(userAnswer.get(counter) + s);
			counter++;
		});
		userAnswer.setAll(help);
		itemAnswerList.setItems(userAnswer);
		System.out.println("userAnswer " + okAnswer);
		TaskPlayer.getInstance().autoPlay(okAnswer);
	}

	@Override
	public void onRepeat() {
		correctAnswerList.setItems(FXCollections.observableArrayList());
		userAnswer = FXCollections.observableArrayList();
		itemAnswerList.setItems(userAnswer);
		itemButtons.forEach(b -> b.setDisable(false));
	}

	@Override
	public void onShowAnswer() {
		if (correctAnswer != null) {
			// correctAnswerList.setVisible(true);
			ObservableList<String> list = FXCollections.observableArrayList();
			correctAnswer.forEach(ans -> {
				list.add(ans);
			});
			correctAnswerList.setItems(list);
		}
	}

	@Override
	public void onSettingsChanged() {
		//System.out.println(getClass() + " onSettingsChanged");
		refreshPanel();
	}

	public void clear() {
		correctAnswer = new LinkedList<>();
		correctAnswerList.setItems(FXCollections.observableArrayList());
	}

	@Override
	public void onTaskChange() {
		//System.out.println("onTaskChange");
		itemButtons.forEach(b -> b.setDisable(true));
	}

}
