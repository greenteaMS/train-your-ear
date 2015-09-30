/*
 *
 */
package mary.train.your.ear.model.player.strategy;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.response.TunerResponse;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.structures.TunerElement;
import mary.train.your.ear.model.util.StructuresFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerStrategy.
 */
public class TunerStrategy extends PlayStrategy {

	public TunerStrategy() {
		LOG = LogManager.getLogger(TunerStrategy.class);
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.model.player.PlayStrategy#play()
	 */
	@Override
	public TaskResponse play() {
		if (TunerSettings.getSettings().getAvailableRatios().isEmpty())
			return new TunerResponse();
		response = new TunerResponse();
		LinkedList<TunerElement> listOfElements = StructuresFactory.generateTunerElements();
		((TunerResponse) response).setTunerElements(listOfElements);
		return response;
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.model.player.PlayStrategy#repeat()
	 */
	@Override
	public void repeat() {
	}

}
