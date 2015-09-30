/*
 *
 */
package mary.train.your.ear.controller.tasks;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.response.TunerResponse;
import mary.train.your.ear.model.structures.TunerElement;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.view.ui.TunerWithSlider;
import javafx.scene.control.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerTaskPaneController.
 */
public class TunerTaskPaneController extends AbstractController implements PlayerPaneObserver {

	/** The tuner task pane. */
	@FXML
	private VBox tunerTaskPane;

	/** The elements. */
	private LinkedList<TunerElement> elements;

	/** The tuners. */
	protected ObservableList<TunerWithSlider> tuners = FXCollections.observableArrayList();

	/** The sliders list. */
	@FXML
	private TilePane slidersList;

	@FXML
	private Label taskInfoLabel;

	/**
	 * Instantiates a new tuner task pane controller.
	 */
	public TunerTaskPaneController() {
		LOG = LogManager.getLogger(getClass());
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.controller.AbstractController#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInit(getClass());
		taskInfoLabel.setText(StringConstants.TI_TUNER);
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onPlay(mary.train.your.ear.model.player.TaskResponse)
	 */
	@Override
	public void onPlay(TaskResponse response) {
		slidersList.getChildren().clear();
		tuners.clear();
		if (!(response instanceof TunerResponse))
			return;
		elements = ((TunerResponse) response).getTunerElements();
		elements.forEach(element -> {
			TunerWithSlider slider = new TunerWithSlider(element);
			tuners.add(slider);
		});
		slidersList.getChildren().addAll(tuners);
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onRepeat()
	 */
	@Override
	public void onRepeat() {
		// this method must be empty
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onShowAnswer()
	 */
	@Override
	public void onShowAnswer() {
		// this method must be empty

	}

	public void showTaskInfo() {
		taskInfoLabel.setVisible(!taskInfoLabel.isVisible());
	}

}
