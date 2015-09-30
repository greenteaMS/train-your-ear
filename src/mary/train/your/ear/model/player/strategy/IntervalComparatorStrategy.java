/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.player.strategy;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import mary.train.your.ear.model.player.response.IntervalComparatorResponse;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.structures.IntervalComparatorElement;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.StructuresFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerStrategy.
 */
public class IntervalComparatorStrategy extends PlayStrategy {

	public IntervalComparatorStrategy() {
		LOG = LogManager.getLogger(IntervalComparatorStrategy.class);
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.model.player.PlayStrategy#play()
	 */
	@Override
	public TaskResponse play() {
		LOG.info("IntervalComparatorStrategy");
		if (IntervalComparatorSettings.getSettings().getAvailableRatios().isEmpty())
			return new IntervalComparatorResponse();
		response = new IntervalComparatorResponse();
		LinkedList<IntervalComparatorElement> listOfElements = StructuresFactory.generateIntervalComparatorElements();
		PatternCreator.createPatternsForComparator(listOfElements);
		((IntervalComparatorResponse) response).setIntervalComparatorList(listOfElements);
		return response;
	}

	/* (non-Javadoc)
	 * @see mary.train.your.ear.model.player.PlayStrategy#repeat()
	 */
	@Override
	public void repeat() {
	}

}
