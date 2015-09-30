package mary.train.your.ear.view.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import mary.train.your.ear.model.statistics.InversionItemStatistics;

public class InversionRecord extends HBox {

	@FXML
	private Label inversionName;

	@FXML
	private Label inversionRatio;

	public InversionRecord() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InversionRecord.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public InversionRecord(InversionItemStatistics stats) {
		this();
		inversionName.setText(stats.getItemName());
		inversionRatio.setText(stats.getCorrectAnswerRatio());
	}
}
