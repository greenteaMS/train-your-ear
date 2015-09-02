package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.DataManager;
import mary.train.your.ear.model.TaskPlayer;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Interval;

public class AddChordPopupController extends AbstractController {

	@FXML
	private Button tryNewChord;
	@FXML
	private Button createNewChord;
	@FXML
	private ListView<String> availableIntervalsList;
	@FXML
	private ListView<String> selectedIntervalsList;

	private ObservableList<String> intervalsList = FXCollections.observableArrayList();
	private ObservableList<String> selectedList = FXCollections.observableArrayList();

	@FXML
	SwingNode scoreNode;

	JScoreComponent scoreUI = new JScoreComponent();
	@FXML
	TextField chordName;
	@FXML
	Label statusLabel;
	@FXML
	TextField chordHints;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		intervalsList = FXCollections.observableArrayList();
		selectedList = FXCollections.observableArrayList();

		for (Interval interval : Interval.values()) {
			intervalsList.add(interval.getSymbol());
		}

		availableIntervalsList.setItems(intervalsList);
		availableIntervalsList.setOnMouseClicked(handler -> {
			if (handler.getClickCount() == 2) {
				String value = availableIntervalsList.getSelectionModel().getSelectedItem();
				selectedList.add(value);
			}
		});

		selectedIntervalsList.setOnMouseClicked(handler -> {

			if (handler.getClickCount() == 2) {
				String value = selectedIntervalsList.getSelectionModel().getSelectedItem();
				System.out.println(value);
				selectedList.remove(value);
			}
		});
		scoreNode.resize(300, 300);
		//scoreUI.setTune(tune);
		scoreNode.setContent(scoreUI);
		scoreUI.validate();
		selectedIntervalsList.setItems(selectedList);

	}

	// TODO
	private void updatePreview(String tuneAsString) {
		System.out.println("updatePreview " + tuneAsString);
		Tune tune = new TuneParser().parse(tuneAsString);
		scoreUI.removeAll();
		scoreUI.setTune(tune);
		scoreUI.validate();

	}

	@FXML
	public void playNewChord() {
		LinkedList<Interval> structure = prepareChord();
		/*String abcNotation = */TaskPlayer.getInstance().tryNewChord(structure);
		//updatePreview(abcNotation);
	}

	@FXML
	public void closeWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private LinkedList<Interval> prepareChord() {
		LinkedList<Interval> structure = new LinkedList<>();
		acc = 0;
		for (int j = 0; j < selectedList.size(); j++) {

			acc += Interval.findBySymbol(selectedList.get(j)).getJfugueRep();

			Interval i = Interval.findByRep(acc);
			if (i != null)
				structure.add(i);
		}
		System.out.println(structure);
		return structure;
	}

	@FXML
	public void createNewChord() {
		LinkedList<Interval> structure = prepareChord();
		if (!validateChord(structure))
			return;
		CustomChord newChord = new CustomChord();
		newChord.setName(chordName.getText().toString());
		newChord.setStructure(structure);
		newChord.setHasInversions(false);
		newChord.setHints(chordHints.getText());
		boolean success = DataManager.createChord(newChord);
		System.out.println(success);
		if (success)
			statusLabel.setText("New chord added");
		else
			statusLabel.setText("Adding new chord failed");
	}

	boolean exists = false;
	int acc = 0;

	private boolean validateChord(LinkedList<Interval> structure) {
		if (chordName.getText().toString().equals("")) {
			statusLabel.setText("Fill chord name");
			return false;
		}
		if (selectedList.size() < 2) {
			statusLabel.setText("There must be at least two selected intervals");
			return false;
		}
		if (ChordsSettings.getSettings().getChordsAvailability().keySet().contains(chordName.getText())) {
			statusLabel.setText("Chord with such a name already exists");
			return false;
		}
		exists = false;
		System.out.println(structure);
		ChordsSettings.getSettings().getChords(selectedList.size() + 1).forEach(chord -> {

			if (chord.getStructure().equals(structure)) {
				exists = true;
				System.out.println(chord.getStructure());
			}
		});
		if (exists) {
			statusLabel.setText("Chord with such a structure already exists");
			return false;
		}
		return true;
	}

}
