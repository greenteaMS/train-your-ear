/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.settings;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.controller.dialog.ManageChordHintsPopupController;
import mary.train.your.ear.model.StructuresManager;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;
import mary.train.your.ear.observers.UserChangeObserver;
import mary.train.your.ear.view.ui.PersistentButtonToggleGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class ChordsSettingsPaneController.
 */
public class ChordsSettingsPaneController extends AbstractController implements UserChangeObserver {

	/** The chords settings pane. */
	@FXML
	private VBox chordsSettingsPane;

	/** The chords list. */
	@FXML
	private VBox chordsList;

	/** The five pitches. */
	@FXML
	private ToggleButton fivePitches;

	/** The four pitches. */
	@FXML
	private ToggleButton fourPitches;

	/** The three pitches. */
	@FXML
	private ToggleButton threePitches;

	/** The inv iv. */
	@FXML
	private ToggleButton invIV;

	/** The inv i. */
	@FXML
	private ToggleButton invI;

	/** The inv iii. */
	@FXML
	private ToggleButton invIII;

	/** The inv ii. */
	@FXML
	private ToggleButton invII;

	/** The no inversion. */
	@FXML
	private ToggleButton noInversion;

	/** The no of pitches group. */
	private ToggleGroup noOfPitchesGroup = new PersistentButtonToggleGroup();

	/** The list of chord buttons. */
	private ObservableList<HBox> listOfChordButtons;

	/** The add new chord button. */
	@FXML
	private Button addNewChordButton;

	/** The no of pitches map. */
	private HashMap<Integer, ToggleButton> noOfPitchesMap = new HashMap<>();

	/** The inversions map. */
	private HashMap<Inversion, ToggleButton> inversionsMap = new HashMap<>();

	/**
	 * Instantiates a new chords settings pane controller.
	 */
	public ChordsSettingsPaneController() {
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

		fivePitches.setToggleGroup(noOfPitchesGroup);
		fourPitches.setToggleGroup(noOfPitchesGroup);
		threePitches.setToggleGroup(noOfPitchesGroup);
		noOfPitchesMap.put(3, threePitches);
		noOfPitchesMap.put(4, fourPitches);
		noOfPitchesMap.put(5, fivePitches);

		updateChordList(3);
		updateTogglesView();

		inversionsMap.put(Inversion.None, noInversion);
		inversionsMap.put(Inversion.First, invI);
		inversionsMap.put(Inversion.Second, invII);
		inversionsMap.put(Inversion.Third, invIII);
		inversionsMap.put(Inversion.Fourth, invIV);

		logInit(getClass());
	}

	/**
	 * Update toggles view.
	 */
	private void updateTogglesView() {
		updateToggles();

		checkRedStyle(noInversion);
		checkRedStyle(invI);
		checkRedStyle(invII);
		checkRedStyle(invIII);
		checkRedStyle(invIV);
	}

	/**
	 * Update chord list.
	 *
	 * @param i
	 *            the number of pitches in chord
	 */
	private void updateChordList(int i) {
		listOfChordButtons = FXCollections.observableArrayList();
		ChordsSettings.getSettings().getChords(i).forEach(chord -> {
			ToggleButton chordButton = new ToggleButton(chord.getName());
			chordButton.setSelected(ChordsSettings.getSettings().getChordsAvailability().get(chord.getName()));
			chordButton.getStyleClass().add("chordToggle");
			checkRedStyle(chordButton);
			chordButton.setOnAction((event) -> {
				LinkedHashMap<String, Boolean> map = ChordsSettings.getSettings().getChordsAvailability();
				map.put(chord.getName(), chordButton.isSelected());
				checkRedStyle(chordButton);
				notifySettingsObservers();
			});
			createContextMenu(chordButton, chord);
			Button play = createPlayButtonForChord(chord);
			HBox panel = new HBox(5, chordButton, play);
			panel.setAlignment(Pos.CENTER);
			listOfChordButtons.add(panel);
		});
		chordsList.getChildren().setAll(listOfChordButtons);
	}

	private Button createPlayButtonForChord(CustomChord chord) {
		Button b = new Button();
		b.getStyleClass().add("speakerButton");
		b.setOnAction(action -> TaskPlayer.getInstance().tryNewChord(chord.getStructure()));
		return b;
	}

