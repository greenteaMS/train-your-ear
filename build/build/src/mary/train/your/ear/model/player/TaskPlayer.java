/*
 *
 */
package mary.train.your.ear.model.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.strategy.ChordStrategy;
import mary.train.your.ear.model.player.strategy.IntervalComparatorStrategy;
import mary.train.your.ear.model.player.strategy.IntervalStrategy;
import mary.train.your.ear.model.player.strategy.PlayStrategy;
import mary.train.your.ear.model.player.strategy.TunerStrategy;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.PatternToAbcNotationParser;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskPlayer.
 */
public class TaskPlayer {

	/** The play strategy. */
	PlayStrategy playStrategy;

	/** The strategy factory. */
	StrategyFactory strategyFactory;

	/** The instance. */
	private static TaskPlayer instance;

	/** The auto play. */
	private boolean autoPlay;

	/**
	 * Gets the single instance of TaskPlayer.
	 *
	 * @return single instance of TaskPlayer
	 */
	public static TaskPlayer getInstance() {
		if (instance == null)
			instance = new TaskPlayer();
		return instance;
	}

	/**
	 * Instantiates a new task player.
	 */
	private TaskPlayer() {
		strategyFactory = new StrategyFactory();
		setStrategy(StringConstants.TASK_INTERVALS);
		autoPlay = false;
	}

	/**
	 * Sets the strategy.
	 *
	 * @param strategy
	 *            the new strategy
	 */
	public void setStrategy(String strategy) {
		playStrategy = strategyFactory.get(strategy);
		playStrategy.clear();
	}

	/**
	 * Gets the play strategy.
	 *
	 * @return the play strategy
	 */
	// for tests only
	public PlayStrategy getPlayStrategy() {
		return playStrategy;
	}

	/**
	 * Play.
	 *
	 * @return the task response
	 */
	public TaskResponse play() {
		return playStrategy.play();
	}

	/**
	 * Repeat.
	 */
	public void repeat() {
		playStrategy.repeat();
	}

	/**
	 * Try new chord.
	 *
	 * @param structure
	 *            the structure
	 * @return the string
	 */
	public String tryNewChord(List<Interval> structure) {
		LinkedList<Pattern> patterns = PatternCreator.createPatternForNewChord(structure);
		patterns.forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			LOG.info("*** tryNewChord - pattern " + pattern);
		});
		return PatternToAbcNotationParser.parsePatternsList(patterns);
	}

	/**
	 * A factory for creating Strategy objects.
	 */
	private class StrategyFactory {

		/** The map. */
		HashMap<String, PlayStrategy> map;

		/**
		 * Instantiates a new strategy factory.
		 */
		public StrategyFactory() {
			map = new HashMap<String, PlayStrategy>();
			map.put(StringConstants.TASK_CHORD, new ChordStrategy());
			map.put(StringConstants.TASK_INTERVALS, new IntervalStrategy());
			map.put(StringConstants.TASK_TUNER, new TunerStrategy());
			map.put(StringConstants.TASK_INTERVAL_COMPARATOR, new IntervalComparatorStrategy());
		}

		/**
		 * Gets the.
		 *
		 * @param strategy
		 *            the strategy
		 * @return the play strategy
		 */
		public PlayStrategy get(String strategy) {
			return map.get(strategy);
		}

	}

	/**
	 * Auto play.
	 *
	 * @param okAnswer
	 *            the ok answer
	 */
	public void autoPlay(boolean okAnswer) {
		if (autoPlay)
			if (okAnswer)
				play();
			else
				repeat();
	}

	/**
	 * Checks if is auto play.
	 *
	 * @return true, if is auto play
	 */
	public boolean isAutoPlay() {
		return autoPlay;
	}

	/**
	 * Sets the auto play.
	 *
	 * @param autoPlay
	 *            the new auto play
	 */
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay = autoPlay;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		TaskPlayer.getInstance().setStrategy(StringConstants.TASK_INTERVALS);
		TaskResponse response = TaskPlayer.getInstance().play();
		TaskPlayer.getInstance().repeat();
	}
}
