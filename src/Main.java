package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application 
{
	public void start(Stage primaryStage){
		GameManager gameManager = new GameManager(primaryStage);
		gameManager.run(primaryStage);
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
