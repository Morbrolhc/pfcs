package ch.fhnw.launcher;

import ch.fhnw.uebung01.MainTrack;
import ch.fhnw.uebung02.MainCar;
import ch.fhnw.uebung03.MainStorm;
import ch.fhnw.uebung04.MainBoomerang;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by joel on 28.10.15.
 */
public class Main extends JFrame {

    final private static String UEBUNG01 = "Uebung 01 - LBahn";
    final private static String UEBUNG02 = "Uebung 02 - Car";
    final private static String UEBUNG03 = "Uebung 03 - Storm";
    final private static String UEBUNG04 = "Uebung 04 - Boomerang";

    String[] options = new String[] {UEBUNG01, UEBUNG02, UEBUNG03, UEBUNG04};


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());
        drawWindow();
    }

    private void drawWindow() {
        setTitle("pfcs - Launcher");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLocation(500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        GridBagConstraints constraints = new GridBagConstraints();

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 10;
        constraints.weightx = 0;
        mainPanel.add(leftPanel, constraints);
        constraints.ipadx = 0;
        constraints.weightx = 1;
        constraints.gridx = 1;
        mainPanel.add(centerPanel, constraints);

        ImagePanel imgPanel = new ImagePanel("logo.png");
        leftPanel.add(imgPanel, BorderLayout.CENTER);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 5, 0);
        centerPanel.add(new JLabel("Select application to run."), constraints);

        JComboBox<String> comboBox = new JComboBox<>(options);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        centerPanel.add(comboBox, constraints);

        JButton button = new JButton("Select");
        button.addActionListener(l -> {
            button.setEnabled(false);
            String result = (String)comboBox.getSelectedItem();
            if( result == null ) System.exit(0);
            switch (result) {
                case UEBUNG01:
                    new MainTrack();
                    break;
                case UEBUNG02:
                    new MainCar();
                    break;
                case UEBUNG03:
                    new MainStorm();
                    break;
                case UEBUNG04:
                    new MainBoomerang();
                    break;
            }
            dispose();
        });
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        centerPanel.add(button, constraints);

        setSize(270, 140);
        setResizable(false);
        setVisible(true);
    }
}
