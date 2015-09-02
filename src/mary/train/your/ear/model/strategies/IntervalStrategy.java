package mary.train.your.ear.model.strategies;

import java.util.LinkedList;

import org.jfugue.player.Player;

import mary.train.your.ear.model.TaskResponse;
import mary.train.your.ear.model.TaskResponse.Type;
import mary.train.your.ear.model.settings.GeneralSettings;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.StructuresFactory;

public class IntervalStrategy implements PlayStrategy {

	TaskResponse response;

	@Override
	public TaskResponse play() {
		System.out.println(IntervalsSettings.getSettings().getIntervalsAvailability());
		if (!IntervalsSettings.getSettings().checkAvailability())
			return null;
		response = new TaskResponse(Type.IntervalResponse);
		LinkedList<Interval[]> structures = StructuresFactory.createIntervals();
		structures.forEach(struct -> response.getAnswer().add(struct[0].getName()));
		response.setReadyToPlay(PatternCreator.createPatterns(structures));

		response.getReadyToPlay().forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			System.out.println(pattern);
			try {
				Thread.sleep(GeneralSettings.getInstance().getPauseLength());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return response;
	}

	@Override
	public void repeat() {
		if (response == null)
			return;
		response.getReadyToPlay().forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			System.out.println(pattern);
		});
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		response = null;
	}

}
