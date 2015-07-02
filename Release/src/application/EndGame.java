package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class EndGame
{
    private Button okButton;

    private Label questionLabel;

    private Group EndGameRoot;
    private Scene EndGameScene;

    public EndGame()
    {
        EndGameRoot = new Group();
        EndGameScene = new Scene(EndGameRoot, 400, 200, Color.LIGHTGREEN);
        EndGameScene.getStylesheets().add("application/application.css");

        questionLabel = new Label();
        questionLabel.setText("You lose!");
        questionLabel.getStyleClass().add("questionLabel");
        questionLabel.setLayoutX(100);
        questionLabel.setLayoutY(20);

        okButton = new Button("Back to menu");
        okButton.getStyleClass().add("menuButton");
        okButton.setLayoutX(130);
        okButton.setLayoutY(120);
        okButton.setPrefSize(150, 50);

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GameManager.setScene("menuScene");
            }
        });

        EndGameRoot.getChildren().add(questionLabel);
        EndGameRoot.getChildren().add(okButton);
    }

    public Scene getScene()
    {
        return EndGameScene;
    }
}
