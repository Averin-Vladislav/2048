package application;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.ArrayList;


public class FileInfo {
    private String fileName;
    private Long fileSize;

    public FileInfo(File file)
    {
        fileName = file.getName();
        fileSize = file.length();
    }

    public static ArrayList<TableColumn<FileInfo, ?>> getColumn(TableView tableView)
    {
        int i;
        ArrayList<TableColumn<FileInfo, ?>> columns = new ArrayList<TableColumn<FileInfo, ?>>();
        String[] columnNames = {"Name", "Size"};
        String[] variableNames = {"fileName", "fileSize"};
        Integer[] columnWidth = {50, 50};
        
        i = 0;
        TableColumn<FileInfo, String> nameColumn = new TableColumn<>(columnNames[i++]);
        TableColumn<FileInfo, Integer> sizeColumn = new TableColumn<>(columnNames[i++]);

        i = 0;
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(100 / columnWidth[i++]));
        sizeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(100 / columnWidth[i++]));

        i = 0;
        nameColumn.setCellValueFactory(new PropertyValueFactory<FileInfo, String>(variableNames[i++]));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<FileInfo, Integer>(variableNames[i++]));

        columns.add(nameColumn);
        columns.add(sizeColumn);

        return columns;
    }

    public String getFileName()
    {
        return fileName;
    }

    public Long getFileSize()
    {
        return fileSize;
    }
}
