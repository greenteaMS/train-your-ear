package mary.train.your.ear.model.statistics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.Inversion;

@Root
public class InversionItemStatistics extends AbstractItemStatistics {

	@Element
	private Inversion inversion;

	public InversionItemStatistics() {
		super();
	}

	public InversionItemStatistics(Inversion inversion) {
		super(inversion.getText());
		this.setInversion(inversion);
	}

	public Inversion getInversion() {
		return inversion;
	}

	public void setInversion(Inversion inversion) {
		this.inversion = inversion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inversion == null) ? 0 : inversion.hashCode());
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
		InversionItemStatistics other = (InversionItemStatistics) obj;
		if (inversion != other.inversion)
			return false;
		return true;
	}

}
