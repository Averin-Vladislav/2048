package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class QuestionMenu
{
	private Button yesButton;
	private Button noButton;

	private Label questionLabel;

	private Group questionRoot;
	private Scene questionScene;

	public QuestionMenu(Stage primaryStage)
	{
		questionRoot = new Group();
		questionScene = new Scene(questionRoot, 500, 400, Color.LIGHTGREEN);
		questionScene.getStylesheets().add("application/application.css");

		yesButton = new Button("Yes");
		yesButton.getStyleClass().add("menuButton");
		yesButton.setLayoutX(60);
		yesButton.setLayoutY(200);
		yesButton.setPrefSize(170, 50);

		noButton = new Button("No");
		noButton.getStyleClass().add("menuButton");
		noButton.setLayoutX(265);
		noButton.setLayoutY(200);
		noButton.setPrefSize(170, 50);

		questionLabel = new Label();
		questionLabel.setText("Are you sure?");
		questionLabel.getStyleClass().add("questionLabel");
		questionLabel.setLayoutX(90);
		questionLabel.setLayoutY(100);

		yesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

		noButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameManager.setScene("menuScene");
			}
		});

		questionRoot.getChildren().add(questionLabel);
		questionRoot.getChildren().add(yesButton);
		questionRoot.getChildren().add(noButton);
	}

	public Scene getScene()
	{
		return questionScene;
	}
}
