/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller.tasks;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import mary.train.your.ear.StringConstants;
import mary.train.your.ear.controller.AbstractController;
import mary.train.your.ear.model.player.response.IntervalComparatorResponse;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.structures.IntervalComparatorElement;
import mary.train.your.ear.observers.PlayerPaneObserver;
import mary.train.your.ear.view.ui.ComparatorViewElement;

public class IntervalComparatorTaskPaneController extends AbstractController implements PlayerPaneObserver {

	@FXML
	private TilePane slidersList;

	LinkedList<IntervalComparatorElement> elements;


	protected ObservableList<ComparatorViewElement> comparators = FXCollections.observableArrayList();

	@FXML Label taskInfoLabel;

	public IntervalComparatorTaskPaneController() {
		LOG = LogManager.getLogger(getClass());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInit(getClass());
		taskInfoLabel.setText(StringConstants.TI_COMPARATOR);
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.observers.PlayerPaneObserver#onPlay(mary.train.your.ear.model.player.TaskResponse)
	 */
	@Override
	public void onPlay(TaskResponse response) {
		LOG.info("onPlay " + response.getClass());
		slidersList.getChildren().clear();
		comparators.clear();
		if (!(response instanceof IntervalComparatorResponse))
			return;
		elements = ((IntervalComparatorResponse) response).getIntervalComparatorList();
		elements.forEach(element -> {
			ComparatorViewElement slider = new ComparatorViewElement(element);
			comparators.add(slider);
		});
		slidersList.getChildren().addAll(comparators);
	}

	@Override
	public void onRepeat() {
		// Should be empty

	}

	@Override
	public void onShowAnswer() {
		// Should be empty

	}

	public void showTaskInfo() {
		taskInfoLabel.setVisible(!taskInfoLabel.isVisible());
	}

}
