package mary.train.your.ear.model;

import java.util.LinkedList;

import org.jfugue.pattern.Pattern;


public class TaskResponse {

	protected LinkedList<Pattern> readyToPlay = new LinkedList<>();
	protected LinkedList<String> intervalNames = new LinkedList<>();
	protected Type type;

	public enum Type {
		ChordResponse, IntervalResponse
	}

	public LinkedList<String> getAnswer() {
		return intervalNames;
	}

	public void setIntervals(LinkedList<String> intervals) {
		this.intervalNames = intervals;
	}

	public LinkedList<Pattern> getReadyToPlay() {
		return readyToPlay;
	}

	public void setReadyToPlay(LinkedList<Pattern> readyToPlay) {
		this.readyToPlay = readyToPlay;
	}

	public TaskResponse() {
	}

	public TaskResponse(LinkedList<Pattern> patterns, LinkedList<String> intervals, Type type) {
		this();
		this.intervalNames = intervals;
		this.readyToPlay = patterns;
		this.type = type;
	}

	public TaskResponse(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

}
