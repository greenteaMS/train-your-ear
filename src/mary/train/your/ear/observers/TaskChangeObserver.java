/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.observers;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about TaskChange information as the TaskChange is constructed.
 */
public interface TaskChangeObserver extends CustomObserver {

	/**
	 * This method is called when information about an TaskChange
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param changeText the change text
	 */
	void onTaskChange(boolean changeText);

}
