package mary.train.your.ear.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import mary.train.your.ear.observers.CustomObserver;

public abstract class AbstractController implements Initializable {

	protected LinkedList<CustomObserver> observers = new LinkedList<>();

	abstract public void initialize(URL arg0, ResourceBundle arg1);

	public void addObserver(CustomObserver observer){
		System.out.println(observer);
		observers.add(observer);
		System.out.println("observer to " + getClass() + " added: " + observer.getClass());
	}

}
