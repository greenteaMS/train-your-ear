/*
 *
 */
package mary.train.your.ear.controller.settings;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.observers.UserChangeObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerSettingsPaneController.
 */
public class TunerSettingsPaneController extends AbstractController implements UserChangeObserver {

	/** The tuner settings pane. */
	@FXML
	private VBox tunerSettingsPane;

	/** The list of buttons. */
	@FXML
	private VBox listOfButtons;

	/** The ratio buttons. */
	private ObservableList<ToggleButton> ratioButtons = FXCollections.observableArrayList();

	/** The manage ratios. */
	@FXML
	Button manageRatios;

	/** The set size spinner. */
	@FXML
	Spinner<Integer> setSizeSpinner;

	/**
	 * Instantiates a new tuner settings pane controller.
	 */
	public TunerSettingsPaneController() {
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
		refreshButtons();

		setSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			TunerSettings.getSettings().setSetSize(newValue);
		});
		logInit(getClass());
	}

	/**
	 * Refresh buttons.
	 */
	private void refreshButtons() {
		ratioButtons.clear();
		TunerSettings.getSettings().getAvailableRatios().forEach((ratio, value) -> {

			ToggleButton button = new ToggleButton(ratio.getName());
			button.setSelected(value);
			button.getStyleClass().add("intervalToggle");
			checkRedStyle(button);
			button.setOnAction((event) -> {
				HashMap<FrequencyRatio, Boolean> map = TunerSettings.getSettings().getAvailableRatios();
				map.put(ratio, !map.get(ratio));
				checkRedStyle(button);
				notifySettingsObservers();
			});
			ratioButtons.add(button);
		});
		listOfButtons.getChildren().setAll(ratioButtons);
	}

	/**
	 * Show manage ratios popup.
	 *
	 * @param event
	 *            the event
	 */
	public void showManageRatiosPopup(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("mary/train/your/ear/view/dialog/ManageRatiosPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 800, 600);
			scene.getStylesheets()
					.add(getClass().getClassLoader().getResource("mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_MANAGE_RATIOS);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner(((Node) event.getSource()).getScene().getWindow());
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					refreshButtons();
					notifySettingsObservers();
				}
			});

		} catch (IOException e) {
			LOG.error("showManageRatiosPopup:\n " + e);
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.observers.UserChangeObserver#onUserChange()
	 */
	@Override
	public void onUserChange() {
		LOG.info("onUserChange");
		refreshButtons();
		setSizeSpinner.getValueFactory().setValue(TunerSettings.getSettings().getSetSize());
		notifySettingsObservers();
	}
}
