/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.dialog;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.StatisticsManager;
import mary.train.your.ear.model.UserData;
import mary.train.your.ear.model.statistics.DailyUserStatistics;
import mary.train.your.ear.view.ui.PersistentButtonToggleGroup;
import mary.train.your.ear.view.ui.StatisticsRecord;

public class CurrentUserStatisticsPopupController extends AbstractPopupController {

	@FXML
	private ListView<String> datesListView;
	@FXML
	private ToggleButton intervalsStatsToggle;
	@FXML
	private ToggleButton chordsStatsToggle;
	@FXML
	private VBox itemStatsPane;
	@FXML
	private VBox correctSetStatsPane;

	private PersistentButtonToggleGroup taskToggleGroup = new PersistentButtonToggleGroup();

	private LinkedHashMap<String, DailyUserStatistics> dateStatisticsMap;
	private ObservableList<String> dateList = FXCollections.observableArrayList();
	private DailyUserStatistics displayedStats;
	@FXML
	private Button clearCurrentButton;
	@FXML
	private Button deleteSelected;
	@FXML
	private Button close;
	@FXML
	private Label statusLabel;
	@FXML
	private ToggleButton comparatorToggle;

	public CurrentUserStatisticsPopupController() {
		LOG = LogManager.getLogger(getClass());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInitializing(getClass());

		intervalsStatsToggle.setToggleGroup(taskToggleGroup);
		chordsStatsToggle.setToggleGroup(taskToggleGroup);
		comparatorToggle.setToggleGroup(taskToggleGroup);

		LinkedList<DailyUserStatistics> userStatisticsList = StatisticsManager.getAllCurrentUserStatistics();
		LOG.info("userStatisticsList " + userStatisticsList.size());
		dateStatisticsMap = new LinkedHashMap<>();
		userStatisticsList.forEach(statistics -> {
			dateList.add(0, statistics.getCreationDateString());
			dateStatisticsMap.put(statistics.getCreationDateString(), statistics);
		});
		datesListView.setItems(dateList);
		datesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			displayedStats = dateStatisticsMap.get(newValue);
			updateView();
		});

		if (!dateList.isEmpty())
			datesListView.getSelectionModel().select(0);
		taskToggleGroup.selectToggle(intervalsStatsToggle);
		updateToggleButtonsStyles();
		updateView();
		logInit(getClass());
	}

	private void updateToggleButtonsStyles() {
		checkRedStyle(chordsStatsToggle);
		checkRedStyle(intervalsStatsToggle);
		checkRedStyle(comparatorToggle);
	}

	private void updateView() {
		LOG.info("updateView");
		if (intervalsStatsToggle.isSelected())
			showIntervalStats();
		else if (chordsStatsToggle.isSelected())
			showChordsStats();
		else if (comparatorToggle.isSelected())
			showComparatorStats();
		statusLabel.setText("View updated");
	}

	@FXML
	public void showIntervalStats() {
		LOG.info("showIntervalStats");
		updateToggleButtonsStyles();
		itemStatsPane.getChildren().clear();
		correctSetStatsPane.getChildren().clear();
		if (displayedStats != null) {
			displayedStats.getIntervalStatistics().getIntervalStatisticsMap().forEach((interval, stat) -> {
				itemStatsPane.getChildren().add(new StatisticsRecord(stat));
			});
			displayedStats.getIntervalStatistics().getCorrectSetStatisticsMap().forEach((setSize, stat) -> {
				correctSetStatsPane.getChildren().add(new StatisticsRecord(stat));
			});
		}
	}

	@FXML
	public void showChordsStats() {
		LOG.info("showChordsStats");
		itemStatsPane.getChildren().clear();
		updateToggleButtonsStyles();
		correctSetStatsPane.getChildren().clear();
		if (displayedStats != null) {
			displayedStats.getCustomChordStatistics().getChordsStatisticsMap().forEach((chord, stat) -> {
				itemStatsPane.getChildren().add(new StatisticsRecord(stat));
			});
			displayedStats.getCustomChordStatistics().getCorrectSetStatisticsMap().forEach((setSize, stat) -> {
				correctSetStatsPane.getChildren().add(new StatisticsRecord(stat));
			});
		}
	}

	@FXML
	public void showComparatorStats() {
		LOG.info("showChordsStats");
		itemStatsPane.getChildren().clear();
		updateToggleButtonsStyles();
		correctSetStatsPane.getChildren().clear();
		if (displayedStats != null) {
			displayedStats.getIntervalComparatorStatistics().getIntervalStatisticsMap().forEach((ratio, stat) -> {
				itemStatsPane.getChildren().add(new StatisticsRecord(stat));
			});
		}
	}

	@FXML
	public void clearCurrentStats() {
		UserData.getUserData().getDailyUserStatistics().clear();
		StatisticsManager.saveCurrentUserStatistics();
		dateStatisticsMap.put(UserData.getUserData().getDailyUserStatistics().getCreationDateString(),
				UserData.getUserData().getDailyUserStatistics());
		displayedStats = null;
		updateView();
		statusLabel.setText(StringConstants.SL_TODAY_S_STATISTICS_CLEARED_SUCCESSFULLY);
	}

	@FXML
	public void deleteSelectedStats() {
		if (displayedStats == null)
			statusLabel.setText(StringConstants.SL_NO_ITEM_SELECTED);
		else if (!displayedStats.getCreationDateString().equals(LocalDate.now().toString())) {
			if (StatisticsManager.deleteStatistics(displayedStats.generateFileName())) {
				dateList.remove(displayedStats.getCreationDateString());
				dateStatisticsMap.remove(displayedStats.getCreationDateString());
				taskToggleGroup.selectToggle(intervalsStatsToggle);
				updateToggleButtonsStyles();
				updateView();
				statusLabel.setText(StringConstants.SL_DELETING_STATISTICS_SUCCEEDED);
			} else
				statusLabel.setText(StringConstants.SL_DELETING_STATISTICS_FAILED);
		} else
			statusLabel.setText(StringConstants.SL_NOT_ALOWED_TO_DELETE);
	}

}
