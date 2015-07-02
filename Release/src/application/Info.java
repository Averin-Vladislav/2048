package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Info
{
    private Group statisticsRoot;
    private Scene statisticsScene;

    private Button backButton;
    private Button javaSortButton;
    private Button scalaSortButton;
    private Button statisticsButton;
    private Button decodeNonationButton;
    private Label javaResultLabel;
    private Label scalaResultLabel;
    private Label statisticsLabel;

    private ArrayList<Integer> position;
    private ArrayList<Character> direction;

    private TextArea notationArea;
    private TableView<FileInfo> tableView;
    private File[] filesStatistics;
    private Map<Long, File> replayMap;
    private File file;

    public Info(Stage primaryStage)
    {
        statisticsRoot = new Group();
        statisticsScene = new Scene(statisticsRoot, 600, 400, Color.LIGHTGREEN);
        statisticsScene.getStylesheets().add("application/application.css");

        backButton = new Button("Back to menu");
        backButton.getStyleClass().add("menuButton");
        backButton.setLayoutX(215);
        backButton.setLayoutY(350);
        backButton.setPrefSize(150, 40);

        decodeNonationButton = new Button("Notation");
        decodeNonationButton.getStyleClass().add("menuButton");
        decodeNonationButton.setLayoutX(420);
        decodeNonationButton.setLayoutY(220);
        decodeNonationButton.setPrefSize(120, 40);

        javaSortButton = new Button("JavaSort");
        javaSortButton.getStyleClass().add("menuButton");
        javaSortButton.setLayoutX(60);
        javaSortButton.setLayoutY(270);
        javaSortButton.setPrefSize(120, 40);

        scalaSortButton = new Button("ScalaSort");
        scalaSortButton.getStyleClass().add("menuButton");
        scalaSortButton.setLayoutX(230);
        scalaSortButton.setLayoutY(270);
        scalaSortButton.setPrefSize(120, 40);

        statisticsButton = new Button("Update");
        statisticsButton.getStyleClass().add("menuButton");
        statisticsButton.setLayoutX(420);
        statisticsButton.setLayoutY(350);
        statisticsButton.setPrefSize(120, 40);

        javaResultLabel = new Label();
        javaResultLabel.setText("000");
        javaResultLabel.getStyleClass().add("settingsInfLabel");
        javaResultLabel.setLayoutX(110);
        javaResultLabel.setLayoutY(320);

        scalaResultLabel = new Label();
        scalaResultLabel.setText("000");
        scalaResultLabel.getStyleClass().add("settingsInfLabel");
        scalaResultLabel.setLayoutX(275);
        scalaResultLabel.setLayoutY(320);

        statisticsLabel = new Label();
        statisticsLabel.setText("   Statistics\n Position: " + position + "\nDirection: " + direction);
        statisticsLabel.getStyleClass().add("settingsInfLabel");
        statisticsLabel.setLayoutX(425);
        statisticsLabel.setLayoutY(270);

        notationArea = new TextArea();
        notationArea.setEditable(false);
        notationArea.setLayoutX(430);
        notationArea.setLayoutY(10);
        notationArea.setPrefSize(100, 200);

        tableView = new TableView<FileInfo>();
        tableView.getColumns().addAll(FileInfo.getColumn(tableView));
        tableView.setItems(setFileInfoTable());
        tableView.setPrefHeight(250);
        tableView.setPrefWidth(310);
        tableView.setLayoutX(50);
        tableView.setLayoutY(10);

        position = new ArrayList<>();
        direction = new ArrayList<>();
       
        decodeNonationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<String> notationList;
                String resultString = new String();
                FileInputStream fileInputStream;
                DataInputStream in;
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Game Session File");
                file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    try {
                        fileInputStream = new FileInputStream(file);
                        in = new DataInputStream(fileInputStream);
                        notationList = createNotationList(in);
                        resultString = DecodingFile.Decoding(notationList, resultString);

                        notationArea.setText(resultString);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileInputStream fileInputStream;
                DataInputStream in;
                Integer resultPosition;
                Character resultDirection;
                File folder = new File("D://Saves");
                filesStatistics = folder.listFiles();

                for (int i = 0; i < filesStatistics.length; i++) {
                    try {
                        fileInputStream = new FileInputStream(filesStatistics[i]);
                        in = new DataInputStream(fileInputStream);
                        statisticsParsing(in);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                resultDirection = Statistics.FindStatisticsElement(direction);
                resultPosition = Statistics.FindStatisticsElement(position);

                statisticsLabel.setText("   Statistics\n Position: " +
                        (resultPosition / 4 + 1) +
                        (resultPosition % 4 + 1) +
                        "\nDirection: " +
                        resultDirection);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameManager.setScene("menuScene");
            }
        });

         javaSortButton.setOnAction((new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 ArrayList<Long> fileList = new ArrayList<Long>();
                 for(Map.Entry entry: replayMap.entrySet()) {
                     fileList.add((Long)entry.getKey());
                 }
                 Long time = System.currentTimeMillis();
                 fileList = javaSort(fileList);
                 time = System.currentTimeMillis() - time;
                 javaResultLabel.setText(time.toString());
                 showJavaSortedArray(fileList);
             }
         }));

        scalaSortButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Integer> fileList = new ArrayList<Integer>();
                for (Map.Entry entry : replayMap.entrySet()) {
                    fileList.add(Integer.parseInt(Long.toString((Long) entry.getKey())));
                }

                List<Integer> fileList1 = new ArrayList<Integer>();
                Long time = System.currentTimeMillis();
                fileList1 = SelectSort.MakeSort(fileList);
                time = System.currentTimeMillis() - time;
                scalaResultLabel.setText(time.toString());
                showScalaSortedArray(fileList1);
            }
        }));

        statisticsRoot.getChildren().add(decodeNonationButton);
        statisticsRoot.getChildren().add(notationArea);
        statisticsRoot.getChildren().add(backButton);
        statisticsRoot.getChildren().add(javaSortButton);
        statisticsRoot.getChildren().add(scalaSortButton);
        statisticsRoot.getChildren().add(statisticsButton);
        statisticsRoot.getChildren().add(statisticsLabel);
        statisticsRoot.getChildren().add(scalaResultLabel);
        statisticsRoot.getChildren().add(javaResultLabel);
        statisticsRoot.getChildren().add(tableView);
    }

    private ArrayList<String> createNotationList(DataInputStream in) {
        ArrayList<String> notationList = new ArrayList<>();

        try {
            String string = new String();
            string +=  "-";
            string += Integer.toString(in.readInt());
            string += Integer.toString(in.readInt());
            string += Integer.toString(in.readInt());
            notationList.add(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String string = new String();
                Character direction = in.readChar();
                string += Character.toString(direction);
                string += Integer.toString(in.readInt());
                string += Integer.toString(in.readInt());
                string += Integer.toString(in.readInt());
                notationList.add(string);
            } catch (IOException e) {
                break;
            }
        }

        return notationList;
    }

    private ObservableList<FileInfo> setFileInfoTable() {
        ObservableList<FileInfo> data = FXCollections.observableArrayList();
        File myFolder = new File("D://Saves");
        File[] files = myFolder.listFiles();

        replayMap = new HashMap<Long, File>();
        for (int i = 0 ; i < files.length; i++)
        {
            data.add(new FileInfo(files[i]));
            replayMap.put(files[i].length(), files[i]);
        }

        return data;
    }

    private ArrayList<Long> javaSort(ArrayList<Long> fileList) {
        int max;
        for (int i = 0; i < fileList.size() - 1; i++)
        {
            max = i;
            for (int j = i + 1; j < fileList.size(); j++)
                if (fileList.get(j) > fileList.get(max))
                    max = j;
            if (max != i)
            {
                Long temp = fileList.get(max);
                fileList.set(max, fileList.get(i));
                fileList.set(i, temp);
            }
        }

        return fileList;
    }

    private void showJavaSortedArray(ArrayList<Long> fileList) {
        ObservableList<FileInfo> data = FXCollections.observableArrayList();
        tableView.setItems(null);
        for (int i = 0; i < fileList.size(); i++) {
            data.add(new FileInfo(replayMap.get(fileList.get(i))));
            tableView.setItems(data);
        }
    }

    private void showScalaSortedArray(List<Integer> fileList) {
        ObservableList<FileInfo> data = FXCollections.observableArrayList();
        tableView.setItems(null);
        for (int i = 0; i < fileList.size(); i++) {
            data.add(new FileInfo(replayMap.get(new Long(fileList.get(i)))));
            tableView.setItems(data);
        }
    }

    private void statisticsParsing(DataInputStream in) {
        try {
            in.readInt();
            position.add(in.readInt() * 4 + in.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                direction.add(in.readChar());
                in.readInt();
                position.add(in.readInt() * 4 + in.readInt());
            } catch (IOException e) {
                break;
            }
        }
    }

    public Scene getScene()
    {
        return statisticsScene;
    }
}
