/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.player.response;

import java.util.LinkedList;

import mary.train.your.ear.model.structures.IntervalComparatorElement;

public class IntervalComparatorResponse extends TaskResponse {

	/** The list of elements. */
	private LinkedList<IntervalComparatorElement> listOfElements;

	/**
	 * Instantiates a new tuner response.
	 */
	public IntervalComparatorResponse() {
		this.type = Type.IntervalComparatResponse;
	}

	public LinkedList<IntervalComparatorElement> getIntervalComparatorList() {
		return listOfElements;
	}

	public void setIntervalComparatorList(LinkedList<IntervalComparatorElement> listOfElements) {
		this.listOfElements = listOfElements;
	}
}
