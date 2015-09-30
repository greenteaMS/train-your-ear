package mary.train.your.ear.view.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mary.train.your.ear.model.statistics.AbstractItemStatistics;
import mary.train.your.ear.model.statistics.CustomChordItemStatistics;

public class StatisticsRecord extends VBox {

	@FXML
	private Label elementNameLabel;

	@FXML
	private Label correctAnswerRatio;

	@FXML
	private Label correctTotalNumbers;

	@FXML
	private VBox inversionsStatPane;

	private AbstractItemStatistics statisticsItem;

	public StatisticsRecord() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatisticsRecord.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public StatisticsRecord(AbstractItemStatistics statisticsItem) {
		this();
		this.statisticsItem = statisticsItem;
		fillView();
	}

	private void fillView() {
		elementNameLabel.setText(statisticsItem.getItemName());
		correctTotalNumbers.setText(statisticsItem.getCorrectTotalNumbers());
		correctAnswerRatio.setText(statisticsItem.getCorrectAnswerRatio());
		if (statisticsItem instanceof CustomChordItemStatistics)
			fillInversions((CustomChordItemStatistics) statisticsItem);
	}

	private void fillInversions(CustomChordItemStatistics chordStatisticsItem) {
		chordStatisticsItem.getInversionStatisticsMap().forEach((inversion, stat) -> {
			inversionsStatPane.getChildren().add(new InversionRecord(stat));
		});
	}

	public AbstractItemStatistics getStatisticsItem() {
		return statisticsItem;
	}

	public void setStatisticsItem(AbstractItemStatistics statisticsItem) {
		this.statisticsItem = statisticsItem;
	}
}
