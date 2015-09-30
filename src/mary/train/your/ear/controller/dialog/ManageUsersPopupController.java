/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.dialog;

import java.net.URL;
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
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class AddUserPopupController.
 */
public class ManageUsersPopupController extends AbstractPopupController {

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

	@FXML
	private ListView<String> usersListView;

	private ObservableList<String> usersList = FXCollections.observableArrayList();

	/**
	 * Instantiates a new adds the user popup controller.
	 */
	public ManageUsersPopupController() {
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
		initUsersList();

		logInit(getClass());
	}

	private void initUsersList() {
		usersList.clear();
		UserData.getUserData().getListOfUsers().stream().filter(u -> !u.getName().equals(StringConstants.GUEST))
				.forEach(user -> {
					usersList.add(user.getName());
				});
		usersListView.setItems(usersList);
	}

	/**
	 * Adds the user.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void addUser() {
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
		if (UserManager.addUser(userName.getText())){
			statusLabel.setText(StringConstants.SL_USER_ADDED_SUCCESSFULLY);
			userName.clear();
		}
		else
			statusLabel.setText(StringConstants.SL_ADDING_USER_FAILED);
		initUsersList();

	}

	@FXML
	public void deleteSelected() {
		String selectedItem = usersListView.getSelectionModel().getSelectedItem();
		if (selectedItem == null) {
			statusLabel.setText(StringConstants.SL_NO_USER_SELECTED);
			return;
		}
		if (UserManager.deleteUser(selectedItem))
			statusLabel.setText(StringConstants.SL_DELETING_USER_SUCCESS);
		else
			statusLabel.setText(StringConstants.SL_DELETING_USER_FAILED);
		initUsersList();
	}
}
