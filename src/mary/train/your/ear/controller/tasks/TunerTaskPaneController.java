package mary.train.your.ear.controller.tasks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.layout.VBox;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.view.ui.TunerWithSlider;

public class TunerTaskPaneController extends AbstractController {
	@FXML
	private VBox tunerTaskPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(getClass() + " controller initialized");
		TunerWithSlider tuner = new TunerWithSlider();
		tunerTaskPane.getChildren().add(tuner);
	}

}
