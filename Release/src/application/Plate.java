package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Plate extends Button
{
	private int value;

	public Plate(int value, int positionX, int positionY, boolean visibility)
	{
		super(Integer.toString(value));
		this.value = value;
		this.getStyleClass().add("gamePlateButton");
		this.setDisable(true);
		this.setVisible(visibility);
		this.setLayoutX(61 + positionX * 67);
		this.setLayoutY(81 + positionY * 67);
		this.setPrefSize(57, 57);
	}

	public void setValue(int value)
	{
		this.value = value;

		switch (value)
		{
			case 2:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;
			case 4:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#87CEFA"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;

			case 8:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#DDA0DD"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;

			case 16:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#9ACD32"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;
			case 32:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D3D3D3"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;

			case 64:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#EEE8AA"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 20));
				break;

			case 128:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#A9A9A9"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 18));
				break;

			case 256:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFE4E1"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 17));
				break;

			case 512:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F4A460"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 17));
				break;

			case 1024:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFD700"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 13));
				break;

			case 2048:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FF1493"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 13));
				break;

			case 4096:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#6495ED"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 13));
				break;

			case 8192:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#6495ED"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 13));
				break;
			case 16384:
				this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFAF0"), CornerRadii.EMPTY, Insets.EMPTY)));
				this.setFont(new Font("Impact", 10));
				break;
		}

		this.setText(Integer.toString(value));
		this.setVisible(true);
	}

	public int getValue()
	{
		return value;
	}
}
