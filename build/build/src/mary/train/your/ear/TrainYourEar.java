package mary.train.your.ear;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mary.train.your.ear.model.StatisticsManager;

public class TrainYourEar extends Application {

	private static final Logger LOG = LogManager.getLogger(TrainYourEar.class);


	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) {
		LOG.info("starting...");
		try {
			// load layout from file

			ResourceBundle bundle = ResourceBundle.getBundle("UIResources");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Main.fxml"));

			loader.setResources(bundle);
			Parent root = loader.load();

			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			if (bounds.getHeight() < 768 && bounds.getWidth() < 1366)
				stage.setMaximized(true);
			else {
				stage.setWidth(1366);
				stage.setHeight(768);
			}

			// create new scene, set title and css resource file
			Scene scene = new Scene(root, 1366, 768);
			scene.getStylesheets().add(getClass().getResource("view/resources/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("view/resources/listview.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Train Your Ear");
			/*Image icon = new Image(TrainYourEar.class.getResourceAsStream("../../../../images/icon.jpg"));
			LOG.info(icon);
			stage.getIcons().add(icon);*/
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					StatisticsManager.deleteAllStatisticsForUser(StringConstants.GUEST);

				}
			});

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LOG.info("launching...");
		launch(args);
	}
}
