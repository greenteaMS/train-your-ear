package mary.train.your.ear.model.statistics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.StringConstants;

@Root
public abstract class AbstractItemStatistics {


	@Element
	protected String itemName;
	@Element
	protected int correctAnswersCounter;
	@Element
	protected int wrongAnswersCounter;

	public AbstractItemStatistics() {
		correctAnswersCounter = 0;
		wrongAnswersCounter = 0;
	}

	public AbstractItemStatistics(String name) {
		super();
		itemName = name;

	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + correctAnswersCounter;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + wrongAnswersCounter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractItemStatistics other = (AbstractItemStatistics) obj;
		if (correctAnswersCounter != other.correctAnswersCounter)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (wrongAnswersCounter != other.wrongAnswersCounter)
			return false;
		return true;
	}

	public int getCorrectAnswersCounter() {
		return correctAnswersCounter;
	}

	public void setCorrectAnswersCounter(int correctAnswersCounter) {
		this.correctAnswersCounter = correctAnswersCounter;
	}

	public int getWrongAnswersCounter() {
		return wrongAnswersCounter;
	}

	public void setWrongAnswersCounter(int wrongAnswersCounter) {
		this.wrongAnswersCounter = wrongAnswersCounter;
	}

	public void updateCounters(boolean correctAnswer) {
		if (correctAnswer)
			correctAnswersCounter++;
		else
			wrongAnswersCounter++;
	}

	public String getCorrectAnswerRatio() {
		if (correctAnswersCounter + wrongAnswersCounter == 0)
			return StringConstants.PERCENTAGE_0;
		return Math.round(((double)correctAnswersCounter / (double)(correctAnswersCounter + wrongAnswersCounter)) * 100) + StringConstants.PERCENTAGE;
	}

	public String getCorrectTotalNumbers() {
		return correctAnswersCounter + StringConstants.SLASH + (correctAnswersCounter + wrongAnswersCounter);
	}

}
