package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Menu
{
	private Scene menuScene;
	private Group menuRoot;

	private Button startButton;
	private Button exitButton;
	private Button tutorialButton;
	private Button sortButton;
	private Button autoModeButton;
	private Button hardModeButton;
	private Button saveModeButton;

	private Label gameLabel;
	private Label settingsLabel;
	private Label autoModeLabel;
	private Label hardModeLabel;
	private Label saveModeLabel;

	private static boolean isHardMode;
	private static boolean isAutoMode;
	private static boolean isSaveMode;

	public Menu()
	{
		menuRoot = new Group();
		menuScene = new Scene(menuRoot, 500, 400, Color.LIGHTGREEN);
		menuScene.getStylesheets().add("application/application.css");

		gameLabel = new Label();
		gameLabel.setText("2048");
		gameLabel.getStyleClass().add("gameLabel");
		gameLabel.setLayoutX(225);
		gameLabel.setLayoutY(10);

		settingsLabel = new Label();
		settingsLabel.setText("Settings");
		settingsLabel.getStyleClass().add("settingsLabel");
		settingsLabel.setLayoutX(50);
		settingsLabel.setLayoutY(120);

		hardModeLabel = new Label();
		hardModeLabel.setText("HardCore:");
		hardModeLabel.getStyleClass().add("settingsInfLabel");
		hardModeLabel.setLayoutX(30);
		hardModeLabel.setLayoutY(190);

		autoModeLabel = new Label();
		autoModeLabel.setText("AutoMode:");
		autoModeLabel.getStyleClass().add("settingsInfLabel");
		autoModeLabel.setLayoutX(30);
		autoModeLabel.setLayoutY(250);

		saveModeLabel = new Label();
		saveModeLabel.setText("SaveMode");
		saveModeLabel.getStyleClass().add("settingsInfLabel");
		saveModeLabel.setLayoutX(65);
		saveModeLabel.setLayoutY(300);

		startButton = new Button("Start");
		startButton.getStyleClass().add("menuButton");
		startButton.setLayoutX(230);
		startButton.setLayoutY(150);
		startButton.setPrefSize(200, 50);

		tutorialButton = new Button("How To Play");
		tutorialButton.getStyleClass().add("menuButton");
		tutorialButton.setLayoutX(230);
		tutorialButton.setLayoutY(230);
		tutorialButton.setPrefSize(200, 50);

		exitButton = new Button("Exit");
		exitButton.getStyleClass().add("menuButton");
		exitButton.setLayoutX(230);
		exitButton.setLayoutY(310);
		exitButton.setPrefSize(200, 50);

		sortButton = new Button("Info");
		sortButton.getStyleClass().add("menuButton");
		sortButton.setLayoutX(110);
		sortButton.setLayoutY(330);
		sortButton.setPrefSize(80, 40);

		saveModeButton = new Button("Load");
		saveModeButton.getStyleClass().add("menuButton");
		saveModeButton.setLayoutX(15);
		saveModeButton.setLayoutY(330);
		saveModeButton.setPrefSize(80, 40);

		hardModeButton = new Button();
		hardModeButton.getStyleClass().add("unSelectButton");
		hardModeButton.setLayoutX(130);
		hardModeButton.setLayoutY(180);
		hardModeButton.setPrefSize(30, 30);
		isHardMode = false;

		autoModeButton = new Button();
		autoModeButton.getStyleClass().add("unSelectButton");
		autoModeButton.setLayoutX(130);
		autoModeButton.setLayoutY(240);
		autoModeButton.setPrefSize(30, 30);
		isAutoMode = false;

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				isSaveMode = false;
				GameManager.setScene("gameScene");
			}
		});

		tutorialButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				isSaveMode = false;
				GameManager.setScene("tutorialScene");
			}
		});

		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				GameManager.setScene("questionMenuScene");
			}
		});

		sortButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameManager.setScene("statisticsScene");
			}
		});

		saveModeButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event)
			{
				isSaveMode = true;
				GameManager.setScene("saveModeScene");
			}
		});

		autoModeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (isAutoMode) {
					autoModeButton.getStyleClass().removeAll("selectButton");
					autoModeButton.getStyleClass().add("unSelectButton");
				} else {
					autoModeButton.getStyleClass().removeAll("unSelectButton");
					autoModeButton.getStyleClass().add("selectButton");
				}
				isAutoMode = !isAutoMode;
			}
		});

		hardModeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (isHardMode) {
					hardModeButton.getStyleClass().removeAll("selectButton");
					hardModeButton.getStyleClass().add("unSelectButton");
				} else {
					hardModeButton.getStyleClass().removeAll("unSelectButton");
					hardModeButton.getStyleClass().add("selectButton");
				}
				isHardMode = !isHardMode;
			}
		});

		menuRoot.getChildren().add(gameLabel);
		menuRoot.getChildren().add(settingsLabel);
		menuRoot.getChildren().add(autoModeLabel);
		menuRoot.getChildren().add(hardModeLabel);
		menuRoot.getChildren().add(saveModeLabel);
		menuRoot.getChildren().add(startButton);
		menuRoot.getChildren().add(tutorialButton);
		menuRoot.getChildren().add(autoModeButton);
		menuRoot.getChildren().add(exitButton);
		menuRoot.getChildren().add(sortButton);
		menuRoot.getChildren().add(hardModeButton);
		menuRoot.getChildren().add(saveModeButton);
	}


	public static boolean isItHardMode()
	{
		return isHardMode;
	}

	public static  boolean isItAutoMode()
	{
		return  isAutoMode;
	}

	public static boolean isItSaveMode()
	{
		return isSaveMode;
	}

	public Scene getScene()
	{
		return menuScene;
	}
}
