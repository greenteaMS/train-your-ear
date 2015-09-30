/*
 * 
 */
package mary.train.your.ear.controller.tasks;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerRunner.
 */
public class TunerRunner implements Runnable {

	/** The done. */
	private volatile boolean done = false;
	
	/** The pattern. */
	private volatile Pattern pattern;
	
	/** The running. */
	boolean running = false;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (pattern != null) {
			running = true;
			Player player = new Player();
			done = false;
			while (!done) {
				player.play(pattern);
			}
		}

	}

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * Sets the pattern.
	 *
	 * @param pattern the new pattern
	 */
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;

	}

	/**
	 * Stop player.
	 */
	public void stopPlayer() {
		done = true;
		running = false;
	}

}
