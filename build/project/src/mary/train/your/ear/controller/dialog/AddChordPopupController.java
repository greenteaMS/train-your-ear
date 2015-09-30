/*
 *
 */
package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Interval;

// TODO: Auto-generated Javadoc
/**
 * The Class AddChordPopupController.
 */
public class AddChordPopupController extends AbstractPopupController {

	/** The try new chord. */
	@FXML
	private Button tryNewChord;

	/** The create new chord. */
	@FXML
	private Button createNewChord;

	/** The available intervals list. */
	@FXML
	private ListView<String> availableIntervalsList;

	/** The selected intervals list. */
	@FXML
	private ListView<String> selectedIntervalsList;

	/** The intervals list. */
	private ObservableList<String> intervalsList = FXCollections.observableArrayList();

	/** The selected list. */
	private ObservableList<String> selectedList = FXCollections.observableArrayList();

	/** The chord name. */
	@FXML
	private TextField chordName;

	/** The status label. */
	@FXML
	private Label statusLabel;

	/** The chord hints. */
	@FXML
	private TextField chordHints;

	/**
	 * Instantiates a new adds the chord popup controller.
	 */
	public AddChordPopupController() {
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInitializing(getClass());
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
				selectedList.remove(value);
			}
		});
		selectedIntervalsList.setItems(selectedList);
		logInit(getClass());
	}

	/**
	 * Play new chord.
	 */
	@FXML
	public void playNewChord() {
		LinkedList<Interval> structure = prepareChord();
		/* String abcNotation = */TaskPlayer.getInstance().tryNewChord(structure);
		// updatePreview(abcNotation);
	}

	/**
	 * Prepare chord.
	 *
	 * @return the linked list
	 */
	private LinkedList<Interval> prepareChord() {
		LinkedList<Interval> structure = new LinkedList<>();
		acc = 0;
		for (int j = 0; j < selectedList.size(); j++) {

			acc += Interval.findBySymbol(selectedList.get(j)).getJfugueRep();

			Interval i = Interval.findByRep(acc);
			if (i != null)
				structure.add(i);
		}
		return structure;
	}

	/**
	 * Creates the new chord.
	 */
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
		boolean success = StructuresManager.createChord(newChord);
		if (success)
			statusLabel.setText(StringConstants.SL_NEW_CHORD_ADDED);
		else
			statusLabel.setText(StringConstants.SL_ADDING_NEW_CHORD_FAILED);
	}

	/** The 'chord already exists' flag. */
	private boolean exists = false;

	/** The accelerator variable. */
	private int acc = 0;

	/**
	 * Validate chord.
	 *
	 * @param structure
	 *            the structure
	 * @return true, if successful
	 */
	private boolean validateChord(LinkedList<Interval> structure) {
		if (chordName.getText().toString().equals("")) {
			statusLabel.setText(StringConstants.SL_FILL_CHORD_NAME);
			return false;
		}
		if (selectedList.size() < 2) {
			statusLabel.setText(StringConstants.SL_TWO_SELECTED_INTERVALS);
			return false;
		}
		if (ChordsSettings.getSettings().getChordsAvailability().keySet().contains(chordName.getText())) {
			statusLabel.setText(StringConstants.SL_CHORD_ALREADY_EXISTS);
			return false;
		}
		exists = false;
		ChordsSettings.getSettings().getChords(selectedList.size() + 1).forEach(chord -> {

			if (chord.getStructure().equals(structure)) {
				exists = true;
			}
		});
		if (exists) {
			statusLabel.setText(StringConstants.SL_CHORD_STRUCTURE_ALREADY_EXISTS);
			return false;
		}
		return true;
	}

}