	private void createContextMenu(ToggleButton button, CustomChord chord) {
		ContextMenu menu = new ContextMenu();
		MenuItem hintItem = new MenuItem(StringConstants.MI_MANAGE_HINTS);
		hintItem.setOnAction(action -> {
			showManageHints(button, chord);
		});
		menu.getItems().add(hintItem);
		if (chord.isCustom()) {
			MenuItem deleteItem = new MenuItem(StringConstants.MI_REMOVE_CHORD);
			deleteItem.setOnAction(action -> {
				StructuresManager.removeChord(chord);
				updateChordList(ChordsSettings.getSettings().getNoOfPitches());
				notifySettingsObservers();
			});
			menu.getItems().add(deleteItem);
		}
		button.setContextMenu(menu);
	}

	/**
	 * Show manage hints.
	 *
	 * @param action
	 *            the action
	 * @param chord
	 *            the chord
	 */
	private void showManageHints(ToggleButton action, CustomChord chord) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource("mary/train/your/ear/view/dialog/ManageChordHintsPopup.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 200);
			ManageChordHintsPopupController controller = fxmlLoader.<ManageChordHintsPopupController> getController();
			controller.setChord(chord);
			scene.getStylesheets().add(getClass().getClassLoader()
					.getResource("mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_MANAGE_CHORD_HINTS);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner(action.getScene().getWindow());
			stage.show();

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					updateChordList(ChordsSettings.getSettings().getNoOfPitches());
					notifySettingsObservers();
				}
			});

		} catch (IOException e) {
			LOG.error("showManageHints:\n" + e);
			e.printStackTrace();
		}
	}

	/**
	 * Update numberOfPitches toggles.
	 */
	private void updateToggles() {
		checkRedStyle(fivePitches);
		checkRedStyle(fourPitches);
		checkRedStyle(threePitches);
	}

	/**
	 * Sets the triads on list.
	 */
	public void setTriadsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(3);
		updateChordList(3);
		invIII.setDisable(true);
		invIV.setDisable(true);
		notifySettingsObservers();
		updateToggles();
	}

	/**
	 * Sets the tetrads on list.
	 */
	public void setTetradsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(4);
		updateChordList(4);
		invIII.setDisable(false);
		invIV.setDisable(true);
		notifySettingsObservers();
		updateToggles();
	}

	/**
	 * Sets the pentads on list.
	 */
	public void setPentadsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(5);
		updateChordList(5);
		invIII.setDisable(false);
		invIV.setDisable(false);
		notifySettingsObservers();
		updateToggles();
	}

	/**
	 * Enable no inversion.
	 */
	public void enableNoInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.None);
		notifySettingsObservers();
		checkRedStyle(noInversion);
	}

	/**
	 * Enable I inversion.
	 */
	public void enableIInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.First);
		notifySettingsObservers();
		checkRedStyle(invI);
	}

	/**
	 * Enable II inversion.
	 */
	public void enableIiInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Second);
		notifySettingsObservers();
		checkRedStyle(invII);
	}

	/**
	 * Enable III inversion.
	 */
	public void enableIiiInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Third);
		notifySettingsObservers();
		checkRedStyle(invIII);
	}

	/**
	 * Enable IV inversion.
	 */
	public void enableIvInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Fourth);
		notifySettingsObservers();
		checkRedStyle(invIV);
	}

	/**
	 * Show addNewChordPopup.
	 *
	 * @param event
	 *            the event
	 */
	public void showAddNewChordPopup(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("mary/train/your/ear/view/dialog/AddChordPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 400);
			scene.getStylesheets().add(getClass().getClassLoader()
					.getResource("mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_ADD_NEW_CHORD);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);

			stage.initOwner(((Node) event.getSource()).getScene().getWindow());
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					updateChordList(ChordsSettings.getSettings().getNoOfPitches());
					notifySettingsObservers();
				}
			});

		} catch (IOException e) {
			LOG.error("showAddNewChordPopup:\n" + e);
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
		int noOfpitches = ChordsSettings.getSettings().getNoOfPitches();
		noOfPitchesGroup.selectToggle(noOfPitchesMap.get(noOfpitches));
		updateChordList(noOfpitches);
		ChordsSettings.getSettings().getEnabledInvertions().forEach((inv, value) -> {
			inversionsMap.get(inv).setSelected(value);
		});
		if (noOfpitches < 4)
			invIII.setDisable(true);
		if (noOfpitches < 5)
			invIV.setDisable(true);
		updateTogglesView();
		notifySettingsObservers();
	}

}
