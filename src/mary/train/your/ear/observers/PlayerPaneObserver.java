package mary.train.your.ear.observers;

import mary.train.your.ear.model.TaskResponse;

public interface PlayerPaneObserver extends CustomObserver {

	void onPlay(TaskResponse response);
	void onRepeat();
	void onShowAnswer();
}
