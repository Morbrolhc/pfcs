package ch.fhnw.launcher;

import ch.fhnw.uebung01.MainTrack;
import ch.fhnw.uebung02.MainCar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joel on 28.10.15.
 */
public class Main extends JFrame {

    final private static String UEBUNG01 = "Uebung 01 - LBahn";
    final private static String UEBUNG02 = "Uebung 02 - Car";

    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        JFrame frame = new JFrame();
        frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());


        String[] options = new String[] {UEBUNG01, UEBUNG02};

        Icon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));

        String result = (String) JOptionPane.showInputDialog (
                frame,
                "Select Program to run.",
                "pfcs - Launcher",
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options, UEBUNG01);

        if( result == null ) System.exit(0);
        switch (result) {
            case UEBUNG01:
                new MainTrack();
                break;
            case UEBUNG02:
                new MainCar();
                break;
        }
    }
}
