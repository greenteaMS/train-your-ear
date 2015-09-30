/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.observers;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about UserChange information as the UserChange is constructed.
 */
public interface UserChangeObserver extends CustomObserver {

	/**
	 * This method is called when information about an UserChange
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 */
	void onUserChange();

}
