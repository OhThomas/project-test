/**
 * 
 * hI THERE FG
 */


package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * Main class to run the bank program. Starts at log in and holds the stage, controller, and database that every other class uses.
 * 
 * @author Thomas Rader (vil203)
 */
public class Main extends Application {
	public static Stage primaryStage;
	public static Controller controller = new Controller();
	public static Database database = new Database();
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
