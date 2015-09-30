/*
 * 
 */
package mary.train.your.ear.observers;

import mary.train.your.ear.model.player.response.TaskResponse;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about PlayerPane information as the PlayerPane is constructed.
 */
public interface PlayerPaneObserver extends CustomObserver {

	/**
	 * This method is called when information about an PlayerPane
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param response the response
	 */
	void onPlay(TaskResponse response);
	
	/**
	 * This method is called when information about an PlayerPane
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 */
	void onRepeat();
	
	/**
	 * This method is called when information about an PlayerPane
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 */
	void onShowAnswer();
}
