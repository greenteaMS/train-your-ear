/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.dialog;

import org.apache.logging.log4j.LogManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.structures.CustomChord;

// TODO: Auto-generated Javadoc
/**
 * The Class ManageChordHintsPopupController.
 */
public class ManageChordHintsPopupController extends AbstractPopupController {


	/** The chord name. */
	@FXML
	private Label chordName;

	/** The chord hints. */
	@FXML
	private TextField chordHints;

	/** The status label. */
	@FXML
	private Label statusLabel;

	/** The save hints. */
	@FXML
	private Button saveHints;

	/** The chord. */
	private CustomChord chord;

	/**
	 * Instantiates a new manage chord hints popup controller.
	 */
	public ManageChordHintsPopupController() {
		LOG = LogManager.getLogger(getClass());
	}

	/**
	 * Save hints.
	 *
	 * @param event the event
	 */
	@FXML
	public void saveHints(ActionEvent event) {
		chord.setHints(chordHints.getText());
		if (StructuresManager.saveChordHints(chord))
			statusLabel.setText(StringConstants.SL_UPDATING_CHORD_HINTS_SUCCEEDED);
		else
			statusLabel.setText(StringConstants.SL_UPDATING_CHORD_HINTS_FAILED);
	}

	/**
	 * Sets the chord.
	 *
	 * @param chord the new chord
	 */
	public void setChord(CustomChord chord) {
		this.chord = chord;
		chordName.setText(chord.getName());
		chordHints.setText(chord.getHints());
	}

}
