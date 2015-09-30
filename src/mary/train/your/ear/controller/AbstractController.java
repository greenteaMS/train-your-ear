/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import mary.train.your.ear.observers.CustomObserver;
import mary.train.your.ear.observers.SettingsChangedObserver;

public abstract class AbstractController implements Initializable {

	protected LinkedList<CustomObserver> observers = new LinkedList<>();
	protected Logger LOG;

	abstract public void initialize(URL arg0, ResourceBundle arg1);

	public void addObserver(CustomObserver observer) {
		observers.add(observer);
	}

	protected void checkRedStyle(ToggleButton button) {
		if (button.isSelected()) {
			while (button.getStyleClass().contains("intervalToggleRed"))
				button.getStyleClass().remove("intervalToggleRed");
		} else if (!button.getStyleClass().contains("intervalToggleRed"))
			button.getStyleClass().add("intervalToggleRed");
	}

	protected void notifySettingsObservers() {
		observers.stream().filter(p -> p instanceof SettingsChangedObserver)
				.forEach(obs -> ((SettingsChangedObserver) obs).onSettingsChanged());
	}

	protected void logInit(Class<?> c) {
		LOG.info("Initialized " + c);
	}

	protected void logInitializing(Class<?> c) {
		LOG.info("Initializing..." + c);
	}

}
