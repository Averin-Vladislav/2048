package application;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class GameManager
{
    private static Menu             menu;
    private static Game             game;
    private static QuestionMenu     questionMenu;
    private static QuestionGame     questionGame;
    private static Tutorial         tutorial;
    private static ContinueGame     continueGame;
    private static Info info;
    private static EndGame          endGame;

    private static Stage primaryStage;
    public static File file;

    public GameManager(Stage primaryStage){
        menu = new Menu();
        game = new Game();
        questionMenu = new QuestionMenu(primaryStage);
        questionGame = new QuestionGame();
        tutorial = new Tutorial();
        info = new Info(primaryStage);
        continueGame = new ContinueGame();
        endGame = new EndGame();

        GameManager.primaryStage = primaryStage;
    }

    public void run(Stage primaryStage) {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        primaryStage.setScene(menu.getScene());
        primaryStage.show();
    }

    public static void setScene(String scene) {
        switch(scene) {
            case "menuScene" :
                primaryStage.setScene(menu.getScene());
                break;

            case "gameScene" :
                primaryStage.setScene(game.getScene());
                game.run(null);
                break;

            case "statisticsScene":
                primaryStage.setScene(info.getScene());
                break;

            case "saveModeScene" :
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Game Session File");
                file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    primaryStage.setScene(game.getScene());
                    game.run(file);
                }
            else
                primaryStage.setScene(menu.getScene());
            break;

            case "tutorialScene" :
                primaryStage.setScene(tutorial.getScene());
                break;

            case "questionGameScene" :
                primaryStage.setScene(questionGame.getScene());
                break;

            case "questionMenuScene" :
                primaryStage.setScene(questionMenu.getScene());
                break;

            case "continueGameScene" :
                primaryStage.setScene(continueGame.getScene());
                break;

            case "endGameScene" :
                primaryStage.setScene(endGame.getScene());
                break;
        }
    }
}
