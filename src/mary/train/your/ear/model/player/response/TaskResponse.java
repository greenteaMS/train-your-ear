/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.player.response;

import java.util.LinkedList;

import org.jfugue.pattern.Pattern;

import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.Interval;


// TODO: Auto-generated Javadoc
/**
 * The Class TaskResponse.
 */
public class TaskResponse {

	/** The ready to play. */
	protected LinkedList<Pattern> readyToPlay = new LinkedList<>();

	/** The interval names. */
	protected LinkedList<String> itemNames = new LinkedList<>();

	/** The type. */
	protected Type type;

	private LinkedList<CustomChord> chords;

	private LinkedList<Interval> intervals;
	/**
	 * The Enum Type.
	 */
	public enum Type {

		/** The Chord response. */
		ChordResponse,
 /** The Interval response. */
 IntervalResponse,
 /** The Tuner response. */
		TunerResponse, IntervalComparatResponse
	}

	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public LinkedList<String> getAnswer() {
		return itemNames;
	}

	/**
	 * Sets the intervals.
	 *
	 * @param items the new intervals
	 */
	public void setItemNames(LinkedList<String> items) {
		this.itemNames = items;
	}

	/**
	 * Gets the ready to play.
	 *
	 * @return the ready to play
	 */
	public LinkedList<Pattern> getReadyToPlay() {
		return readyToPlay;
	}

	/**
	 * Sets the ready to play.
	 *
	 * @param readyToPlay the new ready to play
	 */
	public void setReadyToPlay(LinkedList<Pattern> readyToPlay) {
		this.readyToPlay = readyToPlay;
	}

	/**
	 * Instantiates a new task response.
	 */
	public TaskResponse() {
	}

	/**
	 * Instantiates a new task response.
	 *
	 * @param patterns the patterns
	 * @param intervals the intervals
	 * @param type the type
	 */
	public TaskResponse(LinkedList<Pattern> patterns, LinkedList<String> intervals, Type type) {
		this();
		this.itemNames = intervals;
		this.readyToPlay = patterns;
		this.type = type;
	}

	/**
	 * Instantiates a new task response.
	 *
	 * @param type the type
	 */
	public TaskResponse(Type type) {
		this.type = type;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	public void setChords(LinkedList<CustomChord> structures) {
		chords = structures;
	}

	public LinkedList<CustomChord> getChords(){
		return chords;
	}

	public LinkedList<Interval> getIntervals() {
		return intervals;
	}

	public void setIntervals(LinkedList<Interval> intervals) {
		this.intervals = intervals;
	}

}
