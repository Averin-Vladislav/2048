package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Tutorial
{
    private Scene tutorialScene;
    private Group tutorialRoot;

    private Button backButton;
    private Button[] tutorialButtons;
    private Button playButton;

    private ImageView imageView;
    private Image image;

    private Label tutorialLabel;

    public Tutorial()
    {
        tutorialRoot = new Group();
        tutorialScene = new Scene(tutorialRoot, 500, 400, Color.PALEGOLDENROD);
        tutorialScene.getStylesheets().add("application/application.css");

        backButton = new Button("Back");
        backButton.getStyleClass().add("menuButton");
        backButton.setLayoutX(400);
        backButton.setLayoutY(350);

        tutorialButtons = new Button [3];

        playButton = new Button("Let's Play!");
        playButton.getStyleClass().add("menuButton");
        playButton.setLayoutX(123);
        playButton.setLayoutY(300);
        playButton.setVisible(false);

        imageView = new ImageView();
        imageView.setLayoutX(50);
        imageView.setLayoutY(85);
        image = new Image(Main.class.getResourceAsStream("tutorial1.png"));
        imageView.setImage(image);

        tutorialLabel = new Label("SWIPE TO MOVE ALL TILES");
        tutorialLabel.getStyleClass().add("tutorialLabel");
        tutorialLabel.setLayoutX(80);
        tutorialLabel.setLayoutY(40);

        for (int i = 0; i < 3; i++)
        {
            tutorialButtons[i] = new Button(Integer.toString(i + 1));
            tutorialButtons[i].getStyleClass().add("menuButton");
            tutorialButtons[i].setLayoutX(410);
            tutorialButtons[i].setLayoutY(50 + i * 100);
            tutorialRoot.getChildren().add(tutorialButtons[i]);
        }

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GameManager.setScene("menuScene");
            }
        });

        tutorialButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Image image = new Image(Main.class.getResourceAsStream("tutorial1.png"));
                imageView.setLayoutX(50);
                imageView.setLayoutY(85);
                imageView.setImage(image);
                tutorialLabel.setText("SWIPE TO MOVE ALL TILES");
                tutorialLabel.setLayoutX(80);
                tutorialLabel.setLayoutY(40);
                playButton.setVisible(false);
            }
        });

        tutorialButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Image image = new Image(Main.class.getResourceAsStream("tutorial2.png"));
                imageView.setLayoutX(50);
                imageView.setLayoutY(85);
                imageView.setImage(image);
                tutorialLabel.setText("      WHEN TWO TILES WITH THE SAME\nNUMBER TOUCH, THEY MERGE INTO ONE");
                tutorialLabel.setLayoutX(30);
                tutorialLabel.setLayoutY(20);
                playButton.setVisible(false);
            }
        });

        tutorialButtons[2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Image image = new Image(Main.class.getResourceAsStream("tutorial3.png"));
                imageView.setLayoutX(100);
                imageView.setLayoutY(135);
                imageView.setImage(image);
                tutorialLabel.setText("JOIN THE NUMBERS AND GET TO THE\n                         2048 TILE!");
                tutorialLabel.setLayoutX(50);
                tutorialLabel.setLayoutY(50);

                playButton.setVisible(true);
                playButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        GameManager.setScene("gameScene");
                    }
                });
            }
        });

        tutorialRoot.getChildren().add(backButton);
        tutorialRoot.getChildren().add(imageView);
        tutorialRoot.getChildren().add(tutorialLabel);
        tutorialRoot.getChildren().add(playButton);
    }

    public Scene getScene()
    {
        return tutorialScene;
    }
}