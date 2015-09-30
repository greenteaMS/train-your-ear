/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;

// TODO: Auto-generated Javadoc
/**
 * The Enum Inversion.
 */
@Root
public enum Inversion {

	/** The None. */
	None(StringConstants.INV_NONE, 0), /** The First. */
	First(StringConstants.INV_I, 1), /** The Second. */
	Second(StringConstants.INV_II, 2), /** The Third. */
	Third(StringConstants.INV_III, 3), /** The Fourth. */
	Fourth(StringConstants.INV_IV, 4);

	/** The text. */
	@Element
	private String text;

	/** The index. */
	@Element
	private int index;

	/**
	 * Instantiates a new inversion.
	 *
	 * @param text
	 *            the text
	 * @param index
	 *            the index
	 */
	private Inversion(String text, int index) {
		this.text = text;
		this.index = index;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the by index.
	 *
	 * @param i
	 *            the i
	 * @return the by index
	 */
	public static Inversion getByIndex(int i) {
		for (Inversion inv : values())
			if (inv.getIndex() == i)
				return inv;
		return None;
	}

}
