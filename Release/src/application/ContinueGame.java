package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ContinueGame
{
    private Button yesButton;
    private Button noButton;

    private Label questionLabel;

    private Group ContinueGameRoot;
    private Scene ContinueGameScene;

    public ContinueGame()
    {
        ContinueGameRoot = new Group();
        ContinueGameScene = new Scene(ContinueGameRoot, 500, 300, Color.LIGHTGREEN);
        ContinueGameScene.getStylesheets().add("application/application.css");

        questionLabel = new Label();
        questionLabel.setText("  You win!\nContinue?");
        questionLabel.getStyleClass().add("questionLabel");
        questionLabel.setLayoutX(130);
        questionLabel.setLayoutY(30);

        yesButton = new Button("Yes");
        yesButton.getStyleClass().add("menuButton");
        yesButton.setLayoutX(100);
        yesButton.setLayoutY(200);
        yesButton.setPrefSize(100, 50);

        noButton = new Button("No");
        noButton.getStyleClass().add("menuButton");
        noButton.setLayoutX(300);
        noButton.setLayoutY(200);
        noButton.setPrefSize(100, 50);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GameManager.setScene("gameScene");
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GameManager.setScene("menuScene");
            }
        });

        ContinueGameRoot.getChildren().add(questionLabel);
        ContinueGameRoot.getChildren().add(yesButton);
        ContinueGameRoot.getChildren().add(noButton);
    }

    public Scene getScene()
    {
        return ContinueGameScene;
    }
}
