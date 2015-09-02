package mary.train.your.ear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			//load layout from file
			Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));

			//create new scene, set title and css resource file
			Scene scene = new Scene(root,1024,768);
			scene.getStylesheets().add(getClass().getResource("view/resources/application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("TrainYourEar");

			//set Stage boundaries to visible bounds of the main screen
			/*Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.setX(primaryScreenBounds.getMinX());
	        stage.setY(primaryScreenBounds.getMinY());
	        stage.setWidth(primaryScreenBounds.getWidth());
	        stage.setHeight(primaryScreenBounds.getHeight());*/

	        //show window
	        stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
