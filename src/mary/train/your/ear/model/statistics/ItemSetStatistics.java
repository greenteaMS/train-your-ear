/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.statistics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;

@Root
public class ItemSetStatistics extends AbstractItemStatistics {


	@Element
	private int setSize;

	public ItemSetStatistics() {
		super();
	}

	public ItemSetStatistics(int size) {
		super(size + StringConstants.ITEMS_IN_SET);
		this.setSize = size;
	}

	public int getSetSize() {
		return setSize;
	}

	public void setSetSize(int setSize) {
		this.setSize = setSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + setSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSetStatistics other = (ItemSetStatistics) obj;
		if (setSize != other.setSize)
			return false;
		return true;
	}



}
