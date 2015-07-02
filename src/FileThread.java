package application;


import javafx.animation.AnimationTimer;

import java.io.DataInputStream;
import java.io.IOException;


public class FileThread extends Thread {
    private final DataInputStream in_;
    private int value;
    private int positionX;
    private int positionY;
    private char direction;
    private final AnimationTimer monitor_;

    public FileThread(AnimationTimer monitor, DataInputStream in) {
        monitor_ = monitor;
        in_ = in;
    }

    @Override
    public void run()
    {
        while(true) {
            try {
                direction = in_.readChar();
                value = in_.readInt();
                positionX = in_.readInt();
                positionY = in_.readInt();
            } catch (IOException e) {
                break;
            }
            synchronized (monitor_) {
                monitor_.notifyAll();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public final int getValue()
    {
        return value;
    }

    public final int getPositionX()
    {
        return positionX;
    }

    public final int getPositionY()
    {
        return positionY;
    }

    public final char getDirection()
    {
        return direction;
    }
}
