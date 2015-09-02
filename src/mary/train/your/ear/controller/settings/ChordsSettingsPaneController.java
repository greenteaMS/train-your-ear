package mary.train.your.ear.controller.settings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.controller.dialog.ManageChordHintsPopupController;
import mary.train.your.ear.model.DataManager;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Inversion;
import mary.train.your.ear.observers.SettingsChangedObserver;

public class ChordsSettingsPaneController extends AbstractController {
	@FXML
	private VBox chordsSettingsPane;

	@FXML
	VBox chordsList;
	@FXML
	ToggleButton fivePitches;
	@FXML
	ToggleButton fourPitches;
	@FXML
	ToggleButton threePitches;
	@FXML
	ToggleButton invIV;
	@FXML
	ToggleButton invI;
	@FXML
	ToggleButton invIII;
	@FXML
	ToggleButton invII;
	@FXML
	ToggleButton noInversion;

	private ToggleGroup noOfPitchesGroup = new ToggleGroup();
	private ObservableList<ToggleButton> listOfChordButtons;

	@FXML
	Button addNewChordButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(getClass() + " controller initialized");
		fivePitches.setToggleGroup(noOfPitchesGroup);
		fourPitches.setToggleGroup(noOfPitchesGroup);
		threePitches.setToggleGroup(noOfPitchesGroup);

		updateChordList(3);
		updateToggles();

		checkRedStyle(noInversion);
		checkRedStyle(invI);
		checkRedStyle(invII);
		checkRedStyle(invIII);
		checkRedStyle(invIV);
	}

	private void updateChordList(int i) {
		listOfChordButtons = FXCollections.observableArrayList();
		ChordsSettings.getSettings().getChords(i).forEach(chord -> {
			ToggleButton button = new ToggleButton(chord.getName());
			button.setSelected(ChordsSettings.getSettings().getChordsAvailability().get(chord.getName()));
			button.getStyleClass().add("intervalToggle");
			checkRedStyle(button);
			button.setOnAction((event) -> {
				LinkedHashMap<String, Boolean> map = ChordsSettings.getSettings().getChordsAvailability();
				map.put(chord.getName(), button.isSelected());
				// System.out.println(map);
				checkRedStyle(button);
				notifySettingsObservers();
			});
			ContextMenu menu = new ContextMenu();
			MenuItem hintItem = new MenuItem("Manage hints");
			hintItem.setOnAction(action -> {
				showManageHints(button, chord);
			});
			menu.getItems().add(hintItem);
			if (chord.isCustom()) {
				MenuItem deleteItem = new MenuItem("Remove chord");
				deleteItem.setOnAction(action -> {
					DataManager.removeChord(chord);
					updateChordList(ChordsSettings.getSettings().getNoOfPitches());
					notifySettingsObservers();
				});
				menu.getItems().add(deleteItem);
			}
			button.setContextMenu(menu);
			listOfChordButtons.add(button);
		});
		chordsList.getChildren().setAll(listOfChordButtons);
	}

	private void showManageHints(ToggleButton action, CustomChord chord) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/dialog/ManageChordHintsPopup.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 200);
			ManageChordHintsPopupController controller = fxmlLoader.<ManageChordHintsPopupController>getController();
			controller.setChord(chord);
			scene.getStylesheets().add(getClass().getResource("../../view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Manage chord hints");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkRedStyle(ToggleButton button) {
		if (button.isSelected())
			button.getStyleClass().remove("intervalToggleRed");
		else if (!button.getStyleClass().contains("intervalToggleRed"))
			button.getStyleClass().add("intervalToggleRed");
	}

	private void notifySettingsObservers() {
		observers.stream().filter(p -> p instanceof SettingsChangedObserver).forEach(obs -> {
			System.out.println(obs.getClass());
			((SettingsChangedObserver) obs).onSettingsChanged();
		});
	}

	private void updateToggles() {
		checkRedStyle(fivePitches);
		checkRedStyle(fourPitches);
		checkRedStyle(threePitches);
	}

	public void setTriadsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(3);
		updateChordList(3);
		invIII.setDisable(true);
		invIV.setDisable(true);
		notifySettingsObservers();
		updateToggles();
	}

	public void setTetradsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(4);
		updateChordList(4);
		invIII.setDisable(false);
		invIV.setDisable(true);
		notifySettingsObservers();
		updateToggles();
	}

	public void setPentadsOnList() {

		ChordsSettings.getSettings().setNoOfPitches(5);
		updateChordList(5);
		invIII.setDisable(false);
		invIV.setDisable(false);
		notifySettingsObservers();
		updateToggles();
	}

	public void enableNoInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.None);
		notifySettingsObservers();
		checkRedStyle(noInversion);
	}

	public void enableIInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.First);
		notifySettingsObservers();
		checkRedStyle(invI);
	}

	public void enableIiInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Second);
		notifySettingsObservers();
		checkRedStyle(invII);
	}

	public void enableIiiInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Third);
		notifySettingsObservers();
		checkRedStyle(invIII);
	}

	public void enableIvInversion() {
		ChordsSettings.getSettings().changeInvertionEnabled(Inversion.Fourth);
		notifySettingsObservers();
		checkRedStyle(invIV);
	}

	public void showAddNewChordPopup(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/dialog/AddChordPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 400);
			scene.getStylesheets().add(getClass().getResource("../../view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Add new chord");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
