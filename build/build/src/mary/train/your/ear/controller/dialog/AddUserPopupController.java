/*
 *
 */
package mary.train.your.ear.controller.dialog;

import org.apache.logging.log4j.LogManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class AddUserPopupController.
 */
public class AddUserPopupController extends AbstractPopupController {

	/** The user name. */
	@FXML
	private TextField userName;

	/** The status label. */
	@FXML
	private Label statusLabel;

	/** The add user button. */
	@FXML
	private Button addUserButton;

	/** The valid. */
	private boolean valid = true;

	/**
	 * Instantiates a new adds the user popup controller.
	 */
	public AddUserPopupController() {
		LOG = LogManager.getLogger(getClass());
	}

	/**
	 * Adds the user.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void addUser(ActionEvent event) {
		if (userName.getText().isEmpty()) {
			statusLabel.setText(StringConstants.SL_FILL_USER_NAME);
			return;
		}
		if (!userName.getText().matches("[a-zA-Z]+")) {
			statusLabel.setText(StringConstants.SL_LETTERS_ONLY);
			return;
		}
		valid = true;
		UserData.getUserData().getListOfUsers().forEach(user -> {
			if (user.getName().equals(userName.getText()))
				valid = false;
		});
		if (!valid) {
			statusLabel.setText(StringConstants.SL_USER_ALREADY_EXISTS);
			return;
		}
		if (UserManager.addUser(userName.getText()))
			statusLabel.setText(StringConstants.SL_USER_ADDED_SUCCESSFULLY);
		else
			statusLabel.setText(StringConstants.SL_ADDING_USER_FAILED);

	}

}
