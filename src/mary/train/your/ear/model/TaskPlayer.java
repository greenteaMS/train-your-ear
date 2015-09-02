package mary.train.your.ear.model;

import java.util.HashMap;
import java.util.LinkedList;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import mary.train.your.ear.model.strategies.ChordStrategy;
import mary.train.your.ear.model.strategies.IntervalStrategy;
import mary.train.your.ear.model.strategies.PlayStrategy;
import mary.train.your.ear.model.structures.Interval;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.PatternToAbcNotationParser;

public class TaskPlayer {

	PlayStrategy playStrategy;
	StrategyFactory strategyFactory;

	private static TaskPlayer instance;
	private boolean autoPlay;

	public static TaskPlayer getInstance() {
		if (instance == null)
			instance = new TaskPlayer();
		return instance;
	}

	private TaskPlayer() {
		strategyFactory = new StrategyFactory();
		setStrategy("Intervals");
		autoPlay = false;
	}

	public void setStrategy(String strategy) {
		//System.out.println("taskPlayer --- setStrategy: " + strategy);
		playStrategy = strategyFactory.get(strategy);
		playStrategy.clear();
	}

	// for tests only
	public PlayStrategy getPlayStrategy() {
		return playStrategy;
	}

	public TaskResponse play() {
		return playStrategy.play();
	}

	public void repeat() {
		playStrategy.repeat();
	}

	public String show() {
		return playStrategy.show();
	}

	public String tryNewChord(LinkedList<Interval> structure) {
		LinkedList<Pattern> patterns = PatternCreator.createPatternForNewChord(structure);
		patterns.forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			System.out.println("*** pattern " + pattern);
		});
		return PatternToAbcNotationParser.parsePatternsList(patterns);
	}

	private class StrategyFactory {

		HashMap<String, PlayStrategy> map;

		public StrategyFactory() {
			map = new HashMap<String, PlayStrategy>();
			map.put("Chord", new ChordStrategy());
			map.put("Intervals", new IntervalStrategy());
		}

		public PlayStrategy get(String strategy) {
			return map.get(strategy);
		}

	}

	public void autoPlay(boolean okAnswer) {
		if (autoPlay)
			if (okAnswer)
				play();
			else
				repeat();
	}

	public boolean isAutoPlay() {
		return autoPlay;
	}

	public void setAutoPlay(boolean autoPlay) {
		//System.out.println("setAutoPlay" + autoPlay);
		this.autoPlay = autoPlay;
	}

	public static void main(String[] args) {
		TaskPlayer.getInstance().setStrategy("Interval");
		TaskResponse response = TaskPlayer.getInstance().play();
		System.out.println(response);
		TaskPlayer.getInstance().repeat();
	}
}
