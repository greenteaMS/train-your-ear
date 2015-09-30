/*
 *
 */
package mary.train.your.ear.model.player.strategy;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.response.TaskResponse.Type;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.StructuresFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class IntervalStrategy.
 */
public class IntervalStrategy extends PlayStrategy {

	public IntervalStrategy(){
		LOG = LogManager.getLogger(IntervalStrategy.class);
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.model.player.PlayStrategy#play()
	 */
	@Override
	public TaskResponse play() {
		if (!IntervalsSettings.getSettings().checkAvailability())
			return new TaskResponse(Type.IntervalResponse);
		response = new TaskResponse(Type.IntervalResponse);
		LinkedList<Interval> structures = StructuresFactory.createIntervals();
		LinkedList<Interval> intervals = new LinkedList<>();
		structures.forEach(struct -> {
			response.getAnswer().add(struct.getName());
			intervals.add(struct);
		});
		response.setReadyToPlay(PatternCreator.createPatterns(structures));
		response.setIntervals(intervals);
		doPlay();

		return response;
	}

}
