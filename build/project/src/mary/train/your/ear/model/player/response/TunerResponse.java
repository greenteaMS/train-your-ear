/*
 *
 */
package mary.train.your.ear.model.player.response;

import java.util.LinkedList;

import mary.train.your.ear.model.structures.TunerElement;

// TODO: Auto-generated Javadoc
/**
 * The Class TunerResponse.
 */
public class TunerResponse extends TaskResponse {

	/** The list of elements. */
	private LinkedList<TunerElement> listOfElements;

	/**
	 * Instantiates a new tuner response.
	 */
	public TunerResponse() {
		this.type = Type.TunerResponse;
		listOfElements = new LinkedList<>();
	}

	/**
	 * Sets the tuner elements.
	 *
	 * @param listOfElements the new tuner elements
	 */
	public void setTunerElements(LinkedList<TunerElement> listOfElements) {
		this.listOfElements = listOfElements;
	}

	/**
	 * Gets the tuner elements.
	 *
	 * @return the tuner elements
	 */
	public LinkedList<TunerElement> getTunerElements() {
		return listOfElements;
	}

}
