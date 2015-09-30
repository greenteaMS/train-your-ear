/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.TilePane;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.structures.FrequencyRatio;

// TODO: Auto-generated Javadoc
/**
 * The Class ManageRatiosPopupController.
 */
public class ManageRatiosPopupController extends AbstractPopupController {

	/** The ratio name. */
	@FXML
	private TextField ratioName;

	/** The freq ratio. */
	@FXML
	private TextField freqRatio;

	/** The add ratio. */
	@FXML
	private Button addRatio;

	/** The close button. */
	@FXML
	private Button closeButton;

	/** The buildin ratios list. */
	@FXML
	private TilePane buildinRatiosList;

	/** The custom ratios list. */
	@FXML
	private TilePane customRatiosList;

	/** The list of build in ratios buttons. */
	private ObservableList<ToggleButton> listOfBuildInRatiosButtons = FXCollections.observableArrayList();

	/** The list of custom ratios buttons. */
	private ObservableList<ToggleButton> listOfCustomRatiosButtons = FXCollections.observableArrayList();

	/** The status label. */
	@FXML
	private Label statusLabel;

	/**
	 * Instantiates a new manage ratios popup controller.
	 */
	public ManageRatiosPopupController() {
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
		refreshPanel();
		logInit(getClass());
	}

	/**
	 * Refresh panel.
	 */
	private void refreshPanel() {
		ratioName.clear();
		freqRatio.clear();
		listOfBuildInRatiosButtons.clear();
		listOfCustomRatiosButtons.clear();
		TunerSettings.getSettings().getAllRatios().forEach(ratio -> {
			createButton(ratio);
		});
		buildinRatiosList.getChildren().setAll(listOfBuildInRatiosButtons);
		customRatiosList.getChildren().setAll(listOfCustomRatiosButtons);
	}

	/**
	 * Creates the button.
	 *
	 * @param ratio
	 *            the ratio
	 */
	private void createButton(FrequencyRatio ratio) {
		ToggleButton button = new ToggleButton(ratio.getName());
		button.setSelected(TunerSettings.getSettings().getAvailableRatios().containsKey(ratio));
		button.getStyleClass().add("intervalToggle");
		checkRedStyle(button);
		button.setOnAction((event) -> {
			LinkedHashMap<FrequencyRatio, Boolean> map = TunerSettings.getSettings().getAvailableRatios();
			if (map.containsKey(ratio))
				map.remove(ratio);
			else
				map.put(ratio, true);
			checkRedStyle(button);
		});
		if (ratio.isCustom()) {
			listOfCustomRatiosButtons.add(button);
			createContextMenu(button, ratio);
		} else
			listOfBuildInRatiosButtons.add(button);
	}

	private void createContextMenu(ToggleButton button, FrequencyRatio ratio) {
		ContextMenu menu = new ContextMenu();

		MenuItem deleteItem = new MenuItem(StringConstants.MI_REMOVE_CHORD);
		deleteItem.setOnAction(action -> {
			if (StructuresManager.removeRatio(ratio))
				statusLabel.setText(StringConstants.SL_DELETING_RATIO_SUCCESS);
			else
				statusLabel.setText(StringConstants.SL_DELETING_RATIO_FAILED);
			TunerSettings.getSettings().reloadAllRatios();
			refreshPanel();
		});
		menu.getItems().add(deleteItem);

		button.setContextMenu(menu);
	}

	/** The valid. */
	private boolean valid = true;

	/**
	 * Adds the new ratio.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void addNewRatio(ActionEvent event) {
		double frequenciesRatio = 0.0;
		valid = true;
		try {
			frequenciesRatio = Double.parseDouble(freqRatio.getText());
		} catch (NumberFormatException nfe) {
			statusLabel.setText(StringConstants.SL_WRONG_RATIO_FORMAT);
			return;
		}
		String name = ratioName.getText();
		if (name.isEmpty()) {
			statusLabel.setText(StringConstants.SL_FILL_RATIO_NAME);
			return;
		}
		TunerSettings.getSettings().getAllRatios().forEach(ratio -> {
			if (ratio.getName().equals(name))
				valid = false;
		});
		if (!valid) {
			statusLabel.setText(StringConstants.SL_RATIO_ALREADY_EXISTS);
			return;
		}
		FrequencyRatio newRatio = new FrequencyRatio(name, frequenciesRatio);
		newRatio.setCustom(true);
		if (StructuresManager.saveNewRatio(newRatio)) {
			statusLabel.setText(StringConstants.SL_NEW_RATIO_ADDED_SUCCESSFULLY);
			TunerSettings.getSettings().getAllRatios().add(newRatio);
			refreshPanel();
		} else {
			statusLabel.setText(StringConstants.SL_ADDING_NEW_RATIO_FAILED);
		}

	}

}
