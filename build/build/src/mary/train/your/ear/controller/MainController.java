/*
 *
 */
package mary.train.your.ear.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.settings.ChordsSettingsPaneController;
import mary.train.your.ear.controller.settings.GeneralSettingsPaneController;
import mary.train.your.ear.controller.settings.IntervalComparatorSettingsPaneController;
import mary.train.your.ear.controller.settings.IntervalsSettingsPaneController;
import mary.train.your.ear.controller.settings.TunerSettingsPaneController;
import mary.train.your.ear.controller.tasks.ChordTaskPaneController;
import mary.train.your.ear.controller.tasks.IntervalComparatorTaskPaneController;
import mary.train.your.ear.controller.tasks.IntervalsTaskPaneController;
import mary.train.your.ear.controller.tasks.TunerTaskPaneController;
import mary.train.your.ear.model.StatisticsManager;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.UserManager;
import mary.train.your.ear.model.player.TaskPlayer;
import mary.train.your.ear.observers.TaskChangeObserver;
import mary.train.your.ear.observers.UserChangeObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
public class MainController extends AbstractController {

	private static final String userGuidePath = "./userGuide.pdf";

	/**
	 * Instantiates a new main controller.
	 */
	public MainController() {
		LOG = LogManager.getLogger(getClass());
	}

	/** The tasks tab pane. */
	@FXML
	private TabPane tasksTabPane;

	/** The intervals task. */
	@FXML
	private Tab intervalsTask;

	/** The chords task. */
	@FXML
	private Tab chordsTask;

	@FXML
	private Tab comparatorTask;

	/** The tuner task. */
	@FXML
	private Tab tunerTask;

	/** The player pane controller. */
	@FXML
	private PlayerPaneController playerPaneController;

	/** The general settings pane controller. */
	@FXML
	private GeneralSettingsPaneController generalSettingsPaneController;

	/** The intervals settings pane controller. */
	@FXML
	private IntervalsSettingsPaneController intervalsSettingsPaneController;

	/** The chords settings pane controller. */
	@FXML
	private ChordsSettingsPaneController chordsSettingsPaneController;

	/** The tuner settings pane controller. */
	@FXML
	private TunerSettingsPaneController tunerSettingsPaneController;

	/** The intervals task pane controller. */
	@FXML
	private IntervalsTaskPaneController intervalsTaskPaneController;

	/** The chord task pane controller. */
	@FXML
	private ChordTaskPaneController chordTaskPaneController;

	/** The tuner task pane controller. */
	@FXML
	private TunerTaskPaneController tunerTaskPaneController;

	@FXML
	private IntervalComparatorSettingsPaneController intervalComparatorSettingsPaneController;

	@FXML
	private IntervalComparatorTaskPaneController intervalComparatorTaskPaneController;

	/** The intervals settings wrapper. */
	@FXML
	private AnchorPane intervalsSettingsWrapper;

	/** The chords settings wrapper. */
	@FXML
	private AnchorPane chordsSettingsWrapper;

	/** The tuner settings wrapper. */
	@FXML
	private AnchorPane tunerSettingsWrapper;

	@FXML
	private AnchorPane intervalComparatorSettingsWrapper;

	/** The change text. */
	private boolean disableRepeatAndShowButtons = false;

	/** The user profile. */
	@FXML
	private ComboBox<String> userProfile;

	/** The users list. */
	private ObservableList<String> usersList = FXCollections.observableArrayList();

	@FXML
	MenuBar menuBar;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mary.train.your.ear.controller.AbstractController#initialize(java.net.
	 * URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		logInitializing(getClass());

		tasksTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				intervalsTaskPaneController.clear();
				chordTaskPaneController.clear();
				disableRepeatAndShowButtons = false;
				if (newValue == intervalsTask) {
					intervalsSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy(StringConstants.TASK_INTERVALS);
				} else if (newValue == chordsTask) {
					chordsSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy(StringConstants.TASK_CHORD);
				} else if (newValue == tunerTask) {
					tunerSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy(StringConstants.TASK_TUNER);
					disableRepeatAndShowButtons = true;
				} else if (newValue == comparatorTask) {
					intervalComparatorSettingsWrapper.toFront();
					TaskPlayer.getInstance().setStrategy(StringConstants.TASK_INTERVAL_COMPARATOR);
					disableRepeatAndShowButtons = true;
				}
				notifyTaskChange(disableRepeatAndShowButtons);

			}
		});
		// init observers

		playerPaneController.addObserver(intervalsTaskPaneController);
		playerPaneController.addObserver(chordTaskPaneController);
		playerPaneController.addObserver(tunerTaskPaneController);
		playerPaneController.addObserver(intervalComparatorTaskPaneController);

		chordsSettingsPaneController.addObserver(chordTaskPaneController);
		chordsSettingsPaneController.addObserver(playerPaneController);

		intervalsSettingsPaneController.addObserver(intervalsTaskPaneController);
		intervalsSettingsPaneController.addObserver(playerPaneController);

		observers.add(generalSettingsPaneController);
		observers.add(chordsSettingsPaneController);
		observers.add(intervalsSettingsPaneController);
		observers.add(tunerSettingsPaneController);
		observers.add(intervalComparatorSettingsPaneController);
		observers.add(intervalsTaskPaneController);
		observers.add(chordTaskPaneController);
		observers.add(playerPaneController);

		initUsersList();

		logInit(getClass());

	}

	private void notifyTaskChange(boolean disableRepeatAndShowButtons) {
		observers.stream().filter(p -> p instanceof TaskChangeObserver)
				.forEach(obs -> ((TaskChangeObserver) obs).onTaskChange(disableRepeatAndShowButtons));

	}

	/**
	 * Inits the users list.
	 */
	private void initUsersList() {
		usersList.clear();
		UserData.getUserData().getListOfUsers().forEach(user -> {
			usersList.add(user.getName());
		});
		usersList.add(StringConstants.ADD_NEW_USER);
		userProfile.setItems(usersList);
		userProfile.getSelectionModel().select(StringConstants.GUEST);
		userProfile.setVisibleRowCount(15);
	}

	/**
	 * Choose user.
	 *
	 * @param e
	 *            the e
	 */
	@FXML
	public void chooseUser(ActionEvent e) {
		String selectedItem = userProfile.getSelectionModel().getSelectedItem();
		LOG.debug("chooseUser - selected item: " + selectedItem);
		if (selectedItem == null) {
			userProfile.getSelectionModel().select(StringConstants.GUEST);
		} else if (selectedItem.equals(StringConstants.ADD_NEW_USER)) {
			addNewUser(e);
		} else {
			LOG.info("currentUser before change: " + UserData.getUserData().getCurrentUser());
			UserManager.saveCurrentUserSettings();
			StatisticsManager.saveCurrentUserStatistics();
			UserData.getUserData().setCurrentUser(selectedItem);
			StatisticsManager.loadCurrentUserStatistics();
			LOG.info("currentUser after change: " + UserData.getUserData().getCurrentUser());
			observers.stream().filter(p -> p instanceof UserChangeObserver)
					.forEach(obs -> ((UserChangeObserver) obs).onUserChange());
			tasksTabPane.getSelectionModel().select(intervalsTask);
		}
	}

	/**
	 * Adds the new user.
	 *
	 * @param e
	 *            the e
	 */
	private void addNewUser(ActionEvent e) {
		LOG.debug("addNewUser...");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("mary/train/your/ear/view/dialog/AddUserPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 130);
			scene.getStylesheets().add(
					getClass().getResource("/mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_ADD_USER);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner((Stage) menuBar.getScene().getWindow());

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					initUsersList();
				}
			});
			stage.show();

		} catch (IOException ex) {
			LOG.error("addNewUser:\n" + ex);
			ex.printStackTrace();
		}

	}

	@FXML
	public void close(ActionEvent event) {
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	public void showCurrentUserStatistics() {
		LOG.debug("showCurrentUserStatistics...");
		StatisticsManager.saveCurrentUserStatistics();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource("mary/train/your/ear/view/dialog/CurrentUserStatisticsPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 840, 600);
			scene.getStylesheets().add(
					getClass().getResource("/mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_USER_STATISTICS);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner((Stage) menuBar.getScene().getWindow());
			stage.show();

		} catch (IOException ex) {
			LOG.error("addNewUser:\n" + ex);
			ex.printStackTrace();
		}
	}

	@FXML
	public void clearCurrentStatistics(ActionEvent event) {
		UserData.getUserData().getDailyUserStatistics().clear();
		StatisticsManager.saveCurrentUserStatistics();
	}

	@FXML
	public void showStatisticsSummary(ActionEvent event) {
		// TODO
	}

	@FXML
	public void showUserGuide(ActionEvent event) {
		File pdfFile = new File(userGuidePath);
		if (pdfFile.exists()) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().open(pdfFile);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else {
				LOG.warn("Awt Desktop is not supported!");
			}
		}

		else {
			LOG.warn("File is not exists!");
		}
	}

	@FXML
	public void showAbout(ActionEvent event) {
		LOG.debug("showAbout...");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("mary/train/your/ear/view/dialog/AboutProgramPopup.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 400, 270);
			scene.getStylesheets().add(
					getClass().getResource("/mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_ABOUT);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner((Stage) menuBar.getScene().getWindow());
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					initUsersList();
				}
			});
			stage.show();

		} catch (IOException ex) {
			LOG.error("addNewUser:\n" + ex);
			ex.printStackTrace();
		}
	}

	@FXML
	public void showManageUsers() {
		LOG.debug("showManageUsers...");
		StatisticsManager.saveCurrentUserStatistics();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("mary/train/your/ear/view/dialog/ManageUsersPopup.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root1, 450, 320);
			scene.getStylesheets().add(
					getClass().getResource("/mary/train/your/ear/view/resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle(StringConstants.TITLE_MANAGE_USERS);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner((Stage) menuBar.getScene().getWindow());
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					initUsersList();
					updateCurrentUser();
				}
			});
			stage.show();

		} catch (IOException ex) {
			LOG.error("addNewUser:\n" + ex);
			ex.printStackTrace();
		}
	}

	private void updateCurrentUser() {
		userProfile.getSelectionModel().select(UserData.getUserData().getCurrentUser().getName());
	}

}
