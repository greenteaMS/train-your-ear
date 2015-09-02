package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.DataManager;
import mary.train.your.ear.model.structures.CustomChord;

public class ManageChordHintsPopupController extends AbstractController {
	@FXML
	private Label chordName;
	@FXML
	private TextField chordHints;
	@FXML
	private Label statusLabel;
	@FXML
	private Button saveHints;

	private CustomChord chord;

	// Event Listener on Button[#saveHints].onAction
	@FXML
	public void saveHints(ActionEvent event) {
		chord.setHints(chordHints.getText());
		if (DataManager.saveChordHints(chord))
			statusLabel.setText("Updating chord hints succeeded");
		else statusLabel.setText("Updating chord hints failed");
	}

	// Event Listener on Button.onAction
	@FXML
	public void closeWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setChord(CustomChord chord) {
		this.chord = chord;
		chordName.setText(chord.getName());
		chordHints.setText(chord.getHints());
	}

}
