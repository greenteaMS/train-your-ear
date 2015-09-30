/*
 *
 */
package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mary.train.your.ear.controller.AbstractController;

// TODO: Auto-generated Javadoc
/**
 * The Class AddUserPopupController.
 */
public class AbstractPopupController extends AbstractController {

	/**
	 * Close window.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void closeWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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
		logInit(getClass());
	}
}
