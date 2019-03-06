package brickBreaker;

import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String args[]){
        JFrame frame = new JFrame("BrickBreaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        BrickBreaker game = new BrickBreaker(1280, 720);
        frame.add(game, BorderLayout.CENTER);

        frame.setVisible(true);


    }
}
