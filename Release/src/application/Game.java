package application;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Game {
	private static Scene gameScene;
	private static Group gameRoot;
	private static Random random;
	private File file;

	private Button backButton;

	private Label scoreLabel;
	private Label bestLabel;
	private Label notationLabel;
	private static Label currentResultLabel;
	private static Label bestResultLabel;
	private static Label currentNotationLabel;

	private static Button[][] quads;
	private static Plate[][] plates;

	private int nextValue;
	private int position;
	private int nClearPlates;
	private static int totalScore;
	private static int bestScore;
	private static boolean isWin;

	private int positionX;
	private int positionY;
	private int value;

	public FileOutputStream fileOutputStream;
	public FileInputStream fileInputStream;
	public DataOutputStream out;
	public DataInputStream in;
	public FileThread fileThread;

	public Game(){
		gameRoot = new Group();
		gameScene = new Scene(gameRoot, 500, 400, Color.LIGHTGREEN);
		random = new Random();
		gameScene.getStylesheets().add("application/application.css");

		backButton = new Button("Back");
		backButton.getStyleClass().add("menuButton");
		backButton.setLayoutX(400);
		backButton.setLayoutY(350);

		scoreLabel = new Label("Score");
		scoreLabel.getStyleClass().add("gameScore");
		scoreLabel.setPrefSize(150, 50);
		scoreLabel.setLayoutX(370);
		scoreLabel.setLayoutY(50);

		bestLabel = new Label("Best");
		bestLabel.getStyleClass().add("gameScore");
		bestLabel.setPrefSize(150, 150);
		bestLabel.setLayoutX(370);
		bestLabel.setLayoutY(80);

		notationLabel = new Label("D N  X  Y");
		notationLabel.getStyleClass().add("gameScore");
		notationLabel.setPrefSize(150, 150);
		notationLabel.setLayoutX(370);
		notationLabel.setLayoutY(160);

		currentResultLabel = new Label(Integer.toString(totalScore));
		currentResultLabel.getStyleClass().add("currentScore");
		currentResultLabel.setLayoutX(370);
		currentResultLabel.setLayoutY(87);

		bestResultLabel = new Label(Integer.toString(bestScore));
		bestResultLabel.getStyleClass().add("currentScore");
		bestResultLabel.setLayoutX(370);
		bestResultLabel.setLayoutY(167);

		currentNotationLabel = new Label();
		currentNotationLabel.getStyleClass().add("currentScore");
		currentNotationLabel.setLayoutX(370);
		currentNotationLabel.setLayoutY(250);

		quads = new Button[4][4];
		plates = new Plate[4][4];
		bestScore = 0;
		isWin = false;
		nClearPlates = 16;
		totalScore = 0;

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameManager.setScene("questionGameScene");
			}
		});

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				quads[i][j] = new Button();
				quads[i][j].getStyleClass().add("gameQuadButton");
				quads[i][j].setPrefSize(70, 70);
				quads[i][j].setLayoutX(55 + 67 * j);
				quads[i][j].setLayoutY(75 + 67 * i);
				gameRoot.getChildren().add(quads[i][j]);
			}

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				plates[i][j] = new Plate(0, i, j, false);
				gameRoot.getChildren().add(plates[i][j]);
			}

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if (!Menu.isItAutoMode()) {
					switch (key.getCode()) {
						case UP:
							if (moveUp()) {
								try {
									out.writeChar('u');
								} catch (IOException e) {
									e.printStackTrace();
								}
								createPlate();
							}
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							randHardMode();

							break;

						case DOWN:
							if (moveDown()) {
								try {
									out.writeChar('d');
								} catch (IOException e) {
									e.printStackTrace();
								}
								createPlate();
							}
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							randHardMode();

							break;

						case LEFT:
							if (moveLeft()) {
								try {
									out.writeChar('l');
								} catch (IOException e) {
									e.printStackTrace();
								}
								createPlate();
							}
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							randHardMode();

							break;

						case RIGHT:
							if (moveRight()) {
								try {
									out.writeChar('r');
								} catch (IOException e) {
									e.printStackTrace();
								}
								createPlate();
							}
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							randHardMode();

							break;

						default:
							break;
					}
				}

			}
		});

		gameRoot.getChildren().add(scoreLabel);
		gameRoot.getChildren().add(bestLabel);
		gameRoot.getChildren().add(currentNotationLabel);
		gameRoot.getChildren().add(notationLabel);
		gameRoot.getChildren().add(currentResultLabel);
		gameRoot.getChildren().add(bestResultLabel);
		gameRoot.getChildren().add(backButton);
	}

	public void run(File file) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				plates[i][j].setValue(0);
				plates[i][j].setVisible(false);
			}

		if (!Menu.isItSaveMode()) {
			newFile();
			try {
				notationLabel.setVisible(false);
				currentNotationLabel.setVisible(false);
				fileOutputStream = new FileOutputStream(this.file);
				out = new DataOutputStream(fileOutputStream);
				createPlate();
				if (Menu.isItAutoMode())
					autoModePlay();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				notationLabel.setVisible(true);
				currentNotationLabel.setVisible(true);
				fileInputStream = new FileInputStream(file);
				in = new DataInputStream(fileInputStream);
				saveModePlay();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createPlate() {
		nextValue = (random.nextInt(2) + 1) * 2;
		do
			position = random.nextInt(16);
		while (plates[position / 4][position % 4].isVisible());

		plates[position / 4][position % 4].setValue(nextValue);
		nClearPlates--;

		try {
			out.writeInt(nextValue);
			out.writeInt(position / 4);
			out.writeInt(position % 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void newFile() {
		Calendar time = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yy-HH.mm.ss");
		String str1 = "D:\\Saves\\Date-";
		String str2 = simpleDateFormat.format(time.getTime());
		String str3 = ".txt";
		file = new File(str1 + str2 + str3);
	}

	private boolean moveLeft() {
		int k;
		boolean isMoved = false;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++) {
				k = i + 1;

				while ((k < 4) && !(plates[k][j].isVisible()))
					k++;

				if (k < 4) {
					if (plates[i][j].isVisible()) {
						if (plates[i][j].getValue() == plates[k][j].getValue()) {
							int doubleValue;
							isMoved = true;

							doubleValue = plates[i][j].getValue() * 2;
							plates[i][j].setValue(doubleValue);
							plates[k][j].setValue(0);
							plates[k][j].setVisible(false);

							totalScore += doubleValue;
							currentResultLabel.setText(Integer.toString(totalScore));

							nClearPlates++;

							if (doubleValue == 2048 && !isWin) {
								GameManager.setScene("continueGameScene");
								isWin = true;
							}

						} else if (k != i + 1) {
							isMoved = true;

							plates[i + 1][j].setValue(plates[k][j].getValue());
							plates[k][j].setValue(0);
							plates[k][j].setVisible(false);
						}
					} else {
						isMoved = true;

						plates[i][j].setValue(plates[k][j].getValue());
						plates[k][j].setValue(0);
						plates[k][j].setVisible(false);

						j--;
					}
				}
			}

		return isMoved;
	}

	private boolean moveDown() {
		boolean isMoved;
		rotate("right");
		isMoved = moveLeft();
		rotate("left");
		return isMoved;
	}

	private boolean moveUp() {
		boolean isMoved;
		rotate("left");
		isMoved = moveLeft();
		rotate("right");
		return isMoved;
	}

	private boolean moveRight() {
		boolean isMoved;
		rotate("right");
		rotate("right");
		isMoved = moveLeft();
		rotate("left");
		rotate("left");
		return isMoved;
	}

	private void rotate(String leftOrRight) {
		Plate[][] temp;
		temp = new Plate[4][4];

		switch(leftOrRight)
		{
			case "left" :
				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++)
						temp[i][j] = plates[3-j][i];
				break;

			case "right" :
				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++)
						temp[i][j] = plates[j][3-i];
				break;
		}

		plates = temp;
	}

	private void endGame(){
		if (totalScore > bestScore)
			bestResultLabel.setText(currentResultLabel.getText());

		totalScore = 0;
		nClearPlates = 16;

		GameManager.setScene("endGameScene");
		isWin = false;

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
			{
				plates[i][j].setValue(0);
				plates[i][j].setVisible(false);
			}
	}

	private boolean congestion() {
		for (int i = 1; i < 3; i++)
			for (int j = 1; j < 3; j++)
				if ((plates[i][j].getValue() == plates[i+1][j].getValue()) ||
					(plates[i][j].getValue() == plates[i-1][j].getValue()) ||
					(plates[i][j].getValue() == plates[i][j+1].getValue()) ||
					(plates[i][j].getValue() == plates[i][j-1].getValue()))
					return false;

		for (int j = 1; j < 3; j++)
			if ((plates[0][j].getValue() == plates[0][j+1].getValue()) ||
				(plates[0][j].getValue() == plates[0][j-1].getValue()))
				return false;

		for (int j = 1; j < 3; j++)
			if ((plates[3][j].getValue() == plates[3][j+1].getValue()) ||
				(plates[3][j].getValue() == plates[3][j-1].getValue()))
				return false;

		for (int i = 1; i < 3; i++)
			if ((plates[i][0].getValue() == plates[i+1][0].getValue()) ||
				(plates[i][0].getValue() == plates[i-1][0].getValue()))
				return false;

		for (int i = 1; i < 3; i++)
			if ((plates[i][3].getValue() == plates[i+1][3].getValue()) ||
				(plates[i][3].getValue() == plates[i-1][3].getValue()))
				return false;

		return true;
	}

	private void randHardMode(){
		if(Menu.isItHardMode())
		{
			int randValue;
			randValue = random.nextInt(100);

			if (randValue < 5)
			{
				int randCase;
				randCase = random.nextInt(4);
				switch(randCase)
				{
					case 0 :
						if (moveUp()) {
							try {
								out.writeChar('\n');
								out.writeChar('u');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						break;

					case 1:
						if (moveDown()) {
							try {
								out.writeChar('\n');
								out.writeChar('d');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						break;

					case 2:
						if (moveLeft()) {
							try {
								out.writeChar('\n');
								out.writeChar('l');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						break;

					case 3:
						if (moveRight()) {
							try {
								out.writeChar('\n');
								out.writeChar('r');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						break;

					default :
						break;
				}
			}
		}
	}

	private void autoModePlay() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				int randDirection;
				randDirection = random.nextInt(4);

				switch (randDirection) {
					case 0 :
						if (moveUp()) {
							try {
								out.writeChar('u');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						randHardMode();

						break;

					case 1 :
						if (moveDown()) {
							try {
								out.writeChar('d');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						randHardMode();

						break;

					case 2 :
						if (moveLeft()) {
							try {
								out.writeChar('l');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						randHardMode();

						break;

					case 3 :
						if (moveRight()) {
							try {
								out.writeChar('r');
							} catch (IOException e) {
								e.printStackTrace();
							}
							createPlate();
						}
						if ((nClearPlates == 0) && (congestion()))
							endGame();
						randHardMode();

						break;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void saveModePlay() {
		try {
			value = in.readInt();
			positionX = in.readInt();
			positionY = in.readInt();
			plates[positionX][positionY].setValue(value);
			plates[positionX][positionY].setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String string;
		string = Integer.toString(value) + " " + Integer.toString(positionX + 1) + " " + Integer.toString(positionY + 1);
		currentNotationLabel.setText(string);

		AnimationTimer  timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				synchronized (this)
				{
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					switch (fileThread.getDirection()) {
						case 'u' :
							moveUp();
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							break;

						case 'd' :
							moveDown();
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							break;

						case 'l' :
							moveLeft();
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							break;

						case 'r' :
							moveRight();
							if ((nClearPlates == 0) && (congestion()))
								endGame();
							break;
					}

					plates[fileThread.getPositionX()][fileThread.getPositionY()].setValue(fileThread.getValue());
					plates[fileThread.getPositionX()][fileThread.getPositionY()].setVisible(true);

					String string;
					string = fileThread.getDirection() + "  " + Integer.toString(fileThread.getValue()) + "  " + Integer.toString(fileThread.getPositionX() + 1) + "  " + Integer.toString(fileThread.getPositionY() + 1);
					currentNotationLabel.setText(string);
				}
			}
		};

		fileThread = new FileThread(timer, in);
		fileThread.start();
		timer.start();
	}

	public Scene getScene()
	{
		return gameScene;
	}
}
