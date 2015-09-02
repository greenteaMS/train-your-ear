package mary.train.your.ear.model.strategies;

import mary.train.your.ear.model.TaskResponse;

public interface PlayStrategy {
	public TaskResponse play();

	public void repeat();

	public String show();

	public void clear();
}
