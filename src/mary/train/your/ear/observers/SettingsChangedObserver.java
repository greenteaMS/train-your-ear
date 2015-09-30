/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.observers;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about SettingsChanged information as the SettingsChanged is constructed.
 */
public interface SettingsChangedObserver extends CustomObserver {

	/**
	 * This method is called when information about an SettingsChanged
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 */
	void onSettingsChanged();
}
