/*
 *
 */
package mary.train.your.ear.model.player.strategy;

import org.apache.logging.log4j.Logger;
import org.jfugue.player.Player;

import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.settings.GeneralTaskSettings;

// TODO: Auto-generated Javadoc
/**
 * The Interface PlayStrategy.
 */
public abstract class PlayStrategy {

	protected TaskResponse response;
	protected Logger LOG;

	/**
	 * Play.
	 *
	 * @return the task response
	 */
	public abstract TaskResponse play();

	/**
	 * Repeat.
	 */
	public void repeat() {
		if (response == null)
			return;
		doPlay();
	}

	/**
	 * Clear.
	 */
	public void clear() {
		response = null;
	}

	int counter;

	protected void doPlay() {
		counter = 0;
		response.getReadyToPlay().forEach(pattern -> {
			counter++;
			Player player = new Player();
			player.play(pattern);
			LOG.debug("play pattern " + pattern);
			if (counter != response.getReadyToPlay().size())
				try {
					Thread.sleep(GeneralTaskSettings.getSettings().getPauseLength());
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
}
